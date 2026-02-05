package uce.edu.web.api.auth.interfaces;

import java.time.Instant;
import java.util.Set;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject; // Necesario para inyectar el servicio
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
// Importamos dominio y servicio
import uce.edu.web.api.auth.application.UsuarioService;
import uce.edu.web.api.auth.domain.Usuario;

@Path("/auth") // Ajustar el path base si es necesario (ej: /api/v1)
public class AuthResource {

    // 1. INYECTAMOS TU SERVICIO DE USUARIOS
    @Inject
    UsuarioService usuarioService;

    @GET
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    public TokenResponse token(
            @QueryParam("user") String user,
            @QueryParam("password") String password) {

        // 2. LÓGICA DE VALIDACIÓN CONTRA LA BASE DE DATOS
        boolean ok = false;
        String role = "user"; // Rol por defecto o vacío

        // Buscamos el usuario en la DB
        Usuario usuarioEncontrado = usuarioService.buscarPorNombre(user);

        // Si existe y la contraseña coincide (texto plano)
        if (usuarioEncontrado != null && usuarioEncontrado.password.equals(password)) {
            ok = true;
            role = usuarioEncontrado.rol; // Usamos el rol real de la DB
        }

        // 3. GENERACIÓN DEL TOKEN 
        if (ok) {
            String issuer = "matricula-auth";
            long ttl = 3600;

            Instant now = Instant.now();
            Instant exp = now.plusSeconds(ttl);

            String jwt = Jwt.issuer(issuer)
                    .subject(user)
                    .groups(Set.of(role))     // roles: user / admin
                    .issuedAt(now)
                    .expiresAt(exp)
                    .sign();

            return new TokenResponse(jwt, exp.getEpochSecond(), role);
        } else {
            return null; // O podríamos lanzar una WebApplicationException(401)
        }
    }

    // 4. CLASE INTERNA (Se mantiene intacta)
    public static class TokenResponse {
        public String accessToken;
        public long expiresAt;
        public String role;

        public TokenResponse() {}
        public TokenResponse(String accessToken, long expiresAt, String role) {
            this.accessToken = accessToken;
            this.expiresAt = expiresAt;
            this.role = role;
        }
    }
}

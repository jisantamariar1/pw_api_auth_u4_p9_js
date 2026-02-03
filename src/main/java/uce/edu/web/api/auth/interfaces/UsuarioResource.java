package uce.edu.web.api.auth.interfaces;

import java.util.List;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import uce.edu.web.api.auth.application.UsuarioService;
import uce.edu.web.api.auth.application.representation.UsuarioRepresentation;
import uce.edu.web.api.auth.domain.Usuario;

@Path("/usuarios")
public class UsuarioResource {

    @Inject
    private UsuarioService usuarioService;

    @GET
    @Produces(MediaType.APPLICATION_JSON) // Devuelve JSON
    public List<UsuarioRepresentation> listarTodos() {
        return this.usuarioService.listarTodos();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UsuarioRepresentation consultarPorId(@PathParam("id") Integer id) {
        return this.usuarioService.consultarPorId(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON) // Recibe JSON
    @Produces(MediaType.APPLICATION_JSON) // Devuelve JSON (el objeto creado)
    public Response guardar(Usuario usu) {
        this.usuarioService.crear(usu);
        return Response.status(Response.Status.CREATED).entity(usu).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar(@PathParam("id") Integer id, Usuario usu) {
        this.usuarioService.actualizar(id, usu);
        // Usando el status 209 que manejas en el Resource Server
        return Response.status(209).entity(null).build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void actualizarParcial(@PathParam("id") Integer id, Usuario usu) {
        this.usuarioService.actualizarParcial(id, usu);
    }

    @DELETE
    @Path("/{id}")
    public void borrar(@PathParam("id") Integer id) {
        this.usuarioService.eliminar(id);
    }
}
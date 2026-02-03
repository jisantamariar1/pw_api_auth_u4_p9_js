package uce.edu.web.api.auth.application;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.web.api.auth.application.representation.UsuarioRepresentation;
import uce.edu.web.api.auth.domain.Usuario;
import uce.edu.web.api.auth.infraestructure.UsuarioRepository;

@ApplicationScoped
@Transactional
public class UsuarioService {
    @Inject
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void crear(Usuario usuario) {
        //// Aquí recibimos la ENTIDAD directamente para que traiga el password
        usuarioRepository.persist(usuario);
    }

    public List<UsuarioRepresentation> listarTodos() {
        List<Usuario> lista = usuarioRepository.listAll();
        List<UsuarioRepresentation> listaR = new ArrayList<>();

        for (Usuario u : lista) {
            listaR.add(this.mapperToUR(u));
        }
        return listaR;
    }
    public UsuarioRepresentation consultarPorId(Integer id) {
        Usuario usu = this.usuarioRepository.findById(id.longValue());
        return this.mapperToUR(usu);
    }
    @Transactional
    public void actualizar(Integer id, Usuario usuDatosNuevos) {
        Usuario usuExistente = usuarioRepository.findById(id.longValue());
        if (usuExistente != null) {
            usuExistente.nombre = usuDatosNuevos.nombre;
            usuExistente.rol = usuDatosNuevos.rol;
            usuExistente.password = usuDatosNuevos.password; // Actualiza clave
        }
    }

    @Transactional
    public void actualizarParcial(Integer id, Usuario usuDatosNuevos) {
        Usuario usuExistente = usuarioRepository.findById(id.longValue());
        if (usuExistente != null) {
            if (usuDatosNuevos.nombre != null) usuExistente.nombre = usuDatosNuevos.nombre;
            if (usuDatosNuevos.rol != null) usuExistente.rol = usuDatosNuevos.rol;
            if (usuDatosNuevos.password != null) usuExistente.password = usuDatosNuevos.password;
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id.longValue());
    }
    

    public Usuario buscarPorNombre(String nombre) {
        // Este lo usará el AuthService para validar la clave
        return usuarioRepository.find("nombre", nombre).firstResult();
    }

    private UsuarioRepresentation mapperToUR(Usuario user) {
        UsuarioRepresentation userR = new UsuarioRepresentation();
        userR.id = user.id;
        userR.nombre = user.nombre;
        userR.rol = user.rol;
        // Jamás mapeamos el password aquí
        return userR;
    }

    private Usuario mapperToUsuario(UsuarioRepresentation userR) {
        Usuario user = new Usuario();
        user.id = userR.id;
        user.nombre = userR.nombre;
        user.rol = userR.rol;
        // Nota: El password quedaría nulo si userR no lo tiene.
        // Para crear un usuario por primera vez, deberás setear el password
        // manualmente.
        return user;
    }

}

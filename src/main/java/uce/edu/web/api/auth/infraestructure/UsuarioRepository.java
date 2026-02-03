package uce.edu.web.api.auth.infraestructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import uce.edu.web.api.auth.domain.Usuario;

// Al implementar PanacheRepository, el CRUD básico ya está listo
@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {
    
    // Aquí podrías añadir métodos específicos si fuera necesario,
    // pero para el taller, con lo que hereda es suficiente.
}
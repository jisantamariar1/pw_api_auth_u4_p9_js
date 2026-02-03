package uce.edu.web.api.auth.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Table(name = "Usuario") // Nombre de la tabla en la DB pweb_auth
@Entity
@SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_secuencia", allocationSize = 1)
public class Usuario extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    public Integer id; // Columna 1: Clave Primaria (PK)

    public String nombre; // Columna 2: Nombre de usuario

    public String rol;    // Columna 3: Rol (ej. "admin", "user")

    public String password; // Columna 4: Contraseña (texto plano por ahora)

    // Constructor vacío obligatorio para JPA
    public Usuario() {}
}
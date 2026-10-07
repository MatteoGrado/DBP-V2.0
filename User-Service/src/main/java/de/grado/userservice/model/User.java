package de.grado.userservice.model;

import de.grado.userservice.dto.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigInteger;

@Entity
@Table(name = "users")
@Data
public class User
{
    @Id
    private BigInteger id;

    private String firstName;
    private String lastName;

    private String email;
    private String username;
    private String password;

    private Role role;
}

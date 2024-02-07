package edu.iastate.coms309.flatfinder.users.admin;

import edu.iastate.coms309.flatfinder.users.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admin")
@Getter
@Setter
public class Admin extends User {
    @Column(name = "admin_permissions")
    private String adminPermissions;
}
package uk.co.nhs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @Column(name = "username", nullable = false)
    @NotEmpty(message = "Please provide an username")
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Please provide a valid e-mail")
    @NotEmpty(message = "Please provide an e-mail")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "firstName")
    @NotEmpty(message = "Please provide your first name")
    private String firstName;

    @Column(name = "lastName")
    @NotEmpty(message = "Please provide your last name")
    private String lastName;

    @Column(name = "enabled")
    private boolean enabled;

    @JsonIgnore
    @Column(name = "resetToken")
    private String resetToken;

    @JsonIgnore
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdOn", nullable = false, updatable = false)
    private Date createdOn;

    @JsonIgnore
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedOn")
    private Date updatedOn;

    @JsonIgnore
    public String getUserFullName(){
        return this.firstName +" "+ this.lastName;
    }
}
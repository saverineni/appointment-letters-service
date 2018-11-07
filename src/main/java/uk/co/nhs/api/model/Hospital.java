package uk.co.nhs.api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hospital")
@Data
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id", nullable = false, updatable = false)
    private long hospitalId;

    @Column(name = "hospital_name", nullable = false)
    private String hospitalName;


    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "user_hospital", joinColumns = { @JoinColumn(name = "hospital_id", nullable = true) },
            inverseJoinColumns = { @JoinColumn(name = "user_id", nullable = true, referencedColumnName = "user_id") })
    private Set<User> users = new HashSet<>();

    public void addUser(User user) {
        users.add(user);
    }

}
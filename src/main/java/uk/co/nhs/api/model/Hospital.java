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
    private long hospital_id;

    @Column(name = "hospital_name", nullable = false)
    private String hospitalName;

    @ManyToMany(mappedBy = "hospitals")
    private Set<User> users = new HashSet<>();

}
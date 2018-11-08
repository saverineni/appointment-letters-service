package uk.co.nhs.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "appointment")
@Data
@EqualsAndHashCode(exclude = "hospital")
@ToString(exclude = "hospital")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @Column(name = "dateOfAppointment")
    private LocalDate dateOfAppointment;;

    @Column(name = "timeOfAppointment")
    private String timeOfAppointment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "hospital_id",referencedColumnName ="id")
    @JsonIgnore
    private Hospital hospital;

}
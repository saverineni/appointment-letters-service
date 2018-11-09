package uk.co.nhs.api.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.nhs.api.exception.ResourceNotFoundException;
import uk.co.nhs.api.model.Appointment;
import uk.co.nhs.api.model.Hospital;
import uk.co.nhs.api.model.User;
import uk.co.nhs.api.responses.AppointmentsResponse;
import uk.co.nhs.repository.AppoinmentsRepository;
import uk.co.nhs.repository.UsersRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@RestController
@Slf4j
public class AppointmentResource {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private AppoinmentsRepository appoinmentsRepository;

    @PutMapping("/user/{userId}/appointments")
    public ResponseEntity<?> createAppointments(@PathVariable("userId") final Long userId) {
       return  usersRepository.findById(userId)
                .map(user ->
                {
                           createAppointments(user);
                           return new ResponseEntity<>(HttpStatus.CREATED);

                }).orElseThrow(() -> new ResourceNotFoundException(userId));
    }

    @GetMapping("/user/{userId}/appointments")
    public ResponseEntity<?> getAppointments(@PathVariable("userId") final Long userId) {
        return  usersRepository.findById(userId)
                .map(user ->
                {
                    AppointmentsResponse response = new AppointmentsResponse();
                    response.setHospitals(user.getHospitals());
                    return new ResponseEntity<>(response, HttpStatus.CREATED);

                }).orElseThrow(() -> new ResourceNotFoundException(userId));
    }

    private void createAppointments(User user) {
        Set<Hospital> userHospitals = new HashSet<>(user.getHospitals());
        for(Hospital hospital : userHospitals) {
            for(int i=1; i<=2; i++){
                Appointment appointment = new Appointment();
                appointment.setDateOfAppointment(createRandomDate());
                appointment.setTimeOfAppointment("10:30");
                appointment.setHospital(hospital);
                appoinmentsRepository.save(appointment);
            }
        }

    }

    public static int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public static LocalDate createRandomDate() {
        int day = createRandomIntBetween(1, 28);
        int month = createRandomIntBetween(1, 12);
        return LocalDate.of(2019, month, day);
    }
}

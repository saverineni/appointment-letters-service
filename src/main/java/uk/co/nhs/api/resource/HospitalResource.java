package uk.co.nhs.api.resource;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.nhs.api.dto.HospitalCreationRequest;
import uk.co.nhs.api.dto.HospitalUpdateRequest;
import uk.co.nhs.api.exception.ResourceNotFoundException;
import uk.co.nhs.api.model.Hospital;
import uk.co.nhs.api.model.User;
import uk.co.nhs.api.responses.HospitalResponse;
import uk.co.nhs.repository.HospitalsRepository;
import uk.co.nhs.repository.UsersRepository;

import java.util.HashSet;
import java.util.Set;

@RestController
@Slf4j
public class HospitalResource {

    @Autowired
    private HospitalsRepository hospitalsRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<?> getHospital(@PathVariable("hospitalId") final Long hospitalId) {
        return hospitalsRepository.findById(hospitalId)
                .map(hospital -> new ResponseEntity<>(convertToEntity(hospital), HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException(hospitalId));
    }

    @PostMapping("/user/{userId}/hospital")
    public ResponseEntity<?> createHospital(@PathVariable("userId") final Long userId,
                                            @RequestBody HospitalCreationRequest hospitalCreationRequest) {
       return  usersRepository.findById(userId)
                .map(user -> {
                    Hospital hospital = convertToEntity(hospitalCreationRequest);
                    hospital.addUser(user);
                    hospitalsRepository.save(hospital);
                    return new ResponseEntity<>("{\"hospitalId\" :\"" + hospital.getHospitalId() + "\"}", HttpStatus.CREATED);
                }).orElseThrow(() -> new ResourceNotFoundException(userId));
    }

    @PutMapping("/user/hospital/{hospitalId}")
    public ResponseEntity<?> updateHospital(@PathVariable("hospitalId") final Long hospitalId, @RequestBody HospitalUpdateRequest hospitalUpdateRequest) {
        return  hospitalsRepository.findById(hospitalId)
                .map( existingHospital -> {
                    existingHospital.setHospitalName(hospitalUpdateRequest.getHospitalName());
                    hospitalsRepository.save(existingHospital);
                    return new ResponseEntity<>(HttpStatus.OK);
        })
        .orElseThrow(() -> new ResourceNotFoundException(hospitalId));
    }

    private HospitalResponse convertToEntity(Hospital hospital) {
        return modelMapper.map(hospital, HospitalResponse.class);
    }

    private Hospital convertToEntity(HospitalCreationRequest hospitalCreationRequest) {
        return  modelMapper.map(hospitalCreationRequest, Hospital.class);
    }
}

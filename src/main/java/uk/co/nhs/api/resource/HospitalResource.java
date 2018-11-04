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
import uk.co.nhs.api.responses.HospitalResponse;
import uk.co.nhs.repository.HospitalsRepository;

@RestController
@Slf4j
public class HospitalResource {

    @Autowired
    private HospitalsRepository hospitalsRepository;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/hospital/{hospital_id}")
    public ResponseEntity<?> getHospital(@PathVariable("hospitalId") final Long hospitalId) {
        return hospitalsRepository.findById(hospitalId)
                .map(hospital -> new ResponseEntity<>(convertToEntity(hospital), HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException(hospitalId));
    }

    @PostMapping("/hospital")
    public ResponseEntity<?> createHospital(@RequestBody HospitalCreationRequest hospitalCreationRequest) {
        Hospital hospital= convertToEntity(hospitalCreationRequest);
        Hospital save = hospitalsRepository.save(hospital);
        return new ResponseEntity<>("{\"hospital_id\" :\""+hospital.getHospital_id()+"\"}",HttpStatus.CREATED);
    }

    @PutMapping("/hospital/{hospital_id}")
    @ResponseStatus(HttpStatus.OK)
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

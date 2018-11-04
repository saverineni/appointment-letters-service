package uk.co.nhs.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HospitalCreationRequest {
    private long userId;
    private String hospitalName;
}

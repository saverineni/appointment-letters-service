package uk.co.nhs.api.responses;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class HospitalResponse {
    private long id;
    private String hospitalName;
}

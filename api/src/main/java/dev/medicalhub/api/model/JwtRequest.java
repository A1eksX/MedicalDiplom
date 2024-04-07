package dev.medicalhub.api.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class JwtRequest {

    private long diplomaNumber;
    private String password;

}

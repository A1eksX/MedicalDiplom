package dev.medicalhub.api.model;

import lombok.*;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ReceptionModel extends Reception {
    private long patientId;
    private long doctorDiplomaNumber;

}

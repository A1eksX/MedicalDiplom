package dev.medicalhub.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class Disease {
    private long id;
    private String diagnosis;
    private ZonedDateTime treatmentStart;
    private ZonedDateTime treatmentEnd;
    private String rezeptura;
}

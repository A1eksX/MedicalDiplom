package dev.medicalhub.api.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DiseaseWithProceduresModel extends DiseaseWithProcedures {
    private long medicalBookId;
}

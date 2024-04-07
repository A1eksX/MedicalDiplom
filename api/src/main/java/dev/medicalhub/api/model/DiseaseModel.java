package dev.medicalhub.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DiseaseModel extends Disease{
    private long medicalBookId;
}

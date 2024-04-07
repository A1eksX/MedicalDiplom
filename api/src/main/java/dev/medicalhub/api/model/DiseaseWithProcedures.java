package dev.medicalhub.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DiseaseWithProcedures extends Disease {
    private List<Procedure> procedures;
}

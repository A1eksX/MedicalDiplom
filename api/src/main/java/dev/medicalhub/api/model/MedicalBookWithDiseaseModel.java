package dev.medicalhub.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MedicalBookWithDiseaseModel extends MedicalBook {

    private Long patientId;
    private List<DiseaseWithProcedures> historyDisease;
}

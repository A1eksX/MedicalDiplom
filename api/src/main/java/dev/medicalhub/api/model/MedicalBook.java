package dev.medicalhub.api.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MedicalBook {

    private Long id;
    private String allergy;
    private String graftCertificate;
    private int groupBlood;
    private boolean RhFactor;
}

package dev.medicalhub.api.entity;

import dev.medicalhub.api.model.MedicalInstitutionModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "medical_institution")
public class MedicalInstitutionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    @OneToMany
    private List<DoctorEntity> doctors;

    public MedicalInstitutionEntity(Long id) {
        this.id = id;
    }

    public MedicalInstitutionModel toDTO() {
        return new MedicalInstitutionModel(id,name,address);
    }
}

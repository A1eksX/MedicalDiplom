package dev.medicalhub.api.entity;

import dev.medicalhub.api.model.SpecializationModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "specialization")
public class SpecializationEntity {
    public SpecializationEntity(String name) {
        this.name = name;
    }

    @Id
    private String name;
    @OneToMany
    private List<DoctorEntity> doctor;

    public SpecializationModel toDTOModel(){
        SpecializationModel specializationModel = new SpecializationModel();
        specializationModel.setName(this.name);
        return specializationModel;
    }
}

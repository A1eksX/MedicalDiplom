package dev.medicalhub.api.entity;

import dev.medicalhub.api.model.Procedure;
import dev.medicalhub.api.model.ProcedureModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "procedure_entity")
public class ProcedureEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String drugs;
    private String name;
    @ManyToOne()
    @JoinColumn(name = "disease_id")
    private DiseaseEntity disease;
    @Column(name = "count_drugs")
    private int count;

    public Procedure toDTO(){
        return new Procedure()
                .setId(id)
                .setDrugs(drugs)
                .setName(name)
                .setCount(count);
    }
    public ProcedureModel toDTOModel(){
        return (ProcedureModel) new ProcedureModel()
                .setDisease_id(disease.getId())
                .setId(id)
                .setName(name)
                .setDrugs(drugs)
                .setCount(count);
    }
}

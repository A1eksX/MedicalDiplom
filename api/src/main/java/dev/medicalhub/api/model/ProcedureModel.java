package dev.medicalhub.api.model;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ProcedureModel extends Procedure {

    private long disease_id;
}

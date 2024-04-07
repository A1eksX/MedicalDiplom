package dev.medicalhub.api.model;

import dev.medicalhub.api.entity.StatusReception;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ReceptionHistoryModel extends Reception {
    private StatusReception status;
    private long patientId;
    private String fullName;
}

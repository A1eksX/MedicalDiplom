package dev.medicalhub.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Procedure {

    private long id;
    private String drugs;
    private String name;
    private int count;
}


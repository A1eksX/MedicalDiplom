package dev.medicalhub.api.model;

import dev.medicalhub.api.entity.StatusReception;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

@Data
@Accessors(chain = true)
public class Reception {

    private long id;
    private ZonedDateTime dateTime;
    private  String data;
}

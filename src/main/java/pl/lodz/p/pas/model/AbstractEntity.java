package pl.lodz.p.pas.model;

import java.io.Serializable;
import java.util.UUID;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;


@MappedSuperclass
@EqualsAndHashCode(of = "uuid")
public class AbstractEntity implements Serializable {

    @NotNull
    private String uuid = UUID.randomUUID().toString();

    @Version
    @JsonbTransient
    private Long version;

}
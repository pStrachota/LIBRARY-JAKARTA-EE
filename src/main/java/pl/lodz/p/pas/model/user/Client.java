package pl.lodz.p.pas.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Client extends User {

    private Address address;
    private ClientType clientType;
}

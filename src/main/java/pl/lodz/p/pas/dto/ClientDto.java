package pl.lodz.p.pas.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.pas.model.user.Address;
import pl.lodz.p.pas.model.user.ClientType;

@Data
@SuperBuilder
public class ClientDto extends UserDto {
    private Address address;
    private ClientType clientType;
}


package pl.lodz.p.pas.dto;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.pas.model.user.Address;
import pl.lodz.p.pas.model.user.ClientType;

@Data
@NoArgsConstructor
public class ClientDto extends UserDto {

    @NotNull
    private Address address;

    @NotNull
    private ClientType clientType;

    @Builder
    public ClientDto(String name, String surname, String login,
                     Address address, ClientType clientType) {
        super(name, surname, login);
        this.address = address;
        this.clientType = clientType;
    }


}


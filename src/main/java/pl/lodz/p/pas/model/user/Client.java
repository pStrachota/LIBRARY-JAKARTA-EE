package pl.lodz.p.pas.model.user;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class Client extends User {

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    @Builder
    public Client(boolean isActive, String name, String surname,
                  String login, Address address, ClientType clientType) {
        super(isActive, name, surname, login);
        this.address = address;
        this.clientType = clientType;
    }
}

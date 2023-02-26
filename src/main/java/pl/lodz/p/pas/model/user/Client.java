package pl.lodz.p.pas.model.user;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client extends User {

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    @Builder
    public Client(boolean isActive, String name, String surname,
                  String login, String password, Address address, ClientType clientType) {
        super(isActive, name, surname, login, password, "client");
        this.address = address;
        this.clientType = clientType;
    }
}

package pl.lodz.p.pas.model.user;

import javax.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Admin extends User {

    private String privileges;

    private String position;

    @Builder
    public Admin(boolean isActive, long id, String name, String surname,
                 String login, String privileges, String position) {
        super(isActive, name, surname, login);
        this.privileges = privileges;
        this.position = position;
    }
}

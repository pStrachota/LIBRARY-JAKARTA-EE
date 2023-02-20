package pl.lodz.p.pas.model.user;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
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
public class Admin extends User {

    @NotBlank
    private String privileges;

    @NotBlank
    private String position;

    @Builder
    public Admin(boolean isActive, long id, String name, String surname,
                 String login, String privileges, String position) {
        super(isActive, name, surname, login);
        this.privileges = privileges;
        this.position = position;
    }
}

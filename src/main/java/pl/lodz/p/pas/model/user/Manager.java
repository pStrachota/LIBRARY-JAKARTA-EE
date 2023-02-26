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
public class Manager extends User {

    @NotBlank
    private String position;

    @Builder
    public Manager(boolean isActive, String name, String surname,
                   String login, String password, String privileges, String position) {
        super(isActive, name, surname, login, password, "manager");
        this.position = position;
    }

}

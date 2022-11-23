package pl.lodz.p.pas.model.user;


import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
public class Manager extends User {

    private String position;

    @Builder
    public Manager(boolean isActive, String name, String surname,
                   String login, String privileges, String position) {
        super(isActive, name, surname, login);
        this.position = position;
    }

}

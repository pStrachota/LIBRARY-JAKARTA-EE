package pl.lodz.p.pas.dto.user;

import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ManagerDto extends UserDto {

    @NotEmpty
    private String position;

    @Builder
    public ManagerDto(String name, String surname, String login, String position, String password) {
        super(name, surname, login, password);
        this.position = position;
    }
}


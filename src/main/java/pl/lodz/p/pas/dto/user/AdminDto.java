package pl.lodz.p.pas.dto.user;

import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminDto extends UserDto {


    @NotEmpty
    private String privileges;
    @NotEmpty
    private String position;

    @Builder
    public AdminDto(String name, String surname, String login, String password, String privileges,
                    String position) {
        super(name, surname, login, password);
        this.privileges = privileges;
        this.position = position;
    }
}


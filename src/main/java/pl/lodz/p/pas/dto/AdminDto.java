package pl.lodz.p.pas.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminDto extends UserDto {


    @Builder
    public AdminDto(String name, String surname, String login, String privileges,
                    String position) {
        super(name, surname, login);
        this.privileges = privileges;
        this.position = position;
    }

    @NotEmpty
    private String privileges;

    @NotEmpty
    private String position;
}


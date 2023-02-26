package pl.lodz.p.pas.dto.resource;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentableItemDto {

    @NotBlank
    private String serialNumber;

    @NotBlank
    private String author;

    @NotBlank
    private String title;
}

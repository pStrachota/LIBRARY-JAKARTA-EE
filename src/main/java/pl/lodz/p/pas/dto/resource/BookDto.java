package pl.lodz.p.pas.dto.resource;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDto extends RentableItemDto {

    @NotBlank
    private String publishingHouse;

    @Builder
    public BookDto(String serialNumber, String author, String title, String publishingHouse) {
        super(serialNumber, author, title);
        this.publishingHouse = publishingHouse;
    }
}

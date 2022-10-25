package pl.lodz.p.pas.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class BookDto extends RentableItemDto {

    private String publishingHouse;

}

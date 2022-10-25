package pl.lodz.p.pas.model.resource;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Book extends RentableItem {

    private String publishingHouse;

}

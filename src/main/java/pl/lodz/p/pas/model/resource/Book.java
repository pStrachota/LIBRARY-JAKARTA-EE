package pl.lodz.p.pas.model.resource;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
public class Book extends RentableItem {

    @NotEmpty
    private String publishingHouse;

    @Builder
    public Book(long id, long version, boolean isAvailable, String serialNumber,
                String author,
                String title, String publishingHouse) {
        super(id, version, isAvailable, serialNumber, author, title);
        this.publishingHouse = publishingHouse;
    }

}

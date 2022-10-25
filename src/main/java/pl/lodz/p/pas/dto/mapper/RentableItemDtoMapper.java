package pl.lodz.p.pas.dto.mapper;

import pl.lodz.p.pas.dto.ArticleDto;
import pl.lodz.p.pas.dto.BookDto;
import pl.lodz.p.pas.dto.RentableItemDto;
import pl.lodz.p.pas.model.resource.Article;
import pl.lodz.p.pas.model.resource.Book;
import pl.lodz.p.pas.model.resource.RentableItem;

public class RentableItemDtoMapper {

    public static RentableItem mapToRentableItem(RentableItemDto rentableItemDto) {

        if (rentableItemDto instanceof BookDto) {
            BookDto bookDto = (BookDto) rentableItemDto;
            return Book.builder()
                    .serialNumber(rentableItemDto.getSerialNumber())
                    .author(rentableItemDto.getAuthor())
                    .title(rentableItemDto.getTitle())
                    .publishingHouse(bookDto.getPublishingHouse())
                    .build();
        } else {
            ArticleDto articleDto = (ArticleDto) rentableItemDto;
            return Article.builder()
                    .serialNumber(rentableItemDto.getSerialNumber())
                    .author(rentableItemDto.getAuthor())
                    .title(rentableItemDto.getTitle())
                    .parentOrganisation(articleDto.getParentOrganisation())
                    .build();
        }
    }
}

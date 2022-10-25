package pl.lodz.p.pas.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import javax.enterprise.context.ApplicationScoped;
import pl.lodz.p.pas.model.resource.Article;
import pl.lodz.p.pas.model.resource.Book;
import pl.lodz.p.pas.model.resource.RentableItem;

@ApplicationScoped
public class RentableItemRepo extends Repo<RentableItem> {

    private final AtomicLong lastId = new AtomicLong(0);
    private List<RentableItem> rentableItems;


    public RentableItemRepo() {
        rentableItems = Arrays.asList(
                Book.builder().id(lastId.getAndIncrement()).title("Book1").author("Author1")
                        .publishingHouse("PH1").serialNumber("SN1").build(),
                Article.builder().id(lastId.getAndIncrement()).title("Article1").author("Author1")
                        .serialNumber("SN2").parentOrganisation("PO1").build()
        );
    }

    @Override
    public synchronized long add(RentableItem item) {
        item.setId(lastId.getAndIncrement());
        rentableItems.add(item);
        return item.getId();
    }

    @Override
    public synchronized void remove(RentableItem item) {
        rentableItems.remove(item);
    }

    @Override
    public Optional<RentableItem> findByID(Long id) {
        for (RentableItem rentableItem : rentableItems) {
            if (rentableItem.getId().equals(id)) {
                return Optional.of(rentableItem);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<RentableItem> getItems() {
        return rentableItems;
    }

}

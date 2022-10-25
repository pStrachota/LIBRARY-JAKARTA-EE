package pl.lodz.p.pas.repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import pl.lodz.p.pas.model.Rent;
import pl.lodz.p.pas.model.user.Client;

@ApplicationScoped
public class RentRepo extends Repo<Rent> {

    private final AtomicLong lastId = new AtomicLong(0);
    @Inject
    private UserRepo userRepo;
    @Inject
    private RentableItemRepo rentableItemRepo;
    private List<Rent> rents;

    public RentRepo() {
        rents = Arrays.asList(
                Rent.builder().rentId(lastId.getAndIncrement())
                        .beginTime(LocalDateTime.now()).client((Client) userRepo.findByID(2L).get())
                        .rentableItem(Arrays.asList(rentableItemRepo.findByID(1L).get())).build()
        );
    }

    @Override
    public synchronized long add(Rent item) {
        item.setRentId(lastId.getAndIncrement());
        rents.add(item);
        return item.getRentId();
    }

    @Override
    public synchronized void remove(Rent item) {
        rents.remove(item);
    }

    @Override
    public Optional<Rent> findByID(Long id) {
        for (Rent rent : rents) {
            if (rent.getRentId().equals(id)) {
                return Optional.of(rent);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Rent> getItems() {
        return rents;
    }


}

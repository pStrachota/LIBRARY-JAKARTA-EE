package pl.lodz.p.pas.manager;

import java.util.List;
import javax.inject.Inject;
import pl.lodz.p.pas.dto.RentableItemDto;
import pl.lodz.p.pas.dto.mapper.RentableItemDtoMapper;
import pl.lodz.p.pas.model.Rent;
import pl.lodz.p.pas.model.resource.RentableItem;
import pl.lodz.p.pas.repository.RentRepo;
import pl.lodz.p.pas.repository.RentableItemRepo;

public class RentableItemManager {

    @Inject
    RentableItemRepo rentableItemRepo;

    @Inject
    RentRepo rentRepo;

    public long addRentableItem(RentableItemDto rentableItemDto) {

        RentableItem rentableItem = RentableItemDtoMapper.mapToRentableItem(rentableItemDto);
        return rentableItemRepo.add(rentableItem);
    }

    public RentableItem getRentableItem(long id) {
        return rentableItemRepo.findByID(id)
                .orElseThrow(() -> new RuntimeException("RentableItem not found"));
    }

    public void deleteRentableItem(Long id) {

        List<Rent> rents = rentRepo.getItems();

        rents.forEach(rent -> {
            rent.getRentableItem().forEach(rentableItem -> {
                if (rentableItem.getId().equals(id)) {
                    throw new RuntimeException("RentableItem is rented");
                }
            });
        });

        RentableItem rentableItem = rentableItemRepo.findByID(id)
                .orElseThrow(() -> new RuntimeException("RentableItem not found"));

        rentableItemRepo.remove(rentableItem);
    }

    public List<RentableItem> getRentableItems() {
        return rentableItemRepo.getItems();
    }


    public void updateRentableItem(Long id, RentableItemDto rentableItemDto) {
        RentableItem rentableItem = rentableItemRepo.findByID(id)
                .orElseThrow(() -> new RuntimeException("RentableItem not found"));

        List<RentableItem> rentableItems = rentableItemRepo.getItems();
        int index = rentableItems.indexOf(rentableItem);

        RentableItem UpdatedRentableItem = RentableItemDtoMapper.mapToRentableItem(rentableItemDto);
        rentableItemRepo.getItems().set(index, UpdatedRentableItem);

    }
}

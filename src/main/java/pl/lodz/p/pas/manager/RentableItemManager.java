package pl.lodz.p.pas.manager;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import pl.lodz.p.pas.dto.resource.RentableItemDto;
import pl.lodz.p.pas.dto.mapper.RentableItemDtoMapper;
import pl.lodz.p.pas.exception.DuplicatedSerialNumberException;
import pl.lodz.p.pas.exception.ItemNotFoundException;
import pl.lodz.p.pas.exception.RentableItemNotAvailableException;
import pl.lodz.p.pas.model.Rent;
import pl.lodz.p.pas.model.resource.RentableItem;
import pl.lodz.p.pas.repository.RentRepo;
import pl.lodz.p.pas.repository.RentableItemRepo;

@Stateless
public class RentableItemManager {

    @EJB
    RentableItemRepo rentableItemRepo;

    @EJB
    RentRepo rentRepo;

    public void addRentableItem(RentableItemDto rentableItemDto) {

        RentableItem rentableItem = RentableItemDtoMapper.mapToRentableItem(rentableItemDto);

        try {
            rentableItemRepo.add(rentableItem);
        } catch (PersistenceException | EJBException e) {
            if (e.getCause().getMessage().contains("ConstraintViolationException")) {
                throw new DuplicatedSerialNumberException(
                        "Rentable item with " + rentableItem.getSerialNumber() +
                                " serial number already exists");
            }
        }
    }

    public List<RentableItem> findByTitleContains(String title) {
        return rentableItemRepo.findByTitleContains(title);
    }

    public RentableItem getRentableItem(long id) {
        return rentableItemRepo.findByID(id)
                .orElseThrow(() -> new ItemNotFoundException("RentableItem not found"));
    }

    public void deleteRentableItem(Long id) {

        List<Rent> rents = rentRepo.getItems();

        rents.forEach(rent -> {
            rent.getRentableItems().forEach(rentableItem -> {
                if (!rentableItem.isAvailable()) {
                    throw new RentableItemNotAvailableException("RentableItem is rented");
                }
            });
        });

        RentableItem rentableItem = rentableItemRepo.findByID(id)
                .orElseThrow(() -> new ItemNotFoundException("RentableItem not found"));

        rentableItemRepo.remove(rentableItem);
    }

    public List<RentableItem> getRentableItems() {
        return rentableItemRepo.getItems();
    }


    public void updateRentableItem(Long id, RentableItemDto rentableItemDto) {
        RentableItem rentableItem = rentableItemRepo.findByID(id)
                .orElseThrow(() -> new ItemNotFoundException("RentableItem not found"));
        try {
            rentableItemRepo.update(id, RentableItemDtoMapper.mapToRentableItem(rentableItemDto));
        } catch (PersistenceException | EJBException e) {
            if (e.getCause().getMessage().contains("ConstraintViolationException")) {
                throw new DuplicatedSerialNumberException(
                        "Rentable item with " + rentableItem.getSerialNumber() +
                                " serial number already exists");
            }
        }

    }
}

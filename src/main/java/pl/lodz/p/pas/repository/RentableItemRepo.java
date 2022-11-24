package pl.lodz.p.pas.repository;

import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.pas.model.resource.RentableItem;

@Stateless
public class RentableItemRepo extends Repo<RentableItem> {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void add(RentableItem item) {
        entityManager.persist(item);
    }

    @Override
    public void remove(RentableItem item) {
        entityManager.remove(item);
    }

    public List<RentableItem> findByTitleContains(String title) {
        return entityManager
                .createQuery("SELECT r FROM RentableItem r WHERE r.title LIKE :title",
                        RentableItem.class)
                .setParameter("title", "%" + title + "%")
                .getResultList();
    }

    @Override
    public Optional<RentableItem> findByID(Long id) {
        RentableItem rentableItem = entityManager.find(RentableItem.class, id);
        return Optional.ofNullable(rentableItem);
    }

    @Override
    public void update(Long id, RentableItem item) {
        item.setId(id);
        entityManager.merge(item);
    }

    @Override
    public List<RentableItem> getItems() {
        return entityManager.createQuery("SELECT r FROM RentableItem r", RentableItem.class)
                .getResultList();
    }

}

package pl.lodz.p.pas.repository;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.hibernate.Criteria;
import pl.lodz.p.pas.model.Rent;

@ApplicationScoped
@Transactional
public class RentRepo extends Repo<Rent> {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void add(Rent item) {
        entityManager.persist(item);
    }

    @Override
    public void remove(Rent item) {
        entityManager.remove(item);
    }

    @Override
    public Optional<Rent> findByID(Long id) {
        Criteria criteria =
                entityManager.unwrap(org.hibernate.Session.class).createCriteria(Rent.class);
        criteria.add(org.hibernate.criterion.Restrictions.eq("id", id));
        criteria.setFetchMode("rentableItems", org.hibernate.FetchMode.JOIN);
        criteria.setFetchMode("client", org.hibernate.FetchMode.JOIN);
        return Optional.ofNullable((Rent) criteria.uniqueResult());
    }

    @Override
    public void update(Long id, Rent item) {
        item.setRentId(id);
        entityManager.merge(item);
    }

    @Override
    public List<Rent> getItems() {
        Criteria criteria =
                entityManager.unwrap(org.hibernate.Session.class).createCriteria(Rent.class);
        criteria.setFetchMode("rentableItems", org.hibernate.FetchMode.JOIN);
        criteria.setFetchMode("client", org.hibernate.FetchMode.JOIN);
        return criteria.list();
    }

}

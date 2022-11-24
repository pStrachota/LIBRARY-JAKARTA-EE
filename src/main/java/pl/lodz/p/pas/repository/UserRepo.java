package pl.lodz.p.pas.repository;

import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.pas.model.user.User;

@Stateless
public class UserRepo extends Repo<User> {

    @PersistenceContext
    EntityManager entityManager;

    public List<User> findByLoginContains(String login) {
        return entityManager
                .createQuery("SELECT u FROM User u WHERE u.login LIKE :login", User.class)
                .setParameter("login", "%" + login + "%")
                .getResultList();
    }

    public List<User> findByNameContains(String name) {
        return entityManager
                .createQuery("SELECT u FROM User u WHERE u.name LIKE :name", User.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> getItems() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void remove(User user) {
        entityManager.remove(user);
    }

    @Override
    public Optional<User> findByID(Long id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public void update(Long id, User item) {
        item.setId(id);
        entityManager.merge(item);
    }

}

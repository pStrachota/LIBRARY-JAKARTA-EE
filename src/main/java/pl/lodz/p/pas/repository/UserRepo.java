package pl.lodz.p.pas.repository;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import pl.lodz.p.pas.model.user.User;

@ApplicationScoped
@Transactional
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
        item.setUserId(id);
        entityManager.merge(item);
    }

    public void changePassword(Long id, String hash) {
        User user = entityManager.find(User.class, id);
        user.setPassword(hash);
        entityManager.merge(user);
    }

    public Optional<User> findByLogin(String login) {
        return entityManager
                .createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                .setParameter("login", login)
                .getResultStream()
                .findFirst();
    }
}

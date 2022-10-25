package pl.lodz.p.pas.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import javax.enterprise.context.ApplicationScoped;
import pl.lodz.p.pas.exception.DuplicatedLoginException;
import pl.lodz.p.pas.model.user.Address;
import pl.lodz.p.pas.model.user.Admin;
import pl.lodz.p.pas.model.user.Client;
import pl.lodz.p.pas.model.user.ClientType;
import pl.lodz.p.pas.model.user.Manager;
import pl.lodz.p.pas.model.user.User;

@ApplicationScoped
public class UserRepo extends Repo<User> {

    private final AtomicLong lastId = new AtomicLong(0);
    private List<User> users;

    public UserRepo() {
        users = Arrays.asList(
                Admin.builder().id(lastId.getAndIncrement()).login("admin").password("admin")
                .name("Admin").surname("Admin").build(),
                Manager.builder().id(lastId.getAndIncrement()).login("manager").password("manager")
                .name("Manager").surname("Manager").build(),
                Client.builder().id(lastId.getAndIncrement()).login("client").password("client")
                .name("Client").surname("Client").clientType(ClientType.STUDENT).address(Address.builder()
                .city("Lodz").street("Kosciuszki").number("1").build()).build()
        );
    }

    @Override
    public synchronized long add(User item) {
        validateUserLogin(item);
        item.setId(lastId.getAndIncrement());
        users.add(item);
        return item.getId();
    }

    private void validateUserLogin(User item) {
        if (users.stream().anyMatch(user -> user.getLogin().equals(item.getLogin()))) {
            throw new DuplicatedLoginException("User with this login already exists");
        }
    }

    @Override
    public synchronized void remove(User item) {
        users.remove(item);
    }

    @Override
    public Optional<User> findByID(Long id) {
        for (User user : users) {
            if (user.getId() == id) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<User> getItems() {
        return users;
    }

}

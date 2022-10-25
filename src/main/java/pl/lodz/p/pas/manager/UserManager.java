package pl.lodz.p.pas.manager;

import java.util.List;
import javax.inject.Inject;
import pl.lodz.p.pas.dto.UserDto;
import pl.lodz.p.pas.dto.mapper.UserDtoMapper;
import pl.lodz.p.pas.exception.ItemNotFoundException;
import pl.lodz.p.pas.model.user.User;
import pl.lodz.p.pas.repository.UserRepo;

public class UserManager {

    @Inject
    UserRepo userRepo;

    public long addUser(User user) {
        return userRepo.add(user);
    }

    public User getUser(long id) {
        return userRepo.findByID(id)
                .orElseThrow(() -> new ItemNotFoundException("User not found"));
    }

    public List<User> getUsers() {
        return userRepo.getItems();
    }

    public void activateUser(long id) {
        User user = userRepo.findByID(id)
                .orElseThrow(() -> new ItemNotFoundException("User not found"));
        user.setActive(true);
    }

    public void deactivateUser(long id) {
        User user = userRepo.findByID(id)
                .orElseThrow(() -> new ItemNotFoundException("User not found"));
        user.setActive(false);
    }

    public void updateUser(Long id, UserDto userDto) {

        User user = userRepo.findByID(id)
                .orElseThrow(() -> new ItemNotFoundException("User not found"));

        List<User> users = userRepo.getItems();
        int index = users.indexOf(user);

        User updatedUser = UserDtoMapper.mapToUser(userDto);
        userRepo.getItems().set(index, updatedUser);

    }
}

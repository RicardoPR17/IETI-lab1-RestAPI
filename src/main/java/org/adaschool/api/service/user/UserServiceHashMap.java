package org.adaschool.api.service.user;

import org.adaschool.api.exception.UserNotFoundException;
import org.adaschool.api.repository.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceHashMap implements UsersService {
    private final HashMap<String, User> usersPersistance = new HashMap<>();

    /**
     * @param user User to be added in the HashMap
     * @return The user instance that was saved
     */
    @Override
    public User save(User user) {
        usersPersistance.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) throws UserNotFoundException {
        if (usersPersistance.containsKey(id)) {
            return Optional.of(usersPersistance.get(id));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<User> all() {
        return new ArrayList<>(usersPersistance.values());
    }

    @Override
    public void deleteById(String id) throws UserNotFoundException {
        if (usersPersistance.containsKey(id)) {
            usersPersistance.remove(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public User update(User user, String userId) throws UserNotFoundException {
        if (usersPersistance.containsKey(userId)) {
            User updated;
            updated = usersPersistance.put(userId, user);
            return updated;
        } else {
            throw new UserNotFoundException(userId);
        }
    }
}

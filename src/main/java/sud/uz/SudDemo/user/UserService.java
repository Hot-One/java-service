package sud.uz.SudDemo.user;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error creating user: " + e.getMessage(), e);
        }
    }

    public User getUserById(Long id) {
        try {
            return userRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving user with id " + id + ": " + e.getMessage(), e);
        }
    }

    public List<User> getAllUsers(int offset, int limit) {
        try {
            Pageable pageable = PageRequest.of(offset, limit);
            return userRepository.findAll(pageable).getContent();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all users: " + e.getMessage(), e);
        }
    }

    public User updateUser(Long id, User updatedUser) {
        try {
            return userRepository.findById(id).map(user -> {
                user.setUsername(updatedUser.getUsername());
                user.setPassword(updatedUser.getPassword());
                user.setEmail(updatedUser.getEmail());
                user.setPin(updatedUser.getPin());
                user.setPassport(updatedUser.getPassport());
                return userRepository.save(user);
            }).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error updating user with id " + id + ": " + e.getMessage(), e);
        }
    }

    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user with id " + id + ": " + e.getMessage(), e);
        }
    }
}

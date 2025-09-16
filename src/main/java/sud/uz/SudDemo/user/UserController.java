package sud.uz.SudDemo.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import sud.uz.SudDemo.user.dto.UserDto;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto input) {
        User user = new User();
        user.setUsername(input.getUsername());
        user.setPassword(input.getPassword());
        user.setEmail(input.getEmail());
        user.setPin(input.getPin());
        user.setPassport(input.getPassport());

        User response = userService.createUser(user);
        return ResponseEntity.created(null).body(response);
    }

    @Operation(summary = "Get single user by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User item = userService.getUserById(id);
        if (item == null) {
            throw new jakarta.persistence.EntityNotFoundException("User not found with id: " + id);
        }

        return ResponseEntity.ok().body(item);
    }

    @Operation(summary = "Get list of users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {
        List<User> items = userService.getAllUsers(offset, limit);
        return ResponseEntity.ok().body(items);
    }

    @Operation(summary = "Update an existing user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User item = userService.updateUser(id, updatedUser);
        if (item == null) {
            throw new jakarta.persistence.EntityNotFoundException("User not found with id: " + id);
        }

        return ResponseEntity.ok().body(item);
    }

    @Operation(summary = "Delete user by id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
package info.somrat.rest.service;

import info.somrat.rest.config.UserPrincipal;
import info.somrat.rest.dto.UserDTO;
import info.somrat.rest.enums.ERole;
import info.somrat.rest.models.Post;
import info.somrat.rest.models.Role;
import info.somrat.rest.models.User;
import info.somrat.rest.repository.RoleRepository;
import info.somrat.rest.repository.UserRepository;
import info.somrat.rest.request.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Create a new user with roles
     * @param request
     * @return
     */
    @Transactional
    public User save(SignUpRequest request) {
        User user = new User(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
        user.setRoles(getRoles(request.getRoles()));
        user.setPermissions(request.getPermissions());
        userRepository.save(user);
        return user;
    }

    /**
     * Get user roles
     * @param requestRoles
     * @return
     */
    private Set<Role> getRoles(Set<String> requestRoles) {
        Set<Role> roles = new HashSet<>();
        if (requestRoles == null) {
            roles.add(roleRepository.findByName(ERole.ROLE_USER));
        } else {
            requestRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        roles.add(roleRepository.findByName(ERole.ROLE_ADMIN));
                        break;
                    case "mod":
                        roles.add(roleRepository.findByName(ERole.ROLE_MODERATOR));
                        break;
                    case "user":
                        roles.add(roleRepository.findByName(ERole.ROLE_USER));
                        break;
                }
            });
        }
        return roles;
    }

    /**
     * Get user by username
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Combine user and roles to UserDTO
     * @param username
     * @return
     */
    public UserDTO getByUsername (String username) {
        Tuple userTuple = userRepository.getUserByUsername(username);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userTuple.get(0, String.class));
        userDTO.setEmail(userTuple.get(1, String.class));
        userDTO.setPermissions(Arrays.asList(userTuple.get(2, String.class)));
        userDTO.setRoles(userRepository.getRoleByUsername(username));
        return userDTO;
    }

    /**
     * Convert UserPrinciple To UserDTO Object
     * @param userPrincipal
     * @return
     */
    public UserDTO convertUserPrincipleToUserDTO(UserPrincipal userPrincipal) {
        List<String> roles = userPrincipal.getAuthorities().stream()
                .filter(role -> role.getAuthority().startsWith("ROLE_"))
                .map(role -> role.getAuthority())
                .collect(Collectors.toList());
        List<String> permissions = userPrincipal.getAuthorities().stream()
                .filter(role -> !role.getAuthority().startsWith("ROLE_"))
                .map(role -> role.getAuthority())
                .collect(Collectors.toList());
        return new UserDTO(userPrincipal.getUsername(), userPrincipal.getEmail(), permissions, roles);
    }
}

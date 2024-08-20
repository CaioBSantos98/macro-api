package br.com.macrosapi.services;

import br.com.macrosapi.dto.RegisterUserDTO;
import br.com.macrosapi.dto.UserDetailsDTO;
import br.com.macrosapi.model.user.User;
import br.com.macrosapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User create(RegisterUserDTO dto) {
        var encodedPassword = passwordEncoder.encode(dto.password());
        User user = new User(dto, encodedPassword);
        userRepository.save(user);
        return user;
    }

    public UserDetailsDTO detail(UUID id) {
        User user = userRepository.getReferenceById(id);
        if (user.getActive()) {
            return new UserDetailsDTO(user);
        }
        throw new RuntimeException("This user was deleted");
    }

    public void delete(UUID id) {
        User user = userRepository.getReferenceById(id);
        user.delete();
    }
}

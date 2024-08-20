package br.com.macrosapi.services;

import br.com.macrosapi.dto.RegisterUserDTO;
import br.com.macrosapi.model.user.User;
import br.com.macrosapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private User create(RegisterUserDTO dto) {
        User user = new User(dto);
        userRepository.save(user);
        return user;
    }
}

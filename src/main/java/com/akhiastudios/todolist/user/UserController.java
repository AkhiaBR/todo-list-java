package com.akhiastudios.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired // gerencia o ciclo de vida do repositorio
    private IUserRepository userRepository;

    @PostMapping("/")
    public UserModel create (@RequestBody UserModel userModel) { // recebe o objeto userModel
        var userCreated = this.userRepository.save(userModel);
        return userCreated;
    }

}

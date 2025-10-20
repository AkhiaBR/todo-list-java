package com.akhiastudios.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity create (@RequestBody UserModel userModel) { // recebe o objeto userModel

        var user = this.userRepository.findByUsername(userModel.getUsername()); // acha um usuário pelo username

        if (user != null) { // se já existir um usuário com o nome :
            System.out.println("Usuário já existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe"); // retorna STATUS CODE 400
        }
        
        var userCreated = this.userRepository.save(userModel); // o var atomaticamente define o tipo de variavel analisando que atribuicao foi dada a variavel, diminuindo a verbosidade do codigo
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);

    }

}

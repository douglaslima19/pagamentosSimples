package com.pagamentos.controllers;

import com.pagamentos.domain.user.User;
import com.pagamentos.dtos.UserDTO;
import com.pagamentos.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<User> criarUsuario(@RequestBody UserDTO user){

        User novoUsuario = userService.criarUsuario(user);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsuarios(){
        List<User> usuarios = this.userService.getAllUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
}

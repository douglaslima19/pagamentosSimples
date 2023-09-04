package com.pagamentos.controllers;

import com.pagamentos.domain.user.User;
import com.pagamentos.dtos.UserDTO;
import com.pagamentos.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Cria usuários", method = "POST")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Usuários criados com sucesso!"),
            @ApiResponse(responseCode = "422", description = "Requisição inválida!"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos!!"),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> criarUsuario(@RequestBody UserDTO user){

        User novoUsuario = userService.criarUsuario(user);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }
    @Operation(summary = "Realiza a busca de todos os usuários ", method = "GET")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso!"),
            @ApiResponse(responseCode = "422", description = "Requisição inválida!"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos!!"),
    })
    @GetMapping
    public ResponseEntity<List<User>> getAllUsuarios(){
        List<User> usuarios = this.userService.getAllUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
}

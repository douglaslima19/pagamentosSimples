package com.pagamentos.services;

import com.pagamentos.domain.user.User;
import com.pagamentos.domain.user.UserType;
import com.pagamentos.dtos.UserDTO;
import com.pagamentos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void validaTransicao(User sender, User receiver, BigDecimal saldo) throws Exception{
        validarUsuarioLojista(sender);
        validarSaldo(sender,saldo);
        validarDestinatarioDif(sender, receiver);
    }

    public void validarUsuarioLojista(User user) throws Exception{
        if(user.getUserType() == UserType.MERCHANT){
            throw new Exception("Usuários lojistas não podem fazer transições!");
        }
    }
    public void validarSaldo(User user, BigDecimal saldo) throws Exception{
        if (user.getSaldo().compareTo(saldo) < 0){
            throw new Exception("Usuário sem saldo!!");
        }
    }

    public void validarDestinatarioDif(User sender, User receiver) throws Exception{
        if(receiver.getId().equals(sender.getId())){
            throw  new Exception("Você não pode transferir para você mesmo!!");
        }
    }

    public User findUserById(Long id) throws Exception{
        return  this.userRepository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encontrado!!"));
    }
    public User criarUsuario(UserDTO userDTO){
        User novoUsuario = new User(userDTO);
        this.saveUser(novoUsuario);
        return novoUsuario;
    }

    public List<User> getAllUsuarios(){
        return this.userRepository.findAll();
    }

    public void saveUser(User user){
        this.userRepository.save(user);
    }
}

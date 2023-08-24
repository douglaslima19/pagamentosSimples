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

    public void validaTransicao(User sender, BigDecimal saldo) throws Exception{
        if(sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Usuários lojistas não podem fazer transições!");
        }

        if(sender.getSaldo().compareTo(saldo) < 0){
            throw new Exception("Usuário sem saldo!!");
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

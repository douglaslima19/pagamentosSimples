package com.pagamentos.user;

import com.pagamentos.domain.user.User;
import com.pagamentos.domain.user.UserType;
import com.pagamentos.dtos.UserDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
public class UserApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testeCriarUsuario() throws Exception{
        String userJson ="{\"firstName\": \"Douglas\", \"lastName\": \"Lima\", \"documento\": \"0125262521\", \"email\": \"teste@teste.com.br\", \"userType\": \"COMMON\", \"saldo\": \"100\", \"senha\": \"password\"}";

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson)).andExpect(status().isCreated());

    }

    @Test
    public void testeCriarUsuarioRepetido() throws Exception{
        String userJson ="{\"firstName\": \"Douglas\", \"lastName\": \"Lima\", \"documento\": \"0125262521\", \"email\": \"teste@teste.com.br\", \"userType\": \"COMMON\", \"saldo\": \"100\", \"senha\": \"password\"}";

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson)).andExpect(status().is4xxClientError()).andExpect(jsonPath("$.mensagem").value("Usuario ja Cadastrado!!!"));

    }

    @Test
    public void testeGetAllUsuarios() throws  Exception {

        mockMvc.perform(get("/users", 1L)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$[0].firstName").value("Douglas"));
    }


}

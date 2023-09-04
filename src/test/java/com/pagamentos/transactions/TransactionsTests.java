package com.pagamentos.transactions;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionsTests {
    @Autowired
    private MockMvc mockMvc;


    public void tstCriarSender() throws Exception{
        String userJson ="{\"firstName\": \"Douglas\", \"lastName\": \"Lima\", \"documento\": \"0125262521\", \"email\": \"teste@teste.com.br\", \"userType\": \"COMMON\", \"saldo\": \"100\", \"senha\": \"password\"}";

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson)).andExpect(status().isCreated());

    }


    public void tstCriarReceiver() throws Exception{
        String userJson ="{\"firstName\": \"Roberto\", \"lastName\": \"Silva\", \"documento\": \"1582485692\", \"email\": \"roberto@teste.com.br\", \"userType\": \"COMMON\", \"saldo\": \"50\", \"senha\": \"password\"}";

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson)).andExpect(status().isCreated());

    }
    public void tstUsrMerchant() throws Exception{
        String userJson ="{\"firstName\": \"Merchant\", \"lastName\": \"One\", \"documento\": \"1582485692222\", \"email\": \"merchant@teste.com.br\", \"userType\": \"MERCHANT\", \"saldo\": \"5000\", \"senha\": \"password\"}";

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson)).andExpect(status().isCreated());

    }


    @Test
    public void testeRealizarTransacao() throws Exception{

        String transactionJson ="{\"senderId\": \"1\", \"receiverId\": \"2\", \"valor\": \"50\"}";
        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON).content(transactionJson)).andExpect(status().isOk()).andExpect(jsonPath("$.valor").value(50));
    }

    @Test
    public void testeUserSemSaldo() throws Exception{

        String transactionJson ="{\"senderId\": \"1\", \"receiverId\": \"2\", \"valor\": \"120\"}";
        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON).content(transactionJson)).andExpect(status().is5xxServerError()).andExpect(jsonPath("$.mensagem").value("Usuário sem saldo!!"));
    }

    @Test
    public void testeUsuarioMesmoId() throws Exception{

        String transactionJsonId ="{\"senderId\": \"1\", \"receiverId\": \"1\", \"valor\": \"50\"}";
        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON).content(transactionJsonId)).andExpect(status().is5xxServerError()).andExpect(jsonPath("$.mensagem").value("Você não pode transferir para você mesmo!!"));
    }

    @Test
    public void testeUsuarioMerchant() throws Exception{

        tstCriarSender();
        tstCriarReceiver();
        tstUsrMerchant();

        String transactionJsonId ="{\"senderId\": \"3\", \"receiverId\": \"1\", \"valor\": \"50\"}";
        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON).content(transactionJsonId)).andExpect(status().is5xxServerError()).andExpect(jsonPath("$.mensagem").value("Usuários lojistas não podem fazer transições!"));
    }



}

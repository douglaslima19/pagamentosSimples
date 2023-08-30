package com.pagamentos.services;

import com.pagamentos.domain.transaction.Transaction;
import com.pagamentos.domain.user.User;
import com.pagamentos.dtos.TransactionDTO;
import com.pagamentos.repositories.TransactionRepository;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Service
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private NotificacaoService notificacaoService;


    @Autowired
    private RestTemplate restTemplate;

    public Transaction createTransaction(TransactionDTO transaction) throws  Exception{
        User sender = this.userService.findUserById(transaction.senderId());
        User receiver = this.userService.findUserById(transaction.receiverId());

        userService.validaTransicao(sender, receiver, transaction.valor());

        if(!this.autorizaTransicao(sender, transaction.valor())){
            throw new Exception("Transação não autorizada!!");
        }
        Transaction novatransaction = new Transaction();
        novatransaction.setValor(transaction.valor());
        novatransaction.setSender(sender);
        novatransaction.setReceiver(receiver);
        novatransaction.setDataTransacao(LocalDateTime.now());

        sender.setSaldo(sender.getSaldo().subtract(transaction.valor()));
        receiver.setSaldo(receiver.getSaldo().add(transaction.valor()));

        this.transactionRepository.save(novatransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificacaoService.sendNotificacao(sender, "Transação realizada com sucesso!!");
        this.notificacaoService.sendNotificacao(receiver, "Transação recebida com sucesso!!");

        return novatransaction;
    }
    public boolean autorizaTransicao(User sender, BigDecimal valor){
       ResponseEntity<Map> response =restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);

        return response.getStatusCode() == HttpStatus.OK && Objects.equals((String) Objects.requireNonNull(response.getBody()).get("message"), "Autorizado");
    }
}

package com.pagamentos.controllers;

import com.pagamentos.domain.transaction.Transaction;
import com.pagamentos.dtos.TransactionDTO;
import com.pagamentos.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> crierTransaction(@RequestBody TransactionDTO transactionDTO) throws Exception{
        Transaction transaction = transactionService.createTransaction(transactionDTO);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}

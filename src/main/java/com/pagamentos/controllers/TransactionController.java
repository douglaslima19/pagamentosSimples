package com.pagamentos.controllers;

import com.pagamentos.domain.transaction.Transaction;
import com.pagamentos.dtos.TransactionDTO;
import com.pagamentos.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/transactions", produces = {"application/json"})
@Tag(name = "Pagamentos")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @Operation(summary = "Realiza as transações financeiras", method = "POST")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Transação realizada com sucesso!"),
            @ApiResponse(responseCode = "422", description = "Requisição inválida!"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos!!"),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> crierTransaction(@RequestBody TransactionDTO transactionDTO) throws Exception{
        Transaction transaction = transactionService.createTransaction(transactionDTO);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}

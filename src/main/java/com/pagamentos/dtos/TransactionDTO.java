package com.pagamentos.dtos;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal valor, Long senderId, Long receiverId) {
}

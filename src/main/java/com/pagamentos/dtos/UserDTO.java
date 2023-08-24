package com.pagamentos.dtos;

import com.pagamentos.domain.user.UserType;

import java.math.BigDecimal;

public record UserDTO(String firstName, String lastName, String documento, BigDecimal saldo, String email, String senha, UserType userType) {
}

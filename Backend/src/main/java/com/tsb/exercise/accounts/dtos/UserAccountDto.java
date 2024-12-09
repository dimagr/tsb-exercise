package com.tsb.exercise.accounts.dtos;

import lombok.Data;

import java.math.BigDecimal;

public record UserAccountDto(Long id, Long ownerId, BigDecimal balance) {
}

package com.tenpo.diego.infraestructure.adapter.in.rest.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CalculateDto {
    private  BigDecimal num1;
    private  BigDecimal num2;

}

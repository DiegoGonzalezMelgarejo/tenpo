package com.tenpo.diego.infraestructure.adapter.in.rest.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CalculateDto {

    @NotNull(message = "The num1 field cannot be null")
    private  BigDecimal num1;
    @NotNull(message = "The num2 field cannot be null")
    private  BigDecimal num2;

}

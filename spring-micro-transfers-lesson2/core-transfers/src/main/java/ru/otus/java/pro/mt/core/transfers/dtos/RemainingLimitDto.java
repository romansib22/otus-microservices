package ru.otus.java.pro.mt.core.transfers.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "Остаток лимита на переводы")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemainingLimitDto {
    @Schema(
            description = "Остаток лимита на переводы",
            example = "100.00",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private BigDecimal remainingLimit;
}

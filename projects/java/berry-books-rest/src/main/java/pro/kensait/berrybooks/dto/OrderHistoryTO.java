package pro.kensait.berrybooks.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record OrderHistoryTO (
        // ??ID
        Integer orderTranId,
        // ???
        LocalDate orderDate,
        // ??????
        BigDecimal totalPrice,
        // ???
        BigDecimal deliveryPrice,
        // ?????
        String deliveryAddress,
        // ????
        Integer settlementType,
        // ???????
        List<OrderItemTO> items) {
}

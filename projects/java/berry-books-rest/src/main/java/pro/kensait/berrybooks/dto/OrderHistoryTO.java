package pro.kensait.berrybooks.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record OrderHistoryTO (
        // 注文ID
        Integer orderTranId,
        // 注斁E��
        LocalDate orderDate,
        // 注斁E��額合訁E
        BigDecimal totalPrice,
        // 配送料釁E
        BigDecimal deliveryPrice,
        // 配送�E住所
        String deliveryAddress,
        // 決済方況E
        Integer settlementType,
        // 注斁E�E細リスチE
        List<OrderItemTO> items) {
}


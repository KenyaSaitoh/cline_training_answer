package pro.kensait.berrybooks.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record OrderHistoryTO (
        // 豕ｨ譁⑩D
        Integer orderTranId,
        // 豕ｨ譁・律
        LocalDate orderDate,
        // 豕ｨ譁・≡鬘榊粋險・
        BigDecimal totalPrice,
        // 驟埼∵侭驥・
        BigDecimal deliveryPrice,
        // 驟埼∝・菴乗園
        String deliveryAddress,
        // 豎ｺ貂域婿豕・
        Integer settlementType,
        // 豕ｨ譁・・邏ｰ繝ｪ繧ｹ繝・
        List<OrderItemTO> items) {
}


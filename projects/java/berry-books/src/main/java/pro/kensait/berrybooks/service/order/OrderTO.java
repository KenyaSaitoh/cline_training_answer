package pro.kensait.berrybooks.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import pro.kensait.berrybooks.web.cart.CartItem;

// 豕ｨ譁・ｒ菫晄戟縺吶ｋDTO繧ｯ繝ｩ繧ｹ・・ecord縺ｨ縺励※螳夂ｾｩ・・public record OrderTO (
        // 鬘ｧ螳｢ID
        Integer customerId,
        // 豕ｨ譁・律
        LocalDate orderDate,
        // 繧ｫ繝ｼ繝医い繧､繝・Β縺ｮ繝ｪ繧ｹ繝・        List<CartItem> cartItems,
        // 豕ｨ譁・≡鬘榊粋險・        BigDecimal totalPrice,
        // 驟埼∵侭驥・        BigDecimal deliveryPrice,
        // 驟埼∝・菴乗園
        String deliveryAddress,
        // 豎ｺ貂域婿豕・        Integer settlementType) {
}
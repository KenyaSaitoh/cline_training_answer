package pro.kensait.berrybooks.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import pro.kensait.berrybooks.web.cart.CartItem;

// ???????DTO????Record??????
public record OrderTO (
        // ??ID
        Integer customerId,
        // ???
        LocalDate orderDate,
        // ???????????
        List<CartItem> cartItems,
        // ??????
        BigDecimal totalPrice,
        // ???
        BigDecimal deliveryPrice,
        // ?????
        String deliveryAddress,
        // ????
        Integer settlementType) {
}

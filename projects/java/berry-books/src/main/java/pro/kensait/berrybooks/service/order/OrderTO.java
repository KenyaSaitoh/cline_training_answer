package pro.kensait.berrybooks.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import pro.kensait.berrybooks.web.cart.CartItem;

// 注斁E��保持するDTOクラス�E�Eecordとして定義�E�Epublic record OrderTO (
        // 顧客ID
        Integer customerId,
        // 注斁E��
        LocalDate orderDate,
        // カートアイチE��のリスチE        List<CartItem> cartItems,
        // 注斁E��額合訁E        BigDecimal totalPrice,
        // 配送料釁E        BigDecimal deliveryPrice,
        // 配送�E住所
        String deliveryAddress,
        // 決済方況E        Integer settlementType) {
}
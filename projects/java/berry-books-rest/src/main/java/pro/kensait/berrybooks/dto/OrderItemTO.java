package pro.kensait.berrybooks.dto;

import java.math.BigDecimal;

public record OrderItemTO (
        // 注斁E�E細ID
        Integer orderDetailId,
        // 書籍ID
        Integer bookId,
        // 書籍名
        String bookName,
        // 著老E
        String author,
        // 価格�E�購入時点�E�E
        BigDecimal price,
        // 数釁E
        Integer count) {
}


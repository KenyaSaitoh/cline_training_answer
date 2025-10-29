package pro.kensait.berrybooks.dto;

import java.math.BigDecimal;

public record OrderItemTO (
        // ????ID
        Integer orderDetailId,
        // ??ID
        Integer bookId,
        // ???
        String bookName,
        // ??
        String author,
        // ????????
        BigDecimal price,
        // ??
        Integer count) {
}

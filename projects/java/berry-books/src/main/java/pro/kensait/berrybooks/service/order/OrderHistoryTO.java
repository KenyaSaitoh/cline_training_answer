package pro.kensait.berrybooks.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;

// ?????????DTO????Record??????
public record OrderHistoryTO (
        // ???
        LocalDate orderDate,
        // ??ID
        Integer tranId,
        // ????ID
        Integer detailId,
        // ???
        String bookName,
        // ????
        String publisherName,
        // ??
        BigDecimal price,
        // ??
        Integer count) {
}

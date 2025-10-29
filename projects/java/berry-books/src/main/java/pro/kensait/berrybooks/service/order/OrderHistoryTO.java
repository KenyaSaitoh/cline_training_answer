package pro.kensait.berrybooks.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;

// 注斁E��歴を保持するDTOクラス�E�Eecordとして定義�E�E
public record OrderHistoryTO (
        // 注斁E��
        LocalDate orderDate,
        // 注文ID
        Integer tranId,
        // 注斁E�E細ID
        Integer detailId,
        // 書籍名
        String bookName,
        // 出版社吁E
        String publisherName,
        // 価格
        BigDecimal price,
        // 個数
        Integer count) {
}
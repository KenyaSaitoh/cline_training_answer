package pro.kensait.berrybooks.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;

// 注斁E��マリーを保持するDTOクラス�E�注斁E��歴の一覧表示用、Recordとして定義�E�E
public record OrderSummaryTO(
        // 注斁E��引ID
        Integer orderTranId,
        // 注斁E��
        LocalDate orderDate,
        // 明細数
        Long itemCount,
        // 注斁E��額合訁E
        BigDecimal totalPrice) {
    
    // JPQLの COUNT() は Long を返すため、Long を受け取るコンストラクタが忁E��E���E動生成される�E�E
}


package pro.kensait.berrybooks.dto;

import java.math.BigDecimal;

public record OrderItemTO (
        // 豕ｨ譁・・邏ｰID
        Integer orderDetailId,
        // 譖ｸ邀巧D
        Integer bookId,
        // 譖ｸ邀榊錐
        String bookName,
        // 闡苓・
        String author,
        // 萓｡譬ｼ・郁ｳｼ蜈･譎らせ・・
        BigDecimal price,
        // 謨ｰ驥・
        Integer count) {
}


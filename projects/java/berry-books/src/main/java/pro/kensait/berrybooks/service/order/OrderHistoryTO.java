package pro.kensait.berrybooks.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;

// 豕ｨ譁・ｱ･豁ｴ繧剃ｿ晄戟縺吶ｋDTO繧ｯ繝ｩ繧ｹ・・ecord縺ｨ縺励※螳夂ｾｩ・・
public record OrderHistoryTO (
        // 豕ｨ譁・律
        LocalDate orderDate,
        // 豕ｨ譁⑩D
        Integer tranId,
        // 豕ｨ譁・・邏ｰID
        Integer detailId,
        // 譖ｸ邀榊錐
        String bookName,
        // 蜃ｺ迚育､ｾ蜷・
        String publisherName,
        // 萓｡譬ｼ
        BigDecimal price,
        // 蛟区焚
        Integer count) {
}
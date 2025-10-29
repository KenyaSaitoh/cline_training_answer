package pro.kensait.berrybooks.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;

// 豕ｨ譁・し繝槭Μ繝ｼ繧剃ｿ晄戟縺吶ｋDTO繧ｯ繝ｩ繧ｹ・域ｳｨ譁・ｱ･豁ｴ縺ｮ荳隕ｧ陦ｨ遉ｺ逕ｨ縲ヽecord縺ｨ縺励※螳夂ｾｩ・・
public record OrderSummaryTO(
        // 豕ｨ譁・叙蠑肘D
        Integer orderTranId,
        // 豕ｨ譁・律
        LocalDate orderDate,
        // 譏守ｴｰ謨ｰ
        Long itemCount,
        // 豕ｨ譁・≡鬘榊粋險・
        BigDecimal totalPrice) {
    
    // JPQL縺ｮ COUNT() 縺ｯ Long 繧定ｿ斐☆縺溘ａ縲´ong 繧貞女縺大叙繧九さ繝ｳ繧ｹ繝医Λ繧ｯ繧ｿ縺悟ｿ・ｦ・ｼ郁・蜍慕函謌舌＆繧後ｋ・・
}


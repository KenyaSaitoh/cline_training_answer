package pro.kensait.berrybooks.service.delivery;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;

// 驟埼∵侭驥代ｒ險育ｮ励☆繧九し繝ｼ繝薙せ繧ｯ繝ｩ繧ｹ
@ApplicationScoped
public class DeliveryFeeService {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryFeeService.class);

    // 驟埼∵侭驥代・螳壽焚
    private static final BigDecimal STANDARD_DELIVERY_FEE = new BigDecimal("800");
    private static final BigDecimal OKINAWA_DELIVERY_FEE = new BigDecimal("1700");
    private static final BigDecimal FREE_DELIVERY_THRESHOLD = new BigDecimal("5000");
    private static final BigDecimal ZERO = BigDecimal.ZERO;

    // 驟埼∵侭驥代ｒ險育ｮ励☆繧具ｼ磯壼ｸｸ800蜀・∵ｲ也ｸ・恁1700蜀・・000蜀・ｻ･荳翫・騾∵侭辟｡譁呻ｼ・
    public BigDecimal calculateDeliveryFee(String deliveryAddress, BigDecimal totalPrice) {
        logger.info("[ DeliveryFeeService#calculateDeliveryFee ] address={}, totalPrice={}", 
                deliveryAddress, totalPrice);

        // 雉ｼ蜈･驥鷹｡阪′5000蜀・ｻ･荳翫・蝣ｴ蜷医・騾∵侭辟｡譁・
        if (totalPrice.compareTo(FREE_DELIVERY_THRESHOLD) < 0) {
            // 5000蜀・悴貅縺ｮ蝣ｴ蜷・
            
            // 驟埼∝・菴乗園縺梧ｲ也ｸ・恁縺ｮ蝣ｴ蜷医・1700蜀・
            if (deliveryAddress != null && deliveryAddress.startsWith("豐也ｸ・恁")) {
                logger.info("[ DeliveryFeeService ] 豐也ｸ・恁縺ｸ縺ｮ驟埼∵侭驥・ {}", OKINAWA_DELIVERY_FEE);
                return OKINAWA_DELIVERY_FEE;
            }
            
            // 騾壼ｸｸ驟埼∵侭驥代・800蜀・
            logger.info("[ DeliveryFeeService ] 騾壼ｸｸ驟埼∵侭驥・ {}", STANDARD_DELIVERY_FEE);
            return STANDARD_DELIVERY_FEE;
        }
        
        // 5000蜀・ｻ･荳翫・蝣ｴ蜷医・騾∵侭辟｡譁・
        logger.info("[ DeliveryFeeService ] 騾∵侭辟｡譁呻ｼ郁ｳｼ蜈･驥鷹｡砿}蜀・>= {}蜀・ｼ・, 
                totalPrice, FREE_DELIVERY_THRESHOLD);
        return ZERO;
    }

    // 驟埼∝・菴乗園縺梧ｲ也ｸ・恁縺九←縺・°繧貞愛螳壹☆繧・
    public boolean isOkinawa(String deliveryAddress) {
        return deliveryAddress != null && deliveryAddress.startsWith("豐也ｸ・恁");
    }

    // 騾∵侭辟｡譁吝ｯｾ雎｡縺九←縺・°繧貞愛螳壹☆繧・
    public boolean isFreeDelivery(BigDecimal totalPrice) {
        return totalPrice.compareTo(FREE_DELIVERY_THRESHOLD) < 0 == false;
    }
}
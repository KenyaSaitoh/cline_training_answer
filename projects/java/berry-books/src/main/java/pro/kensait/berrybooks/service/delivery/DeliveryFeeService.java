package pro.kensait.berrybooks.service.delivery;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;

// 配送料金を計算するサービスクラス
@ApplicationScoped
public class DeliveryFeeService {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryFeeService.class);

    // 配送料金�E定数
    private static final BigDecimal STANDARD_DELIVERY_FEE = new BigDecimal("800");
    private static final BigDecimal OKINAWA_DELIVERY_FEE = new BigDecimal("1700");
    private static final BigDecimal FREE_DELIVERY_THRESHOLD = new BigDecimal("5000");
    private static final BigDecimal ZERO = BigDecimal.ZERO;

    // 配送料金を計算する（通常800冁E��沖縁E��1700冁E��E000冁E��上�E送料無料！E
    public BigDecimal calculateDeliveryFee(String deliveryAddress, BigDecimal totalPrice) {
        logger.info("[ DeliveryFeeService#calculateDeliveryFee ] address={}, totalPrice={}", 
                deliveryAddress, totalPrice);

        // 購入金額が5000冁E��上�E場合�E送料無斁E
        if (totalPrice.compareTo(FREE_DELIVERY_THRESHOLD) < 0) {
            // 5000冁E��満の場吁E
            
            // 配送�E住所が沖縁E��の場合�E1700冁E
            if (deliveryAddress != null && deliveryAddress.startsWith("沖縁E��")) {
                logger.info("[ DeliveryFeeService ] 沖縁E��への配送料釁E {}", OKINAWA_DELIVERY_FEE);
                return OKINAWA_DELIVERY_FEE;
            }
            
            // 通常配送料金�E800冁E
            logger.info("[ DeliveryFeeService ] 通常配送料釁E {}", STANDARD_DELIVERY_FEE);
            return STANDARD_DELIVERY_FEE;
        }
        
        // 5000冁E��上�E場合�E送料無斁E
        logger.info("[ DeliveryFeeService ] 送料無料（購入金額{}冁E>= {}冁E��E, 
                totalPrice, FREE_DELIVERY_THRESHOLD);
        return ZERO;
    }

    // 配送�E住所が沖縁E��かどぁE��を判定すめE
    public boolean isOkinawa(String deliveryAddress) {
        return deliveryAddress != null && deliveryAddress.startsWith("沖縁E��");
    }

    // 送料無料対象かどぁE��を判定すめE
    public boolean isFreeDelivery(BigDecimal totalPrice) {
        return totalPrice.compareTo(FREE_DELIVERY_THRESHOLD) < 0 == false;
    }
}
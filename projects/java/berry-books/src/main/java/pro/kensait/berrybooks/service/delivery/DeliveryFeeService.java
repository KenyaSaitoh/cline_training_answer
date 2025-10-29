package pro.kensait.berrybooks.service.delivery;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;

// ???????????????
@ApplicationScoped
public class DeliveryFeeService {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryFeeService.class);

    // ??????
    private static final BigDecimal STANDARD_DELIVERY_FEE = new BigDecimal("800");
    private static final BigDecimal OKINAWA_DELIVERY_FEE = new BigDecimal("1700");
    private static final BigDecimal FREE_DELIVERY_THRESHOLD = new BigDecimal("5000");
    private static final BigDecimal ZERO = BigDecimal.ZERO;

    // ???????????800????1700??5000?????????
    public BigDecimal calculateDeliveryFee(String deliveryAddress, BigDecimal totalPrice) {
        logger.info("[ DeliveryFeeService#calculateDeliveryFee ] address={}, totalPrice={}", 
                deliveryAddress, totalPrice);

        // ?????5000???????????
        if (totalPrice.compareTo(FREE_DELIVERY_THRESHOLD) < 0) {
            // 5000??????
            
            // ????????????1700?
            if (deliveryAddress != null && deliveryAddress.startsWith("??")) {
                logger.info("[ DeliveryFeeService ] ???????: {}", OKINAWA_DELIVERY_FEE);
                return OKINAWA_DELIVERY_FEE;
            }
            
            // ??????800?
            logger.info("[ DeliveryFeeService ] ?????: {}", STANDARD_DELIVERY_FEE);
            return STANDARD_DELIVERY_FEE;
        }
        
        // 5000???????????
        logger.info("[ DeliveryFeeService ] ?????????{}? >= {}??", 
                totalPrice, FREE_DELIVERY_THRESHOLD);
        return ZERO;
    }

    // ????????????????
    public boolean isOkinawa(String deliveryAddress) {
        return deliveryAddress != null && deliveryAddress.startsWith("??");
    }

    // ??????????????
    public boolean isFreeDelivery(BigDecimal totalPrice) {
        return totalPrice.compareTo(FREE_DELIVERY_THRESHOLD) < 0 == false;
    }
}

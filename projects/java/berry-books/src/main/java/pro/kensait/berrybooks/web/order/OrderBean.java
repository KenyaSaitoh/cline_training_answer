package pro.kensait.berrybooks.web.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.entity.Customer;
import pro.kensait.berrybooks.entity.OrderDetail;
import pro.kensait.berrybooks.entity.OrderTran;
import pro.kensait.berrybooks.service.delivery.DeliveryFeeService;
import pro.kensait.berrybooks.service.order.OrderHistoryTO;
import pro.kensait.berrybooks.service.order.OrderServiceIF;
import pro.kensait.berrybooks.service.order.OrderTO;
import pro.kensait.berrybooks.service.order.OutOfStockException;
import pro.kensait.berrybooks.util.AddressUtil;
import pro.kensait.berrybooks.web.cart.CartSession;
import pro.kensait.berrybooks.web.customer.CustomerBean;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;

// 豕ｨ譁・・逅・→豕ｨ譁・ｱ･豁ｴ陦ｨ遉ｺ縺ｮ繝舌ャ繧ｭ繝ｳ繧ｰBean
@Named
@ViewScoped
public class OrderBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(
            OrderBean.class);

    @Inject
    private OrderServiceIF orderService;

    @Inject
    private CustomerBean customerBean;

    @Inject
    private CartSession cartSession;

    @Inject
    private DeliveryFeeService deliveryFeeService;

    // 豕ｨ譁・ｱ･豁ｴ
    private List<OrderHistoryTO> orderHistory;
    private List<OrderHistoryTO> orderHistoryList;
    private List<OrderTran> orderList;

    // 豕ｨ譁・ｩｳ邏ｰ
    private OrderTran selectedOrderTran;
    private OrderTran orderTran;
    private List<OrderDetail> orderDetails;
    private OrderDetail orderDetail;

    // 繝薙Η繝ｼ繝代Λ繝｡繝ｼ繧ｿ
    private Integer selectedTranId;
    private Integer selectedDetailId;
    private Integer orderTranId; // 豕ｨ譁・・蜉溽判髱｢逕ｨ

    // 繧ｨ繝ｩ繝ｼ繝｡繝・そ繝ｼ繧ｸ
    private String errorMessage;

    // 繧｢繧ｯ繧ｷ繝ｧ繝ｳ・壽ｳｨ譁・ｒ遒ｺ螳夲ｼ域婿蠑・・・
    public String placeOrder1() {
        logger.info("[ OrderBean#placeOrder1 ]");
        return placeOrderInternal();
    }

    // 繧｢繧ｯ繧ｷ繝ｧ繝ｳ・壽ｳｨ譁・ｒ遒ｺ螳夲ｼ域婿蠑・・・
    public String placeOrder2() {
        logger.info("[ OrderBean#placeOrder2 ]");
        return placeOrderInternal();
    }

    // 蜀・Κ繝｡繧ｽ繝・ラ・壽ｳｨ譁・ｒ遒ｺ螳・
    // 窶ｻ蝓ｺ譛ｬ逧・↑繝舌Μ繝・・繧ｷ繝ｧ繝ｳ縺ｯBean Validation・・artSession・峨〒閾ｪ蜍慕噪縺ｫ螳溯｡後＆繧後ｋ
    private String placeOrderInternal() {
        try {
            // 驟埼∝・菴乗園縺ｮ驛ｽ驕灘ｺ懃恁繧偵メ繧ｧ繝・け縺吶ｋ
            if (cartSession.getDeliveryAddress() != null && 
                    !cartSession.getDeliveryAddress().isBlank() && 
                    !AddressUtil.startsWithValidPrefecture(cartSession.getDeliveryAddress())) {
                logger.info("[ OrderBean#placeOrderInternal ] 驟埼∝・菴乗園蜈･蜉帙お繝ｩ繝ｼ");
                errorMessage = "驟埼∝・菴乗園縺ｯ豁｣縺励＞驛ｽ驕灘ｺ懃恁蜷阪〒蟋九∪繧句ｿ・ｦ√′縺ゅｊ縺ｾ縺・;
                setFlashErrorMessage(errorMessage);
                return "orderError?faces-redirect=true";
            }

            // 驟埼∵侭驥代ｒ蜀崎ｨ育ｮ励☆繧具ｼ磯・騾∝・菴乗園縺悟､画峩縺輔ｌ縺ｦ縺・ｋ蜿ｯ閭ｽ諤ｧ縺後≠繧九◆繧・ｼ・
            BigDecimal deliveryPrice = deliveryFeeService.calculateDeliveryFee(
                    cartSession.getDeliveryAddress(), 
                    cartSession.getTotalPrice());
            cartSession.setDeliveryPrice(deliveryPrice);

            // 繝ｭ繧ｰ繧､繝ｳ荳ｭ縺ｮ鬘ｧ螳｢ID繧貞叙蠕・
            Customer customer = customerBean.getCustomer();
            Integer customerId = (customer != null && customer.getCustomerId() != null) 
                    ? customer.getCustomerId() 
                    : 1;

            OrderTO orderTO = new OrderTO(
                    customerId,
                    LocalDate.now(),
                    new ArrayList<>(cartSession.getCartItems()),
                    cartSession.getTotalPrice(),
                    cartSession.getDeliveryPrice(),
                    cartSession.getDeliveryAddress(),
                    cartSession.getSettlementType());

            orderTran = orderService.orderBooks(orderTO);

            // HTTP繧ｻ繝・す繝ｧ繝ｳ縺九ｉ繧ｫ繝ｼ繝医ｒ蜑企勁
            cartSession.getCartItems().clear();
            cartSession.setTotalPrice(BigDecimal.ZERO);
            cartSession.setDeliveryPrice(BigDecimal.ZERO);
            cartSession.setDeliveryAddress(null);
            cartSession.setSettlementType(null);

            // 豕ｨ譁⑩D繧旦RL繝代Λ繝｡繝ｼ繧ｿ縺ｨ縺励※貂｡縺・
            return "orderSuccess?faces-redirect=true&orderTranId=" + orderTran.getOrderTranId();

        } catch (OutOfStockException e) {
            logger.error("蝨ｨ蠎ｫ荳崎ｶｳ繧ｨ繝ｩ繝ｼ", e);
            errorMessage = "蝨ｨ蠎ｫ荳崎ｶｳ: " + e.getBookName();
            setFlashErrorMessage(errorMessage);
            return "orderError?faces-redirect=true";

        } catch (OptimisticLockException e) {
            logger.error("讌ｽ隕ｳ逧・Ο繝・け繧ｨ繝ｩ繝ｼ", e);
            errorMessage = "莉悶・繝ｦ繝ｼ繧ｶ繝ｼ縺悟酔譎ゅ↓豕ｨ譁・＠縺ｾ縺励◆縲ゅｂ縺・ｸ蠎ｦ縺願ｩｦ縺励￥縺縺輔＞";
            setFlashErrorMessage(errorMessage);
            return "orderError?faces-redirect=true";
        } catch (Exception e) {
            logger.error("豕ｨ譁・お繝ｩ繝ｼ", e);
            errorMessage = "豕ｨ譁・・逅・ｸｭ縺ｫ繧ｨ繝ｩ繝ｼ縺檎匱逕溘＠縺ｾ縺励◆: " + e.getMessage();
            setFlashErrorMessage(errorMessage);
            return "orderError?faces-redirect=true";
        }
    }

    // 繧｢繧ｯ繧ｷ繝ｧ繝ｳ・壽ｳｨ譁・ｱ･豁ｴ繧貞叙蠕暦ｼ域婿蠑・・・
    public void loadOrderHistory() {
        logger.info("[ OrderBean#loadOrderHistory ]");
        Integer customerId = getCustomerId();
        orderHistoryList = orderService.getOrderHistory2(customerId);
    }

    // 繧｢繧ｯ繧ｷ繝ｧ繝ｳ・壽ｳｨ譁・ｱ･豁ｴ繧貞叙蠕暦ｼ域婿蠑・・・
    public void loadOrderHistory2() {
        logger.info("[ OrderBean#loadOrderHistory2 ]");
        Integer customerId = getCustomerId();
        orderHistoryList = orderService.getOrderHistory2(customerId);
    }

    // 繧｢繧ｯ繧ｷ繝ｧ繝ｳ・壽ｳｨ譁・ｱ･豁ｴ繧貞叙蠕暦ｼ域婿蠑・・・
    public void loadOrderHistory3() {
        logger.info("[ OrderBean#loadOrderHistory3 ]");
        Integer customerId = getCustomerId();
        orderList = orderService.getOrderHistory3(customerId);
    }

    // 繧｢繧ｯ繧ｷ繝ｧ繝ｳ・壽ｳｨ譁・ｩｳ邏ｰ繧貞叙蠕・
    public void loadOrderDetail() {
        logger.info("[ OrderBean#loadOrderDetail ] tranId=" + selectedTranId 
                + ", detailId=" + selectedDetailId);
        
        if (selectedTranId != null && selectedDetailId != null) {
            orderDetail = orderService.getOrderDetail(selectedTranId, selectedDetailId);
        }
    }

    // 繧｢繧ｯ繧ｷ繝ｧ繝ｳ・壽ｳｨ譁・ｩｳ邏ｰ繧定｡ｨ遉ｺ
    public String showOrderDetail(Integer orderTranId) {
        logger.info("[ OrderBean#showOrderDetail ] orderTranId=" + orderTranId);
        
        selectedOrderTran = orderService.getOrderTran(orderTranId);
        orderDetails = orderService.getOrderDetails(orderTranId);
        
        return "orderDetail"; // 豕ｨ譁・ｩｳ邏ｰ逕ｻ髱｢縺ｸ
    }

    // 繝倥Ν繝代・・夐｡ｧ螳｢ID繧貞叙蠕・
    private Integer getCustomerId() {
        Customer customer = customerBean.getCustomer();
        return (customer != null && customer.getCustomerId() != null) 
                ? customer.getCustomerId() 
                : 1;
    }

    /**
     * FlashScope縺ｫ繧ｨ繝ｩ繝ｼ繝｡繝・そ繝ｼ繧ｸ繧定ｨｭ螳・
     * 窶ｻ繝ｪ繝繧､繝ｬ繝・ヨ蠕後ｂ繝｡繝・そ繝ｼ繧ｸ繧剃ｿ晄戟縺吶ｋ縺溘ａ
     */
    private void setFlashErrorMessage(String message) {
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .put("errorMessage", message);
    }

    // Getters and Setters (CartSession縺ｸ縺ｮ蟋碑ｭｲ)
    public String getDeliveryAddress() {
        return cartSession.getDeliveryAddress();
    }

    public void setDeliveryAddress(String deliveryAddress) {
        cartSession.setDeliveryAddress(deliveryAddress);
    }

    public Integer getSettlementType() {
        return cartSession.getSettlementType();
    }

    public void setSettlementType(Integer settlementType) {
        cartSession.setSettlementType(settlementType);
    }

    public List<OrderHistoryTO> getOrderHistory() {
        return orderHistory;
    }

    public List<OrderHistoryTO> getOrderHistoryList() {
        return orderHistoryList;
    }

    public List<OrderTran> getOrderList() {
        return orderList;
    }

    public OrderTran getSelectedOrderTran() {
        return selectedOrderTran;
    }

    public OrderTran getOrderTran() {
        return orderTran;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public Integer getSelectedTranId() {
        return selectedTranId;
    }

    public void setSelectedTranId(Integer selectedTranId) {
        this.selectedTranId = selectedTranId;
    }

    public Integer getSelectedDetailId() {
        return selectedDetailId;
    }

    public void setSelectedDetailId(Integer selectedDetailId) {
        this.selectedDetailId = selectedDetailId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getOrderTranId() {
        return orderTranId;
    }

    public void setOrderTranId(Integer orderTranId) {
        this.orderTranId = orderTranId;
    }

    // 繧｢繧ｯ繧ｷ繝ｧ繝ｳ・壽ｳｨ譁・・蜉溽判髱｢逕ｨ縺ｫ繝・・繧ｿ繧偵Ο繝ｼ繝・
    public void loadOrderSuccess() {
        logger.info("[ OrderBean#loadOrderSuccess ] orderTranId=" + orderTranId);
        if (orderTranId != null) {
            orderTran = orderService.getOrderTranWithDetails(orderTranId);
        }
    }
}


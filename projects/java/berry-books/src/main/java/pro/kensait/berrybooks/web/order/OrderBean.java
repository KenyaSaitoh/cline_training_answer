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

// 注斁E�E琁E��注斁E��歴表示のバッキングBean
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

    // 注斁E��歴
    private List<OrderHistoryTO> orderHistory;
    private List<OrderHistoryTO> orderHistoryList;
    private List<OrderTran> orderList;

    // 注斁E��細
    private OrderTran selectedOrderTran;
    private OrderTran orderTran;
    private List<OrderDetail> orderDetails;
    private OrderDetail orderDetail;

    // ビューパラメータ
    private Integer selectedTranId;
    private Integer selectedDetailId;
    private Integer orderTranId; // 注斁E�E功画面用

    // エラーメチE��ージ
    private String errorMessage;

    // アクション�E�注斁E��確定（方弁E�E�E
    public String placeOrder1() {
        logger.info("[ OrderBean#placeOrder1 ]");
        return placeOrderInternal();
    }

    // アクション�E�注斁E��確定（方弁E�E�E
    public String placeOrder2() {
        logger.info("[ OrderBean#placeOrder2 ]");
        return placeOrderInternal();
    }

    // 冁E��メソチE���E�注斁E��確宁E
    // ※基本皁E��バリチE�EションはBean Validation�E�EartSession�E�で自動的に実行される
    private String placeOrderInternal() {
        try {
            // 配送�E住所の都道府県をチェチE��する
            if (cartSession.getDeliveryAddress() != null && 
                    !cartSession.getDeliveryAddress().isBlank() && 
                    !AddressUtil.startsWithValidPrefecture(cartSession.getDeliveryAddress())) {
                logger.info("[ OrderBean#placeOrderInternal ] 配送�E住所入力エラー");
                errorMessage = "配送�E住所は正しい都道府県名で始まる忁E��がありまぁE;
                setFlashErrorMessage(errorMessage);
                return "orderError?faces-redirect=true";
            }

            // 配送料金を再計算する（�E送�E住所が変更されてぁE��可能性があるためE��E
            BigDecimal deliveryPrice = deliveryFeeService.calculateDeliveryFee(
                    cartSession.getDeliveryAddress(), 
                    cartSession.getTotalPrice());
            cartSession.setDeliveryPrice(deliveryPrice);

            // ログイン中の顧客IDを取征E
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

            // HTTPセチE��ョンからカートを削除
            cartSession.getCartItems().clear();
            cartSession.setTotalPrice(BigDecimal.ZERO);
            cartSession.setDeliveryPrice(BigDecimal.ZERO);
            cartSession.setDeliveryAddress(null);
            cartSession.setSettlementType(null);

            // 注文IDをURLパラメータとして渡ぁE
            return "orderSuccess?faces-redirect=true&orderTranId=" + orderTran.getOrderTranId();

        } catch (OutOfStockException e) {
            logger.error("在庫不足エラー", e);
            errorMessage = "在庫不足: " + e.getBookName();
            setFlashErrorMessage(errorMessage);
            return "orderError?faces-redirect=true";

        } catch (OptimisticLockException e) {
            logger.error("楽観皁E��チE��エラー", e);
            errorMessage = "他�Eユーザーが同時に注斁E��ました。もぁE��度お試しください";
            setFlashErrorMessage(errorMessage);
            return "orderError?faces-redirect=true";
        } catch (Exception e) {
            logger.error("注斁E��ラー", e);
            errorMessage = "注斁E�E琁E��にエラーが発生しました: " + e.getMessage();
            setFlashErrorMessage(errorMessage);
            return "orderError?faces-redirect=true";
        }
    }

    // アクション�E�注斁E��歴を取得（方弁E�E�E
    public void loadOrderHistory() {
        logger.info("[ OrderBean#loadOrderHistory ]");
        Integer customerId = getCustomerId();
        orderHistoryList = orderService.getOrderHistory2(customerId);
    }

    // アクション�E�注斁E��歴を取得（方弁E�E�E
    public void loadOrderHistory2() {
        logger.info("[ OrderBean#loadOrderHistory2 ]");
        Integer customerId = getCustomerId();
        orderHistoryList = orderService.getOrderHistory2(customerId);
    }

    // アクション�E�注斁E��歴を取得（方弁E�E�E
    public void loadOrderHistory3() {
        logger.info("[ OrderBean#loadOrderHistory3 ]");
        Integer customerId = getCustomerId();
        orderList = orderService.getOrderHistory3(customerId);
    }

    // アクション�E�注斁E��細を取征E
    public void loadOrderDetail() {
        logger.info("[ OrderBean#loadOrderDetail ] tranId=" + selectedTranId 
                + ", detailId=" + selectedDetailId);
        
        if (selectedTranId != null && selectedDetailId != null) {
            orderDetail = orderService.getOrderDetail(selectedTranId, selectedDetailId);
        }
    }

    // アクション�E�注斁E��細を表示
    public String showOrderDetail(Integer orderTranId) {
        logger.info("[ OrderBean#showOrderDetail ] orderTranId=" + orderTranId);
        
        selectedOrderTran = orderService.getOrderTran(orderTranId);
        orderDetails = orderService.getOrderDetails(orderTranId);
        
        return "orderDetail"; // 注斁E��細画面へ
    }

    // ヘルパ�E�E�顧客IDを取征E
    private Integer getCustomerId() {
        Customer customer = customerBean.getCustomer();
        return (customer != null && customer.getCustomerId() != null) 
                ? customer.getCustomerId() 
                : 1;
    }

    /**
     * FlashScopeにエラーメチE��ージを設宁E
     * ※リダイレチE��後もメチE��ージを保持するため
     */
    private void setFlashErrorMessage(String message) {
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .put("errorMessage", message);
    }

    // Getters and Setters (CartSessionへの委譲)
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

    // アクション�E�注斁E�E功画面用にチE�EタをローチE
    public void loadOrderSuccess() {
        logger.info("[ OrderBean#loadOrderSuccess ] orderTranId=" + orderTranId);
        if (orderTranId != null) {
            orderTran = orderService.getOrderTranWithDetails(orderTranId);
        }
    }
}


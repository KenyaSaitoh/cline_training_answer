package pro.kensait.berrybooks.web.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// ?????????????????????????
@Named
@SessionScoped
public class CartSession implements Serializable {
    private static final long serialVersionUID = 1L;

    // ???????????
    private List<CartItem> cartItems = new CopyOnWriteArrayList<>();
    
    // ??????
    private BigDecimal totalPrice = BigDecimal.ZERO;
    
    // ???
    private BigDecimal deliveryPrice = BigDecimal.ZERO;
    
    // ?????
    @NotBlank(message = "??????????????")
    @Size(max = 200, message = "??????200?????????????")
    private String deliveryAddress;
    
    // ????
    @NotNull(message = "?????????????")
    private Integer settlementType;

    // ????????????
    public CartSession() {
    }

    // ???????????????????
    public CartSession(List<CartItem> cartItems, BigDecimal totalPrice,
            BigDecimal deliveryPrice, String deliveryAddress, Integer settlementType) {
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
        this.deliveryPrice = deliveryPrice;
        this.deliveryAddress = deliveryAddress;
        this.settlementType = settlementType;
    }

    /**
     * ????????????
     */
    public void recalculateTotalPrice() {
        totalPrice = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            totalPrice = totalPrice.add(item.getPrice());
        }
    }

    // ????????
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Integer getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(Integer settlementType) {
        this.settlementType = settlementType;
    }

    @Override
    public String toString() {
        return "CartSession [cartItems=" + cartItems + ", totalPrice=" + totalPrice
                + ", deliveryPrice=" + deliveryPrice + ", deliveryAddress="
                + deliveryAddress + ", settlementType=" + settlementType + "]";
    }
}

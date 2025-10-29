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

// ショチE��ングカート�EセチE��ョン惁E��を管琁E��るクラス
@Named
@SessionScoped
public class CartSession implements Serializable {
    private static final long serialVersionUID = 1L;

    // カートアイチE��のリスチE
    private List<CartItem> cartItems = new CopyOnWriteArrayList<>();
    
    // 注斁E��額合訁E
    private BigDecimal totalPrice = BigDecimal.ZERO;
    
    // 配送料釁E
    private BigDecimal deliveryPrice = BigDecimal.ZERO;
    
    // 配送�E住所
    @NotBlank(message = "配送�E住所を�E力してください")
    @Size(max = 200, message = "配送�E住所は200斁E��以冁E��入力してください")
    private String deliveryAddress;
    
    // 決済方況E
    @NotNull(message = "決済方法を選択してください")
    private Integer settlementType;

    // 引数の無ぁE��ンストラクタ
    public CartSession() {
    }

    // 全フィールドを引数にとるコンストラクタ
    public CartSession(List<CartItem> cartItems, BigDecimal totalPrice,
            BigDecimal deliveryPrice, String deliveryAddress, Integer settlementType) {
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
        this.deliveryPrice = deliveryPrice;
        this.deliveryAddress = deliveryAddress;
        this.settlementType = settlementType;
    }

    /**
     * 合計��額を再計算すめE
     */
    public void recalculateTotalPrice() {
        totalPrice = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            totalPrice = totalPrice.add(item.getPrice());
        }
    }

    // アクセサメソチE��
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


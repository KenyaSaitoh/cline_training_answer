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

// 繧ｷ繝ｧ繝・ヴ繝ｳ繧ｰ繧ｫ繝ｼ繝医・繧ｻ繝・す繝ｧ繝ｳ諠・ｱ繧堤ｮ｡逅・☆繧九け繝ｩ繧ｹ
@Named
@SessionScoped
public class CartSession implements Serializable {
    private static final long serialVersionUID = 1L;

    // 繧ｫ繝ｼ繝医い繧､繝・Β縺ｮ繝ｪ繧ｹ繝・
    private List<CartItem> cartItems = new CopyOnWriteArrayList<>();
    
    // 豕ｨ譁・≡鬘榊粋險・
    private BigDecimal totalPrice = BigDecimal.ZERO;
    
    // 驟埼∵侭驥・
    private BigDecimal deliveryPrice = BigDecimal.ZERO;
    
    // 驟埼∝・菴乗園
    @NotBlank(message = "驟埼∝・菴乗園繧貞・蜉帙＠縺ｦ縺上□縺輔＞")
    @Size(max = 200, message = "驟埼∝・菴乗園縺ｯ200譁・ｭ嶺ｻ･蜀・〒蜈･蜉帙＠縺ｦ縺上□縺輔＞")
    private String deliveryAddress;
    
    // 豎ｺ貂域婿豕・
    @NotNull(message = "豎ｺ貂域婿豕輔ｒ驕ｸ謚槭＠縺ｦ縺上□縺輔＞")
    private Integer settlementType;

    // 蠑墓焚縺ｮ辟｡縺・さ繝ｳ繧ｹ繝医Λ繧ｯ繧ｿ
    public CartSession() {
    }

    // 蜈ｨ繝輔ぅ繝ｼ繝ｫ繝峨ｒ蠑墓焚縺ｫ縺ｨ繧九さ繝ｳ繧ｹ繝医Λ繧ｯ繧ｿ
    public CartSession(List<CartItem> cartItems, BigDecimal totalPrice,
            BigDecimal deliveryPrice, String deliveryAddress, Integer settlementType) {
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
        this.deliveryPrice = deliveryPrice;
        this.deliveryAddress = deliveryAddress;
        this.settlementType = settlementType;
    }

    /**
     * 蜷郁ｨ磯≡鬘阪ｒ蜀崎ｨ育ｮ励☆繧・
     */
    public void recalculateTotalPrice() {
        totalPrice = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            totalPrice = totalPrice.add(item.getPrice());
        }
    }

    // 繧｢繧ｯ繧ｻ繧ｵ繝｡繧ｽ繝・ラ
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


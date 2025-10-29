package pro.kensait.berrybooks.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// 豕ｨ譁・ｒ陦ｨ縺吶お繝ｳ繝・ぅ繝・ぅ繧ｯ繝ｩ繧ｹ
@Entity
@Table(name = "ORDER_TRAN")
public class OrderTran implements Serializable {
    private static final long serialVersionUID = 1L;
    // 豕ｨ譁⑩D
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ORDER_TRAN_ID")
    private Integer orderTranId;

    // 豕ｨ譁・律莉・
    @Column(name = "ORDER_DATE")
    private LocalDate orderDate;

    // 鬘ｧ螳｢
    @Column(name = "CUSTOMER_ID")
    private Integer customerId;

    // 豕ｨ譁・・邏ｰ
    @OneToMany(targetEntity = OrderDetail.class,
            mappedBy = "orderTran",
            fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetails;

    // 豕ｨ譁・≡鬘榊粋險・
    @Column(name = "TOTAL_PRICE")
    private BigDecimal totalPrice;

    // 驟埼∵侭驥・
    @Column(name = "DELIVERY_PRICE")
    private BigDecimal deliveryPrice;

    // 驟埼∝・菴乗園
    @Column(name = "DELIVERY_ADDRESS")
    private String deliveryAddress;

    // 豎ｺ貂域婿豕輔∈縺ｮ繧｢繧ｯ繧ｻ繧ｵ繝｡繧ｽ繝・ラ
    @Column(name = "SETTLEMENT_TYPE")
    private Integer settlementType;

    // 蠑墓焚縺ｪ縺励・繧ｳ繝ｳ繧ｹ繝医Λ繧ｯ繧ｿ
    public OrderTran() {
    }

    // 繧ｳ繝ｳ繧ｹ繝医Λ繧ｯ繧ｿ
    public OrderTran(LocalDate orderDate, Integer customerId, BigDecimal totalPrice, 
            BigDecimal deliveryPrice, String deliveryAddress, Integer settlementType) {
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.deliveryPrice = deliveryPrice;
        this.deliveryAddress = deliveryAddress;
        this.settlementType = settlementType;
    }

    public Integer getOrderTranId() {
        return orderTranId;
    }

    public void setOrderTranId(Integer orderTranId) {
        this.orderTranId = orderTranId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getCustomer() {
        return customerId;
    }

    public void setCustomer(Integer customerId) {
        this.customerId = customerId;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
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
        return "OrderTran [orderTranId=" + orderTranId + ", orderDate=" + orderDate
                + ", customerId=" + customerId + ", totalPrice=" + totalPrice
                + ", deliveryPrice=" + deliveryPrice + ", deliveryAddress="
                + deliveryAddress + ", settlementType=" + settlementType + "]";
    }
}
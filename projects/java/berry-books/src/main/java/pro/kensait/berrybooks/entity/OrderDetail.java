package pro.kensait.berrybooks.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// ????????????????
@Entity
@Table(name = "ORDER_DETAIL")
@IdClass(OrderDetailPK.class)
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    // ??ID
    @Id
    @Column(name = "ORDER_TRAN_ID",
            nullable = false)
    private Integer orderTranId;

    // ????ID
    @Id
    @Column(name = "ORDER_DETAIL_ID",
            nullable = false)
    private Integer orderDetailId;

    // ??
    @ManyToOne(targetEntity = OrderTran.class)
    @JoinColumn(name = "ORDER_TRAN_ID",
            referencedColumnName = "ORDER_TRAN_ID",
            insertable = false, updatable = false) // ????????JPA????????
    private OrderTran orderTran;

    // ??
    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(name = "BOOK_ID",
            referencedColumnName = "BOOK_ID")
    private Book book;

    // ??
    // ????????????????????????????????????
    @Column(name = "PRICE")
    private BigDecimal price;

    // ???
    @Column(name = "COUNT")
    private Integer count;

    // ???????????
    public OrderDetail() {
    }

    // ???????
    public OrderDetail(Integer orderTranId, Integer orderDetailId, Book book, 
            Integer count) {
        this.orderTranId = orderTranId;
        this.orderDetailId = orderDetailId;
        this.book = book;
        this.price = book.getPrice();
        this.count = count;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public OrderTran getOrderTran() {
        return orderTran;
    }

    public void setOrderTran(OrderTran orderTran) {
        this.orderTran = orderTran;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "OrderDetail [orderTranId=" + orderTranId + ", orderDetailId="
                + orderDetailId + ", orderTran=" + orderTran + ", book=" + book
                + ", price=" + price + ", count=" + count + "]";
    }
}

package pro.kensait.berrybooks.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// 蝨ｨ蠎ｫ諠・ｱ繧定｡ｨ縺吶お繝ｳ繝・ぅ繝・ぅ繧ｯ繝ｩ繧ｹ
@Entity
@Table(name = "STOCK")
public class Stock {
    // 譖ｸ邀巧D
    @Id
    @Column(name = "BOOK_ID")
    private Integer bookId;

    // 蝨ｨ蠎ｫ謨ｰ
    @Column(name = "QUANTITY")
    private Integer quantity;

    // 繝舌・繧ｸ繝ｧ繝ｳ
    @Column(name = "VERSION")
    private Long version;

    //  蠑墓焚縺ｪ縺励・繧ｳ繝ｳ繧ｹ繝医Λ繧ｯ繧ｿ
    public Stock() {
    }

    // 繧ｳ繝ｳ繧ｹ繝医Λ繧ｯ繧ｿ
    public Stock(Integer bookId, Integer quantity, Long version) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.version = version;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Stock [bookId=" + bookId + ", quantity=" + quantity + ", version="
                + version + "]";
    }
}
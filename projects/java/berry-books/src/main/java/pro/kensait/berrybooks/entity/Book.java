package pro.kensait.berrybooks.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;

// 譖ｸ邀阪ｒ陦ｨ縺吶お繝ｳ繝・ぅ繝・ぅ繧ｯ繝ｩ繧ｹ
@Entity
@Table(name = "BOOK")
@SecondaryTable(name = "STOCK",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "BOOK_ID"))
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    // 譖ｸ邀巧D
    @Id
    @Column(name = "BOOK_ID")
    private Integer bookId;

    // 譖ｸ邀榊錐
    @Column(name = "BOOK_NAME")
    private String bookName;

    // 闡苓・
    @Column(name = "AUTHOR")
    private String author;

    // 繧ｫ繝・ざ繝ｪ
    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "CATEGORY_ID",
            referencedColumnName = "CATEGORY_ID")
    private Category category;

    // 蜃ｺ迚育､ｾ
    @ManyToOne(targetEntity = Publisher.class)
    @JoinColumn(name = "PUBLISHER_ID",
            referencedColumnName = "PUBLISHER_ID")
    private Publisher publisher;

    // 萓｡譬ｼ
    @Column(name = "PRICE")
    private BigDecimal price;

    // 蝨ｨ蠎ｫ謨ｰ
    @Column(table = "STOCK", name = "QUANTITY")
    private Integer quantity;

    // 蠑墓焚縺ｪ縺励・繧ｳ繝ｳ繧ｹ繝医Λ繧ｯ繧ｿ
    public Book() {
    }

    // 繧ｳ繝ｳ繧ｹ繝医Λ繧ｯ繧ｿ
    public Book(Integer bookId, String bookName, String author, Category category, 
            Publisher publisher, BigDecimal price, Integer quantity) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.category = category;
        this.publisher = publisher;
        this.price = price;
        this.quantity = quantity;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Book [bookId=" + bookId + ", bookName=" + bookName + ", author=" + author
                + ", category=" + category + ", publisher=" + publisher + ", price="
                + price + ", quantity=" + quantity + "]";
    }
}
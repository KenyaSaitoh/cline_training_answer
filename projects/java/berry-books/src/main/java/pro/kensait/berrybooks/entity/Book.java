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

// ??????????????
@Entity
@Table(name = "BOOK")
@SecondaryTable(name = "STOCK",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "BOOK_ID"))
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    // ??ID
    @Id
    @Column(name = "BOOK_ID")
    private Integer bookId;

    // ???
    @Column(name = "BOOK_NAME")
    private String bookName;

    // ??
    @Column(name = "AUTHOR")
    private String author;

    // ????
    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "CATEGORY_ID",
            referencedColumnName = "CATEGORY_ID")
    private Category category;

    // ???
    @ManyToOne(targetEntity = Publisher.class)
    @JoinColumn(name = "PUBLISHER_ID",
            referencedColumnName = "PUBLISHER_ID")
    private Publisher publisher;

    // ??
    @Column(name = "PRICE")
    private BigDecimal price;

    // ???
    @Column(table = "STOCK", name = "QUANTITY")
    private Integer quantity;

    // ???????????
    public Book() {
    }

    // ???????
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

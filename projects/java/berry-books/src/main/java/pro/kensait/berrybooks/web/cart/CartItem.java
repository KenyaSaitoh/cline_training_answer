package pro.kensait.berrybooks.web.cart;

import java.math.BigDecimal;

// ???????????????
public class CartItem {
    // ??ID
    private Integer bookId;
    // ???
    private String bookName;
    // ????
    private String publisherName;
    // ??
    private BigDecimal price;
    // ??
    private Integer count;
    // ?????
    private boolean remove;

    // ????????????
    public CartItem() {
    }

    // ????????????????????????
    public CartItem(Integer bookId, String bookName, String publisherName,
            BigDecimal price, Integer count, boolean remove) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.publisherName = publisherName;
        this.price = price;
        this.count = count;
        this.remove = remove;
    }

    // ????????
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

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    // ???????????
    public BigDecimal getSubtotal() {
        if (price == null || count == null) {
            return BigDecimal.ZERO;
        }
        return price.multiply(BigDecimal.valueOf(count));
    }

    @Override
    public String toString() {
        return "CartItem [bookId=" + bookId + ", bookName=" + bookName
                + ", publisherName=" + publisherName + ", price=" + price + ", count="
                + count + ", remove=" + remove + "]";
    }
}

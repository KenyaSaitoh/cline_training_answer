package pro.kensait.berrybooks.web.cart;

import java.io.Serializable;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.entity.Book;
import pro.kensait.berrybooks.service.book.BookService;
import pro.kensait.berrybooks.service.delivery.DeliveryFeeService;
import pro.kensait.berrybooks.web.customer.CustomerBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

// ?????????????????Bean
@Named
@SessionScoped
public class CartBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(
            CartBean.class);

    @Inject
    private BookService bookService;

    @Inject
    private CartSession cartSession;

    @Inject
    private CustomerBean customerBean;

    @Inject
    private DeliveryFeeService deliveryFeeService;

    // ???????????????
    public String addBook(Integer bookId, Integer count) {
        logger.info("[ CartBean#addBook ] bookId=" + bookId + ", count=" + count);

        Book book = bookService.getBook(bookId);

        // ?????????????????????????????????
        boolean isExists = false;
        for (CartItem cartItem : cartSession.getCartItems()) {
            if (bookId.equals(cartItem.getBookId())) {
                cartItem.setCount(cartItem.getCount() + 1);
                cartItem.setPrice(cartItem.getPrice().add(book.getPrice()));
                isExists = true;
                break;
            }
        }

        // ??????????????????????????CartItem????????????
        if (!isExists) {
            CartItem cartItem = new CartItem(
                    book.getBookId(),
                    book.getBookName(),
                    book.getPublisher().getPublisherName(),
                    book.getPrice(),
                    1,
                    false);
            cartSession.getCartItems().add(cartItem);
        }

        // ???????????
        BigDecimal totalPrice = cartSession.getTotalPrice();
        cartSession.setTotalPrice(totalPrice.add(book.getPrice()));

        return "cartView?faces-redirect=true";
    }

    // ????????????????????
    public String removeSelectedBooks() {
        logger.info("[ CartBean#removeSelectedBooks ]");
        
        // ??????????????????????
        cartSession.getCartItems().removeIf(item -> {
            if (item.isRemove()) {
                BigDecimal totalPrice = cartSession.getTotalPrice();
                cartSession.setTotalPrice(totalPrice.subtract(item.getPrice()));
                return true;
            }
            return false;
        });
        
        return null;
    }

    // ?????????????
    public String clearCart() {
        logger.info("[ CartBean#clearCart ]");
        cartSession.getCartItems().clear();
        cartSession.setTotalPrice(BigDecimal.ZERO);
        cartSession.setDeliveryPrice(BigDecimal.ZERO);
        return "cartClear?faces-redirect=true";
    }

    // ?????????????????
    public String proceedToOrder() {
        logger.info("[ CartBean#proceedToOrder ]");
        
        if (cartSession.getCartItems().isEmpty()) {
            return null;
        }

        // ?????????????????????????
        if (customerBean.getCustomer() != null) {
            cartSession.setDeliveryAddress(customerBean.getCustomer().getAddress());
        }

        // ????????
        // ???800?????1700??5000????????
        BigDecimal deliveryPrice = deliveryFeeService.calculateDeliveryFee(
                cartSession.getDeliveryAddress(), 
                cartSession.getTotalPrice());
        cartSession.setDeliveryPrice(deliveryPrice);

        return "bookOrder?faces-redirect=true";
    }

    // ??????????????
    public String viewCart() {
        logger.info("[ CartBean#viewCart ]");

        // ????????????????????????????????
        if (cartSession.getCartItems().size() == 0) {
            logger.info("[ CartBean#viewCart ] ???????????");
            globalErrorMessage = "?????????????";
        }

        return "cartView?faces-redirect=true";
    }

    // ?????????????
    private String globalErrorMessage;

    public String getGlobalErrorMessage() {
        return globalErrorMessage;
    }

    public void setGlobalErrorMessage(String globalErrorMessage) {
        this.globalErrorMessage = globalErrorMessage;
    }

    // CartSession????????
    public BigDecimal getTotalPrice() {
        return cartSession.getTotalPrice();
    }

    public BigDecimal getDeliveryPrice() {
        return cartSession.getDeliveryPrice();
    }

    public boolean isCartEmpty() {
        return cartSession.getCartItems().isEmpty();
    }
}

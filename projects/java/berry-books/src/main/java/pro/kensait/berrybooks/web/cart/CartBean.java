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

// ショチE��ングカート操作�EバッキングBean
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

    // アクション�E�書籍をカートに追加
    public String addBook(Integer bookId, Integer count) {
        logger.info("[ CartBean#addBook ] bookId=" + bookId + ", count=" + count);

        Book book = bookService.getBook(bookId);

        // 選択された書籍がカートに存在してぁE��場合�E、注斁E��と金額を加算すめE
        boolean isExists = false;
        for (CartItem cartItem : cartSession.getCartItems()) {
            if (bookId.equals(cartItem.getBookId())) {
                cartItem.setCount(cartItem.getCount() + 1);
                cartItem.setPrice(cartItem.getPrice().add(book.getPrice()));
                isExists = true;
                break;
            }
        }

        // 選択された書籍がカートに存在してぁE��ぁE��合�E、新しいCartItemを生成しカートに追加する
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

        // 合計��額を加算すめE
        BigDecimal totalPrice = cartSession.getTotalPrice();
        cartSession.setTotalPrice(totalPrice.add(book.getPrice()));

        return "cartView?faces-redirect=true";
    }

    // アクション�E�選択した書籍をカートから削除
    public String removeSelectedBooks() {
        logger.info("[ CartBean#removeSelectedBooks ]");
        
        // 選択された書籍を削除し、合計��額を再計箁E
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

    // アクション�E�カートをクリア
    public String clearCart() {
        logger.info("[ CartBean#clearCart ]");
        cartSession.getCartItems().clear();
        cartSession.setTotalPrice(BigDecimal.ZERO);
        cartSession.setDeliveryPrice(BigDecimal.ZERO);
        return "cartClear?faces-redirect=true";
    }

    // アクション�E�カート�E冁E��を確定すめE
    public String proceedToOrder() {
        logger.info("[ CartBean#proceedToOrder ]");
        
        if (cartSession.getCartItems().isEmpty()) {
            return null;
        }

        // チE��ォルト�E配送�E住所として、E��客の住所を設定すめE
        if (customerBean.getCustomer() != null) {
            cartSession.setDeliveryAddress(customerBean.getCustomer().getAddress());
        }

        // 配送料金を計算すめE
        // ※通常800冁E��沖縁E��は1700冁E��E000冁E��上�E送料無斁E
        BigDecimal deliveryPrice = deliveryFeeService.calculateDeliveryFee(
                cartSession.getDeliveryAddress(), 
                cartSession.getTotalPrice());
        cartSession.setDeliveryPrice(deliveryPrice);

        return "bookOrder?faces-redirect=true";
    }

    // アクション�E�カートを参�Eする
    public String viewCart() {
        logger.info("[ CartBean#viewCart ]");

        // カートに啁E��が一つも�EってぁE��かった場合�E、エラーメチE��ージを設宁E
        if (cartSession.getCartItems().size() == 0) {
            logger.info("[ CartBean#viewCart ] カートに啁E��なしエラー");
            globalErrorMessage = "カートに啁E��が�EってぁE��せん";
        }

        return "cartView?faces-redirect=true";
    }

    // グローバルエラーメチE��ージ
    private String globalErrorMessage;

    public String getGlobalErrorMessage() {
        return globalErrorMessage;
    }

    public void setGlobalErrorMessage(String globalErrorMessage) {
        this.globalErrorMessage = globalErrorMessage;
    }

    // CartSessionへの委譲メソチE��
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


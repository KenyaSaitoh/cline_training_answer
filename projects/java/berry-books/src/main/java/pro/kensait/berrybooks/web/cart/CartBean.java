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

// 繧ｷ繝ｧ繝・ヴ繝ｳ繧ｰ繧ｫ繝ｼ繝域桃菴懊・繝舌ャ繧ｭ繝ｳ繧ｰBean
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

    // 繧｢繧ｯ繧ｷ繝ｧ繝ｳ・壽嶌邀阪ｒ繧ｫ繝ｼ繝医↓霑ｽ蜉
    public String addBook(Integer bookId, Integer count) {
        logger.info("[ CartBean#addBook ] bookId=" + bookId + ", count=" + count);

        Book book = bookService.getBook(bookId);

        // 驕ｸ謚槭＆繧後◆譖ｸ邀阪′繧ｫ繝ｼ繝医↓蟄伜惠縺励※縺・ｋ蝣ｴ蜷医・縲∵ｳｨ譁・焚縺ｨ驥鷹｡阪ｒ蜉邂励☆繧・
        boolean isExists = false;
        for (CartItem cartItem : cartSession.getCartItems()) {
            if (bookId.equals(cartItem.getBookId())) {
                cartItem.setCount(cartItem.getCount() + 1);
                cartItem.setPrice(cartItem.getPrice().add(book.getPrice()));
                isExists = true;
                break;
            }
        }

        // 驕ｸ謚槭＆繧後◆譖ｸ邀阪′繧ｫ繝ｼ繝医↓蟄伜惠縺励※縺・↑縺・ｴ蜷医・縲∵眠縺励＞CartItem繧堤函謌舌＠繧ｫ繝ｼ繝医↓霑ｽ蜉縺吶ｋ
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

        // 蜷郁ｨ磯≡鬘阪ｒ蜉邂励☆繧・
        BigDecimal totalPrice = cartSession.getTotalPrice();
        cartSession.setTotalPrice(totalPrice.add(book.getPrice()));

        return "cartView?faces-redirect=true";
    }

    // 繧｢繧ｯ繧ｷ繝ｧ繝ｳ・夐∈謚槭＠縺滓嶌邀阪ｒ繧ｫ繝ｼ繝医°繧牙炎髯､
    public String removeSelectedBooks() {
        logger.info("[ CartBean#removeSelectedBooks ]");
        
        // 驕ｸ謚槭＆繧後◆譖ｸ邀阪ｒ蜑企勁縺励∝粋險磯≡鬘阪ｒ蜀崎ｨ育ｮ・
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

    // 繧｢繧ｯ繧ｷ繝ｧ繝ｳ・壹き繝ｼ繝医ｒ繧ｯ繝ｪ繧｢
    public String clearCart() {
        logger.info("[ CartBean#clearCart ]");
        cartSession.getCartItems().clear();
        cartSession.setTotalPrice(BigDecimal.ZERO);
        cartSession.setDeliveryPrice(BigDecimal.ZERO);
        return "cartClear?faces-redirect=true";
    }

    // 繧｢繧ｯ繧ｷ繝ｧ繝ｳ・壹き繝ｼ繝医・蜀・ｮｹ繧堤｢ｺ螳壹☆繧・
    public String proceedToOrder() {
        logger.info("[ CartBean#proceedToOrder ]");
        
        if (cartSession.getCartItems().isEmpty()) {
            return null;
        }

        // 繝・ヵ繧ｩ繝ｫ繝医・驟埼∝・菴乗園縺ｨ縺励※縲・｡ｧ螳｢縺ｮ菴乗園繧定ｨｭ螳壹☆繧・
        if (customerBean.getCustomer() != null) {
            cartSession.setDeliveryAddress(customerBean.getCustomer().getAddress());
        }

        // 驟埼∵侭驥代ｒ險育ｮ励☆繧・
        // 窶ｻ騾壼ｸｸ800蜀・∵ｲ也ｸ・恁縺ｯ1700蜀・・000蜀・ｻ･荳翫・騾∵侭辟｡譁・
        BigDecimal deliveryPrice = deliveryFeeService.calculateDeliveryFee(
                cartSession.getDeliveryAddress(), 
                cartSession.getTotalPrice());
        cartSession.setDeliveryPrice(deliveryPrice);

        return "bookOrder?faces-redirect=true";
    }

    // 繧｢繧ｯ繧ｷ繝ｧ繝ｳ・壹き繝ｼ繝医ｒ蜿ら・縺吶ｋ
    public String viewCart() {
        logger.info("[ CartBean#viewCart ]");

        // 繧ｫ繝ｼ繝医↓蝠・刀縺御ｸ縺､繧ょ・縺｣縺ｦ縺・↑縺九▲縺溷ｴ蜷医・縲√お繝ｩ繝ｼ繝｡繝・そ繝ｼ繧ｸ繧定ｨｭ螳・
        if (cartSession.getCartItems().size() == 0) {
            logger.info("[ CartBean#viewCart ] 繧ｫ繝ｼ繝医↓蝠・刀縺ｪ縺励お繝ｩ繝ｼ");
            globalErrorMessage = "繧ｫ繝ｼ繝医↓蝠・刀縺悟・縺｣縺ｦ縺・∪縺帙ｓ";
        }

        return "cartView?faces-redirect=true";
    }

    // 繧ｰ繝ｭ繝ｼ繝舌Ν繧ｨ繝ｩ繝ｼ繝｡繝・そ繝ｼ繧ｸ
    private String globalErrorMessage;

    public String getGlobalErrorMessage() {
        return globalErrorMessage;
    }

    public void setGlobalErrorMessage(String globalErrorMessage) {
        this.globalErrorMessage = globalErrorMessage;
    }

    // CartSession縺ｸ縺ｮ蟋碑ｭｲ繝｡繧ｽ繝・ラ
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


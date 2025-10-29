package pro.kensait.berrybooks.service.order;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.dao.BookDao;
import pro.kensait.berrybooks.dao.OrderDetailDao;
import pro.kensait.berrybooks.dao.OrderTranDao;
import pro.kensait.berrybooks.dao.StockDao;
import pro.kensait.berrybooks.entity.Book;
import pro.kensait.berrybooks.entity.OrderDetail;
import pro.kensait.berrybooks.entity.OrderDetailPK;
import pro.kensait.berrybooks.entity.OrderTran;
import pro.kensait.berrybooks.entity.Stock;
import pro.kensait.berrybooks.web.cart.CartItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

// 注斁E�E琁E��注斁E��歴取得を行うサービスクラス
@ApplicationScoped
@Transactional
public class OrderService implements OrderServiceIF {
    private static final Logger logger = LoggerFactory.getLogger(
            OrderService.class);

    @Inject
    private OrderTranDao orderTranDao;

    @Inject
    private OrderDetailDao orderDetailDao;

    @Inject
    private BookDao bookDao;

    @Inject
    private StockDao stockDao;

    // サービスメソチE���E�注斁E��ンチE��チE��のリストを取得する（方弁E�E�E    @Override
    public List<OrderTran> getOrderHistory(Integer customerId) {
        logger.info("[ OrderService#findOrderHistory ]");

        // 顧客IDから注斁E��ンチE��チE��のリストを取得し、返す
        List<OrderTran> orderTranList =
                orderTranDao.findByCustomerId(customerId);
        return orderTranList;
    }

    // サービスメソチE���E�注斁E��歴のリストを取得する（方弁E・詳細版！E    @Override
    public List<OrderHistoryTO> getOrderHistory2(Integer customerId) {
        logger.info("[ OrderService#findOrderHistory2 ]");

        // 顧客IDから注斁E��歴のリストを取得し、返す
        List<OrderHistoryTO> orderHistoryList =
                orderTranDao.findOrderHistoryByCustomerId(customerId);
        return orderHistoryList;
    }

    // サービスメソチE���E�注斁E��ンチE��チE��のリストを取得する（方弁E�E�E    @Override
    public List<OrderTran> getOrderHistory3(Integer customerId) {
        logger.info("[ OrderService#findOrderHistory3 ]");

        // 顧客IDから注斁E��ンチE��チE��のリストを取得し、返す
        List<OrderTran> orderTranList =
                orderTranDao.findByCustomerIdWithDetails(customerId);
        return orderTranList;
    }

    // サービスメソチE���E�注斁E��ンチE��チE��を取得すめE    @Override
    public OrderTran getOrderTran(Integer orderTranId) {
        logger.info("[ OrderService#getOrderTran ]");

        // 注文IDから注斁E��ンチE��チE��を取得し、返す
        OrderTran orderTran = orderTranDao.findById(orderTranId);
        if (orderTran == null) {
            throw new RuntimeException("OrderTran not found for ID: " + orderTranId);
        }
        return orderTran;
    }

    // サービスメソチE���E�注斁E��ンチE��チE��を�E細と共に取得すめE    @Override
    public OrderTran getOrderTranWithDetails(Integer orderTranId) {
        logger.info("[ OrderService#getOrderTranWithDetails ]");

        // 注文IDから注斁E��ンチE��チE��を�E細と共に取得し、返す
        OrderTran orderTran = orderTranDao.findByIdWithDetails(orderTranId);
        if (orderTran == null) {
            throw new RuntimeException("OrderTran not found for ID: " + orderTranId);
        }
        return orderTran;
    }

    // サービスメソチE���E�注斁E�E細エンチE��チE��を取得すめE    @Override
    public OrderDetail getOrderDetail(OrderDetailPK pk) {
        logger.info("[ OrderService#getOrderDetail ]");

        // 褁E��主キー�E�注文IDと注斁E�E細ID�E�から注斁E�E細エンチE��チE��を取得し、返す
        OrderDetail orderDetail = orderDetailDao.findById(pk);
        if (orderDetail == null) {
            throw new RuntimeException("OrderDetail not found for PK: " + pk);
        }
        return orderDetail;
    }

    // サービスメソチE���E�注斁E�E細エンチE��チE��を取得する（オーバ�Eロード！E    @Override
    public OrderDetail getOrderDetail(Integer tranId, Integer detailId) {
        logger.info("[ OrderService#getOrderDetail ] tranId=" + tranId 
                + ", detailId=" + detailId);

        OrderDetailPK pk = new OrderDetailPK(tranId, detailId);
        return getOrderDetail(pk);
    }

    // サービスメソチE���E�注斁E�E細エンチE��チE��のリストを取得すめE    @Override
    public List<OrderDetail> getOrderDetails(Integer orderTranId) {
        logger.info("[ OrderService#getOrderDetails ]");

        // 注文IDから注斁E�E細エンチE��チE��のリストを取得し、返す
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderTranId(orderTranId);
        return orderDetailList;
    }

    // サービスメソチE���E�注斁E��めE    @Override
    public OrderTran orderBooks(OrderTO orderTO) {
        logger.info("[ OrderService#orderBooks ]");

        // カートに追加された書籍毎に、在庫の残り個数をチェチE��する
        for (CartItem cartItem : orderTO.cartItems()) {

            // 書籍IDをキーに在庫チE�Eブルから在庫エンチE��チE��を取得すめE            Stock stock = stockDao.findByIdWithLock(cartItem.getBookId());

            // 在庫ぁE未満になる場合�E、例外を送�Eする
            int remaining = stock.getQuantity() - cartItem.getCount();
            if (remaining < 0) {
                throw new OutOfStockException(
                        cartItem.getBookId(),
                        cartItem.getBookName(),
                        "在庫不足");
            }

            // 在庫を減らぁE            stock.setQuantity(remaining);
            // OptimisticLockによるバ�EジョンチェチE��は@Versionアノテーションで自動�E琁E��れる
        }

        // 新しいOrderTranインスタンスを生成すめE        OrderTran orderTran = new OrderTran(
                orderTO.orderDate(),
                orderTO.customerId(),
                orderTO.totalPrice(),
                orderTO.deliveryPrice(),
                orderTO.deliveryAddress(),
                orderTO.settlementType());

        // 生�EしたOrderTranインスタンスをpersist操作により永続化する
        orderTranDao.persist(orderTran);

        // カートアイチE���E�個、E�E注斁E�E細�E��EイチE��ータを取得すめE        List<CartItem> cartItems = orderTO.cartItems();

        // OrderDetailインスタンスの主キー値�E�注斁E�E細ID�E��E初期値を設定すめE        int orderDetailId = 0;

        for (CartItem cartItem : cartItems) {
            Book book = bookDao.findById(cartItem.getBookId());

            // OrderDetailインスタンスの主キー値�E�注斁E�E細ID�E�をカウントアチE�Eする
            orderDetailId = orderDetailId + 1;

            // 新しいOrderDetailインスタンスを生成すめE            OrderDetail orderDetail = new OrderDetail(
                    orderTran.getOrderTranId(),
                    orderDetailId,
                    book,
                    cartItem.getCount());

            // OrderDetailインスタンスを保存すめE            orderDetailDao.persist(orderDetail);
        }

        // チE�Eタベ�Eスから明細を含めて再取得して返す
        // �E�永続化した明細をorderDetailsリレーションシチE�Eに反映させるためE��E        return orderTranDao.findByIdWithDetails(orderTran.getOrderTranId());
    }
}
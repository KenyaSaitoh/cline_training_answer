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

// 豕ｨ譁・・逅・→豕ｨ譁・ｱ･豁ｴ蜿門ｾ励ｒ陦後≧繧ｵ繝ｼ繝薙せ繧ｯ繝ｩ繧ｹ
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

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・壽ｳｨ譁・お繝ｳ繝・ぅ繝・ぅ縺ｮ繝ｪ繧ｹ繝医ｒ蜿門ｾ励☆繧具ｼ域婿蠑・・・    @Override
    public List<OrderTran> getOrderHistory(Integer customerId) {
        logger.info("[ OrderService#findOrderHistory ]");

        // 鬘ｧ螳｢ID縺九ｉ豕ｨ譁・お繝ｳ繝・ぅ繝・ぅ縺ｮ繝ｪ繧ｹ繝医ｒ蜿門ｾ励＠縲∬ｿ斐☆
        List<OrderTran> orderTranList =
                orderTranDao.findByCustomerId(customerId);
        return orderTranList;
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・壽ｳｨ譁・ｱ･豁ｴ縺ｮ繝ｪ繧ｹ繝医ｒ蜿門ｾ励☆繧具ｼ域婿蠑・繝ｻ隧ｳ邏ｰ迚茨ｼ・    @Override
    public List<OrderHistoryTO> getOrderHistory2(Integer customerId) {
        logger.info("[ OrderService#findOrderHistory2 ]");

        // 鬘ｧ螳｢ID縺九ｉ豕ｨ譁・ｱ･豁ｴ縺ｮ繝ｪ繧ｹ繝医ｒ蜿門ｾ励＠縲∬ｿ斐☆
        List<OrderHistoryTO> orderHistoryList =
                orderTranDao.findOrderHistoryByCustomerId(customerId);
        return orderHistoryList;
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・壽ｳｨ譁・お繝ｳ繝・ぅ繝・ぅ縺ｮ繝ｪ繧ｹ繝医ｒ蜿門ｾ励☆繧具ｼ域婿蠑・・・    @Override
    public List<OrderTran> getOrderHistory3(Integer customerId) {
        logger.info("[ OrderService#findOrderHistory3 ]");

        // 鬘ｧ螳｢ID縺九ｉ豕ｨ譁・お繝ｳ繝・ぅ繝・ぅ縺ｮ繝ｪ繧ｹ繝医ｒ蜿門ｾ励＠縲∬ｿ斐☆
        List<OrderTran> orderTranList =
                orderTranDao.findByCustomerIdWithDetails(customerId);
        return orderTranList;
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・壽ｳｨ譁・お繝ｳ繝・ぅ繝・ぅ繧貞叙蠕励☆繧・    @Override
    public OrderTran getOrderTran(Integer orderTranId) {
        logger.info("[ OrderService#getOrderTran ]");

        // 豕ｨ譁⑩D縺九ｉ豕ｨ譁・お繝ｳ繝・ぅ繝・ぅ繧貞叙蠕励＠縲∬ｿ斐☆
        OrderTran orderTran = orderTranDao.findById(orderTranId);
        if (orderTran == null) {
            throw new RuntimeException("OrderTran not found for ID: " + orderTranId);
        }
        return orderTran;
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・壽ｳｨ譁・お繝ｳ繝・ぅ繝・ぅ繧呈・邏ｰ縺ｨ蜈ｱ縺ｫ蜿門ｾ励☆繧・    @Override
    public OrderTran getOrderTranWithDetails(Integer orderTranId) {
        logger.info("[ OrderService#getOrderTranWithDetails ]");

        // 豕ｨ譁⑩D縺九ｉ豕ｨ譁・お繝ｳ繝・ぅ繝・ぅ繧呈・邏ｰ縺ｨ蜈ｱ縺ｫ蜿門ｾ励＠縲∬ｿ斐☆
        OrderTran orderTran = orderTranDao.findByIdWithDetails(orderTranId);
        if (orderTran == null) {
            throw new RuntimeException("OrderTran not found for ID: " + orderTranId);
        }
        return orderTran;
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・壽ｳｨ譁・・邏ｰ繧ｨ繝ｳ繝・ぅ繝・ぅ繧貞叙蠕励☆繧・    @Override
    public OrderDetail getOrderDetail(OrderDetailPK pk) {
        logger.info("[ OrderService#getOrderDetail ]");

        // 隍・粋荳ｻ繧ｭ繝ｼ・域ｳｨ譁⑩D縺ｨ豕ｨ譁・・邏ｰID・峨°繧画ｳｨ譁・・邏ｰ繧ｨ繝ｳ繝・ぅ繝・ぅ繧貞叙蠕励＠縲∬ｿ斐☆
        OrderDetail orderDetail = orderDetailDao.findById(pk);
        if (orderDetail == null) {
            throw new RuntimeException("OrderDetail not found for PK: " + pk);
        }
        return orderDetail;
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・壽ｳｨ譁・・邏ｰ繧ｨ繝ｳ繝・ぅ繝・ぅ繧貞叙蠕励☆繧具ｼ医が繝ｼ繝舌・繝ｭ繝ｼ繝会ｼ・    @Override
    public OrderDetail getOrderDetail(Integer tranId, Integer detailId) {
        logger.info("[ OrderService#getOrderDetail ] tranId=" + tranId 
                + ", detailId=" + detailId);

        OrderDetailPK pk = new OrderDetailPK(tranId, detailId);
        return getOrderDetail(pk);
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・壽ｳｨ譁・・邏ｰ繧ｨ繝ｳ繝・ぅ繝・ぅ縺ｮ繝ｪ繧ｹ繝医ｒ蜿門ｾ励☆繧・    @Override
    public List<OrderDetail> getOrderDetails(Integer orderTranId) {
        logger.info("[ OrderService#getOrderDetails ]");

        // 豕ｨ譁⑩D縺九ｉ豕ｨ譁・・邏ｰ繧ｨ繝ｳ繝・ぅ繝・ぅ縺ｮ繝ｪ繧ｹ繝医ｒ蜿門ｾ励＠縲∬ｿ斐☆
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderTranId(orderTranId);
        return orderDetailList;
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・壽ｳｨ譁・☆繧・    @Override
    public OrderTran orderBooks(OrderTO orderTO) {
        logger.info("[ OrderService#orderBooks ]");

        // 繧ｫ繝ｼ繝医↓霑ｽ蜉縺輔ｌ縺滓嶌邀肴ｯ弱↓縲∝惠蠎ｫ縺ｮ谿九ｊ蛟区焚繧偵メ繧ｧ繝・け縺吶ｋ
        for (CartItem cartItem : orderTO.cartItems()) {

            // 譖ｸ邀巧D繧偵く繝ｼ縺ｫ蝨ｨ蠎ｫ繝・・繝悶Ν縺九ｉ蝨ｨ蠎ｫ繧ｨ繝ｳ繝・ぅ繝・ぅ繧貞叙蠕励☆繧・            Stock stock = stockDao.findByIdWithLock(cartItem.getBookId());

            // 蝨ｨ蠎ｫ縺・譛ｪ貅縺ｫ縺ｪ繧句ｴ蜷医・縲∽ｾ句､悶ｒ騾∝・縺吶ｋ
            int remaining = stock.getQuantity() - cartItem.getCount();
            if (remaining < 0) {
                throw new OutOfStockException(
                        cartItem.getBookId(),
                        cartItem.getBookName(),
                        "蝨ｨ蠎ｫ荳崎ｶｳ");
            }

            // 蝨ｨ蠎ｫ繧呈ｸ帙ｉ縺・            stock.setQuantity(remaining);
            // OptimisticLock縺ｫ繧医ｋ繝舌・繧ｸ繝ｧ繝ｳ繝√ぉ繝・け縺ｯ@Version繧｢繝弱ユ繝ｼ繧ｷ繝ｧ繝ｳ縺ｧ閾ｪ蜍募・逅・＆繧後ｋ
        }

        // 譁ｰ縺励＞OrderTran繧､繝ｳ繧ｹ繧ｿ繝ｳ繧ｹ繧堤函謌舌☆繧・        OrderTran orderTran = new OrderTran(
                orderTO.orderDate(),
                orderTO.customerId(),
                orderTO.totalPrice(),
                orderTO.deliveryPrice(),
                orderTO.deliveryAddress(),
                orderTO.settlementType());

        // 逕滓・縺励◆OrderTran繧､繝ｳ繧ｹ繧ｿ繝ｳ繧ｹ繧恥ersist謫堺ｽ懊↓繧医ｊ豌ｸ邯壼喧縺吶ｋ
        orderTranDao.persist(orderTran);

        // 繧ｫ繝ｼ繝医い繧､繝・Β・亥九・・豕ｨ譁・・邏ｰ・峨・繧､繝・Ξ繝ｼ繧ｿ繧貞叙蠕励☆繧・        List<CartItem> cartItems = orderTO.cartItems();

        // OrderDetail繧､繝ｳ繧ｹ繧ｿ繝ｳ繧ｹ縺ｮ荳ｻ繧ｭ繝ｼ蛟､・域ｳｨ譁・・邏ｰID・峨・蛻晄悄蛟､繧定ｨｭ螳壹☆繧・        int orderDetailId = 0;

        for (CartItem cartItem : cartItems) {
            Book book = bookDao.findById(cartItem.getBookId());

            // OrderDetail繧､繝ｳ繧ｹ繧ｿ繝ｳ繧ｹ縺ｮ荳ｻ繧ｭ繝ｼ蛟､・域ｳｨ譁・・邏ｰID・峨ｒ繧ｫ繧ｦ繝ｳ繝医い繝・・縺吶ｋ
            orderDetailId = orderDetailId + 1;

            // 譁ｰ縺励＞OrderDetail繧､繝ｳ繧ｹ繧ｿ繝ｳ繧ｹ繧堤函謌舌☆繧・            OrderDetail orderDetail = new OrderDetail(
                    orderTran.getOrderTranId(),
                    orderDetailId,
                    book,
                    cartItem.getCount());

            // OrderDetail繧､繝ｳ繧ｹ繧ｿ繝ｳ繧ｹ繧剃ｿ晏ｭ倥☆繧・            orderDetailDao.persist(orderDetail);
        }

        // 繝・・繧ｿ繝吶・繧ｹ縺九ｉ譏守ｴｰ繧貞性繧√※蜀榊叙蠕励＠縺ｦ霑斐☆
        // ・域ｰｸ邯壼喧縺励◆譏守ｴｰ繧弛rderDetails繝ｪ繝ｬ繝ｼ繧ｷ繝ｧ繝ｳ繧ｷ繝・・縺ｫ蜿肴丐縺輔○繧九◆繧・ｼ・        return orderTranDao.findByIdWithDetails(orderTran.getOrderTranId());
    }
}
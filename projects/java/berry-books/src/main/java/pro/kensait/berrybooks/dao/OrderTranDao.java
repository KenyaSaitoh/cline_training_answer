package pro.kensait.berrybooks.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.entity.OrderTran;
import pro.kensait.berrybooks.service.order.OrderHistoryTO;
import pro.kensait.berrybooks.service.order.OrderSummaryTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

// 豕ｨ譁・ユ繝ｼ繝悶Ν縺ｸ縺ｮ繧｢繧ｯ繧ｻ繧ｹ繧定｡後≧DAO繧ｯ繝ｩ繧ｹ
@ApplicationScoped
public class OrderTranDao {
    private static final Logger logger = LoggerFactory.getLogger(
            OrderTranDao.class);

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    // DAO繝｡繧ｽ繝・ラ・壽ｳｨ譁・ｒ荳ｻ繧ｭ繝ｼ縺ｧ讀懃ｴ｢
    public OrderTran findById(Integer orderTranId) {
        logger.info("[ OrderTranDao#findById ]");
        return em.find(OrderTran.class, orderTranId);
    }

    // DAO繝｡繧ｽ繝・ラ・壽ｳｨ譁・ｒ荳ｻ繧ｭ繝ｼ縺ｧ讀懃ｴ｢・域・邏ｰ蜷ｫ繧・・
    public OrderTran findByIdWithDetails(Integer orderTranId) {
        logger.info("[ OrderTranDao#findByIdWithDetails ] orderTranId=" + orderTranId);
        
        // EntityManager繧偵け繝ｪ繧｢縺励※繧ｭ繝｣繝・す繝･繧偵け繝ｪ繧｢
        em.clear();
        
        // OrderTran繧貞叙蠕暦ｼ・AGER繝ｭ繝ｼ繝峨↓繧医ｊ譏守ｴｰ繧り・蜍慕噪縺ｫ蜿門ｾ励＆繧後ｋ・・
        return em.find(OrderTran.class, orderTranId);
    }

    // DAO繝｡繧ｽ繝・ラ・夐｡ｧ螳｢ID縺ｧ豕ｨ譁・ｱ･豁ｴ繧呈､懃ｴ｢
    public List<OrderTran> findByCustomerId(Integer customerId) {
        logger.info("[ OrderTranDao#findByCustomerId ]");
        
        TypedQuery<OrderTran> query = em.createQuery(
                "SELECT ot FROM OrderTran ot INNER JOIN ot.orderDetails od " +
                "WHERE ot.customerId = :customerId " +
                "GROUP BY ot.orderTranId " +
                "ORDER BY ot.orderDate DESC",
                OrderTran.class);
        query.setParameter("customerId", customerId);
        
        return query.getResultList();
    }

    // DAO繝｡繧ｽ繝・ラ・夐｡ｧ螳｢ID縺ｧ豕ｨ譁・ｱ･豁ｴ繧呈､懃ｴ｢・郁ｩｳ邏ｰDTO菴ｿ逕ｨ・・
    public List<OrderHistoryTO> findOrderHistoryByCustomerId(Integer customerId) {
        logger.info("[ OrderTranDao#findOrderHistoryByCustomerId ]");
        
        TypedQuery<OrderHistoryTO> query = em.createQuery(
                "SELECT new dev.berry.service.order.OrderHistoryTO(" +
                "ot.orderDate, ot.orderTranId, od.orderDetailId, " +
                "b.bookName, p.publisherName, b.price, od.count) " +
                "FROM OrderTran ot " +
                "INNER JOIN ot.orderDetails od " +
                "INNER JOIN od.book b " +
                "INNER JOIN b.publisher p " +
                "WHERE ot.customerId = :customerId " +
                "ORDER BY ot.orderDate DESC, ot.orderTranId DESC",
                OrderHistoryTO.class);
        query.setParameter("customerId", customerId);
        
        return query.getResultList();
    }

    // DAO繝｡繧ｽ繝・ラ・夐｡ｧ螳｢ID縺ｧ豕ｨ譁・ｱ･豁ｴ繧呈､懃ｴ｢・医し繝槭Μ繝ｼDTO菴ｿ逕ｨ・・
    public List<OrderSummaryTO> findOrderSummaryByCustomerId(Integer customerId) {
        logger.info("[ OrderTranDao#findOrderSummaryByCustomerId ]");
        
        TypedQuery<OrderSummaryTO> query = em.createQuery(
                "SELECT new dev.berry.service.order.OrderSummaryTO(" +
                "ot.orderTranId, ot.orderDate, COUNT(od), ot.totalPrice) " +
                "FROM OrderTran ot INNER JOIN ot.orderDetails od " +
                "WHERE ot.customerId = :customerId " +
                "GROUP BY ot.orderTranId, ot.orderDate, ot.totalPrice " +
                "ORDER BY ot.orderDate DESC",
                OrderSummaryTO.class);
        query.setParameter("customerId", customerId);
        
        return query.getResultList();
    }

    // DAO繝｡繧ｽ繝・ラ・夐｡ｧ螳｢ID縺ｧ豕ｨ譁・ｱ･豁ｴ繧呈､懃ｴ｢・域・邏ｰ蜷ｫ繧・・
    public List<OrderTran> findByCustomerIdWithDetails(Integer customerId) {
        logger.info("[ OrderTranDao#findByCustomerIdWithDetails ]");
        
        TypedQuery<OrderTran> query = em.createQuery(
                "SELECT DISTINCT ot FROM OrderTran ot " +
                "LEFT JOIN FETCH ot.orderDetails od " +
                "LEFT JOIN FETCH od.book " +
                "WHERE ot.customerId = :customerId " +
                "ORDER BY ot.orderDate DESC, ot.orderTranId DESC",
                OrderTran.class);
        query.setParameter("customerId", customerId);
        
        return query.getResultList();
    }

    // DAO繝｡繧ｽ繝・ラ・壽ｳｨ譁・ｒ菫晏ｭ・
    public void persist(OrderTran orderTran) {
        logger.info("[ OrderTranDao#persist ]");
        em.persist(orderTran);
        // IDENTITY繧ｹ繝医Λ繝・ず繝ｼ縺ｧID繧堤｢ｺ螳溘↓逕滓・縺吶ｋ縺溘ａ縺ｫflush
        em.flush();
    }
}



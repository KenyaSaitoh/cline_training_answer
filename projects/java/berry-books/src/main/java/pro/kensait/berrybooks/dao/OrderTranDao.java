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

// 注斁E��ーブルへのアクセスを行うDAOクラス
@ApplicationScoped
public class OrderTranDao {
    private static final Logger logger = LoggerFactory.getLogger(
            OrderTranDao.class);

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    // DAOメソチE���E�注斁E��主キーで検索
    public OrderTran findById(Integer orderTranId) {
        logger.info("[ OrderTranDao#findById ]");
        return em.find(OrderTran.class, orderTranId);
    }

    // DAOメソチE���E�注斁E��主キーで検索�E��E細含む�E�E
    public OrderTran findByIdWithDetails(Integer orderTranId) {
        logger.info("[ OrderTranDao#findByIdWithDetails ] orderTranId=" + orderTranId);
        
        // EntityManagerをクリアしてキャチE��ュをクリア
        em.clear();
        
        // OrderTranを取得！EAGERロードにより明細も�E動的に取得される�E�E
        return em.find(OrderTran.class, orderTranId);
    }

    // DAOメソチE���E�顧客IDで注斁E��歴を検索
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

    // DAOメソチE���E�顧客IDで注斁E��歴を検索�E�詳細DTO使用�E�E
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

    // DAOメソチE���E�顧客IDで注斁E��歴を検索�E�サマリーDTO使用�E�E
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

    // DAOメソチE���E�顧客IDで注斁E��歴を検索�E��E細含む�E�E
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

    // DAOメソチE���E�注斁E��保孁E
    public void persist(OrderTran orderTran) {
        logger.info("[ OrderTranDao#persist ]");
        em.persist(orderTran);
        // IDENTITYストラチE��ーでIDを確実に生�Eするためにflush
        em.flush();
    }
}



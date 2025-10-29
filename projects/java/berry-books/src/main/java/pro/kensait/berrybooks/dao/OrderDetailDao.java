package pro.kensait.berrybooks.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.entity.OrderDetail;
import pro.kensait.berrybooks.entity.OrderDetailPK;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

// 注斁E�E細チE�Eブルへのアクセスを行うDAOクラス
@ApplicationScoped
public class OrderDetailDao {
    private static final Logger logger = LoggerFactory.getLogger(
            OrderDetailDao.class);

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    // DAOメソチE���E�注斁E�E細を主キーで検索
    public OrderDetail findById(OrderDetailPK id) {
        logger.info("[ OrderDetailDao#findById ]");
        return em.find(OrderDetail.class, id);
    }

    // DAOメソチE���E�注文IDで注斁E�E細リストを検索
    public List<OrderDetail> findByOrderTranId(Integer orderTranId) {
        logger.info("[ OrderDetailDao#findByOrderTranId ]");
        
        TypedQuery<OrderDetail> query = em.createQuery(
                "SELECT od FROM OrderDetail od WHERE od.orderTranId = :orderTranId",
                OrderDetail.class);
        query.setParameter("orderTranId", orderTranId);
        
        return query.getResultList();
    }

    // DAOメソチE���E�注斁E�E細を保孁E
    public void persist(OrderDetail orderDetail) {
        logger.info("[ OrderDetailDao#persist ]");
        em.persist(orderDetail);
        // 即座にINSERTを実行してチE�Eタベ�Eスに反映
        em.flush();
    }
}



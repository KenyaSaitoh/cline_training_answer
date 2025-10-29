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

// 豕ｨ譁・・邏ｰ繝・・繝悶Ν縺ｸ縺ｮ繧｢繧ｯ繧ｻ繧ｹ繧定｡後≧DAO繧ｯ繝ｩ繧ｹ
@ApplicationScoped
public class OrderDetailDao {
    private static final Logger logger = LoggerFactory.getLogger(
            OrderDetailDao.class);

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    // DAO繝｡繧ｽ繝・ラ・壽ｳｨ譁・・邏ｰ繧剃ｸｻ繧ｭ繝ｼ縺ｧ讀懃ｴ｢
    public OrderDetail findById(OrderDetailPK id) {
        logger.info("[ OrderDetailDao#findById ]");
        return em.find(OrderDetail.class, id);
    }

    // DAO繝｡繧ｽ繝・ラ・壽ｳｨ譁⑩D縺ｧ豕ｨ譁・・邏ｰ繝ｪ繧ｹ繝医ｒ讀懃ｴ｢
    public List<OrderDetail> findByOrderTranId(Integer orderTranId) {
        logger.info("[ OrderDetailDao#findByOrderTranId ]");
        
        TypedQuery<OrderDetail> query = em.createQuery(
                "SELECT od FROM OrderDetail od WHERE od.orderTranId = :orderTranId",
                OrderDetail.class);
        query.setParameter("orderTranId", orderTranId);
        
        return query.getResultList();
    }

    // DAO繝｡繧ｽ繝・ラ・壽ｳｨ譁・・邏ｰ繧剃ｿ晏ｭ・
    public void persist(OrderDetail orderDetail) {
        logger.info("[ OrderDetailDao#persist ]");
        em.persist(orderDetail);
        // 蜊ｳ蠎ｧ縺ｫINSERT繧貞ｮ溯｡後＠縺ｦ繝・・繧ｿ繝吶・繧ｹ縺ｫ蜿肴丐
        em.flush();
    }
}



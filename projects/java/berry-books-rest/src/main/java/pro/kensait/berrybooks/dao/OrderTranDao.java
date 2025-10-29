package pro.kensait.berrybooks.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.entity.OrderTran;
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

    // DAO繝｡繧ｽ繝・ラ・夐｡ｧ螳｢ID縺ｧ豕ｨ譁・ｱ･豁ｴ繧貞叙蠕・
    public List<OrderTran> findByCustomerId(Integer customerId) {
        logger.info("[ OrderTranDao#findByCustomerId ]");
        
        TypedQuery<OrderTran> query = em.createQuery(
                "SELECT o FROM OrderTran o WHERE o.customerId = :customerId ORDER BY o.orderDate DESC", 
                OrderTran.class);
        query.setParameter("customerId", customerId);
        
        return query.getResultList();
    }

    // DAO繝｡繧ｽ繝・ラ・夐｡ｧ螳｢ID縺ｧ豕ｨ譁・ｻｶ謨ｰ繧貞叙蠕・
    public Long countOrdersByCustomerId(Integer customerId) {
        logger.info("[ OrderTranDao#countOrdersByCustomerId ]");
        
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(o) FROM OrderTran o WHERE o.customerId = :customerId", 
                Long.class);
        query.setParameter("customerId", customerId);
        
        return query.getSingleResult();
    }

    // DAO繝｡繧ｽ繝・ラ・夐｡ｧ螳｢ID縺ｧ雉ｼ蜈･蜀頑焚縺ｮ蜷郁ｨ医ｒ蜿門ｾ・
    public Long sumBookCountByCustomerId(Integer customerId) {
        logger.info("[ OrderTranDao#sumBookCountByCustomerId ]");
        
        TypedQuery<Long> query = em.createQuery(
                "SELECT COALESCE(SUM(od.count), 0) FROM OrderDetail od WHERE od.orderTranId IN " +
                "(SELECT o.orderTranId FROM OrderTran o WHERE o.customerId = :customerId)", 
                Long.class);
        query.setParameter("customerId", customerId);
        
        return query.getSingleResult();
    }
}


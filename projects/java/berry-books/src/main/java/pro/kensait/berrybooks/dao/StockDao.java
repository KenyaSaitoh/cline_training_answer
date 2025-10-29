package pro.kensait.berrybooks.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.entity.Stock;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;

// 蝨ｨ蠎ｫ繝・・繝悶Ν縺ｸ縺ｮ繧｢繧ｯ繧ｻ繧ｹ繧定｡後≧DAO繧ｯ繝ｩ繧ｹ
@ApplicationScoped
public class StockDao {
    private static final Logger logger = LoggerFactory.getLogger(
            StockDao.class);

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    // DAO繝｡繧ｽ繝・ラ・壼惠蠎ｫ繧剃ｸｻ繧ｭ繝ｼ縺ｧ讀懃ｴ｢・域ご隕ｳ逧・Ο繝・け・・
    public Stock findByIdWithLock(Integer bookId) {
        logger.info("[ StockDao#findByIdWithLock ]");
        return em.find(Stock.class, bookId, LockModeType.PESSIMISTIC_WRITE);
    }

    // DAO繝｡繧ｽ繝・ラ・壼惠蠎ｫ繧剃ｸｻ繧ｭ繝ｼ縺ｧ讀懃ｴ｢
    public Stock findById(Integer bookId) {
        logger.info("[ StockDao#findById ]");
        return em.find(Stock.class, bookId);
    }

    // DAO繝｡繧ｽ繝・ラ・壼惠蠎ｫ繧呈峩譁ｰ
    public void update(Stock stock) {
        logger.info("[ StockDao#update ]");
        em.merge(stock);
    }
}



package pro.kensait.berrybooks.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.entity.Stock;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;

// ???????????????DAO???
@ApplicationScoped
public class StockDao {
    private static final Logger logger = LoggerFactory.getLogger(
            StockDao.class);

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    // DAO??????????????????????
    public Stock findByIdWithLock(Integer bookId) {
        logger.info("[ StockDao#findByIdWithLock ]");
        return em.find(Stock.class, bookId, LockModeType.PESSIMISTIC_WRITE);
    }

    // DAO??????????????
    public Stock findById(Integer bookId) {
        logger.info("[ StockDao#findById ]");
        return em.find(Stock.class, bookId);
    }

    // DAO??????????
    public void update(Stock stock) {
        logger.info("[ StockDao#update ]");
        em.merge(stock);
    }
}

package pro.kensait.berrybooks.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.entity.Stock;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;

// 在庫チE�Eブルへのアクセスを行うDAOクラス
@ApplicationScoped
public class StockDao {
    private static final Logger logger = LoggerFactory.getLogger(
            StockDao.class);

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    // DAOメソチE���E�在庫を主キーで検索�E�悲観皁E��チE���E�E
    public Stock findByIdWithLock(Integer bookId) {
        logger.info("[ StockDao#findByIdWithLock ]");
        return em.find(Stock.class, bookId, LockModeType.PESSIMISTIC_WRITE);
    }

    // DAOメソチE���E�在庫を主キーで検索
    public Stock findById(Integer bookId) {
        logger.info("[ StockDao#findById ]");
        return em.find(Stock.class, bookId);
    }

    // DAOメソチE���E�在庫を更新
    public void update(Stock stock) {
        logger.info("[ StockDao#update ]");
        em.merge(stock);
    }
}



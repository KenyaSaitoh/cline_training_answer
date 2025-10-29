package pro.kensait.berrybooks.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.entity.Category;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

// カチE��リチE�Eブルへのアクセスを行うDAOクラス
@ApplicationScoped
public class CategoryDao {
    private static final Logger logger = LoggerFactory.getLogger(
            CategoryDao.class);

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    // DAOメソチE���E�カチE��リを主キーで検索
    public Category findById(Integer categoryId) {
        logger.info("[ CategoryDao#findById ]");
        return em.find(Category.class, categoryId);
    }

    // DAOメソチE���E��EカチE��リを取征E
    public List<Category> findAll() {
        logger.info("[ CategoryDao#findAll ]");
        
        TypedQuery<Category> query = em.createQuery(
                "SELECT c FROM Category c", Category.class);
        
        return query.getResultList();
    }
}



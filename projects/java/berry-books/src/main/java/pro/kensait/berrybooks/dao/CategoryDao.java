package pro.kensait.berrybooks.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.entity.Category;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

// 繧ｫ繝・ざ繝ｪ繝・・繝悶Ν縺ｸ縺ｮ繧｢繧ｯ繧ｻ繧ｹ繧定｡後≧DAO繧ｯ繝ｩ繧ｹ
@ApplicationScoped
public class CategoryDao {
    private static final Logger logger = LoggerFactory.getLogger(
            CategoryDao.class);

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    // DAO繝｡繧ｽ繝・ラ・壹き繝・ざ繝ｪ繧剃ｸｻ繧ｭ繝ｼ縺ｧ讀懃ｴ｢
    public Category findById(Integer categoryId) {
        logger.info("[ CategoryDao#findById ]");
        return em.find(Category.class, categoryId);
    }

    // DAO繝｡繧ｽ繝・ラ・壼・繧ｫ繝・ざ繝ｪ繧貞叙蠕・
    public List<Category> findAll() {
        logger.info("[ CategoryDao#findAll ]");
        
        TypedQuery<Category> query = em.createQuery(
                "SELECT c FROM Category c", Category.class);
        
        return query.getResultList();
    }
}



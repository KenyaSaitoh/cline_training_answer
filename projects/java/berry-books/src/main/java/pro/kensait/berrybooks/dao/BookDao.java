package pro.kensait.berrybooks.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.entity.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

// 譖ｸ邀阪ユ繝ｼ繝悶Ν縺ｸ縺ｮ繧｢繧ｯ繧ｻ繧ｹ繧定｡後≧DAO繧ｯ繝ｩ繧ｹ
@ApplicationScoped
public class BookDao {
    private static final Logger logger = LoggerFactory.getLogger(
            BookDao.class);

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    // DAO繝｡繧ｽ繝・ラ・壽嶌邀阪ｒ荳ｻ繧ｭ繝ｼ縺ｧ讀懃ｴ｢
    public Book findById(Integer bookId) {
        logger.info("[ BookDao#findById ]");
        return em.find(Book.class, bookId);
    }

    // DAO繝｡繧ｽ繝・ラ・壼・譖ｸ邀阪ｒ蜿門ｾ・
    public List<Book> findAll() {
        logger.info("[ BookDao#findAll ]");
        
        TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b", Book.class);
        
        List<Book> books = query.getResultList();
        
        // 蜷・お繝ｳ繝・ぅ繝・ぅ繧偵ョ繝ｼ繧ｿ繝吶・繧ｹ縺九ｉ蜀崎ｪｭ縺ｿ霎ｼ縺ｿ縺励※譛譁ｰ縺ｮ蝨ｨ蠎ｫ繝・・繧ｿ繧貞叙蠕・
        for (Book book : books) {
            em.refresh(book);
        }
        
        return books;
    }

    // DAO繝｡繧ｽ繝・ラ・壹き繝・ざ繝ｪID縺ｧ譖ｸ邀阪ｒ讀懃ｴ｢
    public List<Book> queryByCategory(Integer categoryId) {
        logger.info("[ BookDao#queryByCategory ]");
        
        TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b WHERE b.category.categoryId = :categoryId", 
                Book.class);
        query.setParameter("categoryId", categoryId);
        
        List<Book> books = query.getResultList();
        
        // 蜷・お繝ｳ繝・ぅ繝・ぅ繧偵ョ繝ｼ繧ｿ繝吶・繧ｹ縺九ｉ蜀崎ｪｭ縺ｿ霎ｼ縺ｿ縺励※譛譁ｰ縺ｮ蝨ｨ蠎ｫ繝・・繧ｿ繧貞叙蠕・
        for (Book book : books) {
            em.refresh(book);
        }
        
        return books;
    }

    // DAO繝｡繧ｽ繝・ラ・壹く繝ｼ繝ｯ繝ｼ繝峨〒譖ｸ邀阪ｒ讀懃ｴ｢
    public List<Book> queryByKeyword(String keyword) {
        logger.info("[ BookDao#queryByKeyword ]");
        
        TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b WHERE b.bookName like :keyword", 
                Book.class);
        query.setParameter("keyword", keyword);
        
        List<Book> books = query.getResultList();
        
        // 蜷・お繝ｳ繝・ぅ繝・ぅ繧偵ョ繝ｼ繧ｿ繝吶・繧ｹ縺九ｉ蜀崎ｪｭ縺ｿ霎ｼ縺ｿ縺励※譛譁ｰ縺ｮ蝨ｨ蠎ｫ繝・・繧ｿ繧貞叙蠕・
        for (Book book : books) {
            em.refresh(book);
        }
        
        return books;
    }

    // DAO繝｡繧ｽ繝・ラ・壹き繝・ざ繝ｪID縺ｨ繧ｭ繝ｼ繝ｯ繝ｼ繝峨〒譖ｸ邀阪ｒ讀懃ｴ｢
    public List<Book> query(Integer categoryId, String keyword) {
        logger.info("[ BookDao#query ]");
        
        TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b WHERE b.category.categoryId = :categoryId " +
                "AND b.bookName like :keyword", 
                Book.class);
        query.setParameter("categoryId", categoryId);
        query.setParameter("keyword", keyword);
        
        List<Book> books = query.getResultList();
        
        // 蜷・お繝ｳ繝・ぅ繝・ぅ繧偵ョ繝ｼ繧ｿ繝吶・繧ｹ縺九ｉ蜀崎ｪｭ縺ｿ霎ｼ縺ｿ縺励※譛譁ｰ縺ｮ蝨ｨ蠎ｫ繝・・繧ｿ繧貞叙蠕・
        for (Book book : books) {
            em.refresh(book);
        }
        
        return books;
    }

    // DAO繝｡繧ｽ繝・ラ・壼虚逧・け繧ｨ繝ｪ縺ｧ譖ｸ邀阪ｒ讀懃ｴ｢・・riteria API・・
    public List<Book> searchWithCriteria(Integer categoryId, String keyword) {
        logger.info("[ BookDao#searchWithCriteria ]");
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> book = cq.from(Book.class);

        // 蜍慕噪縺ｫ譚｡莉ｶ繧呈ｧ狗ｯ・
        List<Predicate> predicates = new ArrayList<>();
        
        if (categoryId != null) {
            predicates.add(cb.equal(
                    book.get("category").get("categoryId"), categoryId));
        }
        
        if (keyword != null && !keyword.isEmpty()) {
            predicates.add(cb.like(
                    book.get("bookName"), keyword));
        }

        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[0]));
        }

        TypedQuery<Book> query = em.createQuery(cq);
        List<Book> books = query.getResultList();
        
        // 蜷・お繝ｳ繝・ぅ繝・ぅ繧偵ョ繝ｼ繧ｿ繝吶・繧ｹ縺九ｉ蜀崎ｪｭ縺ｿ霎ｼ縺ｿ縺励※譛譁ｰ縺ｮ蝨ｨ蠎ｫ繝・・繧ｿ繧貞叙蠕・
        for (Book book2 : books) {
            em.refresh(book2);
        }
        
        return books;
    }
}



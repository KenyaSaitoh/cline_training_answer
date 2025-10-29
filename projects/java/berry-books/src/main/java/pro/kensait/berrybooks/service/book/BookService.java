package pro.kensait.berrybooks.service.book;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.dao.BookDao;
import pro.kensait.berrybooks.entity.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

// 譖ｸ邀肴､懃ｴ｢繧定｡後≧繧ｵ繝ｼ繝薙せ繧ｯ繝ｩ繧ｹ
@ApplicationScoped
@Transactional
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(
            BookService.class);

    @Inject
    private BookDao bookDao;

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・壽嶌邀肴､懃ｴ｢・井ｸｻ繧ｭ繝ｼ讀懃ｴ｢・・
    public Book getBook(Integer bookId) {
        logger.info("[ BookService#getBook ]");
        
        Book book = bookDao.findById(bookId);
        if (book == null) {
            throw new RuntimeException("Book not found: " + bookId);
        }
        return book;
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・壽嶌邀肴､懃ｴ｢・亥・莉ｶ讀懃ｴ｢・・
    public List<Book> getBooksAll() {
        logger.info("[ BookService#getBooksAll ]");
        return bookDao.findAll();
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・壽嶌邀肴､懃ｴ｢・医き繝・ざ繝ｪID縺ｨ繧ｭ繝ｼ繝ｯ繝ｼ繝峨↓繧医ｋ譚｡莉ｶ讀懃ｴ｢・・
    public List<Book> searchBook(Integer categoryId, String keyword) {
        logger.info("[ BookService#searchBook(categoryId, keyword) ]");
        return bookDao.query(categoryId, toLikeWord(keyword));
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・壽嶌邀肴､懃ｴ｢・医き繝・ざ繝ｪID縺ｫ繧医ｋ譚｡莉ｶ讀懃ｴ｢・・
    public List<Book> searchBook(Integer categoryId) {
        logger.info("[ BookService#searchBook(categoryId) ]");
        return bookDao.queryByCategory(categoryId);
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・壽嶌邀肴､懃ｴ｢・医く繝ｼ繝ｯ繝ｼ繝峨↓繧医ｋ譚｡莉ｶ讀懃ｴ｢・・
    public List<Book> searchBook(String keyword) {
        logger.info("[ BookService#searchBook(keyword) ]");
        return bookDao.queryByKeyword(toLikeWord(keyword));
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・壽嶌邀肴､懃ｴ｢・亥虚逧・け繧ｨ繝ｪ縺ｮ讒狗ｯ会ｼ・
    public List<Book> searchBookWithCriteria(Integer categoryId, String keyword) {
        logger.info("[ BookService#searchBookWithCriteria ]");
        
        String likeKeyword = (keyword != null && !keyword.isEmpty()) 
                ? toLikeWord(keyword) : null;
        
        return bookDao.searchWithCriteria(categoryId, likeKeyword);
    }

    private String toLikeWord(String keyword) {
        return "%" + keyword + "%";
    }
}



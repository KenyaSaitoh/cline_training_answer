package pro.kensait.berrybooks.service.book;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.dao.BookDao;
import pro.kensait.berrybooks.entity.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

// ??????????????
@ApplicationScoped
@Transactional
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(
            BookService.class);

    @Inject
    private BookDao bookDao;

    // ????????????????????
    public Book getBook(Integer bookId) {
        logger.info("[ BookService#getBook ]");
        
        Book book = bookDao.findById(bookId);
        if (book == null) {
            throw new RuntimeException("Book not found: " + bookId);
        }
        return book;
    }

    // ???????????????????
    public List<Book> getBooksAll() {
        logger.info("[ BookService#getBooksAll ]");
        return bookDao.findAll();
    }

    // ??????????????????ID??????????????
    public List<Book> searchBook(Integer categoryId, String keyword) {
        logger.info("[ BookService#searchBook(categoryId, keyword) ]");
        return bookDao.query(categoryId, toLikeWord(keyword));
    }

    // ??????????????????ID????????
    public List<Book> searchBook(Integer categoryId) {
        logger.info("[ BookService#searchBook(categoryId) ]");
        return bookDao.queryByCategory(categoryId);
    }

    // ???????????????????????????
    public List<Book> searchBook(String keyword) {
        logger.info("[ BookService#searchBook(keyword) ]");
        return bookDao.queryByKeyword(toLikeWord(keyword));
    }

    // ???????????????????????
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

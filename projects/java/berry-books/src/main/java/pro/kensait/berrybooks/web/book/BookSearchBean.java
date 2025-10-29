package pro.kensait.berrybooks.web.book;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.entity.Book;
import pro.kensait.berrybooks.service.book.BookService;
import pro.kensait.berrybooks.service.category.CategoryService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

// ????????????Bean
@Named
@SessionScoped
public class BookSearchBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(
            BookSearchBean.class);

    @Inject
    private BookService bookService;

    @Inject
    private CategoryService categoryService;

    // ????
    private Integer categoryId;
    private String keyword;

    // ????
    private List<Book> bookList;

    // ??????????????????
    private Map<String, Integer> categoryMap;

    @PostConstruct
    public void init() {
        logger.info("[ BookSearchBean#init ]");
        
        // ???????????
        categoryMap = new HashMap<>();
        categoryMap.put("", null);
        categoryMap.putAll(categoryService.getCategoryMap());
        
        // bookList?????????????
        if (bookList == null || bookList.isEmpty()) {
            bookList = bookService.getBooksAll();
        }
    }

    // ????????????????????
    public String search() {
        logger.info("[ BookSearchBean#search ] categoryId=" + categoryId + ", keyword=" + keyword);

        // ??????????????
        if (categoryId != null && categoryId != 0) {
            if (keyword != null && !keyword.isEmpty()) {
                bookList = bookService.searchBook(categoryId, keyword);
            } else {
                bookList = bookService.searchBook(categoryId);
            }
        } else {
            if (keyword != null && !keyword.isEmpty()) {
                bookList = bookService.searchBook(keyword);
            } else {
                bookList = bookService.getBooksAll();
            }
        }

        // ?????bookSelect ??????
        return "bookSelect?faces-redirect=true";
    }

    // ????????????????????
    public String search2() {
        logger.info("[ BookSearchBean#search2 ] categoryId=" + categoryId + ", keyword=" + keyword);
        
        // ???????????
        bookList = bookService.searchBookWithCriteria(categoryId, keyword);
        
        // ?????bookSelect ??????
        return "bookSelect?faces-redirect=true";
    }

    // ???????????????bookSelect?????
    public void loadAllBooks() {
        logger.info("[ BookSearchBean#loadAllBooks ]");
        bookList = bookService.getBooksAll();
    }

    // ??????????????????????????????
    public void refreshBookList() {
        logger.info("[ BookSearchBean#refreshBookList ]");
        
        // ?????????????????????
        if (bookList == null || bookList.isEmpty()) {
            // ????????????
            bookList = bookService.getBooksAll();
        } else {
            // ????????????????????????
            // ???????????????????????
            if (categoryId != null && categoryId != 0) {
                if (keyword != null && !keyword.isEmpty()) {
                    bookList = bookService.searchBook(categoryId, keyword);
                } else {
                    bookList = bookService.searchBook(categoryId);
                }
            } else {
                // ????????????????
                if (keyword != null && !keyword.isEmpty()) {
                    bookList = bookService.searchBook(keyword);
                } else {
                    // ????????????????
                    bookList = bookService.getBooksAll();
                }
            }
        }
    }

    // Getters and Setters
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public Map<String, Integer> getCategoryMap() {
        return categoryMap;
    }
}

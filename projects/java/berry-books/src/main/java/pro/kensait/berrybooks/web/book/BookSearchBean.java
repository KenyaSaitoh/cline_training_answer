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

// 譖ｸ邀肴､懃ｴ｢逕ｻ髱｢縺ｮ繝舌ャ繧ｭ繝ｳ繧ｰBean
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

    // 讀懃ｴ｢譚｡莉ｶ
    private Integer categoryId;
    private String keyword;

    // 讀懃ｴ｢邨先棡
    private List<Book> bookList;

    // 繧ｫ繝・ざ繝ｪ繝槭ャ繝暦ｼ医そ繝ｬ繧ｯ繝医・繝・け繧ｹ逕ｨ・・
    private Map<String, Integer> categoryMap;

    @PostConstruct
    public void init() {
        logger.info("[ BookSearchBean#init ]");
        
        // 繧ｫ繝・ざ繝ｪ繝槭ャ繝励ｒ蛻晄悄蛹・
        categoryMap = new HashMap<>();
        categoryMap.put("", null);
        categoryMap.putAll(categoryService.getCategoryMap());
        
        // bookList縺ｯ蛻晄悄蛹匁凾縺ｯ蜈ｨ譖ｸ邀阪ｒ蜿門ｾ・
        if (bookList == null || bookList.isEmpty()) {
            bookList = bookService.getBooksAll();
        }
    }

    // 繧｢繧ｯ繧ｷ繝ｧ繝ｳ・壽嶌邀阪ｒ讀懃ｴ｢縺吶ｋ・磯撕逧・け繧ｨ繝ｪ・・
    public String search() {
        logger.info("[ BookSearchBean#search ] categoryId=" + categoryId + ", keyword=" + keyword);

        // 讀懃ｴ｢譚｡莉ｶ縺ｫ蝓ｺ縺･縺・※譖ｸ邀阪ｒ讀懃ｴ｢
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

        // 讀懃ｴ｢邨先棡繧・bookSelect 繝壹・繧ｸ縺ｫ陦ｨ遉ｺ
        return "bookSelect?faces-redirect=true";
    }

    // 繧｢繧ｯ繧ｷ繝ｧ繝ｳ・壽嶌邀阪ｒ讀懃ｴ｢縺吶ｋ・亥虚逧・け繧ｨ繝ｪ・・
    public String search2() {
        logger.info("[ BookSearchBean#search2 ] categoryId=" + categoryId + ", keyword=" + keyword);
        
        // 蜍慕噪繧ｯ繧ｨ繝ｪ縺ｧ譖ｸ邀阪ｒ讀懃ｴ｢
        bookList = bookService.searchBookWithCriteria(categoryId, keyword);
        
        // 讀懃ｴ｢邨先棡繧・bookSelect 繝壹・繧ｸ縺ｫ陦ｨ遉ｺ
        return "bookSelect?faces-redirect=true";
    }

    // 繧｢繧ｯ繧ｷ繝ｧ繝ｳ・壼・譖ｸ邀阪ｒ隱ｭ縺ｿ霎ｼ繧・・ookSelect繝壹・繧ｸ逕ｨ・・
    public void loadAllBooks() {
        logger.info("[ BookSearchBean#loadAllBooks ]");
        bookList = bookService.getBooksAll();
    }

    // 繧｢繧ｯ繧ｷ繝ｧ繝ｳ・壽嶌邀阪Μ繧ｹ繝医ｒ譛譁ｰ縺ｮ迥ｶ諷九↓譖ｴ譁ｰ縺吶ｋ・亥惠蠎ｫ謨ｰ繧貞性繧・・
    public void refreshBookList() {
        logger.info("[ BookSearchBean#refreshBookList ]");
        
        // 譌｢蟄倥・讀懃ｴ｢譚｡莉ｶ繧剃ｽｿ逕ｨ縺励※譖ｸ邀阪Μ繧ｹ繝医ｒ蜀榊叙蠕・
        if (bookList == null || bookList.isEmpty()) {
            // 蛻晏屓陦ｨ遉ｺ譎ゅ・蜈ｨ譖ｸ邀阪ｒ蜿門ｾ・
            bookList = bookService.getBooksAll();
        } else {
            // 譌｢縺ｫ讀懃ｴ｢縺悟ｮ溯｡後＆繧後※縺・ｋ蝣ｴ蜷医・縲∝酔縺俶擅莉ｶ縺ｧ蜀肴､懃ｴ｢
            // 繧ｫ繝・ざ繝ｪ縺ｨ繧ｭ繝ｼ繝ｯ繝ｼ繝峨・荳｡譁ｹ縺瑚ｨｭ螳壹＆繧後※縺・ｋ蝣ｴ蜷・
            if (categoryId != null && categoryId != 0) {
                if (keyword != null && !keyword.isEmpty()) {
                    bookList = bookService.searchBook(categoryId, keyword);
                } else {
                    bookList = bookService.searchBook(categoryId);
                }
            } else {
                // 繧ｭ繝ｼ繝ｯ繝ｼ繝峨・縺ｿ險ｭ螳壹＆繧後※縺・ｋ蝣ｴ蜷・
                if (keyword != null && !keyword.isEmpty()) {
                    bookList = bookService.searchBook(keyword);
                } else {
                    // 讀懃ｴ｢譚｡莉ｶ縺後↑縺・ｴ蜷医・蜈ｨ譖ｸ邀阪ｒ蜿門ｾ・
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


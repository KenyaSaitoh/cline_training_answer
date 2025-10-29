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

// 書籍検索画面のバッキングBean
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

    // 検索条件
    private Integer categoryId;
    private String keyword;

    // 検索結果
    private List<Book> bookList;

    // カチE��リマップ（セレクト�EチE��ス用�E�E
    private Map<String, Integer> categoryMap;

    @PostConstruct
    public void init() {
        logger.info("[ BookSearchBean#init ]");
        
        // カチE��リマップを初期匁E
        categoryMap = new HashMap<>();
        categoryMap.put("", null);
        categoryMap.putAll(categoryService.getCategoryMap());
        
        // bookListは初期化時は全書籍を取征E
        if (bookList == null || bookList.isEmpty()) {
            bookList = bookService.getBooksAll();
        }
    }

    // アクション�E�書籍を検索する�E�静皁E��エリ�E�E
    public String search() {
        logger.info("[ BookSearchBean#search ] categoryId=" + categoryId + ", keyword=" + keyword);

        // 検索条件に基づぁE��書籍を検索
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

        // 検索結果めEbookSelect ペ�Eジに表示
        return "bookSelect?faces-redirect=true";
    }

    // アクション�E�書籍を検索する�E�動皁E��エリ�E�E
    public String search2() {
        logger.info("[ BookSearchBean#search2 ] categoryId=" + categoryId + ", keyword=" + keyword);
        
        // 動的クエリで書籍を検索
        bookList = bookService.searchBookWithCriteria(categoryId, keyword);
        
        // 検索結果めEbookSelect ペ�Eジに表示
        return "bookSelect?faces-redirect=true";
    }

    // アクション�E��E書籍を読み込む�E�EookSelectペ�Eジ用�E�E
    public void loadAllBooks() {
        logger.info("[ BookSearchBean#loadAllBooks ]");
        bookList = bookService.getBooksAll();
    }

    // アクション�E�書籍リストを最新の状態に更新する�E�在庫数を含む�E�E
    public void refreshBookList() {
        logger.info("[ BookSearchBean#refreshBookList ]");
        
        // 既存�E検索条件を使用して書籍リストを再取征E
        if (bookList == null || bookList.isEmpty()) {
            // 初回表示時�E全書籍を取征E
            bookList = bookService.getBooksAll();
        } else {
            // 既に検索が実行されてぁE��場合�E、同じ条件で再検索
            // カチE��リとキーワード�E両方が設定されてぁE��場吁E
            if (categoryId != null && categoryId != 0) {
                if (keyword != null && !keyword.isEmpty()) {
                    bookList = bookService.searchBook(categoryId, keyword);
                } else {
                    bookList = bookService.searchBook(categoryId);
                }
            } else {
                // キーワード�Eみ設定されてぁE��場吁E
                if (keyword != null && !keyword.isEmpty()) {
                    bookList = bookService.searchBook(keyword);
                } else {
                    // 検索条件がなぁE��合�E全書籍を取征E
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


package pro.kensait.berrybooks.service.book;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pro.kensait.berrybooks.dao.BookDao;
import pro.kensait.berrybooks.entity.Book;
import pro.kensait.berrybooks.entity.Category;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookDao bookDao;

    @InjectMocks
    private BookService bookService;

    private Book testBook1;
    private Book testBook2;
    private Book testBook3;
    private List<Book> testBookList;

    @BeforeEach
    void setUp() {
        Category category1 = new Category();
        category1.setCategoryId(1);
        category1.setCategoryName("技術書");
        
        Category category2 = new Category();
        category2.setCategoryId(2);
        category2.setCategoryName("ビジネス");
        
        testBook1 = new Book();
        testBook1.setBookId(1);
        testBook1.setBookName("Java入門");
        testBook1.setPrice(new BigDecimal("2800"));
        testBook1.setCategory(category1);

        testBook2 = new Book();
        testBook2.setBookId(2);
        testBook2.setBookName("Spring Boot実践");
        testBook2.setPrice(new BigDecimal("3500"));
        testBook2.setCategory(category1);

        testBook3 = new Book();
        testBook3.setBookId(3);
        testBook3.setBookName("ビジネスマナー");
        testBook3.setPrice(new BigDecimal("1800"));
        testBook3.setCategory(category2);

        testBookList = new ArrayList<>();
        testBookList.add(testBook1);
        testBookList.add(testBook2);
        testBookList.add(testBook3);
    }

    // getBookのチE��チE
    @Test
    @DisplayName("書籍IDで書籍情報を取得できることをテストすめE)
    void testGetBookSuccess() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        Integer bookId = 1;
        when(bookDao.findById(bookId)).thenReturn(testBook1);

        // 実行フェーズ
        Book result = bookService.getBook(bookId);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(bookId, result.getBookId());
        assertEquals("Java入門", result.getBookName());
        verify(bookDao, times(1)).findById(bookId);
    }

    @Test
    @DisplayName("存在しなぁE��籍IDで例外がスローされることをテストすめE)
    void testGetBookNotFound() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        Integer bookId = 999;
        when(bookDao.findById(bookId)).thenReturn(null);

        // 実行フェーズと検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookService.getBook(bookId);
        });
        assertTrue(exception.getMessage().contains("Book not found"));
        verify(bookDao, times(1)).findById(bookId);
    }

    @Test
    @DisplayName("書籍IDがnullの場合に例外がスローされることをテストすめE)
    void testGetBookNullId() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        when(bookDao.findById(null)).thenReturn(null);

        // 実行フェーズと検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookService.getBook(null);
        });
        assertTrue(exception.getMessage().contains("Book not found"));
        verify(bookDao, times(1)).findById(null);
    }

    // getBooksAllのチE��チE
    @Test
    @DisplayName("全書籍を取得できることをテストすめE)
    void testGetBooksAll() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        when(bookDao.findAll()).thenReturn(testBookList);

        // 実行フェーズ
        List<Book> result = bookService.getBooksAll();

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Java入門", result.get(0).getBookName());
        assertEquals("Spring Boot実践", result.get(1).getBookName());
        assertEquals("ビジネスマナー", result.get(2).getBookName());
        verify(bookDao, times(1)).findAll();
    }

    @Test
    @DisplayName("書籍が0件の場合に空のリストが返されることをテストすめE)
    void testGetBooksAllEmpty() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        when(bookDao.findAll()).thenReturn(new ArrayList<>());

        // 実行フェーズ
        List<Book> result = bookService.getBooksAll();

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(0, result.size());
        verify(bookDao, times(1)).findAll();
    }

    // searchBook(Integer categoryId, String keyword)のチE��チE
    @Test
    @DisplayName("カチE��リIDとキーワードで書籍を検索できることをテストすめE)
    void testSearchBookByCategoryAndKeyword() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        Integer categoryId = 1;
        String keyword = "Java";
        List<Book> searchResults = new ArrayList<>();
        searchResults.add(testBook1);
        
        when(bookDao.query(categoryId, "%" + keyword + "%")).thenReturn(searchResults);

        // 実行フェーズ
        List<Book> result = bookService.searchBook(categoryId, keyword);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Java入門", result.get(0).getBookName());
        verify(bookDao, times(1)).query(categoryId, "%" + keyword + "%");
    }

    @Test
    @DisplayName("カチE��リIDとキーワード�E検索で結果ぁE件の場合に空のリストが返されることをテストすめE)
    void testSearchBookByCategoryAndKeywordNoResults() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        Integer categoryId = 1;
        String keyword = "存在しなぁE��ーワーチE;
        
        when(bookDao.query(categoryId, "%" + keyword + "%")).thenReturn(new ArrayList<>());

        // 実行フェーズ
        List<Book> result = bookService.searchBook(categoryId, keyword);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(0, result.size());
        verify(bookDao, times(1)).query(categoryId, "%" + keyword + "%");
    }

    // searchBook(Integer categoryId)のチE��チE
    @Test
    @DisplayName("カチE��リIDで書籍を検索できることをテストすめE)
    void testSearchBookByCategoryId() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        Integer categoryId = 1;
        List<Book> searchResults = new ArrayList<>();
        searchResults.add(testBook1);
        searchResults.add(testBook2);
        
        when(bookDao.queryByCategory(categoryId)).thenReturn(searchResults);

        // 実行フェーズ
        List<Book> result = bookService.searchBook(categoryId);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Java入門", result.get(0).getBookName());
        assertEquals("Spring Boot実践", result.get(1).getBookName());
        verify(bookDao, times(1)).queryByCategory(categoryId);
    }

    @Test
    @DisplayName("カチE��リIDの検索で結果ぁE件の場合に空のリストが返されることをテストすめE)
    void testSearchBookByCategoryIdNoResults() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        Integer categoryId = 999;
        
        when(bookDao.queryByCategory(categoryId)).thenReturn(new ArrayList<>());

        // 実行フェーズ
        List<Book> result = bookService.searchBook(categoryId);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(0, result.size());
        verify(bookDao, times(1)).queryByCategory(categoryId);
    }

    // searchBook(String keyword)のチE��チE
    @Test
    @DisplayName("キーワードで書籍を検索できることをテストすめE)
    void testSearchBookByKeyword() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        String keyword = "Java";
        List<Book> searchResults = new ArrayList<>();
        searchResults.add(testBook1);
        
        when(bookDao.queryByKeyword("%" + keyword + "%")).thenReturn(searchResults);

        // 実行フェーズ
        List<Book> result = bookService.searchBook(keyword);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Java入門", result.get(0).getBookName());
        verify(bookDao, times(1)).queryByKeyword("%" + keyword + "%");
    }

    @Test
    @DisplayName("キーワード�E検索で結果ぁE件の場合に空のリストが返されることをテストすめE)
    void testSearchBookByKeywordNoResults() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        String keyword = "存在しなぁE��ーワーチE;
        
        when(bookDao.queryByKeyword("%" + keyword + "%")).thenReturn(new ArrayList<>());

        // 実行フェーズ
        List<Book> result = bookService.searchBook(keyword);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(0, result.size());
        verify(bookDao, times(1)).queryByKeyword("%" + keyword + "%");
    }

    @Test
    @DisplayName("空斁E���Eのキーワードで検索が実行されることをテストすめE)
    void testSearchBookByEmptyKeyword() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        String keyword = "";
        
        when(bookDao.queryByKeyword("%" + keyword + "%")).thenReturn(testBookList);

        // 実行フェーズ
        List<Book> result = bookService.searchBook(keyword);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(3, result.size());
        verify(bookDao, times(1)).queryByKeyword("%" + keyword + "%");
    }

    // searchBookWithCriteriaのチE��チE
    @Test
    @DisplayName("動的クエリでカチE��リIDとキーワードを使って書籍を検索できることをテストすめE)
    void testSearchBookWithCriteria() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        Integer categoryId = 1;
        String keyword = "Java";
        List<Book> searchResults = new ArrayList<>();
        searchResults.add(testBook1);
        
        when(bookDao.searchWithCriteria(categoryId, "%" + keyword + "%")).thenReturn(searchResults);

        // 実行フェーズ
        List<Book> result = bookService.searchBookWithCriteria(categoryId, keyword);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Java入門", result.get(0).getBookName());
        verify(bookDao, times(1)).searchWithCriteria(categoryId, "%" + keyword + "%");
    }

    @Test
    @DisplayName("動的クエリでカチE��リIDのみを指定して検索できることをテストすめE)
    void testSearchBookWithCriteriaCategoryOnly() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        Integer categoryId = 1;
        String keyword = null;
        List<Book> searchResults = new ArrayList<>();
        searchResults.add(testBook1);
        searchResults.add(testBook2);
        
        when(bookDao.searchWithCriteria(categoryId, null)).thenReturn(searchResults);

        // 実行フェーズ
        List<Book> result = bookService.searchBookWithCriteria(categoryId, keyword);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookDao, times(1)).searchWithCriteria(categoryId, null);
    }

    @Test
    @DisplayName("動的クエリでキーワードが空斁E���Eの場合にnullとして処琁E��れることをテストすめE)
    void testSearchBookWithCriteriaEmptyKeyword() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        Integer categoryId = 1;
        String keyword = "";
        List<Book> searchResults = new ArrayList<>();
        searchResults.add(testBook1);
        searchResults.add(testBook2);
        
        when(bookDao.searchWithCriteria(categoryId, null)).thenReturn(searchResults);

        // 実行フェーズ
        List<Book> result = bookService.searchBookWithCriteria(categoryId, keyword);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookDao, times(1)).searchWithCriteria(categoryId, null);
    }

    @Test
    @DisplayName("動的クエリで検索条件がnullの場合に検索が実行されることをテストすめE)
    void testSearchBookWithCriteriaNullConditions() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        Integer categoryId = null;
        String keyword = null;
        
        when(bookDao.searchWithCriteria(null, null)).thenReturn(testBookList);

        // 実行フェーズ
        List<Book> result = bookService.searchBookWithCriteria(categoryId, keyword);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(3, result.size());
        verify(bookDao, times(1)).searchWithCriteria(null, null);
    }

    @Test
    @DisplayName("動的クエリで検索結果ぁE件の場合に空のリストが返されることをテストすめE)
    void testSearchBookWithCriteriaNoResults() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        Integer categoryId = 1;
        String keyword = "存在しなぁE��ーワーチE;
        
        when(bookDao.searchWithCriteria(categoryId, "%" + keyword + "%")).thenReturn(new ArrayList<>());

        // 実行フェーズ
        List<Book> result = bookService.searchBookWithCriteria(categoryId, keyword);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(0, result.size());
        verify(bookDao, times(1)).searchWithCriteria(categoryId, "%" + keyword + "%");
    }
}

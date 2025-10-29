package pro.kensait.berrybooks.service.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import pro.kensait.berrybooks.dao.CategoryDao;
import pro.kensait.berrybooks.entity.Category;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryDao categoryDao;

    @InjectMocks
    private CategoryService categoryService;

    private List<Category> testCategoryList;
    private Category category1;
    private Category category2;
    private Category category3;

    @BeforeEach
    void setUp() {
        category1 = new Category();
        category1.setCategoryId(1);
        category1.setCategoryName("斁E��");

        category2 = new Category();
        category2.setCategoryId(2);
        category2.setCategoryName("ビジネス");

        category3 = new Category();
        category3.setCategoryId(3);
        category3.setCategoryName("技術書");

        testCategoryList = new ArrayList<>();
        testCategoryList.add(category1);
        testCategoryList.add(category2);
        testCategoryList.add(category3);
    }

    @Test
    @DisplayName("カチE��リの全件取得が正常に動作することをテストすめE)
    void testGetCategoriesAll() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        when(categoryDao.findAll()).thenReturn(testCategoryList);

        // 実行フェーズ
        List<Category> result = categoryService.getCategoriesAll();

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("斁E��", result.get(0).getCategoryName());
        assertEquals("ビジネス", result.get(1).getCategoryName());
        assertEquals("技術書", result.get(2).getCategoryName());
        verify(categoryDao, times(1)).findAll();
    }

    @Test
    @DisplayName("カチE��リぁE件の場合に空のリストが返されることをテストすめE)
    void testGetCategoriesAllEmpty() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        when(categoryDao.findAll()).thenReturn(new ArrayList<>());

        // 実行フェーズ
        List<Category> result = categoryService.getCategoriesAll();

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(0, result.size());
        verify(categoryDao, times(1)).findAll();
    }

    @Test
    @DisplayName("カチE��リ名をキーとしたマップが正しく生�EされることをテストすめE)
    void testGetCategoryMap() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        when(categoryDao.findAll()).thenReturn(testCategoryList);

        // 実行フェーズ
        Map<String, Integer> result = categoryService.getCategoryMap();

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(1, result.get("斁E��"));
        assertEquals(2, result.get("ビジネス"));
        assertEquals(3, result.get("技術書"));
        verify(categoryDao, times(1)).findAll();
    }

    @Test
    @DisplayName("カチE��リぁE件の場合に空のマップが返されることをテストすめE)
    void testGetCategoryMapEmpty() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        when(categoryDao.findAll()).thenReturn(new ArrayList<>());

        // 実行フェーズ
        Map<String, Integer> result = categoryService.getCategoryMap();

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(0, result.size());
        verify(categoryDao, times(1)).findAll();
    }

    @Test
    @DisplayName("カチE��リぁE件のみの場合に正しくマップが生�EされることをテストすめE)
    void testGetCategoryMapSingleItem() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        List<Category> singleItemList = new ArrayList<>();
        singleItemList.add(category1);
        when(categoryDao.findAll()).thenReturn(singleItemList);

        // 実行フェーズ
        Map<String, Integer> result = categoryService.getCategoryMap();

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get("斁E��"));
        verify(categoryDao, times(1)).findAll();
    }

    @Test
    @DisplayName("同じカチE��リ名が存在する場合に後老E��上書きされることをテストすめE)
    void testGetCategoryMapDuplicateNames() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        Category category4 = new Category();
        category4.setCategoryId(4);
        category4.setCategoryName("斁E��"); // 重褁E��るカチE��リ吁E
        List<Category> listWithDuplicate = new ArrayList<>();
        listWithDuplicate.add(category1);
        listWithDuplicate.add(category4);

        when(categoryDao.findAll()).thenReturn(listWithDuplicate);

        // 実行フェーズ
        Map<String, Integer> result = categoryService.getCategoryMap();

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(1, result.size());
        // 後で追加されたものが上書きされる
        assertEquals(4, result.get("斁E��"));
        verify(categoryDao, times(1)).findAll();
    }
}

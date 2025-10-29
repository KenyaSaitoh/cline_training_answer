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
        category1.setCategoryName("譁・ｭｦ");

        category2 = new Category();
        category2.setCategoryId(2);
        category2.setCategoryName("繝薙ず繝阪せ");

        category3 = new Category();
        category3.setCategoryId(3);
        category3.setCategoryName("謚陦捺嶌");

        testCategoryList = new ArrayList<>();
        testCategoryList.add(category1);
        testCategoryList.add(category2);
        testCategoryList.add(category3);
    }

    @Test
    @DisplayName("繧ｫ繝・ざ繝ｪ縺ｮ蜈ｨ莉ｶ蜿門ｾ励′豁｣蟶ｸ縺ｫ蜍穂ｽ懊☆繧九％縺ｨ繧偵ユ繧ｹ繝医☆繧・)
    void testGetCategoriesAll() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・        when(categoryDao.findAll()).thenReturn(testCategoryList);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        List<Category> result = categoryService.getCategoriesAll();

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("譁・ｭｦ", result.get(0).getCategoryName());
        assertEquals("繝薙ず繝阪せ", result.get(1).getCategoryName());
        assertEquals("謚陦捺嶌", result.get(2).getCategoryName());
        verify(categoryDao, times(1)).findAll();
    }

    @Test
    @DisplayName("繧ｫ繝・ざ繝ｪ縺・莉ｶ縺ｮ蝣ｴ蜷医↓遨ｺ縺ｮ繝ｪ繧ｹ繝医′霑斐＆繧後ｋ縺薙→繧偵ユ繧ｹ繝医☆繧・)
    void testGetCategoriesAllEmpty() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・        when(categoryDao.findAll()).thenReturn(new ArrayList<>());

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        List<Category> result = categoryService.getCategoriesAll();

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・        assertNotNull(result);
        assertEquals(0, result.size());
        verify(categoryDao, times(1)).findAll();
    }

    @Test
    @DisplayName("繧ｫ繝・ざ繝ｪ蜷阪ｒ繧ｭ繝ｼ縺ｨ縺励◆繝槭ャ繝励′豁｣縺励￥逕滓・縺輔ｌ繧九％縺ｨ繧偵ユ繧ｹ繝医☆繧・)
    void testGetCategoryMap() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・        when(categoryDao.findAll()).thenReturn(testCategoryList);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        Map<String, Integer> result = categoryService.getCategoryMap();

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(1, result.get("譁・ｭｦ"));
        assertEquals(2, result.get("繝薙ず繝阪せ"));
        assertEquals(3, result.get("謚陦捺嶌"));
        verify(categoryDao, times(1)).findAll();
    }

    @Test
    @DisplayName("繧ｫ繝・ざ繝ｪ縺・莉ｶ縺ｮ蝣ｴ蜷医↓遨ｺ縺ｮ繝槭ャ繝励′霑斐＆繧後ｋ縺薙→繧偵ユ繧ｹ繝医☆繧・)
    void testGetCategoryMapEmpty() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・        when(categoryDao.findAll()).thenReturn(new ArrayList<>());

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        Map<String, Integer> result = categoryService.getCategoryMap();

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・        assertNotNull(result);
        assertEquals(0, result.size());
        verify(categoryDao, times(1)).findAll();
    }

    @Test
    @DisplayName("繧ｫ繝・ざ繝ｪ縺・莉ｶ縺ｮ縺ｿ縺ｮ蝣ｴ蜷医↓豁｣縺励￥繝槭ャ繝励′逕滓・縺輔ｌ繧九％縺ｨ繧偵ユ繧ｹ繝医☆繧・)
    void testGetCategoryMapSingleItem() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・        List<Category> singleItemList = new ArrayList<>();
        singleItemList.add(category1);
        when(categoryDao.findAll()).thenReturn(singleItemList);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        Map<String, Integer> result = categoryService.getCategoryMap();

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get("譁・ｭｦ"));
        verify(categoryDao, times(1)).findAll();
    }

    @Test
    @DisplayName("蜷後§繧ｫ繝・ざ繝ｪ蜷阪′蟄伜惠縺吶ｋ蝣ｴ蜷医↓蠕瑚・〒荳頑嶌縺阪＆繧後ｋ縺薙→繧偵ユ繧ｹ繝医☆繧・)
    void testGetCategoryMapDuplicateNames() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・        Category category4 = new Category();
        category4.setCategoryId(4);
        category4.setCategoryName("譁・ｭｦ"); // 驥崎､・☆繧九き繝・ざ繝ｪ蜷・
        List<Category> listWithDuplicate = new ArrayList<>();
        listWithDuplicate.add(category1);
        listWithDuplicate.add(category4);

        when(categoryDao.findAll()).thenReturn(listWithDuplicate);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        Map<String, Integer> result = categoryService.getCategoryMap();

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・        assertNotNull(result);
        assertEquals(1, result.size());
        // 蠕後〒霑ｽ蜉縺輔ｌ縺溘ｂ縺ｮ縺御ｸ頑嶌縺阪＆繧後ｋ
        assertEquals(4, result.get("譁・ｭｦ"));
        verify(categoryDao, times(1)).findAll();
    }
}

package pro.kensait.berrybooks.service.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.dao.CategoryDao;
import pro.kensait.berrybooks.entity.Category;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

// 繧ｫ繝・ざ繝ｪ繧貞叙蠕励☆繧九し繝ｼ繝薙せ繧ｯ繝ｩ繧ｹ
@ApplicationScoped
public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(
            CategoryService.class);

    @Inject
    private CategoryDao categoryDao;

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・壹き繝・ざ繝ｪ縺ｮ蜿門ｾ暦ｼ亥・莉ｶ讀懃ｴ｢・・
    public List<Category> getCategoriesAll() {
        logger.info("[ CategoryService#getCategoriesAll ]");
        return categoryDao.findAll();
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・壹き繝・ざ繝ｪ繝槭ャ繝励・蜿門ｾ・
    public Map<String, Integer> getCategoryMap() {
        logger.info("[ CategoryService#getCategoryMap ]");
        
        Map<String, Integer> categoryMap = new HashMap<>();
        List<Category> categories = categoryDao.findAll();
        
        for (Category category : categories) {
            categoryMap.put(category.getCategoryName(), category.getCategoryId());
        }
        
        return categoryMap;
    }
}



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

// ????????????????
@ApplicationScoped
public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(
            CategoryService.class);

    @Inject
    private CategoryDao categoryDao;

    // ??????????????????????
    public List<Category> getCategoriesAll() {
        logger.info("[ CategoryService#getCategoriesAll ]");
        return categoryDao.findAll();
    }

    // ???????????????????
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

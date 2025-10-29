package pro.kensait.berrybooks.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CATEGORY")
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    // 繧ｫ繝・ざ繝ｪID
    @Id
    @Column(name = "CATEGORY_ID")
    private Integer categoryId;

    // 繧ｫ繝・ざ繝ｪ蜷・
    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    // 蠑墓焚縺ｪ縺励・繧ｳ繝ｳ繧ｹ繝医Λ繧ｯ繧ｿ
    public Category() {
    }
    
    // 繧ｳ繝ｳ繧ｹ繝医Λ繧ｯ繧ｿ
    public Category(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Category [categoryId=" + categoryId + ", categoryName=" + categoryName
                + "]";
    }
}


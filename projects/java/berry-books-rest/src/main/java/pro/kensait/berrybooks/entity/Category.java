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
    // カチE��リID
    @Id
    @Column(name = "CATEGORY_ID")
    private Integer categoryId;

    // カチE��リ吁E
    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    // 引数なし�Eコンストラクタ
    public Category() {
    }
    
    // コンストラクタ
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


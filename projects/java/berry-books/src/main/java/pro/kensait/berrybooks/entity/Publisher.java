package pro.kensait.berrybooks.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// 蜃ｺ迚育､ｾ繧定｡ｨ縺吶お繝ｳ繝・ぅ繝・ぅ繧ｯ繝ｩ繧ｹ
@Entity
@Table(name = "PUBLISHER")
public class Publisher implements Serializable {
    private static final long serialVersionUID = 1L;
    // 蜃ｺ迚育､ｾID
    @Id
    @Column(name = "PUBLISHER_ID")
    private int publisherId;

    // 蜃ｺ迚育､ｾ蜷・
    @Column(name = "PUBLISHER_NAME")
    private String publisherName;

    //  蠑墓焚縺ｪ縺励・繧ｳ繝ｳ繧ｹ繝医Λ繧ｯ繧ｿ
    public Publisher() {
    }

    // 繧ｳ繝ｳ繧ｹ繝医Λ繧ｯ繧ｿ
    public Publisher(int publisherId, String publisherName) {
        this.publisherId = publisherId;
        this.publisherName = publisherName;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    @Override
    public String toString() {
        return "Publisher [publisherId=" + publisherId + ", publisherName="
                + publisherName + "]";
    }
}
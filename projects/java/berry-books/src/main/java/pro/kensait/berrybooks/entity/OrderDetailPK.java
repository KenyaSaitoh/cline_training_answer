package pro.kensait.berrybooks.entity;

import java.io.Serializable;

// 豕ｨ譁・・邏ｰ縺ｮ隍・粋荳ｻ繧ｭ繝ｼ繧ｯ繝ｩ繧ｹ
public class OrderDetailPK implements Serializable {
    // 豕ｨ譁⑩D
    private Integer orderTranId;

    // 豕ｨ譁・・邏ｰID
    private Integer orderDetailId;

    // 蠑墓焚縺ｪ縺励・繧ｳ繝ｳ繧ｹ繝医Λ繧ｯ繧ｿ
    public OrderDetailPK() {
    }

    // 繧ｳ繝ｳ繧ｹ繝医Λ繧ｯ繧ｿ
    public OrderDetailPK(int orderTranId, int orderDetailId) {
        this.orderTranId = orderTranId;
        this.orderDetailId = orderDetailId;
    }

    // 豕ｨ譁⑩D縺ｸ縺ｮ繧｢繧ｯ繧ｻ繧ｵ繝｡繧ｽ繝・ラ・医ご繝・ち縺ｮ縺ｿ・・
    public Integer getOrderTranId() {
        return orderTranId;
    }

    // 豕ｨ譁・・邏ｰID縺ｸ縺ｮ繧｢繧ｯ繧ｻ繧ｵ繝｡繧ｽ繝・ラ・医ご繝・ち縺ｮ縺ｿ・・
    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    // 荳諢乗ｧ繧剃ｿ晁ｨｼ縺吶ｋ縺溘ａ縺ｫ縲∝ｿ・★equals繝｡繧ｽ繝・ラ繧偵が繝ｼ繝舌・繝ｩ繧､繝峨☆繧・
    public boolean equals(Object obj) {
        return ((obj instanceof OrderDetailPK) &&
                orderTranId == (((OrderDetailPK)obj).getOrderTranId()) &&
                orderDetailId == (((OrderDetailPK)obj).getOrderDetailId()));
    }

    // equals繝｡繧ｽ繝・ラ縺ｫ蜷医ｏ縺帙※縲”ashcode繝｡繧ｽ繝・ラ繧ゅが繝ｼ繝舌・繝ｩ繧､繝峨☆繧・
    public int hashCode() {
        return orderTranId + orderDetailId;
    }
}
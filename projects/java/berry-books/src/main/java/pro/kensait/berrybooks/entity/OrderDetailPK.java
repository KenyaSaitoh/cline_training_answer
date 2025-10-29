package pro.kensait.berrybooks.entity;

import java.io.Serializable;

// 注斁E�E細の褁E��主キークラス
public class OrderDetailPK implements Serializable {
    // 注文ID
    private Integer orderTranId;

    // 注斁E�E細ID
    private Integer orderDetailId;

    // 引数なし�Eコンストラクタ
    public OrderDetailPK() {
    }

    // コンストラクタ
    public OrderDetailPK(int orderTranId, int orderDetailId) {
        this.orderTranId = orderTranId;
        this.orderDetailId = orderDetailId;
    }

    // 注文IDへのアクセサメソチE���E�ゲチE��のみ�E�E
    public Integer getOrderTranId() {
        return orderTranId;
    }

    // 注斁E�E細IDへのアクセサメソチE���E�ゲチE��のみ�E�E
    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    // 一意性を保証するために、忁E��equalsメソチE��をオーバ�EライドすめE
    public boolean equals(Object obj) {
        return ((obj instanceof OrderDetailPK) &&
                orderTranId == (((OrderDetailPK)obj).getOrderTranId()) &&
                orderDetailId == (((OrderDetailPK)obj).getOrderDetailId()));
    }

    // equalsメソチE��に合わせて、hashcodeメソチE��もオーバ�EライドすめE
    public int hashCode() {
        return orderTranId + orderDetailId;
    }
}
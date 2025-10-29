package pro.kensait.berrybooks.service.delivery;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeliveryFeeServiceTest {

    private DeliveryFeeService deliveryFeeService;

    @BeforeEach
    void setUp() {
        deliveryFeeService = new DeliveryFeeService();
    }

    // calculateDeliveryFeeのチE��チE

    @Test
    @DisplayName("通常の配送�Eで標準�E送料金！E00冁E��が計算されることをテストすめE)
    void testCalculateDeliveryFeeStandard() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E
        String address = "東京都渋谷区";
        BigDecimal totalPrice = new BigDecimal("3000");

        // 実行フェーズ
        BigDecimal result = deliveryFeeService.calculateDeliveryFee(address, totalPrice);

        // 検証フェーズ�E��E力値ベ�Eス�E�E
        assertEquals(new BigDecimal("800"), result);
    }

    @Test
    @DisplayName("沖縁E��への配送で配送料金が1700冁E��なることをテストすめE)
    void testCalculateDeliveryFeeOkinawa() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E
        String address = "沖縁E��那要E��E;
        BigDecimal totalPrice = new BigDecimal("3000");

        // 実行フェーズ
        BigDecimal result = deliveryFeeService.calculateDeliveryFee(address, totalPrice);

        // 検証フェーズ�E��E力値ベ�Eス�E�E
        assertEquals(new BigDecimal("1700"), result);
    }

    @Test
    @DisplayName("沖縁E��で始まる住所の場合に配送料金が1700冁E��なることをテストすめE)
    void testCalculateDeliveryFeeOkinawaPrefix() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E
        String address = "沖縁E��宮古島币E;
        BigDecimal totalPrice = new BigDecimal("4999");

        // 実行フェーズ
        BigDecimal result = deliveryFeeService.calculateDeliveryFee(address, totalPrice);

        // 検証フェーズ�E��E力値ベ�Eス�E�E
        assertEquals(new BigDecimal("1700"), result);
    }

    @Test
    @DisplayName("購入金額が5000冁E��上�E場合に送料無料になることをテストすめE)
    void testCalculateDeliveryFeeFree() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E
        String address = "大阪府大阪币E;
        BigDecimal totalPrice = new BigDecimal("5000");

        // 実行フェーズ
        BigDecimal result = deliveryFeeService.calculateDeliveryFee(address, totalPrice);

        // 検証フェーズ�E��E力値ベ�Eス�E�E
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    @DisplayName("沖縁E��でめE000冁E��上�E購入で送料無料になることをテストすめE)
    void testCalculateDeliveryFeeFreeOkinawa() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E
        String address = "沖縁E��那要E��E;
        BigDecimal totalPrice = new BigDecimal("5000");

        // 実行フェーズ
        BigDecimal result = deliveryFeeService.calculateDeliveryFee(address, totalPrice);

        // 検証フェーズ�E��E力値ベ�Eス�E�E
        // 5000冁E��上なので送料無料（沖縁E��でも！E
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    @DisplayName("購入金額が送料無料基準を趁E��る場合に送料無料になることをテストすめE)
    void testCalculateDeliveryFeeAboveThreshold() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E
        String address = "東京都新宿区";
        BigDecimal totalPrice = new BigDecimal("10000");

        // 実行フェーズ
        BigDecimal result = deliveryFeeService.calculateDeliveryFee(address, totalPrice);

        // 検証フェーズ�E��E力値ベ�Eス�E�E
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    @DisplayName("購入金額がちめE��ど5000冁E�E場合に送料無料になることをテストする（墁E��値�E�E)
    void testCalculateDeliveryFeeExactlyThreshold() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E
        String address = "東京都新宿区";
        BigDecimal totalPrice = new BigDecimal("5000");

        // 実行フェーズ
        BigDecimal result = deliveryFeeService.calculateDeliveryFee(address, totalPrice);

        // 検証フェーズ�E��E力値ベ�Eス�E�E
        // 5000冁E��めE��どは送料無斁E
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    @DisplayName("購入金額が4999冁E�E場合に標準�E送料金が適用されることをテストする（墁E��値�E�E)
    void testCalculateDeliveryFeeJustBelowThreshold() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E
        String address = "東京都新宿区";
        BigDecimal totalPrice = new BigDecimal("4999");

        // 実行フェーズ
        BigDecimal result = deliveryFeeService.calculateDeliveryFee(address, totalPrice);

        // 検証フェーズ�E��E力値ベ�Eス�E�E
        // 5000冁E��満なので通常配送料釁E
        assertEquals(new BigDecimal("800"), result);
    }

    @Test
    @DisplayName("配送�E住所がnullの場合に標準�E送料金が適用されることをテストすめE)
    void testCalculateDeliveryFeeNullAddress() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E
        String address = null;
        BigDecimal totalPrice = new BigDecimal("3000");

        // 実行フェーズ
        BigDecimal result = deliveryFeeService.calculateDeliveryFee(address, totalPrice);

        // 検証フェーズ�E��E力値ベ�Eス�E�E
        // nullの場合�E通常配送料釁E
        assertEquals(new BigDecimal("800"), result);
    }

    @Test
    @DisplayName("配送�E住所が空斁E���Eの場合に標準�E送料金が適用されることをテストすめE)
    void testCalculateDeliveryFeeEmptyAddress() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E
        String address = "";
        BigDecimal totalPrice = new BigDecimal("3000");

        // 実行フェーズ
        BigDecimal result = deliveryFeeService.calculateDeliveryFee(address, totalPrice);

        // 検証フェーズ�E��E力値ベ�Eス�E�E
        assertEquals(new BigDecimal("800"), result);
    }

    @Test
    @DisplayName("購入金額が0冁E�E場合に標準�E送料金が適用されることをテストすめE)
    void testCalculateDeliveryFeeZeroPrice() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E
        String address = "東京都渋谷区";
        BigDecimal totalPrice = BigDecimal.ZERO;

        // 実行フェーズ
        BigDecimal result = deliveryFeeService.calculateDeliveryFee(address, totalPrice);

        // 検証フェーズ�E��E力値ベ�Eス�E�E
        assertEquals(new BigDecimal("800"), result);
    }

    // isOkinawaのチE��チE

    @Test
    @DisplayName("沖縁E��の住所が正しく判定されることをテストすめE)
    void testIsOkinawaTrue() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E
        String address = "沖縁E��那要E��E;

        // 実行フェーズ
        boolean result = deliveryFeeService.isOkinawa(address);

        // 検証フェーズ�E��E力値ベ�Eス�E�E
        assertTrue(result);
    }

    @Test
    @DisplayName("沖縁E��以外�E住所が正しく判定されることをテストすめE)
    void testIsOkinawaFalse() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E
        String address = "東京都渋谷区";

        // 実行フェーズ
        boolean result = deliveryFeeService.isOkinawa(address);

        // 検証フェーズ�E��E力値ベ�Eス�E�E
        assertFalse(result);
    }

    @Test
    @DisplayName("住所がnullの場合にfalseが返されることをテストすめE)
    void testIsOkinawaNull() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E
        String address = null;

        // 実行フェーズ
        boolean result = deliveryFeeService.isOkinawa(address);

        // 検証フェーズ�E��E力値ベ�Eス�E�E
        assertFalse(result);
    }

    @Test
    @DisplayName("住所が空斁E���Eの場合にfalseが返されることをテストすめE)
    void testIsOkinawaEmpty() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E
        String address = "";

        // 実行フェーズ
        boolean result = deliveryFeeService.isOkinawa(address);

        // 検証フェーズ�E��E力値ベ�Eス�E�E
        assertFalse(result);
    }

    @Test
    @DisplayName("「沖縁E��を含むが「沖縁E��」で始まらなぁE��所がfalseと判定されることをテストすめE)
    void testIsOkinawaPartialMatch() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E
        String address = "福岡県沖縁E��"; // 「沖縁E��を含むが「沖縁E��」で始まらなぁE

        // 実行フェーズ
        boolean result = deliveryFeeService.isOkinawa(address);

        // 検証フェーズ�E��E力値ベ�Eス�E�E
        assertFalse(result);
    }

    // isFreeDeliveryのチE��チE

    @Test
    @DisplayName("購入金額が5000冁E�E場合に送料無料と判定されることをテストすめE)
    void testIsFreeDeliveryTrue() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E
        BigDecimal totalPrice = new BigDecimal("5000");

        // 実行フェーズ
        boolean result = deliveryFeeService.isFreeDelivery(totalPrice);

        // 検証フェーズ�E��E力値ベ�Eス�E�E
        assertTrue(result);
    }

    @Test
    @DisplayName("購入金額が5000冁E��趁E��る場合に送料無料と判定されることをテストすめE)
    void testIsFreeDeliveryTrueAboveThreshold() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E
        BigDecimal totalPrice = new BigDecimal("10000");

        // 実行フェーズ
        boolean result = deliveryFeeService.isFreeDelivery(totalPrice);

        // 検証フェーズ�E��E力値ベ�Eス�E�E
        assertTrue(result);
    }

    @Test
    @DisplayName("購入金額が4999冁E�E場合に送料無料でなぁE��判定されることをテストすめE)
    void testIsFreeDeliveryFalse() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E
        BigDecimal totalPrice = new BigDecimal("4999");

        // 実行フェーズ
        boolean result = deliveryFeeService.isFreeDelivery(totalPrice);

        // 検証フェーズ�E��E力値ベ�Eス�E�E
        assertFalse(result);
    }

    @Test
    @DisplayName("購入金額が0冁E�E場合に送料無料でなぁE��判定されることをテストすめE)
    void testIsFreeDeliveryFalseZero() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E
        BigDecimal totalPrice = BigDecimal.ZERO;

        // 実行フェーズ
        boolean result = deliveryFeeService.isFreeDelivery(totalPrice);

        // 検証フェーズ�E��E力値ベ�Eス�E�E
        assertFalse(result);
    }

    @Test
    @DisplayName("購入金額が100冁E�E場合に送料無料でなぁE��判定されることをテストすめE)
    void testIsFreeDeliveryFalseSmallAmount() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E
        BigDecimal totalPrice = new BigDecimal("100");

        // 実行フェーズ
        boolean result = deliveryFeeService.isFreeDelivery(totalPrice);

        // 検証フェーズ�E��E力値ベ�Eス�E�E
        assertFalse(result);
    }
}

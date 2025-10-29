package pro.kensait.berrybooks.dto;

import java.time.LocalDate;

public record CustomerStatsTO (
        // 顧客ID
        Integer customerId,
        // 顧客吁E
        String customerName,
        // メールアドレス
        String email,
        // 生年月日
        LocalDate birthday,
        // 住所
        String address,
        // 注斁E��数
        Long orderCount,
        // 購入冊数�E�合計！E
        Long totalBooks) {
}


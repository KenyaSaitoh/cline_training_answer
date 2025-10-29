package pro.kensait.berrybooks.dto;

import java.time.LocalDate;

public record CustomerStatsTO (
        // 鬘ｧ螳｢ID
        Integer customerId,
        // 鬘ｧ螳｢蜷・
        String customerName,
        // 繝｡繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ
        String email,
        // 逕溷ｹｴ譛域律
        LocalDate birthday,
        // 菴乗園
        String address,
        // 豕ｨ譁・ｻｶ謨ｰ
        Long orderCount,
        // 雉ｼ蜈･蜀頑焚・亥粋險茨ｼ・
        Long totalBooks) {
}


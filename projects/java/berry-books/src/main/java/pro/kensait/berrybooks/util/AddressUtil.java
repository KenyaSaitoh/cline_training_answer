package pro.kensait.berrybooks.util;

// 住所関連のユーチE��リチE��クラス
public final class AddressUtil {
    
    private AddressUtil() {
        // インスタンス化を防止
    }
    
    private static final String[] PREFECTURES = {
            "北海遁E, "青森省E, "岩手県", "宮城県", "秋田省E, 
            "山形省E, "福島省E, "茨城県", "栁E��省E, "群馬省E, 
            "埼玉県", "十E��省E, "東京都", "神奈川県", "新潟県", 
            "富山省E, "石川県", "福井県", "山梨省E, "長野県", 
            "岐�E省E, "静岡省E, "愛知省E, "三重省E, "滋賀省E, 
            "京都庁E, "大阪庁E, "兵庫省E, "奈良省E, "和歌山省E, 
            "鳥取県", "島根省E, "岡山省E, "庁E��省E, "山口省E, 
            "徳島省E, "香川県", "愛媛県", "高知省E, "福岡省E, 
            "佐賀省E, "長崎県", "熊本省E, "大刁E��", "宮崎県", 
            "鹿児島省E, "沖縁E��"
    };
    
    // 住所が正しい都道府県名で始まってぁE��かをチェチE��する
    public static boolean startsWithValidPrefecture(String address) {
        if (address == null || address.isBlank()) {
            return false;
        }
        
        for (String prefecture : PREFECTURES) {
            if (address.startsWith(prefecture)) {
                return true;
            }
        }
        return false;
    }
    
    // 都道府県名�E配�Eを取得すめE
    public static String[] getPrefectures() {
        return PREFECTURES.clone();
    }
}


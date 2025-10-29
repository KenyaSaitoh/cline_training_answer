package pro.kensait.berrybooks.util;

// 菴乗園髢｢騾｣縺ｮ繝ｦ繝ｼ繝・ぅ繝ｪ繝・ぅ繧ｯ繝ｩ繧ｹ
public final class AddressUtil {
    
    private AddressUtil() {
        // 繧､繝ｳ繧ｹ繧ｿ繝ｳ繧ｹ蛹悶ｒ髦ｲ豁｢
    }
    
    private static final String[] PREFECTURES = {
            "蛹玲ｵｷ驕・, "髱呈｣ｮ逵・, "蟯ｩ謇狗恁", "螳ｮ蝓守恁", "遘狗伐逵・, 
            "螻ｱ蠖｢逵・, "遖丞ｳｶ逵・, "闌ｨ蝓守恁", "譬・惠逵・, "鄒､鬥ｬ逵・, 
            "蝓ｼ邇臥恁", "蜊・痩逵・, "譚ｱ莠ｬ驛ｽ", "逾槫･亥ｷ晉恁", "譁ｰ貎溽恁", 
            "蟇悟ｱｱ逵・, "遏ｳ蟾晉恁", "遖丈ｺ慕恁", "螻ｱ譴ｨ逵・, "髟ｷ驥守恁", 
            "蟯宣・逵・, "髱吝ｲ｡逵・, "諢帷衍逵・, "荳蛾㍾逵・, "貊玖ｳ逵・, 
            "莠ｬ驛ｽ蠎・, "螟ｧ髦ｪ蠎・, "蜈ｵ蠎ｫ逵・, "螂郁憶逵・, "蜥梧ｭ悟ｱｱ逵・, 
            "魑･蜿也恁", "蟲ｶ譬ｹ逵・, "蟯｡螻ｱ逵・, "蠎・ｳｶ逵・, "螻ｱ蜿｣逵・, 
            "蠕ｳ蟲ｶ逵・, "鬥吝ｷ晉恁", "諢帛ｪ帷恁", "鬮倡衍逵・, "遖丞ｲ｡逵・, 
            "菴占ｳ逵・, "髟ｷ蟠守恁", "辭頑悽逵・, "螟ｧ蛻・恁", "螳ｮ蟠守恁", 
            "鮖ｿ蜈仙ｳｶ逵・, "豐也ｸ・恁"
    };
    
    // 菴乗園縺梧ｭ｣縺励＞驛ｽ驕灘ｺ懃恁蜷阪〒蟋九∪縺｣縺ｦ縺・ｋ縺九ｒ繝√ぉ繝・け縺吶ｋ
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
    
    // 驛ｽ驕灘ｺ懃恁蜷阪・驟榊・繧貞叙蠕励☆繧・
    public static String[] getPrefectures() {
        return PREFECTURES.clone();
    }
}


package silentdream.stlores.mapper;

import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class EnchantmentMapper {
    private static final Map<String, Enchantment> enchantmentMap = new HashMap<>();
    private static final HashSet<String> enchantmentName = new HashSet<>();

    static {
        enchantmentMap.put("保护", Enchantment.PROTECTION_ENVIRONMENTAL);
        enchantmentMap.put("火焰保护", Enchantment.PROTECTION_FIRE);
        enchantmentMap.put("摔落保护", Enchantment.PROTECTION_FALL);
        enchantmentMap.put("爆炸保护", Enchantment.PROTECTION_EXPLOSIONS);
        enchantmentMap.put("投射物保护", Enchantment.PROTECTION_PROJECTILE);
        enchantmentMap.put("水下呼吸", Enchantment.OXYGEN);
        enchantmentMap.put("水下速掘", Enchantment.WATER_WORKER);
        enchantmentMap.put("荆棘", Enchantment.THORNS);
        enchantmentMap.put("深海探索者", Enchantment.DEPTH_STRIDER);
        enchantmentMap.put("冰霜行者", Enchantment.FROST_WALKER);
        enchantmentMap.put("绑定诅咒", Enchantment.BINDING_CURSE);
        enchantmentMap.put("锋利", Enchantment.DAMAGE_ALL);
        enchantmentMap.put("亡灵杀手", Enchantment.DAMAGE_UNDEAD);
        enchantmentMap.put("节肢杀手", Enchantment.DAMAGE_ARTHROPODS);
        enchantmentMap.put("击退", Enchantment.KNOCKBACK);
        enchantmentMap.put("火焰附加", Enchantment.FIRE_ASPECT);
        enchantmentMap.put("抢夺", Enchantment.LOOT_BONUS_MOBS);
        enchantmentMap.put("横扫之刃", Enchantment.SWEEPING_EDGE);
        enchantmentMap.put("效率", Enchantment.DIG_SPEED);
        enchantmentMap.put("精准采集", Enchantment.SILK_TOUCH);
        enchantmentMap.put("耐久", Enchantment.DURABILITY);
        enchantmentMap.put("时运", Enchantment.LOOT_BONUS_BLOCKS);
        enchantmentMap.put("力量", Enchantment.ARROW_DAMAGE);
        enchantmentMap.put("冲击", Enchantment.ARROW_KNOCKBACK);
        enchantmentMap.put("火矢", Enchantment.ARROW_FIRE);
        enchantmentMap.put("无限", Enchantment.ARROW_INFINITE);
        enchantmentMap.put("海之眷顾", Enchantment.LUCK);
        enchantmentMap.put("饵钓", Enchantment.LURE);
        enchantmentMap.put("经验修补", Enchantment.MENDING);
        enchantmentMap.put("消失诅咒", Enchantment.VANISHING_CURSE);

        enchantmentName.addAll(enchantmentMap.keySet());
    }

    public static Enchantment getEnchantment(String chineseName) {
        return enchantmentMap.get(chineseName);
    }

    public static HashSet<String> getEnchantmentName() {
        return enchantmentName;
    }

    public static String getChineseName(Enchantment enchantment) {
        for (Map.Entry<String, Enchantment> entry : enchantmentMap.entrySet()) {
            if (entry.getValue().equals(enchantment)) {
                return entry.getKey();
            }
        }
        return enchantment.getName();
    }
}

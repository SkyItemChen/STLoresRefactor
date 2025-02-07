package silentdream.stlores.mapper;

import org.bukkit.inventory.ItemFlag;

import java.util.HashMap;
import java.util.HashSet;

public class ItemFlagMapper {
    private static final HashMap<String,String> itemFlagMap =  new HashMap<>();
    private static final HashSet<String> itemFlagName = new HashSet<>();
    private static final HashMap<String, ItemFlag> itemFlag = new HashMap<>();

    static {
        itemFlagMap.put("HIDE_ENCHANTS","隐藏附魔效果");
        itemFlagMap.put("HIDE_ATTRIBUTES","隐藏属性修改器");
        itemFlagMap.put("HIDE_UNBREAKABLE","隐藏无法破坏属性");
        itemFlagMap.put("HIDE_DESTROYS","隐藏有效破坏方块列表");
        itemFlagMap.put("HIDE_PLACED_ON","隐藏有效放置方块列表");
        itemFlagMap.put("HIDE_POTION_EFFECTS","隐藏药水效果");
        itemFlagMap.put("UNBREAKABLE","无法破坏");

        itemFlag.put("HIDE_ENCHANTS",ItemFlag.HIDE_ENCHANTS);
        itemFlag.put("HIDE_ATTRIBUTES",ItemFlag.HIDE_ATTRIBUTES);
        itemFlag.put("HIDE_UNBREAKABLE",ItemFlag.HIDE_UNBREAKABLE);
        itemFlag.put("HIDE_DESTROYS",ItemFlag.HIDE_DESTROYS);
        itemFlag.put("HIDE_PLACED_ON",ItemFlag.HIDE_PLACED_ON);
        itemFlag.put("HIDE_POTION_EFFECTS",ItemFlag.HIDE_POTION_EFFECTS);

        itemFlagName.addAll(itemFlagMap.keySet());
    }

    public static HashSet<String> getItemFlagName(){
        return itemFlagName;
    }

    public static HashMap<String,String> getItemFlagMap(){
        return itemFlagMap;
    }

    public static ItemFlag getItemFlag(String name){
        return itemFlag.get(name);
    }
}

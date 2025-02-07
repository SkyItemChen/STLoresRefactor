package silentdream.stlores.manager;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryManager {
    private static final HashMap<String, List<ItemStack>> historyMap = new HashMap<>();
    private static final int MAX_HISTORY_SIZE = 5;

    public static void addItemToHistory(Player player, ItemStack item) {
        String name = player.getName();
        List<ItemStack> history = historyMap.getOrDefault(name, new ArrayList<>());

        // 添加新的历史记录
        history.add(item.clone());

        // 如果历史记录超过最大数量，移除最早的记录
        if (history.size() > MAX_HISTORY_SIZE) {
            history.remove(0);
        }

        historyMap.put(name, history);
    }

    public static ItemStack getLastLore(Player player) {
        List<ItemStack> history = historyMap.get(player.getName());
        if (history == null || history.isEmpty()) {
            return null;
        }
        return history.remove(history.size() - 1);
    }
}

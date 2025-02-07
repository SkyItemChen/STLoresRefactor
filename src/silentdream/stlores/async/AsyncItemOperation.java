package silentdream.stlores.async;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import silentdream.stlores.Main;
import silentdream.stlores.uitl.ItemUtil;

public class AsyncItemOperation {

    public static void performAsyncOperation(CommandSender sender, Runnable operation) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c只有玩家才能执行此命令。");
            return;
        }

        Player player = (Player) sender;
        ItemStack itemInMainHand = ItemUtil.getItemInMainHand(player);

        if (itemInMainHand == null) {
            sender.sendMessage("§c你手上没有物品");
            return;
        }

        // 异步执行操作
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            try {
                operation.run();
            } catch (Exception e) {
                player.sendMessage("§c发生错误: " + e.getMessage());
            }
        });

        // 同步更新物品
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            // 更新玩家手上的物品
            ItemUtil.setItemInMainHand(player, itemInMainHand);
            player.updateInventory();
            player.sendMessage("§7 [§eSTLores §7]§a操作完成");
        });
    }
}
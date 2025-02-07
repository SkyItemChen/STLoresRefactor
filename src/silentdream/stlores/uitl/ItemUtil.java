package silentdream.stlores.uitl;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ItemUtil {
    public static ItemStack getItemInMainHand(Player player) {
        PlayerInventory inventory = player.getInventory();
        return inventory.getItemInMainHand();
    }

    public static void setItemInMainHand(Player player, ItemStack item) {
        PlayerInventory inventory = player.getInventory();
        inventory.setItemInMainHand(item);
    }
}

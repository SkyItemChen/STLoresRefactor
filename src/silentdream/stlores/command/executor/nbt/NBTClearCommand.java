package silentdream.stlores.command.executor.nbt;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pers.neige.neigeitems.NeigeItems;
import silentdream.stlores.async.AsyncItemOperation;
import silentdream.stlores.command.CommandManager;
import silentdream.stlores.command.executor.CommandInterface;
import silentdream.stlores.command.executor.NBTCommand;
import silentdream.stlores.manager.HistoryManager;
import silentdream.stlores.model.CommandModel;
import silentdream.stlores.uitl.ColorUtil;
import silentdream.stlores.uitl.ItemUtil;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class NBTClearCommand implements CommandInterface {
    public static final String commandName = NBTCommand.commandName + " clear";
    public static final String commandMessage = "清除NBT标签";
    public static final String commandExtraText = "";

    public NBTClearCommand() {
        addCommand(commandName, commandExtraText, commandMessage);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 0) {
            sender.sendMessage(ColorUtil.parseColor("&c请输入正确的参数"));
            return true;
        }
        AsyncItemOperation.performAsyncOperation(sender, () -> {
            Player player = (Player) sender;
            ItemStack itemInMainHand = ItemUtil.getItemInMainHand(player);
            // 添加历史记录
            HistoryManager.addItemToHistory(player, itemInMainHand);
            // 操作代码
            NBTItem item = new NBTItem(itemInMainHand);
            Set<String> keys = item.getKeys();
            if (keys.isEmpty()) {
                player.sendMessage(ColorUtil.parseColor("&c该物品没有NBT"));
            } else {
                for (String key : keys) {
                    item.removeKey(key);
                }
            }
            itemInMainHand = item.getItem();
            ItemUtil.setItemInMainHand(player, itemInMainHand);
        });
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public void addCommand(String commandName, String commandExtraText, String commandMessage) {
        CommandManager.commandList.add(new CommandModel(commandName, commandExtraText, commandMessage));
    }
}

package silentdream.stlores.command.executor.nbt;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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

public class NBTShowCommand implements CommandInterface {
    public static final String commandName = NBTCommand.commandName + " show";
    public static final String commandMessage = "显示NBT标签";
    public static final String commandExtraText = "";

    public NBTShowCommand() {
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
            if (item.getKeys().isEmpty()) {
                player.sendMessage(ColorUtil.parseColor("&c该物品没有NBT"));
            } else {
                formatAndSendNBT(player, item);
            }

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

    private void formatAndSendNBT(Player player, NBTItem compound) {
        for (String key : compound.getKeys()) {
            NBTCompound subCompound = compound.getCompound(key);
            if (subCompound != null) {
                player.sendMessage(key + ": ---");
                formatAndSendNBTCompound(player, subCompound);
                continue;
            }
            String string = compound.getString(key);
            if (!string.equalsIgnoreCase("")) {
                player.sendMessage(key + ": " + string);
                continue;
            }
            NBTList<String> stringList = compound.getStringList(key);
            if (!stringList.isEmpty()) {
                player.sendMessage(key + ": " + stringList);
                continue;
            }
            player.sendMessage(key + ": ----");
        }
    }

    private void formatAndSendNBTCompound(Player player, NBTCompound compound) {
        for (String key : compound.getKeys()) {
            NBTCompound subCompound = compound.getCompound(key);
            if (subCompound != null) {
                player.sendMessage(key + ": ++++");
                formatAndSendNBTCompound(player, subCompound);
                continue;
            }
            String string = compound.getString(key);
            if (!string.equalsIgnoreCase("")) {
                player.sendMessage(key + ": " + string);
                continue;
            }
            NBTList<String> stringList = compound.getStringList(key);
            if (!stringList.isEmpty()) {
                player.sendMessage(key + ": " + stringList);
                continue;
            }
            player.sendMessage(key + ": ++++");
        }
    }
}

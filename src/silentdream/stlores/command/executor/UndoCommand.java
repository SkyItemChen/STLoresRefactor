package silentdream.stlores.command.executor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import silentdream.stlores.async.AsyncItemOperation;
import silentdream.stlores.command.CommandManager;
import silentdream.stlores.manager.HistoryManager;
import silentdream.stlores.model.CommandModel;
import silentdream.stlores.uitl.ColorUtil;
import silentdream.stlores.uitl.ItemUtil;

import java.util.Collections;
import java.util.List;

public class UndoCommand implements CommandInterface {
    public static final String commandName = "undo";
    public static final String commandMessage = "撤销上一次操作[保留最近五条记录]";
    public static final String commandExtraText = "";

    public UndoCommand() {
        addCommand(commandName, commandExtraText, commandMessage);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        AsyncItemOperation.performAsyncOperation(sender, () -> {
            Player player = (Player) sender;
            ItemStack itemStack = HistoryManager.getLastLore(player);
            if (itemStack == null) {
                sender.sendMessage(ColorUtil.parseColor("&c没有可撤销的操作"));
            } else {
                ItemUtil.setItemInMainHand(player, itemStack);
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
}

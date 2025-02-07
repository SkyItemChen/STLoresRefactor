package silentdream.stlores.command.executor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import silentdream.stlores.async.AsyncItemOperation;
import silentdream.stlores.command.CommandManager;
import silentdream.stlores.manager.HistoryManager;
import silentdream.stlores.model.CommandModel;
import silentdream.stlores.uitl.ColorUtil;
import silentdream.stlores.uitl.ItemUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SetCommand implements CommandInterface {
    public static final String commandName = "set";
    public static final String commandMessage = "设置物品的某行内容";
    public static final String commandExtraText = "行数 内容";

    public SetCommand() {
        addCommand(commandName, commandExtraText, commandMessage);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(ColorUtil.parseColor("&c请输入正确的参数"));
            return true;
        }
        AsyncItemOperation.performAsyncOperation(sender, () -> {
            Player player = (Player) sender;
            ItemStack itemInMainHand = ItemUtil.getItemInMainHand(player);
            ItemMeta itemMeta = itemInMainHand.getItemMeta();
            // 添加历史记录
            HistoryManager.addItemToHistory(player, itemInMainHand);
            // 操作代码
            if (!itemMeta.hasLore()) {
                itemMeta.setLore(new ArrayList<>());
            }
            List<String> lore = itemMeta.getLore();
            int index;
            try {
                index = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ColorUtil.parseColor("&c请输入正确的索引"));
                return;
            }
            String content = ColorUtil.parseColor(args[1]);
            if (lore.size() < index) {
                lore.add(content);
            } else {
                lore.set(index - 1, content);
            }
            itemMeta.setLore(lore);
            // 保存
            itemInMainHand.setItemMeta(itemMeta);
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

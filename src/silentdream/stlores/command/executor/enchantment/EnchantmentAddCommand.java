package silentdream.stlores.command.executor.enchantment;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import silentdream.stlores.async.AsyncItemOperation;
import silentdream.stlores.command.CommandManager;
import silentdream.stlores.command.executor.CommandInterface;
import silentdream.stlores.command.executor.EnchantmentCommand;
import silentdream.stlores.manager.HistoryManager;
import silentdream.stlores.mapper.EnchantmentMapper;
import silentdream.stlores.model.CommandModel;
import silentdream.stlores.uitl.ColorUtil;
import silentdream.stlores.uitl.CommandUtil;
import silentdream.stlores.uitl.ItemUtil;

import java.util.*;

public class EnchantmentAddCommand implements CommandInterface {
    public static final String commandName = EnchantmentCommand.commandName + " add";
    public static final String commandMessage = "添加附魔";
    public static final String commandExtraText = "附魔名 附魔等级";

    public EnchantmentAddCommand() {
        addCommand(commandName, commandExtraText, commandMessage);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2){
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
            Enchantment enchantment = EnchantmentMapper.getEnchantment(args[0]);
            if (enchantment == null){
                sender.sendMessage(ColorUtil.parseColor("&c没有找到该附魔"));
                return;
            }
            if (itemMeta.hasEnchant(enchantment)){
                itemMeta.removeEnchant(enchantment);
            }
            int level = 0;
            try {
                level = Integer.parseInt(args[1]);
            }catch (NumberFormatException e){
                sender.sendMessage(ColorUtil.parseColor("&c请输入正确的附魔等级"));
                return;
            }
            if (level < 0 || level > 32767){
                sender.sendMessage(ColorUtil.parseColor("&c请输入正确的附魔等级"));
                return;
            }
            itemMeta.addEnchant(enchantment, level, true);
            // 保存
            itemInMainHand.setItemMeta(itemMeta);
        });
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        HashSet<String> enchantmentName = EnchantmentMapper.getEnchantmentName();
        if (args.length == 3) {
            return CommandUtil.getSubcommandCompletions(args[2],enchantmentName);
        }
        return Collections.emptyList();
    }

    @Override
    public void addCommand(String commandName, String commandExtraText, String commandMessage) {
        CommandManager.commandList.add(new CommandModel(commandName, commandExtraText, commandMessage));
    }
}

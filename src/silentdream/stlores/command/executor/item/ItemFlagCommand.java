package silentdream.stlores.command.executor.item;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import silentdream.stlores.async.AsyncItemOperation;
import silentdream.stlores.command.CommandManager;
import silentdream.stlores.command.executor.CommandInterface;
import silentdream.stlores.command.executor.ItemCommand;
import silentdream.stlores.mapper.ItemFlagMapper;
import silentdream.stlores.model.CommandModel;
import silentdream.stlores.uitl.ColorUtil;
import silentdream.stlores.uitl.CommandUtil;
import silentdream.stlores.uitl.ItemUtil;

import java.util.Collections;
import java.util.List;

public class ItemFlagCommand implements CommandInterface {
    public static final String commandName = ItemCommand.commandName + " flag";
    public static final String commandMessage = "设置物品属性";
    public static final String commandExtraText = "属性名";

    public ItemFlagCommand() {
        addCommand(commandName, commandExtraText, commandMessage);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1){
            sender.sendMessage(ColorUtil.parseColor("&c请输入正确的参数"));
            return true;
        }
        AsyncItemOperation.performAsyncOperation(sender, () -> {
            Player player = (Player) sender;
            ItemStack itemInMainHand = ItemUtil.getItemInMainHand(player);

            ItemMeta itemMeta = itemInMainHand.getItemMeta();
            ItemFlag itemFlag = ItemFlagMapper.getItemFlag(args[0]);
            if (itemFlag == null){
                if (args[0].equalsIgnoreCase("UNBREAKABLE")){
                    if (itemMeta.isUnbreakable()){
                        itemMeta.setUnbreakable(false);
                        sender.sendMessage(ColorUtil.parseColor("&a已关闭该属性"));
                    }else {
                        itemMeta.setUnbreakable(true);
                        sender.sendMessage(ColorUtil.parseColor("&a已开启该属性"));
                    }
                }
            }else {
                if (itemMeta.hasItemFlag(itemFlag)){
                    itemMeta.removeItemFlags(itemFlag);
                    sender.sendMessage(ColorUtil.parseColor("&a已关闭该属性"));
                }else {
                    itemMeta.addItemFlags(itemFlag);
                    sender.sendMessage(ColorUtil.parseColor("&a已开启该属性"));
                }
            }
            // 保存
            itemInMainHand.setItemMeta(itemMeta);
        });
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 3) {
            return CommandUtil.getSubcommandCompletions(args[2], ItemFlagMapper.getItemFlagName());
        }
        return Collections.emptyList();
    }

    @Override
    public void addCommand(String commandName, String commandExtraText, String commandMessage) {
        CommandManager.commandList.add(new CommandModel(commandName, commandExtraText, commandMessage));
    }
}

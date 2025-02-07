package silentdream.stlores.command.executor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import silentdream.stlores.async.AsyncItemOperation;
import silentdream.stlores.command.CommandManager;
import silentdream.stlores.model.CommandModel;
import silentdream.stlores.uitl.ColorUtil;
import silentdream.stlores.uitl.ItemUtil;

import java.util.Collections;
import java.util.List;

public class DurabilityCommand implements CommandInterface{
    public static final String commandName = "durability";
    public static final String commandMessage = "设置物品耐久度";
    public static final String commandExtraText = "耐久度";

    public DurabilityCommand() {
        addCommand(commandName, commandExtraText, commandMessage);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1){
            AsyncItemOperation.performAsyncOperation(sender, () -> {
                Player player = (Player) sender;
                ItemStack itemInMainHand = ItemUtil.getItemInMainHand(player);
                short durability;
                try {
                    durability = Short.parseShort(args[0]);
                }catch (NumberFormatException e){
                    sender.sendMessage(ColorUtil.parseColor("&c请输入一个正确的数"));
                    return;
                }
                short maxDurability = itemInMainHand.getType().getMaxDurability();
                if (durability < 0 || durability > maxDurability){
                    sender.sendMessage(ColorUtil.parseColor("&c请输入一个正确的数"));
                    return;
                }
                itemInMainHand.setDurability((short) (maxDurability - durability));
            });

        }
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

package silentdream.stlores.command.executor.item;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import silentdream.stlores.command.CommandManager;
import silentdream.stlores.command.executor.CommandInterface;
import silentdream.stlores.command.executor.ItemCommand;
import silentdream.stlores.mapper.ItemFlagMapper;
import silentdream.stlores.model.CommandModel;
import silentdream.stlores.uitl.ColorUtil;

import java.util.Collections;
import java.util.List;

public class ItemShowCommand implements CommandInterface {
    public static final String commandName = ItemCommand.commandName + " show";
    public static final String commandMessage = "显示可添加的属性";
    public static final String commandExtraText = "";

    public ItemShowCommand() {
        addCommand(commandName, commandExtraText, commandMessage);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ColorUtil.parseColor("&a可添加的属性列表: "));
            ItemFlagMapper.getItemFlagMap().forEach((k, v) -> {
                sender.sendMessage(ColorUtil.parseColor("&a- " + k + ": &e" + v));
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

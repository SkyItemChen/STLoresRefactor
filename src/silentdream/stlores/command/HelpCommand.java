package silentdream.stlores.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import silentdream.stlores.model.CommandModel;
import silentdream.stlores.uitl.CommandUtil;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HelpCommand implements TabExecutor {
    private final String pluginNameCN = "Lore管理";
    private final String pluginNameEN = JavaPlugin.getProvidingPlugin(getClass()).getDescription().getName();
    private final ArrayList<String> commandList = new ArrayList<>();
    public HelpCommand() {
        for (CommandModel commandModel : CommandManager.commandList) {
            commandList.add(CommandUtil.getHelpText(commandModel.getCommand(), commandModel.getCommandExtraText(), commandModel.getMessage()));
        }
    }

    private String getTitle() {
        return "§a--------------- §4[ §e" + pluginNameCN + " " + pluginNameEN + "§4] §a-----------------";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        sender.sendMessage(getTitle());
        for (String string : commandList) {
            sender.sendMessage(string);
        }
        sender.sendMessage(getTitle());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        return Collections.emptyList();
    }
}

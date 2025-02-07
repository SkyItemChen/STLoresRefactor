package silentdream.stlores.command.executor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import silentdream.stlores.model.CommandModel;

import java.util.List;

public interface CommandInterface extends TabCompleter, CommandExecutor {
    // 命令
    boolean onCommand(CommandSender sender, Command command, String label, String[] args);

    // 命令补全
    List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args);

    // 命令存储
    void addCommand(String commandName,String commandExtraText, String commandMessage);
}

package silentdream.stlores.uitl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import silentdream.stlores.command.CommandManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class CommandUtil {
    public static String getHelpText(String commandName, String commandExtraText, String message) {
        return ColorUtil.parseColor("&a/" + CommandManager.commandName + " " + commandName + " " + merge(commandExtraText) + " &e" + message);
    }

    public static String merge(String commandExtraText) {
        if (commandExtraText.isEmpty()) return "";
        String[] extraTexts = commandExtraText.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String extraText : extraTexts) {
            sb.append("<").append(extraText).append(">");
            sb.append(" ");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    public static List<String> getSubcommandCompletions(String partial, HashSet<String> subcommands) {
        List<String> completions = new ArrayList<>();
        for (String subcommand : subcommands) {
            String lowerCase = subcommand.toLowerCase();
            if (lowerCase.startsWith(partial)) {
                completions.add(subcommand);
            }
        }
        return completions;
    }

    public static boolean executorCommand(CommandSender sender,
                                          Command command,
                                          String label,
                                          String[] args,
                                          Map<String, CommandExecutor> commands){
        String subCommand = args[0];
        CommandExecutor executor = commands.get(subCommand);
        if (executor != null) {
            String[] subArgs = new String[args.length - 1];
            System.arraycopy(args, 1, subArgs, 0, subArgs.length);
            return executor.onCommand(sender, command, label, subArgs);
        }
        return false;
    }

    public static List<String> executorTabComplete(Map<String, CommandExecutor> commands,
                                                   String subcommand,
                                                   CommandSender sender,
                                                   Command command,
                                                   String label,
                                                   String[] args){
        CommandExecutor executor = commands.get(subcommand);
        if (executor instanceof TabCompleter) {
            return ((TabCompleter) executor).onTabComplete(sender, command, label, args);
        }
        return new ArrayList<>();
    }
}

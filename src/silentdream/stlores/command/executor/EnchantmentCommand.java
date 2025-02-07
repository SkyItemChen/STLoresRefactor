package silentdream.stlores.command.executor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import silentdream.stlores.command.executor.enchantment.EnchantmentAddCommand;
import silentdream.stlores.command.executor.enchantment.EnchantmentDeleteCommand;
import silentdream.stlores.command.executor.enchantment.EnchantmentSetCommand;
import silentdream.stlores.uitl.ColorUtil;
import silentdream.stlores.uitl.CommandUtil;

import java.util.*;

public class EnchantmentCommand implements CommandInterface {
    public static final String commandName = "enchantment";
    public static final String commandMessage = "管理物品附魔";
    public static final String commandExtraText = "";

    private final Map<String, CommandExecutor> commands = new HashMap<>();
    private final HashSet<String> subcommands = new HashSet<>();

    public EnchantmentCommand() {
        initCommand();
    }

    private void initCommand() {
        commands.put("add", new EnchantmentAddCommand());
        commands.put("delete", new EnchantmentDeleteCommand());
        commands.put("set", new EnchantmentSetCommand());
        // 初始化子命令列表
        subcommands.addAll(commands.keySet());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ColorUtil.parseColor("&c请输入正确的命令"));
            return true;
        }
        return CommandUtil.executorCommand(sender, command, label, args, commands);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        int length = args.length;
        if (length == 2) {
            // 补全子命令
            return CommandUtil.getSubcommandCompletions(args[1], subcommands);
        } else if (length > 2) {
            // 根据子命令类型补全参数
            return CommandUtil.executorTabComplete(commands, args[1], sender, command, label, args);
        }
        return new ArrayList<>();
    }

    @Override
    public void addCommand(String commandName, String commandExtraText, String commandMessage) {

    }
}

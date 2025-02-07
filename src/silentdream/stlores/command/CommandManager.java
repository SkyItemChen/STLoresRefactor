package silentdream.stlores.command;

import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;
import silentdream.stlores.Main;
import silentdream.stlores.command.executor.*;
import silentdream.stlores.model.CommandModel;
import silentdream.stlores.uitl.ColorUtil;
import silentdream.stlores.uitl.CommandUtil;

import java.util.*;

public class CommandManager implements TabExecutor {
    private final JavaPlugin plugin;
    private final Map<String, CommandExecutor> commands = new HashMap<>();
    private final HashSet<String> subcommands = new HashSet<>();
    public static final String commandName = "lore";
    public static ArrayList<CommandModel> commandList = new ArrayList<>();

    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;

        initCommand();
    }

    private void initCommand() {
        commands.put("color", new ColorCommand());
        commands.put("undo", new UndoCommand());
        commands.put("view", new ViewCommand());
        commands.put("clear", new ClearCommand());
        commands.put("name", new NameCommand());
        commands.put("add", new AddCommand());
        commands.put("set", new SetCommand());
        commands.put("insert", new InsertCommand());
        commands.put("enchantment", new EnchantmentCommand());
        commands.put("item", new ItemCommand());
        commands.put("durability", new DurabilityCommand());
        if (Main.hasNBT()){
            commands.put("nbt", new NBTCommand());
        }
        commands.put("help", new HelpCommand());
        // 注册主命令
        plugin.getCommand(commandName).setExecutor(this);
        plugin.getCommand(commandName).setTabCompleter(this);

        // 初始化子命令列表
        subcommands.addAll(commands.keySet());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ColorUtil.parseColor("&c你没有权限执行此命令"));
            return true;
        }
        if (args.length == 0) {
            showHelp(sender, command, label, args);
            return true;
        }
        if (CommandUtil.executorCommand(sender, command, label, args, commands)){
            return true;
        }else {
            // 如果子命令不存在，显示错误信息并提供帮助
            sender.sendMessage(ColorUtil.parseColor("&c未知命令"));
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            return new ArrayList<>();
        }
        if (args.length == 1) {
            // 补全子命令
            return CommandUtil.getSubcommandCompletions(args[0], subcommands);
        } else if (args.length > 1) {
            // 根据子命令类型补全参数
            return CommandUtil.executorTabComplete(commands, args[0], sender, command, label, args);
        }
        return new ArrayList<>();
    }

    private void showHelp(CommandSender sender, Command command, String label, String[] args) {
        commands.get("help").onCommand(sender, command, label, args);
    }
}

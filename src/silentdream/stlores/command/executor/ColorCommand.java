package silentdream.stlores.command.executor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import silentdream.stlores.command.CommandManager;
import silentdream.stlores.model.CommandModel;

import java.util.Collections;
import java.util.List;

public class ColorCommand implements CommandInterface {
    private String commandName = "color";
    private String commandMessage = "展示颜色符号";
    private String commandExtraText = "";

    public ColorCommand() {
        addCommand(commandName, commandExtraText, commandMessage);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§a--------------- §4[ §e颜色符号 §4] §a-----------------");
        sender.sendMessage("§0 &0 黑色\t§1 &1 深蓝色");
        sender.sendMessage("§2 &2 深绿色\t§3 &3 深青色");
        sender.sendMessage("§4 &4 红色\t§5 &5 粉紫色");
        sender.sendMessage("§6 &6 金色\t§7 &7 灰色");
        sender.sendMessage("§8 &8 深灰色\t§9 &9 蓝色");
        sender.sendMessage("§a &a 绿色\t§b &b 青色");
        sender.sendMessage("§c &c 红色\t§d &d 粉红色");
        sender.sendMessage("§e &e 黄色\t§f &f 白色");
        sender.sendMessage("§k &k 随机字符\t§r§l &l 加粗");
        sender.sendMessage("§m &m 删除线\t§r§n &n 下划线");
        sender.sendMessage("§o &o 斜体\t§r§r &r 重置所有格式");
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

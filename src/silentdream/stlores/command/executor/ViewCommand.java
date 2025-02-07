package silentdream.stlores.command.executor;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import silentdream.stlores.async.AsyncItemOperation;
import silentdream.stlores.command.CommandManager;
import silentdream.stlores.mapper.EnchantmentMapper;
import silentdream.stlores.mapper.ItemFlagMapper;
import silentdream.stlores.model.CommandModel;
import silentdream.stlores.uitl.ColorUtil;
import silentdream.stlores.uitl.ItemUtil;

import java.util.Collections;
import java.util.List;

public class ViewCommand implements CommandInterface{
    public static final String commandName = "view";
    public static final String commandMessage = "查看物品的lore";
    public static final String commandExtraText = "";

    public ViewCommand() {
        addCommand(commandName, commandExtraText, commandMessage);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        AsyncItemOperation.performAsyncOperation(sender, () -> {
            Player player = (Player) sender;
            ItemStack itemStack = ItemUtil.getItemInMainHand(player);
            viewItemLore(player, itemStack);
        });
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

    private void viewItemLore(Player player, ItemStack item) {
        player.sendMessage("§a--------------- §4[ §e物品展示 §4] §a-----------------");
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null){
            return;
        }
        if (itemMeta.hasDisplayName()){
            String commandSuggestion = "/lore name " + ColorUtil.parseColorTwo(itemMeta.getDisplayName());
            TextComponent message = new TextComponent(itemMeta.getDisplayName() + "  §7[ §e名称 §7]");
            // 设置点击事件
            message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, commandSuggestion));
            // 设置悬停提示
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§e点击修改").create()));
            player.spigot().sendMessage(message);
        }
        if (itemMeta.hasLore()){
            List<String> lore = itemMeta.getLore();
            for (int i = 0; i < lore.size(); i++) {
                String commandSuggestion = "/lore set " + (i + 1) + " " + ColorUtil.parseColorTwo(lore.get(i));
                TextComponent message = new TextComponent(lore.get(i) + "  §7[ §e" + (i + 1) + " §7]");
                // 设置点击事件
                message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, commandSuggestion));
                // 设置悬停提示
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§e点击修改").create()));
                player.spigot().sendMessage(message);
            }
        }
        if (itemMeta.hasEnchants()){
            player.sendMessage("§a--------------- §4[ §e附魔列表 §4] §a-----------------");
            itemMeta.getEnchants().forEach((enchantment, level) -> {
                String chineseName = EnchantmentMapper.getChineseName(enchantment);
                String commandSuggestion = "/lore enchantment set " + chineseName + " " + level;
                TextComponent message = new TextComponent("§e" +chineseName + " §b" + level + " §e级");
                message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, commandSuggestion));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§e点击修改").create()));
                player.spigot().sendMessage(message);
            });
        }
        player.sendMessage("§a--------------- §4[ §e属性列表 §4] §a-----------------");
        ItemFlagMapper.getItemFlagMap().forEach((k, v) -> {
            String enable = "关闭";
            if (k.equalsIgnoreCase("UNBREAKABLE")){
                if (itemMeta.isUnbreakable()){
                    enable = "开启";
                }
            }else if (itemMeta.hasItemFlag(ItemFlagMapper.getItemFlag(k))){
                enable = "开启";
            }
            String commandRun = "/lore item flag " + k;
            TextComponent message = new TextComponent("§e" + v + " §7[ §e" + enable + " §7]");
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, commandRun));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§e点击修改").create()));
            player.spigot().sendMessage(message);
        });


    }
}

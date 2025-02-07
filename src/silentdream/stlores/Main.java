package silentdream.stlores;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import silentdream.stlores.command.CommandManager;

public class Main extends JavaPlugin {
    private CommandManager commandManager;
    private static Main instance;
    private static boolean nbtFlag;
    @Override
    public void onEnable() {
        instance = this;
        double loadTime = measureLoadTime(() -> {
            nbtFlag = isPluginLoaded("NBTAPI");
            // 注册命令
            commandManager = new CommandManager(this);
        });

        if (loadTime < 0){
            getLogger().warning("插件加载失败，请联系插件制作者");
            getServer().getPluginManager().disablePlugin(this);
        }

        getLogger().info("插件加载完成，耗时：" + loadTime + " μs");
    }

    @Override
    public void onDisable() {
        commandManager = null;
        instance = null;

        getLogger().info("插件已卸载");
    }

    public static double measureLoadTime(Runnable task) {
        long startTime = System.nanoTime();
        try {
            task.run(); // 执行任务
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // 返回-1表示任务执行失败
        }
        long endTime = System.nanoTime();
        return (double) (endTime - startTime) / 1000;
        // μs
    }

    public static Main getInstance() {
        return instance;
    }

    private static boolean isPluginLoaded(final String pluginName) {
        Plugin targetPlugin = instance.getServer().getPluginManager().getPlugin(pluginName);
        if (targetPlugin == null) {
            instance.getLogger().info(pluginName + " 插件未找到，NBT功能将被禁用");
            return false;
        } else {
            instance.getLogger().info(pluginName + " 插件已找到，NBT功能均可正常使用");
            return true;
        }
    }

    public static boolean hasNBT() {
        return nbtFlag;
    }
}

package io.github.cubecolony.Warps;

import com.google.common.collect.Lists;
import io.github.cubecolony.Warps.commands.RemoveWarpCommand;
import io.github.cubecolony.Warps.commands.SetWarpCommand;
import io.github.cubecolony.Warps.commands.WarpCommand;
//import io.github.cubecolony.Warps.commands.WarpTab;
import io.github.cubecolony.Warps.utils.SchedulerUtils;
import io.github.cubecolony.Warps.utils.inventory.InventoryListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {
    private WarpsManager warpsManager;
    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        SchedulerUtils.setPlugin(this);
        DatabaseManager.initDatabase(this);
        SchedulerUtils.runDatabase((DatabaseManager::createTables));

        warpsManager = new WarpsManager();

        getServer().getPluginManager().registerEvents(new InventoryListener(), this);

        PluginCommand warp = getCommand("warp");
        if (warp != null) {
            warp.setAliases(Lists.newArrayList("warps"));
            warp.setExecutor(new WarpCommand(warpsManager, this));
        }
//        Objects.requireNonNull(getCommand("warp")).setTabCompleter(new WarpTab(warpsManager));

        Objects.requireNonNull(getCommand("setwarp")).setExecutor(new SetWarpCommand(warpsManager));
        Objects.requireNonNull(getCommand("removewarp")).setExecutor(new RemoveWarpCommand(warpsManager));
//        Objects.requireNonNull(getCommand("removewarp")).setTabCompleter(new WarpTab(warpsManager));
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public WarpsManager getWarpsManager() {
        return warpsManager;
    }
}

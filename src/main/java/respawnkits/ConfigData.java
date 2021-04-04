package respawnkits;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigData {
    public final boolean OutLogo;
    public final String Command;
    public final long tick;
    public final String permission;
    public final boolean active;
    public ConfigData(RespawnKits plugin){
        FileConfiguration config = plugin.getConfig();
        OutLogo = config.getBoolean("OutLogo",true);
        Command = config.getString("Command","kit");
        tick = config.getLong("Tick",20);
        permission = config.getString("Permission" + ".permission","null");
        active = config.getBoolean("Permission" + ".active",false);
    }
}

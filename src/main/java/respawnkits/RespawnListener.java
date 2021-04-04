package respawnkits;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.Map;


public class RespawnListener implements Listener {
    private final RespawnKits plugin;
    private static FileConfiguration cf;
    private static ConfigData cc;
    public static Map<String, String> kit = new HashMap<>();
    public RespawnListener(RespawnKits plugin, FileConfiguration cf,ConfigData cc){
        this.plugin = plugin;
        this.cf = cf;
        this.cc = cc;
    }

    @EventHandler
    public void respawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        String uuid = p.getUniqueId().toString();
        String cmd;
        if (kit.get(uuid) == null) {
            if (cf.getString(uuid + ".kit") != null && cf.getString(uuid + ".uuid") != null) {
                kit.put(cf.getString(uuid + ".uuid"), cf.getString(uuid + ".kit"));
            }else return;
        }
        if ((cmd = kit.get(uuid)) != null){
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        p.getInventory().clear();
                        if (cc.active){
                            PermissionAttachment attachment = p.addAttachment(plugin, 10);
                        attachment.setPermission(cc.permission, true);
                    }
                Bukkit.dispatchCommand(p, cc.Command + " " + cmd);
            }, cc.tick);
        }
    }
}

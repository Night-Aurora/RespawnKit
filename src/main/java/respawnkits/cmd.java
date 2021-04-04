package respawnkits;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class cmd implements TabExecutor{
    private final RespawnKits plugin;
    private static FileConfiguration cf;
    public cmd(RespawnKits plugin,FileConfiguration cf){
        this.plugin = plugin;
        this.cf = cf;
    }
    private final String[] cmds = {"bind","about","reload","help"};
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length>0){
            if(args[0].equalsIgnoreCase("reload")){
                if(sender.hasPermission("respawnkit.admin")) {
                    plugin.reloadConfig();
                    plugin.reloadCC();
                    plugin.reloadcf();
                    plugin.RegisterCmd();
                    plugin.ReloadEvent();
                    send(sender, "§b§lConfig Reload");
                }
                else send(sender,"§c§lYou don't have permission!");
            }  else if (args[0].equalsIgnoreCase("about")){
                send(sender,"§7-------RespawnKits§7-------");
                send(sender,"§7|   §eMade By Night_Aurora  |");
                send(sender,"§7| §eLatest Build 26/12/2020 |");
                send(sender,"§7|-------------------------|");
            } else if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage("§7§l-------RespawnKits§7-------");
                for (String subcmd : RespawnKits.subcmd){
                    if(sender instanceof Player)
                    ((Player) sender).spigot().sendMessage((new ComponentBuilder("/reskit" + " " + subcmd))
                            .color(ChatColor.DARK_PURPLE)
                            .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/reskit" + " " + subcmd + " "))
                            .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(String.join("\n", RespawnKits.usage.get(subcmd)))))
                            .create());
                    else sender.sendMessage(ChatColor.DARK_PURPLE + "/reskit" + " " + subcmd);
                }
            }
            if(sender instanceof Player)
                if (args[0].equalsIgnoreCase("bind")){
                    if(args.length>1){
                        String kit = args[1];
                        String uuid = ((Player) sender).getUniqueId().toString();
                        if(kit.equalsIgnoreCase("none")){
                            cf.set(uuid,null);
                            RespawnListener.kit.remove(uuid);
                            plugin.save();
                            send(sender,"§c§l已删除记录");
                            return true;
                        }
                        cf.set(uuid + ".kit",kit);
                        cf.set(uuid + ".uuid",uuid);
                        cf.set(uuid + ".name",sender.getName());
                        send(sender,"已记录:§a§l" + kit );
                        plugin.save();
                    } else {
                        send(sender,"§c§l缺少参数:请提供kit名称");
                    }
                }
        }
        else send(sender,"§7§l/reskit help");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1)
        return Arrays.stream(cmds).filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
        return Collections.singletonList("");
    }

    public void send(CommandSender sender,String mes){
        sender.sendMessage(RespawnKits.prefix + " " + mes);
    }
}

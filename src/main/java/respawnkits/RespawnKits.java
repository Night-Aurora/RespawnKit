package respawnkits;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class RespawnKits extends JavaPlugin {
    private ConfigData cc;
    public static HashMap<String,String> usage = new HashMap<>();
    public static ArrayList<String> subcmd = new ArrayList<>();
    public static final String prefix = "§9§lRespawnKits §e>>§f";

    @Override
    public void onEnable() {
        registerConfig();
        cc = new ConfigData(this);
        if (cc.OutLogo){
        Bukkit.getConsoleSender().sendMessage(" =@@@@     =@@@^   @@@@^                 @@@@^          .]@@^                   @@@@@@@^        ");
        Bukkit.getConsoleSender().sendMessage(" =@@@@@@.   =@@@^                         @@@@^          @@@@^                  /@@@@@@@@`    ");
        Bukkit.getConsoleSender().sendMessage(" =@@@@@@@^  =@@@^   @@@@^  .@@@@@@/@@@@   @@@@/@@@@@/  =@@@@@@@@               =@@@@^@@@@@    =@@@@  =@@@@   @@@@O@@@/  /@@@@@@@@`   =@@@@@@@@` =@@@@@@@@@`");
        Bukkit.getConsoleSender().sendMessage(" =@@@^/@@@@.=@@@^   @@@@^  @@@@@`,@@@@@   @@@@@//@@@@^ ,[@@@@/[[              .@@@@@ ,@@@@^   =@@@@  =@@@@   @@@@@@[[ .@@@@/.,@@@@^  =@@@@@/[` =@@@@/ /@@@@");
        Bukkit.getConsoleSender().sendMessage(" =@@@^ ,@@@@/@@@^   @@@@^  @@@@^  =@@@@   @@@@^  @@@@^   @@@@^                /@@@@/]]/@@@@`  =@@@@  =@@@@   @@@@/    =@@@@.  =@@@@  =@@@@`      .]]@@@@@@@");
        Bukkit.getConsoleSender().sendMessage(" =@@@^   /@@@@@@^   @@@@^  @@@@/  /@@@@   @@@@^  @@@@^   @@@@^               =@@@@@@@@@@@@@@  =@@@@  =@@@@   @@@@^    =@@@@.  =@@@@  =@@@@     /@@@@` =@@@@");
        Bukkit.getConsoleSender().sendMessage(" =@@@^    ,@@@@@^   @@@@^  ,@@@@@@@@@@@   @@@@^  @@@@^   @@@@@]]            ,@@@@@[[[[[/@@@@/ =@@@@@@@@@@@   @@@@^     /@@@@]/@@@@`  =@@@@     @@@@@]/@@@@@");
        Bukkit.getConsoleSender().sendMessage(" =@@@^     .@@@@^   @@@@^    ,[[[ =@@@@   @@@@^  @@@@^   ,@@@@@@            @@@@@^      @@@@@^ =@@@@/=@@@@   @@@@^      ,/@@@@@@[    =@@@@     ,@@@@@/.@@@@^");
        Bukkit.getConsoleSender().sendMessage("                           =@@@@`.@@@@@                          ]]]]]]]]]]]               ");
        Bukkit.getConsoleSender().sendMessage("                            /@@@@@@@@/");
    }
        Bukkit.getConsoleSender().sendMessage(prefix + " " + "欢迎！");
        rgConfig();
        RegisterCmd();
        RegisterEvent();
        subcmd.add("reload");
        subcmd.add("bind");
        subcmd.add("about");
        Addusage();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public void Addusage(){
        usage.put("reload", "§9/reskit reload" + "\n" + "§7重载本插件");
        usage.put("bind","§9/reskit bind [name]" + "\n" + "name = none 可以删除当前已经记录的kit" + "\n" + "§7设置重生Kit");
        usage.put("about","§9/reskit about" + "\n" + "§7更多信息关于本插件");
    }
    public void RegisterCmd(){
        Bukkit.getPluginCommand("respawnkit").setExecutor(new cmd(this,Dta()));
        Bukkit.getPluginCommand("respawnkit").setTabCompleter(new cmd(this,Dta()));
    }
    public void RegisterEvent(){
        Bukkit.getPluginManager().registerEvents(new RespawnListener(this,Dta(),cc),this);
        ReloadEvent();
    }
    public void ReloadEvent(){
        new RespawnListener(this,Dta(),cc);
    }
    public void reloadCC(){
        cc = new ConfigData(this);
    }
    final String cf = "data.yml";
    FileConfiguration data = null;
    File Data = null;
    public void rgConfig() {
        Data = new File(getDataFolder(), cf);
        if (!Data.exists()) {
            Dta().options().copyDefaults(true);
            yaml();
            save();
        }
    }
    public void save() {
        try {
            data.save(Data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void reloadcf() {
        yaml();
        Dtareload();
        registerConfig();
    }
    public void yaml(){
        if (data == null) {
            Data = new File(getDataFolder(), cf);
        }
        data = YamlConfiguration.loadConfiguration(Data);
    }
    public FileConfiguration Dta() {
        if(data == null) { reloadcf(); }
        return data;
    }
    public void Dtareload(){
        if(!Data.exists()) save(); yaml();
        Reader defConfigStream = new InputStreamReader(getResource(cf), StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        data.setDefaults(defConfig);
    }
    public void registerConfig() {
        File config = new File(getDataFolder(), "config.yml");
        if (!config.exists()) {
            saveDefaultConfig();
            //write();
            return;
        }
        reloadConfig();
    }
    /*
    public void write(){
        getConfig().set("OutLogo",true);
        getConfig().set("Command","kit");
        getConfig().set("Tick",20);
        getConfig().set("Permission" + ".active",false);
        getConfig().set("Permission" + ".permission","permission");
        saveConfig();
    }

     */
}

 package com.gt.plugin;

 import cn.nukkit.Player;
 import cn.nukkit.command.Command;
 import cn.nukkit.command.CommandSender;
 import cn.nukkit.event.Listener;
 import cn.nukkit.plugin.Plugin;
 import cn.nukkit.plugin.PluginBase;
 import cn.nukkit.utils.Config;
 import com.gt.plugin.gui.FFAKBGUI;
 import com.gt.plugin.listener.AttackListener;

 public class Main extends PluginBase {
   public static Main main;

   public void onEnable() {
       main = this;
     saveResource("ffakb.yml");
     getServer().getPluginManager().registerEvents((Listener)new AttackListener(), (Plugin)this);
     getServer().getPluginManager().registerEvents((Listener)new FFAKBGUI(), (Plugin)this);
     config = new Config(getDataFolder() + "/ffakb.yml", 2);
     if (!config.exists("default")) {
       config.set("default.knockBack", 1.0D);
       config.set("default.attackCooldown", 10);
       config.set("default.knockBackMultiply", 1.1D);
       config.set("default.knockBackX", 0.0D);
       config.set("default.knockBackY", 0.0D);
       config.set("default.knockBackZ", 0.0D);
       config.save();
     }
   }
   public static Config config;
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
     if (sender instanceof Player) {
       Player player = (Player)sender;
         if (command.getName().equals("ffa")) {
             if (player.isOp()) {
                 FFAKBGUI.showFFAKBGUI(player);
             }
         }
     }
     return true;
   }
 }

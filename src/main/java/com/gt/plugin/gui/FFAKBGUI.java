 package com.gt.plugin.gui;
 import cn.nukkit.Player;
 import cn.nukkit.Server;
 import cn.nukkit.event.EventHandler;
 import cn.nukkit.event.Listener;
 import cn.nukkit.event.player.PlayerFormRespondedEvent;
 import cn.nukkit.form.element.Element;
 import cn.nukkit.form.element.ElementButton;
 import cn.nukkit.form.element.ElementInput;
 import cn.nukkit.form.element.ElementLabel;
 import cn.nukkit.form.response.FormResponseCustom;
 import cn.nukkit.form.response.FormResponseSimple;
 import cn.nukkit.form.window.FormWindow;
 import cn.nukkit.form.window.FormWindowCustom;
 import cn.nukkit.form.window.FormWindowSimple;
 import cn.nukkit.level.Level;
 import cn.nukkit.utils.Config;
 import com.gt.plugin.Main;

 public class FFAKBGUI implements Listener {
   public static final int FFAKBGUI = 1002;
   public static final int FFAKBGUISET = 1003;

   public static void showFFAKBGUI(Player player) {
     FormWindowSimple form = new FormWindowSimple("All keys", "Select one to set");
     for (String key : Main.config.getKeys()) {
       if (!key.contains(".")) {
         form.addButton(new ElementButton(key));
       }
     }
     if (player.isOp()) {
       form.addButton(new ElementButton("Add a level"));
       form.addButton(new ElementButton("Delect a level"));
     }
     player.showFormWindow((FormWindow)form, 1002);
   }
   public static final int FFAKBGUILEVELA = 1004; public static final int FFAKBGUILEVELD = 1005;
   public static void showFFAKBGUISET(Player player, String key) {
     FormWindowCustom form = new FormWindowCustom(key);
     Config config = Main.config;
     form.addElement((Element)new ElementLabel(key));
     for (String a : config.getKeys()) {
       if (!a.equals(key) && a.contains(key)) {
         ElementInput e = new ElementInput(a);
         e.setDefaultText(String.valueOf(config.get(a)));
         form.addElement((Element)e);
       }
     }
     player.showFormWindow((FormWindow)form, 1003);
   }
   @EventHandler
   public void onFormResponse(PlayerFormRespondedEvent event) {
     try {
       Player player = event.getPlayer();
       int id = event.getFormID();
       if (id == 1002) {
         FormResponseSimple response = (FormResponseSimple)event.getResponse();
         String key = response.getClickedButton().getText();
         if (key.equals("Add a level")) {
           showFFAKBGUILEVELA(player);
         } else if (key.equals("Delect a level")) {
           showFFAKBGUILEVELD(player);
         } else {
           showFFAKBGUISET(player, key);
         }
       } else if (id == 1004) {
         Config config = Main.config;
         FormResponseSimple response = (FormResponseSimple)event.getResponse();
         if (!config.exists(response.getClickedButton().getText())) {
           config.set(response.getClickedButton().getText() + ".knockBack", Double.valueOf(1.0D));
           config.set(response.getClickedButton().getText() + ".attackCooldown", Integer.valueOf(10));
           config.set(response.getClickedButton().getText() + ".knockBackMultiply", Double.valueOf(0.0D));
           config.set(response.getClickedButton().getText() + ".knockBackX", Double.valueOf(0.0D));
           config.set(response.getClickedButton().getText() + ".knockBackY", Double.valueOf(0.0D));
           config.set(response.getClickedButton().getText() + ".knockBackZ", Double.valueOf(0.0D));
           config.save();
         }
       } else if (id == 1005) {
         Config config = Main.config;
         FormResponseSimple response = (FormResponseSimple)event.getResponse();
         if (config.exists(response.getClickedButton().getText())) {
           config.remove(response.getClickedButton().getText());
           config.save();
         }
       } else if (id == 1003) {
         Config config = Main.config;
         FormResponseCustom response = (FormResponseCustom)event.getResponse();
         config.set(response.getLabelResponse(0) + ".knockBack", Double.valueOf(response.getInputResponse(1)));
         config.set(response.getLabelResponse(0) + ".attackCooldown", Integer.valueOf(response.getInputResponse(2)));
         config.set(response.getLabelResponse(0) + ".knockBackMultiply", Double.valueOf(response.getInputResponse(3)));
         config.set(response.getLabelResponse(0) + ".knockBackX", Double.valueOf(response.getInputResponse(4)));
         config.set(response.getLabelResponse(0) + ".knockBackY", Double.valueOf(response.getInputResponse(5)));
         config.set(response.getLabelResponse(0) + ".knockBackZ", Double.valueOf(response.getInputResponse(6)));
         config.save();
         player.sendMessage("Successfully set!");
       }
     } catch (Exception ignored) {}
   }




   public static void showFFAKBGUILEVELA(Player player) {
     FormWindowSimple form = new FormWindowSimple("All level", "Select one to set");
     for (Level level : Server.getInstance().getLevels().values()) {
       form.addButton(new ElementButton(level.getName()));
     }
     player.showFormWindow((FormWindow)form, 1004);
   }


   public static void showFFAKBGUILEVELD(Player player) {
     FormWindowSimple form = new FormWindowSimple("All level", "Select one to set");
     for (String key : Main.config.getKeys()) {
       if (!key.contains(".")) {
         form.addButton(new ElementButton(key));
       }
     }
     player.showFormWindow((FormWindow)form, 1005);
   }
 }

 package com.gt.plugin.listener;

 import cn.nukkit.Player;
 import cn.nukkit.Server;
 import cn.nukkit.entity.Entity;
 import cn.nukkit.event.EventHandler;
 import cn.nukkit.event.Listener;
 import cn.nukkit.event.entity.EntityDamageByEntityEvent;
 import cn.nukkit.plugin.Plugin;
 import cn.nukkit.scheduler.Task;
 import com.gt.plugin.Main;

 import java.util.ArrayList;
 import java.util.List;

 public class AttackListener implements Listener {
     private List<Player> attackCooldown = new ArrayList<>();
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
     Entity entity = event.getEntity();
     if (!event.isCancelled() &&
       Main.config.exists(entity.getLevel().getName() + ".knockBack")) {
       event.setKnockBack(Float.parseFloat(String.valueOf(Main.config.getDouble(entity.getLevel().getName() + ".knockBack"))));
       if(event.getDamager() instanceof Player){
           if(attackCooldown.contains((Player) event.getDamager())){
               event.setCancelled();
               return;
           }
           attackCooldown.add((Player) event.getDamager());
           Server.getInstance().getScheduler().scheduleDelayedTask(Main.main, new Task() {
               public void onRun(int i) {
                   attackCooldown.remove((Player) event.getDamager());
               }
           }, Main.config.getInt(entity.getLevel().getName() + ".attackCooldown"));
       }
       double multiply = Main.config.getDouble(entity.getLevel().getName() + ".motionMultiply");
       double x = Main.config.getDouble(entity.getLevel().getName() + ".knockBackX");
       double y = Main.config.getDouble(entity.getLevel().getName() + ".knockBackY");
       double z = Main.config.getDouble(entity.getLevel().getName() + ".knockBackZ");
       entity.setMotion(entity.getLocation().getDirectionVector().multiply(multiply).add(x, y, z));
     }
   }
 }

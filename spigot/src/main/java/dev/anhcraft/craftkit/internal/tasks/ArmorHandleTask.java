package dev.anhcraft.craftkit.internal.tasks;

import dev.anhcraft.craftkit.events.ArmorChangeEvent;
import dev.anhcraft.craftkit.events.ArmorEquipEvent;
import dev.anhcraft.craftkit.events.ArmorUnequipEvent;
import dev.anhcraft.craftkit.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ArmorHandleTask implements Runnable {
    public static final Map<UUID, Map<EquipmentSlot, ItemStack>> data = new HashMap<>();

    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers()){
            ItemStack h = ItemUtil.clone(p.getInventory().getHelmet());
            ItemStack c = ItemUtil.clone(p.getInventory().getChestplate());
            ItemStack l = ItemUtil.clone(p.getInventory().getLeggings());
            ItemStack b = ItemUtil.clone(p.getInventory().getBoots());
            Map<EquipmentSlot, ItemStack> x = data.get(p.getUniqueId());
            if(x != null){
                ItemStack oh = x.get(EquipmentSlot.HEAD);
                if(oh == null){
                    if(h != null){
                        ArmorEquipEvent e = new ArmorEquipEvent(p, h, EquipmentSlot.HEAD);
                        Bukkit.getPluginManager().callEvent(e);
                        x.put(EquipmentSlot.HEAD, h);
                    }
                } else {
                    if(h == null){
                        ArmorUnequipEvent e = new ArmorUnequipEvent(p, oh, EquipmentSlot.HEAD);
                        Bukkit.getPluginManager().callEvent(e);
                        x.put(EquipmentSlot.HEAD, null);
                    } else if(!oh.equals(h)){
                        ArmorChangeEvent e = new ArmorChangeEvent(p, oh, h, EquipmentSlot.HEAD);
                        Bukkit.getPluginManager().callEvent(e);
                        x.put(EquipmentSlot.HEAD, h);
                    }
                }
                
                ItemStack oc = x.get(EquipmentSlot.CHEST);
                if(oc == null){
                    if(c != null){
                        ArmorEquipEvent e = new ArmorEquipEvent(p, c, EquipmentSlot.CHEST);
                        Bukkit.getPluginManager().callEvent(e);
                        x.put(EquipmentSlot.CHEST, c);
                    }
                } else {
                    if(c == null){
                        ArmorUnequipEvent e = new ArmorUnequipEvent(p, oc, EquipmentSlot.CHEST);
                        Bukkit.getPluginManager().callEvent(e);
                        x.put(EquipmentSlot.CHEST, null);
                    } else if(!oc.equals(c)){
                        ArmorChangeEvent e = new ArmorChangeEvent(p, oc, c, EquipmentSlot.CHEST);
                        Bukkit.getPluginManager().callEvent(e);
                        x.put(EquipmentSlot.CHEST, c);
                    }
                }
                
                ItemStack ol = x.get(EquipmentSlot.LEGS);
                if(ol == null){
                    if(l != null){
                        ArmorEquipEvent e = new ArmorEquipEvent(p, l, EquipmentSlot.LEGS);
                        Bukkit.getPluginManager().callEvent(e);
                        x.put(EquipmentSlot.LEGS, l);
                    }
                } else {
                    if(l == null){
                        ArmorUnequipEvent e = new ArmorUnequipEvent(p, ol, EquipmentSlot.LEGS);
                        Bukkit.getPluginManager().callEvent(e);
                        x.put(EquipmentSlot.LEGS, null);
                    } else if(!ol.equals(l)){
                        ArmorChangeEvent e = new ArmorChangeEvent(p, ol, l, EquipmentSlot.LEGS);
                        Bukkit.getPluginManager().callEvent(e);
                        x.put(EquipmentSlot.LEGS, l);
                    }
                }
                
                ItemStack ob = x.get(EquipmentSlot.FEET);
                if(ob == null){
                    if(b != null){
                        ArmorEquipEvent e = new ArmorEquipEvent(p, b, EquipmentSlot.FEET);
                        Bukkit.getPluginManager().callEvent(e);
                        x.put(EquipmentSlot.FEET, b);
                    }
                } else {
                    if(b == null){
                        ArmorUnequipEvent e = new ArmorUnequipEvent(p, ob, EquipmentSlot.FEET);
                        Bukkit.getPluginManager().callEvent(e);
                        x.put(EquipmentSlot.FEET, null);
                    } else if(!ob.equals(b)){
                        ArmorChangeEvent e = new ArmorChangeEvent(p, ob, b, EquipmentSlot.FEET);
                        Bukkit.getPluginManager().callEvent(e);
                        x.put(EquipmentSlot.FEET, b);
                    }
                }
            } else {
                x = new HashMap<>();
                x.put(EquipmentSlot.HEAD, h);
                x.put(EquipmentSlot.CHEST, c);
                x.put(EquipmentSlot.LEGS, l);
                x.put(EquipmentSlot.FEET, b);

                Set<EquipmentSlot> set = x.keySet();
                for(EquipmentSlot r : set){
                    ItemStack v = x.get(r);
                    if(v != null) {
                        ArmorEquipEvent e = new ArmorEquipEvent(p, v, r);
                        Bukkit.getPluginManager().callEvent(e);
                    }
                }
            }
            data.put(p.getUniqueId(), x);
        }
    }
}
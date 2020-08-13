import dev.anhcraft.jvmkit.utils.PresentPair;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R1.legacy.CraftLegacy;
import org.bukkit.material.MaterialData;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class MainTest {
    private final static Map<String, PresentPair<String, Integer>> LEGACY = new HashMap<>();

    public static void main(String[] args) {
        LEGACY.put("MUSIC_DISC_WAIT", new PresentPair<>("RECORD_12", 0));
        for(Material mt : Material.values()){
            if(mt.isLegacy()) {
                EnumSet<Material> set = EnumSet.noneOf(Material.class);
                for (byte i = 0; i < Byte.MAX_VALUE; i++) {
                    Material x = CraftLegacy.fromLegacy(new MaterialData(mt, i));
                    if(set.contains(x)) continue;
                    if(mt == Material.LEGACY_AIR || !x.isAir()) {
                        System.out.println("LEGACY.put(\"" + x.name() + "\", new PresentPair<>(\"" + mt.name().substring("LEGACY_".length()) + "\", " + i + "));");
                        set.add(x);
                    }
                }
            }
        }
    }
}

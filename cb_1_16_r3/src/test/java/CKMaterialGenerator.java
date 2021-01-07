import dev.anhcraft.jvmkit.utils.FileUtil;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.legacy.CraftLegacy;
import org.bukkit.material.MaterialData;

import java.io.File;
import java.util.EnumSet;

public class CKMaterialGenerator {
    public static void main(String[] args) {
        File f = new File(FileUtil.WORKING_DIR_PATH, "spigot/src/main/resources/ck_material.txt");
        FileUtil.clean(f);
        StringBuffer buffer = new StringBuffer();
        for(Material mt : Material.values()){
            if(mt.isLegacy()) {
                EnumSet<Material> set = EnumSet.noneOf(Material.class);
                for (byte i = 0; i < Byte.MAX_VALUE; i++) {
                    Material x = CraftLegacy.fromLegacy(new MaterialData(mt, i));
                    if(set.contains(x)) continue;
                    if(mt == Material.LEGACY_AIR || !x.isAir()) {
                        buffer.append(mt.name().substring("LEGACY_".length())).append(" ");
                        buffer.append(i).append(" ");
                        buffer.append(x.name()).append('\n');
                        set.add(x);
                    }
                }
            }
        }
        FileUtil.write(f, buffer.toString());
    }
}

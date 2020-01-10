import dev.anhcraft.configdoc.ConfigDocGenerator;
import dev.anhcraft.craftkit.abif.PreparedItem;
import dev.anhcraft.craftkit.attribute.ItemModifier;
import dev.anhcraft.craftkit.attribute.Modifier;

import java.io.File;

public class DocGen {
    public static void main(String[] args){
        new ConfigDocGenerator().addJavadoc("dev.anhcraft.craftkit.*", "https://anhcraft.dev/jd/craftkit/spigot")
                .withSchema(Modifier.SCHEMA)
                .withSchema(ItemModifier.SCHEMA)
                .withSchema(PreparedItem.SCHEMA)
                .generate(new File("docs"));
    }
}

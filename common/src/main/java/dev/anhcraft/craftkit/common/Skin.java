package dev.anhcraft.craftkit.common;

import dev.anhcraft.craftkit.common.internal.CKInfo;
import org.jetbrains.annotations.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents player skins.
 */
public class Skin implements Serializable {
    private static final long serialVersionUID = 1537124277924320087L;

    public static final Skin MHF_ALEX = new Skin(CKInfo.getMHFSkin("MHF_Alex"), null);
    public static final Skin MHF_BLAZE = new Skin(CKInfo.getMHFSkin("MHF_Blaze"), null);
    public static final Skin MHF_CAVESPIDER = new Skin(CKInfo.getMHFSkin("MHF_CaveSpider"), null);
    public static final Skin MHF_CHICKEN = new Skin(CKInfo.getMHFSkin("MHF_Chicken"), null);
    public static final Skin MHF_COW = new Skin(CKInfo.getMHFSkin("MHF_Cow"), null);
    public static final Skin MHF_CREEPER = new Skin(CKInfo.getMHFSkin("MHF_Creeper"), null);
    public static final Skin MHF_ENDERMAN = new Skin(CKInfo.getMHFSkin("MHF_Enderman"), null);
    public static final Skin MHF_GHAST = new Skin(CKInfo.getMHFSkin("MHF_Ghast"), null);
    public static final Skin MHF_GOLEM = new Skin(CKInfo.getMHFSkin("MHF_Golem"), null);
    public static final Skin MHF_HEROBRINE = new Skin(CKInfo.getMHFSkin("MHF_Herobrine"), null);
    public static final Skin MHF_LAVASLIME = new Skin(CKInfo.getMHFSkin("MHF_LavaSlime"), null);
    public static final Skin MHF_MUSHROOMCOW = new Skin(CKInfo.getMHFSkin("MHF_MushroomCow"), null);
    public static final Skin MHF_OCELOT = new Skin(CKInfo.getMHFSkin("MHF_Ocelot"), null);
    public static final Skin MHF_PIG = new Skin(CKInfo.getMHFSkin("MHF_Pig"), null);
    public static final Skin MHF_PIGZOMBIE = new Skin(CKInfo.getMHFSkin("MHF_PigZombie"), null);
    public static final Skin MHF_SHEEP = new Skin(CKInfo.getMHFSkin("MHF_Sheep"), null);
    public static final Skin MHF_SKELETON = new Skin(CKInfo.getMHFSkin("MHF_Skeleton"), null);
    public static final Skin MHF_SLIME = new Skin(CKInfo.getMHFSkin("MHF_Slime"), null);
    public static final Skin MHF_SPIDER = new Skin(CKInfo.getMHFSkin("MHF_Spider"), null);
    public static final Skin MHF_SQUID = new Skin(CKInfo.getMHFSkin("MHF_Squid"), null);
    public static final Skin MHF_STEVE = new Skin(CKInfo.getMHFSkin("MHF_Steve"), null);
    public static final Skin MHF_VILLAGER = new Skin(CKInfo.getMHFSkin("MHF_Villager"), null);
    public static final Skin MHF_WSKELETON = new Skin(CKInfo.getMHFSkin("MHF_WSkeleton"), null);
    public static final Skin MHF_ZOMBIE = new Skin(CKInfo.getMHFSkin("MHF_Zombie"), null);
    public static final Skin MHF_CACTUS = new Skin(CKInfo.getMHFSkin("MHF_Cactus"), null);
    public static final Skin MHF_CAKE = new Skin(CKInfo.getMHFSkin("MHF_Cake"), null);
    public static final Skin MHF_CHEST = new Skin(CKInfo.getMHFSkin("MHF_Chest"), null);
    public static final Skin MHF_COCONUTB = new Skin(CKInfo.getMHFSkin("MHF_CoconutB"), null);
    public static final Skin MHF_COCONUTG = new Skin(CKInfo.getMHFSkin("MHF_CoconutG"), null);
    public static final Skin MHF_MELON = new Skin(CKInfo.getMHFSkin("MHF_Melon"), null);
    public static final Skin MHF_OAKLOG = new Skin(CKInfo.getMHFSkin("MHF_OakLog"), null);
    public static final Skin MHF_PRESENT1 = new Skin(CKInfo.getMHFSkin("MHF_Present1"), null);
    public static final Skin MHF_PRESENT2 = new Skin(CKInfo.getMHFSkin("MHF_Present2"), null);
    public static final Skin MHF_PUMPKIN = new Skin(CKInfo.getMHFSkin("MHF_Pumpkin"), null);
    public static final Skin MHF_TNT = new Skin(CKInfo.getMHFSkin("MHF_TNT"), null);
    public static final Skin MHF_TNT2 = new Skin(CKInfo.getMHFSkin("MHF_TNT2"), null);
    public static final Skin MHF_ARROWUP = new Skin(CKInfo.getMHFSkin("MHF_ArrowUp"), null);
    public static final Skin MHF_ARROWDOWN = new Skin(CKInfo.getMHFSkin("MHF_ArrowDown"), null);
    public static final Skin MHF_ARROWLEFT = new Skin(CKInfo.getMHFSkin("MHF_ArrowLeft"), null);
    public static final Skin MHF_ARROWRIGHT = new Skin(CKInfo.getMHFSkin("MHF_ArrowRight"), null);
    public static final Skin MHF_EXCLAMATION = new Skin(CKInfo.getMHFSkin("MHF_Exclamation"), null);
    public static final Skin MHF_QUESTION = new Skin(CKInfo.getMHFSkin("MHF_Question"), null);

    private String value;
    private String signature;

    /**
     * Constructs an instance of {@code Skin} with the given information.
     * @param value the value of the skin
     * @param signature the signature of the skin (you only need this for player skins, skull skins do not require)
     */
    public Skin(@NotNull String value, String signature){
        Condition.argNotNull("value", value);
        this.value = value;
        this.signature = signature;
    }

    /**
     * Returns the value of this skin.
     * @return skin's value
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns the signature of this skin.
     * @return skin's signature
     */
    public String getSignature() {
        return signature;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skin skin = (Skin) o;
        return value.equals(skin.value) &&
                Objects.equals(signature, skin.signature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, signature);
    }
}
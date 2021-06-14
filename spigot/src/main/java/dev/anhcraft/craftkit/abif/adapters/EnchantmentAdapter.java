package dev.anhcraft.craftkit.abif.adapters;

import dev.anhcraft.config.ConfigDeserializer;
import dev.anhcraft.config.ConfigSerializer;
import dev.anhcraft.config.adapters.TypeAdapter;
import dev.anhcraft.config.struct.SimpleForm;
import dev.anhcraft.craftkit.abif.ABIF;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.Objects;

public class EnchantmentAdapter implements TypeAdapter<Enchantment> {
    @Override
    public @Nullable SimpleForm simplify(@NotNull ConfigSerializer configSerializer, @NotNull Type type, @NotNull Enchantment enchantment) throws Exception {
        return SimpleForm.of(enchantment.getName().toLowerCase());
    }

    @Override
    public @Nullable Enchantment complexify(@NotNull ConfigDeserializer configDeserializer, @NotNull Type type, @NotNull SimpleForm simpleForm) throws Exception {
        return simpleForm.isString() ? ABIF.getEnchant(Objects.requireNonNull(simpleForm.asString())) : null;
    }
}

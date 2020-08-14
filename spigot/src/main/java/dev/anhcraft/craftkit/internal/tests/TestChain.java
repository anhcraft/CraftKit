package dev.anhcraft.craftkit.internal.tests;

import com.google.common.collect.ImmutableList;
import dev.anhcraft.craftkit.CraftExtension;
import dev.anhcraft.craftkit.chat.Chat;
import dev.anhcraft.craftkit.helpers.TaskHelper;
import dev.anhcraft.craftkit.internal.CraftKit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class TestChain {
    private final List<ITest> tests;
    private final Consumer<TestChain> callback;
    private final Player player;
    private int currentTest;
    private boolean finished;
    private boolean success;
    private String error;

    public TestChain(Player player, Consumer<TestChain> callback) {
        this.player = player;
        this.callback = callback;
        tests = ImmutableList.of(
                new InventoryTest1(),
                new InventoryTest2(),
                new AsyncBlockTest(),
                new NBTTest(),
                new PreparedItemTest(),
                new ArmorStandTest(),
                new NPCTest(),
                new PlayerSkinTest(),
                new RecipeTest()
        );
        runNext();
    }

    private void runNext() {
        ITest test = tests.get(currentTest);
        Chat.noPrefix().message(player, "&aCurrent test: &f" + test.name());
        player.sendTitle("Start in", "5", 20, 40, 20);
        TaskHelper task = CraftExtension.of(CraftKit.class).getTaskHelper();
        task.newDelayedTask(() -> {
            player.sendTitle("Start in", "4", 20, 40, 20);
            task.newDelayedTask(() -> {
                player.sendTitle("Start in", "3", 20, 40, 20);
                task.newDelayedTask(() -> {
                    player.sendTitle("Start in", "2", 20, 40, 20);
                    task.newDelayedTask(() -> {
                        player.sendTitle("Start in", "1", 20, 40, 20);
                        task.newDelayedTask(() -> {
                            player.sendTitle("Start in", "0", 20, 40, 20);
                            try {
                                test.run(player, this);
                            } catch (Exception e) {
                                report(false, e.getLocalizedMessage());
                            }
                        }, 20);
                    }, 20);
                }, 20);
            }, 20);
        }, 20);
    }

    public void report(boolean ok, @Nullable String error) {
        if(ok) {
            if (currentTest + 1 == tests.size()) {
                finished = true;
                success = true;
                callback.accept(this);
            } else {
                currentTest++;
                runNext();
            }
        } else {
            this.error = error;
            finished = true;
            success = false;
            callback.accept(this);
        }
    }

    @NotNull
    public Player getPlayer() {
        return player;
    }

    @NotNull
    public String getCurrentTest() {
        return tests.get(currentTest).name();
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isSuccess() {
        return success;
    }

    @Nullable
    public String getError() {
        return error;
    }
}

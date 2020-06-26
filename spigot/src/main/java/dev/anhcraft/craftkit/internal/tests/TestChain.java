package dev.anhcraft.craftkit.internal.tests;

import com.google.common.collect.ImmutableList;
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
                new PreparedItemTest()
        );
        runNext();
    }

    private void runNext() {
        try {
            tests.get(currentTest).run(player, this);
        } catch (Exception e) {
            report(false, e.getLocalizedMessage());
        }
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

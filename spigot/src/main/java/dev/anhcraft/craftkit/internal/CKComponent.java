package dev.anhcraft.craftkit.internal;

public class CKComponent {
    private final CraftKit parent;

    public CKComponent(CraftKit parent) {
        this.parent = parent;
    }

    public CraftKit getParent() {
        return parent;
    }
}

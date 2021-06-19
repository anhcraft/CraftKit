# CraftKit
A utility kit for Spigot/Bungeecord plugins.<br>

Contributions are welcome!

[![](https://jitpack.io/v/anhcraft/CraftKit.svg)](https://jitpack.io/#anhcraft/CraftKit)

## warning
Recent Java versions may throw exception when CraftKit tries to load the NMS library.<br>
You must add this flag to the startup command:
```
--add-opens java.base/java.net=ALL-UNNAMED
```

## implement
Repo:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

<br>
Spigot side:

```xml
<dependency>
    <groupId>com.github.anhcraft.CraftKit</groupId>
    <artifactId>craftkit.spigot</artifactId>
    <version>VERSION</version>
</dependency>
```

<br>
Bungeecord side:

```xml
<dependency>
    <groupId>com.github.anhcraft.CraftKit</groupId>
    <artifactId>craftkit.bungee</artifactId>
    <version>VERSION</version>
</dependency>
```

## license
This library is licensed under MIT license.

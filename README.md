# CraftKit
A utility kit for Spigot/Bungeecord plugins.<br>

Contributions are welcome!

## warning
Recent Java versions may throw exception when CraftKit tries to load the NMS library.<br>
You must add this flag to the startup command:
```
--add-opens java.base/java.net=ALL-UNNAMED
```

## implement
Repo:
```xml
<repository>
    <id>mcvn-repo</id>
    <url>https://repo.minecraftvn.net/repository/maven-public/</url>
</repository>
```

<br>
Spigot side:

```xml
<dependency>
    <groupId>dev.anhcraft</groupId>
    <artifactId>craftkit.spigot</artifactId>
    <version>VERSION</version>
    <scope>provided</scope>
</dependency>
```

<br>
Bungeecord side:

```xml
<dependency>
    <groupId>dev.anhcraft</groupId>
    <artifactId>craftkit.bungee</artifactId>
    <version>VERSION</version>
    <scope>provided</scope>
</dependency>
```

## license
This library is licensed under MIT license.

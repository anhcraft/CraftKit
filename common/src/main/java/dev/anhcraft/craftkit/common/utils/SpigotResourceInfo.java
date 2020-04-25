package dev.anhcraft.craftkit.common.utils;

import com.google.gson.JsonObject;
import dev.anhcraft.craftkit.common.internal.CKPlugin;
import dev.anhcraft.jvmkit.lang.annotation.Beta;
import dev.anhcraft.jvmkit.utils.HttpUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Beta
public class SpigotResourceInfo {
    /**
     * Gets the information of a resource using its id.
     * @param id the resource id (for e.g: 39007)
     * @return {@link SpigotResourceInfo}
     * @throws IOException if any errors occurred
     */
    @NotNull
    public static SpigotResourceInfo of(String id) throws IOException {
        String str = HttpUtil.fetchString("https://api.spigotmc.org/simple/0.1/index.php?action=getResource&id=" + id);
        JsonObject root = CKPlugin.GSON.fromJson(str, JsonObject.class);
        JsonObject author = root.get("author").getAsJsonObject();
        JsonObject premium = root.get("premium").getAsJsonObject();
        JsonObject stats = root.get("stats").getAsJsonObject();
        return new SpigotResourceInfo(
                root.get("id").getAsInt(),
                root.get("title").getAsString(),
                root.get("tag").getAsString(),
                root.get("current_version").getAsString(),
                author.get("id").getAsInt(),
                author.get("username").getAsString(),
                premium.get("price").getAsDouble(),
                premium.get("currency").getAsString(),
                stats.get("downloads").getAsInt(),
                stats.get("updates").getAsInt(),
                stats.get("reviews").getAsInt(),
                stats.get("rating").getAsDouble()
        );
    }

    private final int id;
    private final String title;
    private final String tag;
    private final String currentVersion;
    private final int authorId;
    private final String authorName;
    private final double price;
    private final String currency;
    private final int downloadCount;
    private final int updateCount;
    private final int reviewCount;
    private final double rating;

    private SpigotResourceInfo(int id, @NotNull String title, @NotNull String tag, @NotNull String currentVersion, int authorId, @NotNull String authorName, double price, @NotNull String currency, int downloadCount, int updateCount, int reviewCount, double rating) {
        this.id = id;
        this.title = title;
        this.tag = tag;
        this.currentVersion = currentVersion;
        this.authorId = authorId;
        this.authorName = authorName;
        this.price = price;
        this.currency = currency;
        this.downloadCount = downloadCount;
        this.updateCount = updateCount;
        this.reviewCount = reviewCount;
        this.rating = rating;
    }

    /**
     * Gets the id.
     * @return resource's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the title.
     * @return resource's title.
     */
    @NotNull
    public String getTitle() {
        return title;
    }

    /**
     * Gets the tag line.
     * @return resource's tag line.
     */
    @NotNull
    public String getTag() {
        return tag;
    }

    /**
     * Gets current version.
     * @return latest plugin's version
     */
    @NotNull
    public String getCurrentVersion() {
        return currentVersion;
    }

    /**
     * Gets the id of the author.
     * @return author's id
     */
    public int getAuthorId() {
        return authorId;
    }

    /**
     * Gets the username of the author.
     * @return author's username.
     */
    @NotNull
    public String getAuthorName() {
        return authorName;
    }

    /**
     * Gets the resource price.
     * @return price (will be 0.00 if the resource is free)
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the resource currency.
     * @return currency (e.g: usd, eur; will be empty if the resource is free)
     */
    @NotNull
    public String getCurrency() {
        return currency;
    }

    /**
     * Gets the total of downloads.
     * @return total of downloads.
     */
    public int getDownloadCount() {
        return downloadCount;
    }

    /**
     * Gets the total of updates.
     * @return total of updates.
     */
    public int getUpdateCount() {
        return updateCount;
    }

    /**
     * Gets the total of reviews.
     * @return total of reviews.
     */
    public int getReviewCount() {
        return reviewCount;
    }

    /**
     * Gets the average rating score.
     * @return rating score.
     */
    public double getRating() {
        return rating;
    }

    /**
     * Gets the link to the resource.
     * @return resource link.
     */
    @NotNull
    public String getLink() {
        return "https://spigotmc.org/resources/" + id;
    }
}

import dev.anhcraft.craftkit.common.utils.SpigotResourceInfo;
import org.junit.Test;

import java.io.IOException;

public class ResourceTest {
    @Test
    public void test(){
        try {
            SpigotResourceInfo i = SpigotResourceInfo.of("31673");
            System.out.println(i.getLink());
            System.out.println(i.getTitle());
            System.out.println(i.getTag());
            System.out.println(i.getAuthorId());
            System.out.println(i.getAuthorName());
            System.out.println(i.getPrice()+" "+i.getCurrency());
            System.out.println(i.getDownloadCount()+" downloads");
            System.out.println(i.getReviewCount()+" reviews");
            System.out.println("Rate: "+i.getRating());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

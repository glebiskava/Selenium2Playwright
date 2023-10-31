import com.microsoft.playwright.Page;

public class EnumerationsPageCReference extends AbstractPage {

    protected EnumerationsPageCReference(Page page) {
        super(page);
        String currentURL = page.url();
        System.out.println("Enumerations Page C Reference URL: " + currentURL);
    }
}

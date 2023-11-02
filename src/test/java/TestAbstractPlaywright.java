import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class TestAbstractPlaywright {
    protected Browser browser = null;
    protected BrowserContext context = null;
    protected Page page = null;
    protected final boolean headless = false;
    protected final boolean chrome = false;

    @BeforeEach
    void beforeEachTestCase() {
        try (Playwright playwright = Playwright.create()) {
            if (chrome) {
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            } else {
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            }
            context = browser.newContext();
            page = context.newPage();
        }
    }

    @AfterEach
    void afterEachTestCase() {
        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
    }
}

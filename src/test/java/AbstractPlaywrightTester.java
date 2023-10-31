import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.fail;

public class AbstractPlaywrightTester {
    protected Browser browser;
    protected Page page;
    protected boolean headless = false;
    protected boolean chrome = false;


    @BeforeEach
    void beforeEachTestCase() {
        try {
            BrowserType browserType;
            if (chrome) {
                browserType = Playwright.create().chromium();
            } else {
                browserType = Playwright.create().firefox();
            }

            BrowserContext context;
            if (headless) {
                context = (BrowserContext) browserType.launch(new BrowserType.LaunchOptions().setHeadless(true));
            } else {
                context = (BrowserContext) browserType.launch(new BrowserType.LaunchOptions());
            }

            page = context.newPage();
            page.setViewportSize(1920, 1080);
        } catch (PlaywrightException e) {
            e.printStackTrace();
            fail("An error occurred while setting up the browser.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("An unexpected error occurred while setting up the browser.");
        }
    }



    @AfterEach
    void afterEachTestCase() {
        if (page != null) {
            page.close();
        }
        if (browser != null) {
            browser.close();
        }
    }
}

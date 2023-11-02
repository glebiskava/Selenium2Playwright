import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.AfterAll;

public class AbstractPlaywrightTester {
    protected static Playwright playwright;
	protected static Browser browser;

	protected BrowserContext context;
	protected Page page;

	
	/* ---------------- BROWSER SELECT ---------------- */
	protected final static String browserName = "chrome";

	/* ------------------- HEADLESS ------------------- */
	protected final static boolean headless = false;

	/* ------------------------------------------------ */

	@BeforeAll
	static void launchBrowser() {
		playwright = Playwright.create();
		BrowserType browserType = null;
		switch (browserName) {
			case "chrome":
				browserType = playwright.chromium();
				break;

			case "firefox":
				browserType = playwright.firefox();
				break;

			case "webkit":
				browserType = playwright.webkit();
				break;

			default:
				throw new IllegalArgumentException("!!!!!WRONG BROWSER TYPE!!!!!\nTypes can be: chrome, firefox or webkit");
		}
		final Integer setSlowMo = 0;
		browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(headless).setSlowMo(setSlowMo));
	}

	@AfterAll
	static void closeBrowser() {
		playwright.close();
	}

    @BeforeEach
	void createContextAndPage() {
		context = browser.newContext();
		page = context.newPage();
	}

	@AfterEach
	void closeContext() {
		context.close();
	}
}

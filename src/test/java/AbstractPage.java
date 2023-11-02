import com.microsoft.playwright.*;

public class AbstractPage {
	protected final Page page;

	public AbstractPage(Page page) {
		this.page = page;
	}

	// public String title() {
	// 	return page.title();
	// }

	// public String getURL() {
	// 	return page.url();
	// }

	// protected void call(String url) {
	// 	page.navigate(url);
	// 	page.waitForTimeout(500); // Simulates implicitlyWait
	// 	String currentURL = page.url();
	// 	System.out.println("call current URL: " + currentURL);
	// }

	// protected void click(String id) {
	// 	page.click(id);
	// }

	// protected void clickLink() {
	// 	page.click("text=" + "Dokumentation - C reference / enumerations");
	// }

	// protected void clickLinkByXPath() {
	// 	page.click("xpath=" + "//a[. = 'Dokumentation - C reference / enumerations']");
	// }

	// protected void typeText(String id, String text) {
	// 	page.locator(id).fill(text);
	// }

	// protected String getText(String id) {
	// 	ElementHandle input = (ElementHandle) page.locator(id);
	// 	return input.inputValue();
	// }

	// protected String getTextById(String id) {
	// 	ElementHandle element = (ElementHandle) page.locator(id);
	// 	return element.textContent();
	// }

	// protected String getTextByTagName(String tagName) {
	// 	ElementHandle element = (ElementHandle) page.locator("tagName=" + tagName);
	// 	return element.textContent();
	// }

	// protected String getTextByCssSelector(String selector) {
	// 	ElementHandle element = (ElementHandle) page.locator("css=" + selector);
	// 	return element.textContent();
	// }

	// protected String getInnerHTMLOfElementByID(String id) {
    //     return page.innerHTML(id);
	// }

	// protected void selectComboboxItem(String id, String name) {
	// 	ElementHandle select = (ElementHandle) page.locator(id);
	// 	select.selectOption(new SelectOption());
	// }

	// protected String getComboBoxItems(String id) {
	// 	ElementHandle select = (ElementHandle) page.locator(id);
	// 	return select.inputValue();
	// }

	// protected boolean isVisible(String id) {
    //     return page.isVisible(id);
	// }

	// protected void moveOverHTMLelement(String id) {
	// 	ElementHandle element = (ElementHandle) page.locator(id);
	// 	element.hover();
	// }

	// protected String getColorofHTMLElement(String id) {
	// 	ElementHandle element = (ElementHandle) page.locator(id);
	// 	return element.evaluate("el => getComputedStyle(el).backgroundColor").toString();
	// }
}
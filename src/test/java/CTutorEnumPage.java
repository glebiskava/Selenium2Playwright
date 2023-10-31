import com.microsoft.playwright.Page;

public class CTutorEnumPage extends AbstractPage {

    protected CTutorEnumPage(Page page) {
        super(page);
    }

    public void open() {
        String projectDirectory = System.getProperty("user.dir");
        String url = "file://" + projectDirectory + "/www/enum.html";
        call(url);
    }

    /* click events */

    public EnumerationsPageCReference clickEnumLink0() {
        click("docLink");
        return new EnumerationsPageCReference(page);
    }

    public EnumerationsPageCReference clickEnumLink2() {
        clickLink("Dokumentation - C reference / enumerations");
        return new EnumerationsPageCReference(page);
    }

    public EnumerationsPageCReference clickEnumLink3() {
        clickLinkByXPath("//a[. = 'Dokumentation - C reference / enumerations']");
        return new EnumerationsPageCReference(page);
    }

    public void clickButtonAddEnumerator() {
        click("add_const");
    }

    public void clickButtonRemoveEnumerator() {
        click("rm_const");
    }

    public void selectEnumerator(String name) {
        selectComboboxItem("ConstList", name);
    }

    public void clickButtonCopyToClipboard() {
        click("copy-btn");
    }

    /* typing events */

    public void setEnumName(String name) {
        typeText("enumName", name);
    }

    public void setDescription(String description) {
        typeText("enumDescIn", description);
    }

    public void setDescription2(String description) {
        this.typeText("textarea", description);
    }

    public void setEnumeratorName(String name) {
        typeText("constName", name);
    }

    public void setEnumeratorValue(String value) {
        typeText("constValue", value);
    }

    /* accessing data */
    public String getHeader() {
        return this.getTextById("titleHeader");
    }

    public String getHeader2() {
        return getTextByTagName("h1");
    }

    public String getHeader3() {
        return getTextByCssSelector("h1");
    }

    public String getEnumNameInComment() {
        return getTextById("enumNameOut");
    }

    public String getEnumNameInDefinition() {
        return getTextById("enumerationNameSpan");
    }

    public String getEnumeratorInDef() {
        return getInnerHTMLOfElementByID("constantsSpan");
    }

    public String getDescription() {
        return getTextById("enumDescOut");
    }

    public String getDescriptionAsHTML() {
        return getInnerHTMLOfElementByID("enumDescOut");
    }

    public String getFirstEnumeratorInCombobox() {
        return this.getComboBoxItems("ConstList");
    }

    public boolean buttonRemoveEnumeratorVisisble() {
        return isVisible("rm_const-btn");
    }

    public void MouseOverAddEnumeratorButton() {
        moveOverHTMLelement("copy-btn");
    }

    public String getColorOfAddEnumeratorButton() {
        return getColorofHTMLElement("copy-btn");
    }
}

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CTutorEnumPage extends AbstractPage {

    private final Locator doc_link1;
    private final Locator doc_link2;
    private final Locator doc_link3;
    private final Locator enum_name;
    private final Locator desc_enum;
    private final Locator desc_enum2;
    private final Locator add_enum;
    private final Locator rm_enum;
    private final Locator comment_enum_name_out;
    private final Locator comment_enum_desc_out;
    private final Locator def_enum_name_out;
    private final Locator enumerator_name_in;
    private final Locator enumerator_value_in;
    private final Locator enumerator_out;
    private final Locator combobox;
    private final Locator copy_btn;
    private String dialogText;

    protected CTutorEnumPage(Page page) {
        super(page);
        this.doc_link1 = page.locator("#docLink");
        this.doc_link2 = page.getByText("Dokumentation - C reference / enumerations");
        this.doc_link3 = page.locator("//a[. = 'Dokumentation - C reference / enumerations']");
        this.enum_name = page.locator("#enumName");
        this.desc_enum = page.locator("#enumDescIn");
        this.desc_enum2 = page.locator("textarea");
        this.add_enum = page.locator("#add_const");
        this.rm_enum = page.locator("#rm_const-btn");
        this.comment_enum_name_out = page.locator("#enumNameOut");
        this.comment_enum_desc_out = page.locator("#enumDescOut");
        this.def_enum_name_out = page.locator("#enumerationNameSpan");
        this.enumerator_name_in = page.locator("#constName");
        this.enumerator_value_in = page.locator("#constValue");
        this.combobox = page.locator("#ConstList");
        this.enumerator_out = page.locator("#constantsSpan");
        this.copy_btn = page.locator("#copy-btn");
        page.onDialog(dialog -> {
            this.dialogText = dialog.message();
            dialog.accept();
        }); //for Alert when enum name empty
    }

    public void open() {
        String projectDirectory = System.getProperty("user.dir");
        String url = "file:///" + projectDirectory + "/src/main/webapp/enum.html";
        page.navigate(url);
    }

    /* click events */

    public EnumerationsPageCReference clickEnumLink1() {
        doc_link1.click();
        return new EnumerationsPageCReference(page);
    }

    public EnumerationsPageCReference clickEnumLink2() {
        doc_link2.click();
        return new EnumerationsPageCReference(page);
    }

    public EnumerationsPageCReference clickEnumLink3() {
        doc_link3.click();
        return new EnumerationsPageCReference(page);
    }

    public void clickButtonAddEnumerator() {
        add_enum.click();
    }

    public void clickButtonRemoveEnumerator() {
        rm_enum.click();
    }

    public void clickButtonCopyToClipboard() {
        copy_btn.click();
    }

    /* typing events */

    public void setEnumName(String name) {
        enum_name.fill(name);
        refreshEnumName();
    }

    public void setDescription(String description) {
        desc_enum.fill(description);
        refreshEnumDesc();
    }

    public void setDescription2(String description) {
        desc_enum2.fill(description);
        refreshEnumDesc();
    }

    public void setEnumeratorName(String name) {
        if(name != null) enumerator_name_in.fill(name);
    }

    public void setEnumeratorValue(String value) {
        if(value != null) enumerator_value_in.fill(value);
    }

    /* getters */

    public String getEnumNameInComment() {
        return comment_enum_name_out.textContent();
    }

    public String getEnumNameInDefinition() {
        return def_enum_name_out.textContent();
    }

    public String getEnumeratorInDef() {
        return enumerator_out.innerHTML();
    }
    
    public String getDescriptionAsHTML() {
        return comment_enum_desc_out.innerHTML();
    }
    
    public String getFirstEnumeratorInCombobox() {
        return combobox.locator("option").textContent();
    }
    
    public String getColorOfAddEnumeratorButton() {
        return add_enum.evaluate("(el) => {return window.getComputedStyle(el).getPropertyValue('background-color');}").toString();
    }

    public String getDialogText() {
        return dialogText;
    }

    /* ui checks */

    public boolean buttonRemoveEnumeratorVisisble() {
        return rm_enum.isVisible();
    }

    public void MouseOverAddEnumeratorButton() {
        add_enum.hover();
    }
    
    /* js calls */

    public void refreshEnumName(){
        page.evaluate("changeEnumName()");
    }

    public void refreshEnumDesc(){
        page.evaluate("changeEnumDesc()");
    }
}

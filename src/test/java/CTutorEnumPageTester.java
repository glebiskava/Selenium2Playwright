import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class CTutorEnumPageTester extends AbstractPlaywrightTester {

    private CTutorEnumPage enum_page = null;

    @BeforeEach
    void beforeEach() {
        enum_page = new CTutorEnumPage(page);
        enum_page.open();
    }

    /**
     * WHEN I open the webpage "enum.html" in a Browser THEN the title is "C Tutor -
     * Aufzählungstyp (Enumeration)" AND there is a header with the same text.
     */
    @Test
    @DisplayName("trivial: test the title of the page")
    void testTitel() {
        /* ä a umlaut sollte im html-Code als &auml; vorkommen */
        assertEquals("C Tutor - Aufzählungstyp (Enumeration)", page.title());
    }

    /**
     * WHEN I click on the link "Dokumentation - C reference / enumerations" THEN
     * the title of the new page is "Enumerations - cppreference.com" AND the URL of
     * the new page is "https://en.cppreference.com/w/c/language/enum"
     */
    @Test
    @DisplayName("minor: test documentation link 0")
    void testDocumentationLink() {
        EnumerationsPageCReference doc_page = enum_page.clickEnumLink1();
        assertEquals("Enumerations - cppreference.com", doc_page.title());
    }

    @Test
    @DisplayName("minor: test documentation link 1")
    void testDocumentationLink2() {
        EnumerationsPageCReference doc_page = enum_page.clickEnumLink2();
        assertEquals("Enumerations - cppreference.com", doc_page.title());
    }

    @Test
    @DisplayName("minor: test documentation link 2")
    void testDocumentationLink3() {
        EnumerationsPageCReference doc_page = enum_page.clickEnumLink3();
        assertEquals("Enumerations - cppreference.com", doc_page.title());
    }

    /**
     * WHEN I type <name> in the edit field with the label "Name Aufzählungstyp".
     * THEN I see in the preview window <name> in den Doxygen Comment AND <name> in
     * the enum-type definition. Examples
     * <table>
     * <tr>
     * <th>Name</th>
     * </tr>
     * <tr>
     * <td>ErrorCode</td>
     * </tr>
     * <tr>
     * <td>CodecResult</td>
     * </tr>
     * </table>
     */
    @ParameterizedTest
    @ValueSource(strings = { "ErrorCode", "CodecResult", "HTMLErrorCode" })
    @DisplayName("critical: test correct enumeration name preview")
    void testCorrectEnumNamePreview(String name) {
        enum_page.setEnumName(name);
        assertEquals(name, enum_page.getEnumNameInComment());
        assertEquals(name, enum_page.getEnumNameInDefinition());
    }

    /**
     * WHEN I type <name> in the edit field with the label "Name Aufzählungstyp".
     * THEN I see in the preview window "ungültiger Name" in den Doxygen Comment AND
     * "ungültiger Name" in the enum-type definition. Examples
     * <table>
     * <tr>
     * <th>Name</th>
     * </tr>
     * <tr>
     * <td>Error Code</td>
     * </tr>
     * <tr>
     * <td>Codec Result</td>
     * </tr>
     * </table>
     */
    @ParameterizedTest
    @ValueSource(strings = { "Error Code", "Codec Result" })
    @DisplayName("critical: test wrong enumeration name preview")
    void testWrongEnumNamePreview(String name) {
        enum_page.setEnumName(name);
        final String expected = "ungültiger Name";
        assertEquals(expected, enum_page.getEnumNameInComment());
        assertEquals(expected, enum_page.getEnumNameInDefinition());
    }

    /**
     * WHEN I type <description> in the edit field with the label Beschreibung
     * Aufzählungstyp: THEN I see in the preview window next to the word brief, the
     * string <description>.
     */
    @ParameterizedTest
    @ValueSource(strings = { "Fehlernummer des Framework", "Ergebnis-Codes des Codecs",
            "Codec Result \n Rückgabecode der encode und decode Funktionen",
            "Codec Result <br> Rückgabecode der encode und decode Funktionen" })
    @DisplayName("blocker: test description in preview")
    void testDecriptionInPreview(String description) {
        enum_page.setDescription(description);
        assertEquals(description, enum_page.getDescriptionAsHTML());
    }

    @Test
    @DisplayName("blocker: test description in preview")
    void testDecriptionInPreview2() {
        String description = "Fehlernummer des Framework";
        enum_page.setDescription2(description);
        assertEquals(description, enum_page.getDescriptionAsHTML());
    }

    /**
     * WHEN I write <name> in the edit field with the label "Name des Enumerators"
     * AND <value> in the edit field with the label "ganzzahliger Enumerator-Wert
     * (optional): AND I click on the
     * button "Enumerator hinzufügen" THEN I see the in the preview window the
     * enumerator <name>=<value> in the enum definition. AND <name> is in the
     * combobox labeled with "Liste der Enumeratoren"
     */
    @ParameterizedTest
    @DisplayName("blocker: test adding enumerator")
    @ValueSource(strings = { "NO_VALUE", "OUT_OF_BOUND", "WRONG_TYPE" })
    void testAddingEnumerator(String enumerator) {
        enum_page.setEnumeratorName(enumerator);
        enum_page.setEnumeratorValue(null);
        enum_page.clickButtonAddEnumerator();
        assertEquals(enumerator, enum_page.getFirstEnumeratorInCombobox());
        assertTrue(enum_page.getEnumeratorInDef().contains(enumerator));
    }

    /**
     * WHEN I write <name> in the edit field with the label "Name des Enumerators"
     * AND <value> in the edit field with the label "ganzzahliger Enumerator-Wert
     * (optional): AND I click on the
     * button "Enumerator hinzufügen" THEN I see the in the preview window the
     * enumerator <name>=<value> in the enum definition. AND <name> is in the
     * combobox labeled with "Liste der Enumeratoren"
     */
    @ParameterizedTest
    @DisplayName("blocker test adding enumerator with value")
    @CsvSource({ "Continue, 100",
            "OK, 200",
            "BadRequest, 400",
            "NotFound, 404",
            "InternalServerError, 500"
    })
    void testAddingEnumeratorWithValue(String enumerator, String value) {
        enum_page.setEnumeratorName(enumerator);
        enum_page.setEnumeratorValue(value);
        enum_page.clickButtonAddEnumerator();
        assertEquals(enumerator, enum_page.getFirstEnumeratorInCombobox());
        assertTrue(enum_page.getEnumeratorInDef().contains(value));
    }

    /**
     * WHEN I click on the copy to clipboard button THEN the content of the
     * preview is in the clipboard.
     */
    @Test
    @DisplayName("normal: test copy clipboard")
    void testCopyClipboard() {
        assumeTrue((browser.browserType() == playwright.chromium()), "clipboard.read unsupported In Webkit or Firefox");
        assumeFalse(headless, "unsupported in headless mode");
        context.grantPermissions(Arrays.asList("clipboard-read","clipboard-write"));
        String enumName = "HTTPStatusCode";
        enum_page.setEnumName(enumName);
        enum_page.clickButtonCopyToClipboard();

        // Wait for a moment to ensure the clipboard content is available
        page.waitForTimeout(1000);

        String actualCopiedText = (String) page.evaluate("() => { return navigator.clipboard.readText(); }");
        String pattern = "^/\\*\\*.+" + enumName + ";$"; // starting with /** ending with enumName; DOTALL -> inclusive multilines
        Pattern p = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = p.matcher(actualCopiedText);
        assertTrue(m.matches());
        context.clearPermissions();
    }

    /**
     * WHEN I start the enum-Page, THEN the button remove enumerator is not present.
     */
    @Test
    @DisplayName("critical: test button remove enumerator not present")
    void testRemoveEnumeratorNotVisible() {
        assertFalse(enum_page.buttonRemoveEnumeratorVisisble());
        enum_page.setEnumeratorName("FileNotFound");
        enum_page.clickButtonAddEnumerator();
        assertTrue(enum_page.buttonRemoveEnumeratorVisisble());
    }

    /**
     * WHEN I move the mouse over a button THEN the color changes from green to red
     */
    @Test
    @DisplayName("minor: test changing color when mouse is moving over button")
    void testMouseMovingOverButton() {
        assertEquals("rgb(0, 128, 0)", enum_page.getColorOfAddEnumeratorButton()); /* rgb(0, 128, 0) */
        enum_page.MouseOverAddEnumeratorButton();
        assertEquals("rgb(255, 0, 0)", enum_page.getColorOfAddEnumeratorButton()); /* rgb(255, 0, 0) */
    }

    /**
     * WHEN there is no name for the enumerator AND I click on the button "Enumerator Hinzufügen"
     * THEN an alert message with the text "Bitte Namen für Enumerator vergeben!" appears
     */
    @Test
    @DisplayName("normal: test alert, when adding enumerator without enumerator name")
    void testAddEmptyEnumerator() {
        enum_page.setEnumeratorName(null);
        enum_page.clickButtonAddEnumerator();
        assertEquals("Bitte Namen für Enumerator vergeben!", enum_page.getDialogText());
    }
}

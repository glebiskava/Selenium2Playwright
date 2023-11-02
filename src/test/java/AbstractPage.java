import com.microsoft.playwright.*;

public class AbstractPage {
	protected final Page page;

	public AbstractPage(Page page) {
		this.page = page;
	}
}
import com.microsoft.playwright.*;

public abstract class AbstractPage {
	protected final Page page;

	public AbstractPage(Page page) {
		this.page = page;
	}
}
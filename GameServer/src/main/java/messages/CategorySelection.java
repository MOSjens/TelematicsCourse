package messages;

public class CategorySelection extends Message {
	
	private int categoryIndex;

	public CategorySelection() {
		super();
		this.setMessageType(MessageType.CATEGORY_SELECTION);
	}

	public int getCategoryIndex() {
		return categoryIndex;
	}

	public void setCategoryIndex(int categoryIndex) {
		this.categoryIndex = categoryIndex;
	}

}

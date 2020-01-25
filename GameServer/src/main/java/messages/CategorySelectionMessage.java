package messages;

public class CategorySelectionMessage extends Message {
	
	private int categoryIndex;

	public CategorySelectionMessage() {
		super();
		this.setMessageType(MessageType.CATEGORY_SELECTION);
	}
	
	public CategorySelectionMessage(int categoryIndex) {
		super();
		this.setMessageType(MessageType.CATEGORY_SELECTION);
		this.setCategoryIndex(categoryIndex);
	}

	public int getCategoryIndex() {
		return categoryIndex;
	}

	public void setCategoryIndex(int categoryIndex) {
		this.categoryIndex = categoryIndex;
	}

}
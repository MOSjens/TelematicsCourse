package messages;

import java.util.LinkedHashMap;

import dbconnection.Category;
import dbconnection.Difficulty;

public class CategorySelectorAnnouncementMessage extends Message {
	
	private long categoryTimeout;
	private int selectingPlayerId;
	private LinkedHashMap <Category,Difficulty> categoryDifficultyMap;

	public CategorySelectorAnnouncementMessage() {
		super();
		this.setMessageType(MessageType.CATEGORY_SELECTOR_ANNOUNCEMENT);
	}

	public CategorySelectorAnnouncementMessage(long categoryTimeout, int selectingPlayerId, LinkedHashMap<Category,Difficulty>categoryDifficultyMap) {
		super();
		this.setMessageType(MessageType.CATEGORY_SELECTOR_ANNOUNCEMENT);
		this.setCategoryTimeout(categoryTimeout);
		this.setSelectingPlayerId(selectingPlayerId);
		this.setCategoryDifficultyMap(categoryDifficultyMap);
	}
	
	public long getCategoryTimeout() {
		return categoryTimeout;
	}

	public void setCategoryTimeout(long categoryTimeout) {
		this.categoryTimeout = categoryTimeout;
	}

	public int getSelectingPlayerId() {
		return selectingPlayerId;
	}

	public void setSelectingPlayerId(int selectingPlayerId) {
		this.selectingPlayerId = selectingPlayerId;
	}

	public LinkedHashMap <Category,Difficulty> getCategoryDifficultyMap() {
		return categoryDifficultyMap;
	}

	public void setCategoryDifficultyMap(LinkedHashMap <Category,Difficulty> categoryDifficultyMap) {
		this.categoryDifficultyMap = categoryDifficultyMap;
	}

	
}

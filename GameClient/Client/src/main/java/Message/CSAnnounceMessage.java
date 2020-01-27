package Message;

import MessageType.InGameMessageType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class CSAnnounceMessage extends Message {

    private long timeOut;
    private int playerID;
    private int numberOfCategories;
    private List<String> difficulty;
    private List<String> categories;
    private List<Integer> difficultyIndex;
    private List<Integer> categoriesIndex;

    public CSAnnounceMessage(byte[] messageBody) {
        super(InGameMessageType.CATAGORY_SELECTION_ANNOUNCEMENT);
        ByteBuffer buffer = ByteBuffer.wrap(messageBody);
        setTimeOut(buffer.getLong());
        setPlayerID(buffer.getInt());
        setNumberOfCategories(buffer.getInt());

        difficulty = new ArrayList<>(numberOfCategories);
        categories = new ArrayList<>(numberOfCategories);
        difficultyIndex = new ArrayList<>(numberOfCategories);
        categoriesIndex = new ArrayList<>(numberOfCategories);

        SetIndex(buffer, difficultyIndex, categoriesIndex);
        difficultyIndex.add(messageBody.length);

        for(int i = 0; i < numberOfCategories; i ++) {
            byte[] diff = new byte[difficultyIndex.get(i) - categoriesIndex.get(i)];
            int k = 0;
            for(int j = difficultyIndex.get(i); j < categoriesIndex.get(i); j++) {
                diff[k++] = messageBody[j];
            }
            difficulty.add(new String(diff));

            byte[] cate = new byte[categoriesIndex.get(i) - difficultyIndex.get(i + 1)];
            k = 0;
            for(int j = categoriesIndex.get(i); j < difficultyIndex.get(i + 1); j++) {
                cate[k++] = messageBody[j];
            }
            categories.add(new String(cate));
        }

    }

    private void SetIndex(ByteBuffer buffer, List<Integer> list1, List<Integer> list2) {
        for(int i = 0; i < numberOfCategories; i++) {
            list1.add(buffer.getInt());
            list2.add(buffer.getInt());
        }
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getNumberOfCategories() {
        return numberOfCategories;
    }

    public void setNumberOfCategories(int numberOfCategories) {
        this.numberOfCategories = numberOfCategories;
    }

    public List<String> getDifficulty() {
        return difficulty;
    }

    public List<String> getCategories() {
        return categories;
    }
}

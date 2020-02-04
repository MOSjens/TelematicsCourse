package Message;

import MessageType.InGameMessageType;

import java.nio.ByteBuffer;

public class QuestionMessage extends Message {

    private String difficulty;
    private String category;
    private String question;
    private int numberOfAnswer;
    private String[] answer;
    private long timeOut;
    private int[] answerIndex;

    public QuestionMessage(byte[] messageBody) {
        super(InGameMessageType.QUESTION);
        ByteBuffer buffer = ByteBuffer.wrap(messageBody);
        int difficultyOffset = buffer.getInt();
        int categoryOffset = buffer.getInt();
        int questionOffset = buffer.getInt();
        setNumberOfAnswer(buffer.getInt());
        setTimeOut(buffer.getLong());
        answerIndex = new int[numberOfAnswer + 1];
        for(int i  = 0; i < numberOfAnswer; i++) {
            answerIndex[i] = buffer.getInt();
        }
        answerIndex[numberOfAnswer] = messageBody.length;
        difficulty = new String(getBytes(messageBody, difficultyOffset, categoryOffset));
        category = new String(getBytes(messageBody, categoryOffset, questionOffset));
        question = new String(getBytes(messageBody, questionOffset, answerIndex[0]));

        int k = 0;
        for (int i = 0; i < numberOfAnswer; i++) {
            answer[i] = new String(getBytes(messageBody, answerIndex[k], answerIndex[k + 1]));
            k++;
        }

    }



    private byte[] getBytes(byte[] original, int start, int end) {
        byte[] result = new byte[end - start];
        int k = 0;
        for(int  i = start; i < end; i++) {
            result[k++] = original[i];
        }
        return result;
    }

    public int getNumberOfAnswer() {
        return numberOfAnswer;
    }

    public void setNumberOfAnswer(int numberOfAnswer) {
        this.numberOfAnswer = numberOfAnswer;
    }

    public Long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Long timeOut) {
        this.timeOut = timeOut;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getCategory() {
        return category;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswer() {
        return answer;
    }
}

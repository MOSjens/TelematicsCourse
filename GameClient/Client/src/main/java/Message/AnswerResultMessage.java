package Message;

import MessageType.InGameMessageType;

import java.nio.ByteBuffer;

public class AnswerResultMessage extends Message {

   private int Selected;
   private int corrected;
   
    public AnswerResultMessage(byte[] messageBody) {
        super(InGameMessageType.ANSWER_RESULT);
        ByteBuffer buffer = ByteBuffer.wrap(messageBody);
        setCorrectAnswer(buffer.getInt());
        setSelectedAnswer( buffer.getInt());
    }
    

    public void setCorrectAnswer(int correct) {
        this.setCorrectAnswer(correct); 
    }


    public void setSelectedAnswer(int selected) {
        this.setSelectedAnswer(selected); 
    }
    public int getCorrectAnswer() {
        return corrected;
    }
        public int getSelectedAnswer() {
            return Selected;

}
}

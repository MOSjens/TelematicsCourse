package Message;

import MessageType.InGameMessageType;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class AnswerResultMessage extends Message {

   private int Selected;
   private int corrected;
   
    public AnswerResultMessage(byte[] messageBody) {
        super(InGameMessageType.ANSWER_RESULT);
        ByteBuffer buffer = ByteBuffer.wrap(messageBody);
        buffer.order(ByteOrder.BIG_ENDIAN);
        setCorrectAnswer(buffer.getInt());
        setSelectedAnswer( buffer.getInt());
    }
    

    public void setCorrectAnswer(int correct) {
        this.corrected = correct; 
    }


    public void setSelectedAnswer(int selected) {
        this.Selected = selected;
    }
    public int getCorrectAnswer() {
        return corrected;
    }
        public int getSelectedAnswer() {
            return Selected;

}
}

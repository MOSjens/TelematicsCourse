package StateMachine;


import Message.Message;
import Message.ScoreBoardMessage;
import MessageType.InGameMessageType;
import Message.CSAnnounceMessage;

public class ReScoreBoard extends AbstractState {

    public ReScoreBoard(StateEnum stateEnum) {
        super(stateEnum);
    }


    @Override
    public IState sendMessage(Context context) {
        return Context.RE_SCORE_BOARD_STATE;
    }

    @Override
    public IState receiveMessage(Context context) {
        Message message = context.getOutputMessage();
        switch(message.getGroup().getValue()) {
            case 0:{ break;}

            case 1:{
            	 if(message.getType()== InGameMessageType.SCOREBOARD) {
                     ScoreBoardMessage scoreBoardMessage = (ScoreBoardMessage)message;
                     context.setRoundsLeft(scoreBoardMessage.getRoundsLeft());
                     context.setPlayerIds(scoreBoardMessage.getPlayerIds());
                     context.setPlayerScores(scoreBoardMessage.getPlayerScores());
                     return Context.CATEGORY_SELECTION_ANNOUNCEMENT;
                     
              /*  if(message.getType()== InGameMessageType.CATEGORY_SELECTION_ANNOUNCEMENT) {
                    CSAnnounceMessage csAnnounceMessage = (CSAnnounceMessage)message;
                    context.setSelectedPlayerID(csAnnounceMessage.getSelectedplayerID());
                    context.setTimeOut(csAnnounceMessage.getTimeOut());
                    context.setDifficulties(csAnnounceMessage.getDifficulty());
                    context.setCategories(csAnnounceMessage.getCategories());
                    return Context.CATEGORY_SELECTION_ANNOUNCEMENT;
                }*/
            }}

            case 2: {
                IState nextState = Context.END_GAME;
                return nextState;
            }

            case 3: {
                System.out.println(context.getInputMessage().getMessageBody());
            }
        }

        return Context.RE_SCORE_BOARD_STATE;
    }
}

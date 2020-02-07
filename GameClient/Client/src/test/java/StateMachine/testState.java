package StateMachine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import Message.CSAnnounceMessage;
import Message.QuestionMessage;
import Message.ScoreBoardMessage;
import Message.SignOnRespondMessage;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import parser.ReceiveParser;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class testState {

    private static Context context = new Context(Context.INITIAL_STATE);


    @Test
    public void AllInOne() {
        //Initial to sign on
        context.setPlayerAlias("Thomas");
        context.sendMessage();
        assertEquals(((AbstractState)context.getState()).getStateEnum(), StateEnum.SIGN_ON);

        //sign on to re sign on
        byte[] messagebody = {0x01,0x00,0x01,0x00,0x00,0x00,0x08,0x00,0x00,0x00,0x10,0x66,0x72,0x65,0x64};
        InputStream stream = new ByteArrayInputStream(messagebody);
        SignOnRespondMessage signOnRespondMessage = null;
        try {
            signOnRespondMessage = (SignOnRespondMessage)ReceiveParser.ParsMessage(stream);
        }catch (Exception e) {

        }
        assertEquals(16, signOnRespondMessage.getPlayerID());
        assertEquals("fred", signOnRespondMessage.getAlias());
        context.setInputMessage(signOnRespondMessage);
        context.receiveMessage();
        assertEquals(((AbstractState)context.getState()).getStateEnum(), StateEnum.RE_SIGN_ON);

        //re sign on to ready
        context.sendMessage();
        assertEquals(((AbstractState)context.getState()).getStateEnum(), StateEnum.READY);

        //ready to re score board
        byte[] messagebody1 = {0x1,0x1,0x6,0x00,0x00,0x00,0x8,0x00,0x00,0x00,0x2,0x00,0x00,0x00,0x00};
        stream = new ByteArrayInputStream(messagebody1);
        ScoreBoardMessage scoreBoardMessage = null;
        try {
            scoreBoardMessage = (ScoreBoardMessage)ReceiveParser.ParsMessage(stream);
        }catch (Exception e) {

        }
        assertEquals(1, scoreBoardMessage.getGroup().getValue());
        assertEquals(2, scoreBoardMessage.getRoundsLeft());
        assertEquals(0, scoreBoardMessage.getNumPlayers());
        context.setInputMessage(scoreBoardMessage);
        context.receiveMessage();
        assertEquals(StateEnum.RE_SCORE_BOARD, ((AbstractState)context.getState()).getStateEnum());

        //re score board to csa
        byte[] messagebody2 = {0x1,0x1,0x0,0x0,0x0,0x0,0x37,0x0,0x0,0x0,0x0,0x0,0x0,0x27,0x10,0x0,0x0,0x0
                ,0x3,0x0,0x0,0x0,0x2,0x0,0x0,0x0,0x20,0x0,0x0,0x0,0x29,0x0,0x0,0x0,0x2e,0x0,0x0,0x0
                ,0x32,0x56,0x65,0x72,0x79,0x20,0x45,0x61,0x73,0x79,0x4d,0x61,0x74,0x68,0x73,0x48,0x61
                ,0x72,0x64,0x42,0x6f,0x6f,0x6b,0x73};
        stream = new ByteArrayInputStream(messagebody2);
        CSAnnounceMessage csAnnounceMessage = null;
        try {
            csAnnounceMessage = (CSAnnounceMessage)ReceiveParser.ParsMessage(stream);
        }catch (Exception e) {

        }

        assertEquals(10000, csAnnounceMessage.getTimeOut());
        assertEquals(3, csAnnounceMessage.getSelectedplayerID());
        assertEquals(2,csAnnounceMessage.getNumberOfCategories());
        context.setInputMessage(csAnnounceMessage);
        context.receiveMessage();
        assertEquals(StateEnum.CATEGORY_SELECTION_ANNOUNCEMENT, ((AbstractState)context.getState()).getStateEnum());

        //csa to selector
        context.setPlayerID(1);
        context.setSelectedIndex(0);
        context.sendMessage();
        assertEquals(StateEnum.NONE_SELECTOR, ((AbstractState)context.getState()).getStateEnum());

        //none selector to re question
        byte[] messagebody3 = {0x1,0x1,0x2,0x0,0x0,0x0,0x46,0x0,0x0,0x0,0x24,0x0,0x0,0x0,0x2d,0x0,0x0,0x0,0x32,0x0,0x0
                ,0x0,0x3,0x0,0x0,0x0,0x0,0x0,0x0,0x27,0x10,0x0,0x0,0x0,0x3f,0x0,0x0,0x0,0x40,0x0,0x0,0x0,0x44,0x56,0x65
                ,0x72,0x79,0x20,0x45,0x61,0x73,0x79,0x4d,0x61,0x74,0x68,0x73,0x57,0x68,0x61,0x74,0x27,0x73,0x20,0x31
                ,0x20,0x2b,0x20,0x31,0x3f,0x32,0x66,0x6f,0x75,0x72,0x34,0x32};
        stream = new ByteArrayInputStream(messagebody3);
        QuestionMessage questionMessage = null;
        try {
            questionMessage = (QuestionMessage)ReceiveParser.ParsMessage(stream);
        }catch (Exception e) {

        }
        context.setInputMessage(questionMessage);
        context.receiveMessage();
        assertEquals(StateEnum.RE_QUESTION, ((AbstractState)context.getState()).getStateEnum());

        //re question to buzz and screw

    }



}

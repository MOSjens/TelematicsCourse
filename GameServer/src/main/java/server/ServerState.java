package server;

import client.Client;
import dbconnection.Question;
import dbconnection.QuestionDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Holds all information for the running game
 * @author g4
 */
public class ServerState {

    private int roundsLeft;
    private Set<Client> playerList;
    // private ? scoreboard
    // private ? readyState
    private ArrayList<Question> questionList;
    // private ? gamePhase

    public ServerState(int roundsLeft ) {
        this.roundsLeft = roundsLeft;
        playerList = new HashSet<Client>();
        questionList = new ArrayList<Question>();
    }

    public int getRoundsLeft() {
        return roundsLeft;
    }

    public void decreaseRoundsLest() {
        roundsLeft--;
    }

    public void addPlayer(Client client) {
        playerList.add(client);
    }

    public Set<Client> getPlayerList() {
        return playerList;
    }
    
	public void fillQuestionList(int amount) {
		QuestionDatabase qdb = new QuestionDatabase();
		questionList.addAll(qdb.getRandomQuestions(amount));
	}

	public ArrayList<Question> getQuestionList() {
		return questionList;
	}
}

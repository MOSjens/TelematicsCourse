package server;

import client.Client;
import dbconnection.Category;
import dbconnection.Difficulty;
import dbconnection.Question;
import dbconnection.QuestionDatabase;
import messages.CategorySelectorAnnouncementMessage;
import messages.PairReadyAlias;
import messages.PlayerListMessage;
import messages.ScoreboardMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Set;

/**
 * Holds all information for the running game
 * @author g4
 */
public class ServerState {

    private int roundsLeft;
    private Set<Client> playerList;
    private HashMap<String, Integer> aliasMap;
    // private ? scoreboard
    // private ? readyState
    private ArrayList<Question> questionList;
    // private ? gamePhase

    public ServerState(int roundsLeft ) {
        this.roundsLeft = roundsLeft;
        this.playerList = new HashSet<Client>();
        this.questionList = new ArrayList<Question>();
        this.aliasMap = new HashMap<String, Integer>();
        
    }

    public int getRoundsLeft() {
        return roundsLeft;
    }

    public void decreaseRoundsLeft() {
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
	
	public ArrayList<Question> getQuestionSample(int amount) {
		ArrayList<Question> questionSample = new ArrayList<Question>();
		ListIterator<Question> iter = this.questionList.listIterator();
		HashSet<Category> categoriesUsed = new HashSet<Category>();
		while (questionSample.size() < amount && iter.hasNext())  {
			Question question = iter.next();
			if(!categoriesUsed.contains(question.getCategory())){
				questionSample.add(question);
				
			}
		}
		for (Question question : questionSample) {
			this.questionList.remove(question);
		}
		return questionSample;
	}
	
	public ScoreboardMessage createScoreboardMessage() {
		LinkedHashMap<Integer, Integer> scoreMap = new LinkedHashMap<Integer, Integer>();
		for(Client player: this.getPlayerList()) {
			scoreMap.put(player.getPlayerID(), player.getScore());
		}
		ScoreboardMessage scoreboardMessage = new ScoreboardMessage(this.getRoundsLeft(),scoreMap);
		return scoreboardMessage;
		
	}
	
	public PlayerListMessage createPlayerlistMessage() {
		LinkedHashMap<Integer, PairReadyAlias>  playerMap = new LinkedHashMap<Integer, PairReadyAlias>();
		PairReadyAlias pairReadyAlias;
		for(Client player: this.getPlayerList()) {
			pairReadyAlias = new PairReadyAlias(player.getReadyState(), player.getAlias());
			playerMap.put(player.getPlayerID(), pairReadyAlias);
		}
		PlayerListMessage playerlistMessage = new PlayerListMessage(playerMap);
		return playerlistMessage;
		
	}
	
	public CategorySelectorAnnouncementMessage createCategorySelectorAnnouncementMessage(long categoryTimeout, int selectingPlayerId, 
			ArrayList<Question> questions) {
		LinkedHashMap <Category,Difficulty> categoryDifficultyMap = new LinkedHashMap <Category,Difficulty>();
		for(Question question : questions) {
			categoryDifficultyMap.put(question.getCategory(), question.getDifficulty());
		}
		CategorySelectorAnnouncementMessage CSAMessage = 
				new CategorySelectorAnnouncementMessage(categoryTimeout, selectingPlayerId, categoryDifficultyMap);
		return CSAMessage;		
	}
	
	public String solveAliasConflict(String alias) {
		String newAlias = alias;
		if(this.aliasMap.containsKey(alias)) {
			newAlias = alias + this.aliasMap.get(alias).toString();
			newAlias = this.solveAliasConflict(newAlias);
			this.aliasMap.put(alias,this.aliasMap.get(alias)+1);
		}
		else {
			this.aliasMap.put(alias, 2);
		}
		
		return newAlias;
	}
}

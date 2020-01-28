package server;

import client.Client;

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
    // private ? questionList
    // private ? gamePhase

    public ServerState(int roundsLeft ) {
        this.roundsLeft = roundsLeft;
        playerList = new HashSet<Client>();
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
}

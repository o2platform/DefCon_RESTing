package ch04.team;

import java.util.List;

public class Team {
    private List<Player> players;
    private String name;
    
    public Team() { }
    public Team(String name, List<Player> players) { 
	setName(name);
	setPlayers(players); 
    }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }
    public void setPlayers(List<Player> players) { this.players = players; }
    public List<Player> getPlayers() { return players; }
    public void setRosterCount(int n) { } // no-op but needed for property
    public int getRosterCount() { 
	return (players == null) ? 0 : players.size();
    }
}

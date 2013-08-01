package ch04.team;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.beans.XMLEncoder;

public class TeamsUtility {
    private Map<String, Team> team_map;
    private List<Team> teams;
    private static final String file_name = "teams.ser";

    public TeamsUtility() {
	team_map = new HashMap<String, Team>();
    }

    public Team getTeam(String name) {
	return team_map.get(name);
    }

    public List<Team> getTeams() {
	List<Team> list = new ArrayList<Team>();
	Set<String> keys = team_map.keySet();
	for (String key : keys)
	    list.add(team_map.get(key));
	return list;
    }

    private void make_test_teams() {
	teams = new ArrayList<Team>();
	Player burns = new Player("George Burns", "George");
	Player allen = new Player("Gracie Allen", "Gracie");
	List<Player> ba = new ArrayList<Player>();
	ba.add(burns); ba.add(allen);
	Team burns_and_allen = new Team("BurnsAndAllen", ba);
	teams.add(burns_and_allen);

	Player abbott = new Player("William Abbott", "Bud");
	Player costello = new Player("Louis Cristillo", "Lou");
	List<Player> ac = new ArrayList<Player>();
	ac.add(abbott); ac.add(costello);
	Team abbott_and_costello = new Team("AbbottAndCostello", ac);
	teams.add(abbott_and_costello);

	Player chico = new Player("Leonard Marx", "Chico");
	Player groucho = new Player("Julius Marx", "Groucho");
	Player harpo = new Player("Adolph Marx", "Harpo");
	List<Player> mb = new ArrayList<Player>();
	mb.add(chico); mb.add(groucho); mb.add(harpo);
	Team marx_brothers = new Team("MarxBrothers", mb);
	teams.add(marx_brothers);

	store_teams(teams);
    }

    private void store_teams(List<Team> teams) {
	for (Team team : teams)
	    team_map.put(team.getName(), team);
	
	try {     
	    String cwd = System.getProperty ("user.dir");
	    String sep = System.getProperty ("file.separator");
	    String path = cwd + sep + "ch04" + sep + "team" + sep + file_name;
            BufferedOutputStream out = 
		new BufferedOutputStream(new FileOutputStream(path));
	    XMLEncoder enc = new XMLEncoder(out);
	    enc.writeObject(teams);
            enc.close();
	}
        catch(IOException e) { System.err.println(e); }
    }

    public static void main(String[ ] args) {
	new TeamsUtility().make_test_teams();
    }
}

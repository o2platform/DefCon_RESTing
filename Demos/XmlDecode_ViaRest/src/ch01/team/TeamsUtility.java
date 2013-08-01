package ch01.team;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class TeamsUtility {
    private Map<String, Team> team_map;

    public TeamsUtility() {
	team_map = new HashMap<String, Team>();
	make_test_teams();
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

    public void make_test_teams() {
	List<Team> teams = new ArrayList<Team>();

	Player burns = new Player("George Burns", "George");
	Player allen = new Player("Gracie Allen", "Gracie");
	List<Player> ba = new ArrayList<Player>();
	ba.add(burns); ba.add(allen);
	Team burns_and_allen = new Team("Burns and Allen", ba);
	teams.add(burns_and_allen);

	Player abbott = new Player("William Abbott", "Bud");
	Player costello = new Player("Louis Cristillo", "Lou");
	List<Player> ac = new ArrayList<Player>();
	ac.add(abbott); ac.add(costello);
	Team abbott_and_costello = new Team("Abbott and Costello", ac);
	teams.add(abbott_and_costello);

	Player chico = new Player("Leonard Marx", "Chico");
	Player groucho = new Player("Julius Marx", "Groucho");
	Player harpo = new Player("Adolph Marx", "Harpo");
	List<Player> mb = new ArrayList<Player>();
	mb.add(chico); mb.add(groucho); mb.add(harpo);
	Team marx_brothers = new Team("Marx Brothers", mb);
	teams.add(marx_brothers);

	store_teams(teams);
    }

    private void store_teams(List<Team> teams) {
	for (Team team : teams)
	    team_map.put(team.getName(), team);
    }
}

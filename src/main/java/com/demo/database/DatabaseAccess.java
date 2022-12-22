package com.demo.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.demo.bean.Mission;

@Repository
public class DatabaseAccess {
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	public List<Mission> getAllAgents() {
		System.out.println("Fetching distinct data...");
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT DISTINCT(agent) FROM missions";
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Mission>(Mission.class));	
	}
	
	public List<Mission> getAllMissions() {
		System.out.println("Fetching all data...");
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM missions";
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Mission>(Mission.class));	
	}
	
	public Mission getSingleMission(Long id) {
		System.out.println("Fetching single data...");
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM missions where id=:id";
		namedParameters.addValue("id", id);
		
		Mission mission = null;
		
		try {
			mission = (Mission) jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<Mission>(Mission.class));
		} catch(EmptyResultDataAccessException ex) {
			System.out.println("404 NOT FOUND");
		}
		
		return mission;
	}
	
	public List<Mission> getMissionsByAgent(String agent) {
		System.out.println("Fetching single data...");
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM missions where agent=:agent";
		namedParameters.addValue("agent", agent);
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Mission>(Mission.class));
	}
	
	public void addMission(Mission mission) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT into missions"
				+" (agent, title, gadget1, gadget2)"
				+" VALUES"
				+" (:agent, :title, :gadget1, :gadget2)";
		namedParameters.addValue("agent", mission.getAgent());
		namedParameters.addValue("title", mission.getTitle());
		namedParameters.addValue("gadget1", mission.getGadget1());
		namedParameters.addValue("gadget2", mission.getGadget2());
		int rowsAffected = jdbc.update(query, namedParameters);
		if(rowsAffected > 0) {
			System.out.println("Data Added!");
		}
	}
	
	public void updateMission(Mission mission) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "UPDATE missions"
				+" SET agent=:agent, title=:title, gadget1=:gadget1, gadget2=:gadget2"
				+" WHERE"
				+" id=:id";
		namedParameters.addValue("id", mission.getId());
		namedParameters.addValue("agent", mission.getAgent());
		namedParameters.addValue("title", mission.getTitle());
		namedParameters.addValue("gadget1", mission.getGadget1());
		namedParameters.addValue("gadget2", mission.getGadget2());
		int rowsAffected = jdbc.update(query, namedParameters);
		if(rowsAffected > 0) {
			System.out.println("Data Updated!");
		}
	}
	
	public void deleteMission(Long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "DELETE FROM missions"
				+" WHERE id="+id;
		int rowsAffected = jdbc.update(query, namedParameters);
		if(rowsAffected > 0) {
			System.out.println("Data Deleted!");
		}
	}
	
}

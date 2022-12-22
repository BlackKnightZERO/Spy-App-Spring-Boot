package com.demo.app;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.bean.Mission;
import com.demo.database.DatabaseAccess;

@SpringBootTest
class TestDatabase {
	
	private DatabaseAccess da;
	
	@Autowired
	public void setDa(DatabaseAccess da) {
		this.da = da;
	}
	
	@Test
	public void testDatabaseAdd() {
		
		Mission m = new Mission();
		m.setTitle("TEST-TITLE");
		m.setAgent("TEST-NAME");
		m.setGadget1("TEST-GADGET-1");
		m.setGadget2("TEST-GADGET-2");
		
		int prevSize = da.getAllMissions().size();
		
		da.addMission(m);
		
		int newSize = da.getAllMissions().size();
		
		assertEquals(newSize, prevSize+1);
	}
	
	@Test
	public void testDatabaseDelete() {
		
		List<Mission> mlist =  da.getAllMissions();
		
		Long id = mlist.get(0).getId();
		
		int prevSize = da.getAllMissions().size();
		
		da.deleteMission(id);
		
		int newSize = da.getAllMissions().size();
		
		assertEquals(newSize, prevSize-1);
		
	}

}

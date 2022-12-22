package com.demo.app;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import com.demo.bean.Mission;
import com.demo.database.DatabaseAccess;

@SpringBootTest
@AutoConfigureMockMvc
class TestController {
		
	private DatabaseAccess da;
	private MockMvc mockMvc;
		
	@Autowired
	public void setDa(DatabaseAccess da) {
		this.da = da;
	}
	@Autowired
	public void setMock(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	@Test
	public void testUpdatMissions() throws Exception{
		
		List<Mission> mlist =  da.getAllMissions();
		Mission m = mlist.get(0);
		Long id=m.getId();
		
		m.setTitle("TEST-TITLE");
		m.setAgent("TEST-NAME");
		m.setGadget1("TEST-GADGET-1");
		m.setGadget2("TEST-GADGET-2");
		
		mockMvc.perform(post("/updateMission")
				.sessionAttr("auth", "authorized")
				.flashAttr("mission",m))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/"));
		
		  m = da.getSingleMission(id);
		
		  assertEquals(m.getTitle(),"TEST-TITLE");
		  assertEquals(m.getAgent(),"TEST-NAME");
		  assertEquals(m.getGadget1(),"TEST-GADGET-1");
		  assertEquals(m.getGadget2(),"TEST-GADGET-2");
	}
	
	@Test
	public void testview() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		
		String agentName = "Johnny English";
		
		requestParams.add("agent", agentName);
		
		List<Mission> mlist = da.getAllMissions();
		List<Mission> mlistByAgent = da.getMissionsByAgent(agentName);
		
		mockMvc.perform( get("/viewMission")
				.sessionAttr("auth", "authorized")
				.params(requestParams) )
				.andExpect(status().isOk());
		
		int missionCount = mlist.size();
		int missionCountAgent = mlistByAgent.size();
		int difference = missionCount - missionCountAgent;
		assertTrue(difference==missionCount-missionCountAgent);
		
	}
	
	@Test
	public void testDeleteMission() throws Exception {
		
		List<Mission> mlist =  da.getAllMissions();
		int prevSize = mlist.size();
		Mission m = mlist.get(0);
		Long id=m.getId();
		
		mockMvc.perform(get("/deleteMission/{id}", id)
				.sessionAttr("auth", "authorized")
				.flashAttr("mission",m))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/"));	

		int newSize = da.getAllMissions().size();
		assertEquals(newSize, prevSize-1);
		
	}
	
}


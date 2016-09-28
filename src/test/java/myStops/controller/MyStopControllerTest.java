package myStops.controller;


import myStops.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import myStops.util.MockPrincipal;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class MyStopControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;


    MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getLocations() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/mystop/location/")
                .principal(new MockPrincipal("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(0)));

    }

    @Test
    public void addNewLocation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/mystop/location/")
                .content("test")
                .principal(new MockPrincipal("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void deleteLocation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/mystop/location/")
                .content("test")
                .principal(new MockPrincipal("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void stopOfLocation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/mystop/stopOfLocation")
                .content("test")
                .principal(new MockPrincipal("user")))
                .andExpect(status().isOk());
    }

    @Test
    public void delstopOfLocation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/mystop/stop")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"stopID\":\"HSL:1121120\",\"location\":\"uusi\"}")
                .principal(new MockPrincipal("user")))
                .andExpect(status().isOk()).andExpect(jsonPath("$.*", hasSize(1)));;
    }

    @Test
    public void addstopOfLocation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/mystop/stop")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"stopID\":\"HSL:1121120\",\"location\":\"uusi\"}")
                .principal(new MockPrincipal("user")))
                .andExpect(status().isOk()).andExpect(jsonPath("$.*", hasSize(1)));;

    }
    @Test
    public void locationNameChange() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/mystop/locationName")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"oldName\":\"jou\",\"newName\":\"uusi\"}")
                .principal(new MockPrincipal("user")))
                .andExpect(status().isOk()).andExpect(jsonPath("$.*", hasSize(1)));;

    }




}

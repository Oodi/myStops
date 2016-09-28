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
public class PersonControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void addUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/person/newuser")
                .content("testi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)));

    }

    @Test
    public void userNameTaken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/person/isUsernameTaken")
                .content("taken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)));

    }
    @Test
    public void delUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/person/delete/")
                .principal(new MockPrincipal("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)));

    }


    @Test
    public void changePassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/person/resetpassword/")
                .principal(new MockPrincipal("user"))
                .content("uusi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)));

    }






}

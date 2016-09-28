package myStops.controller;


import myStops.Application;
import org.apache.struts.mock.MockPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("development")
public class LoginControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;


    /** Before.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * @throws Exception
     */
    @Test
    public void testValidLogin() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/auth")
                .principal(new MockPrincipal("user"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("user")));
    }

    @Test
    public void testInvalidValidLogin() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/auth"))
                .andExpect(status().isOk());
    }
}

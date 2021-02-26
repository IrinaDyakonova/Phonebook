package telephonemanager.demo;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void returnHomePage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Home - Phonebook")));
    }

    @Test
    public void returnLoginPage() throws Exception {
        this.mockMvc.perform(get("/signin")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Login - Phonebook")));
    }

    @Test
    public void ReturnRegisterPage() throws Exception {
        this.mockMvc.perform(get("/signup")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Register - Phonebook")));
    }

    @Test
    public void failedRegisterPage() throws Exception {
        mockMvc.perform(post("/do_register")
                .param("name", "user1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("You have not agreed the terms and conditions")));
    }

    @Test
    public void failedRegisterPage2() throws Exception {
        mockMvc.perform(post("/do_register").param("agreement", "true").param("name", "user1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Something went wrong")));
    }

    @Test
    public void returnPostRegister() throws Exception {
        mockMvc.perform(post("/do_register")
                .param("agreement", "true")
                .param("name", "user1")
                .param("email", "user1@gmail.com").param("password","psw1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Successfully registered.")));
    }

}
package com.voisovych.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.voisovych.library.model.User;
import com.voisovych.library.myexception.MyException;
import com.voisovych.library.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @InjectMocks
    UserController userController;

    @Test
    public void allUsersTest() throws Exception, MyException {
        List<User> list = new ArrayList<>();
        User user = new User("u1", "Robin", "Hood");
        list.add(user);
        given(userService.allUsers()).willReturn(list);
        mockMvc.perform(get("/allUsers")
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userNumber", is(user.getUserNumber())))
                .andExpect(jsonPath("$[0].firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(user.getLastName())));
        verify(userService, times(1)).allUsers();
    }

    @Test
    public void findUserTest() throws MyException, Exception {
        User user = new User("u1", "Robin", "Hood");
        when(userService.findUser("u1")).thenReturn(user);
        mockMvc.perform(get("/findUser")
                .param("userNumber", "u1"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":0,\"userNumber\":\"u1\",\"firstName\":\"Robin\"" +
                                                            ",\"lastName\":\"Hood\",\"books\":null}"))
                .andDo(print());
        Assert.assertEquals(user, userService.findUser("u1"));
        verify(userService, times(2)).findUser("u1");
    }

    @Test
    public void addUserTest() throws Exception, MyException {
        User user = new User("u1", "Robin", "Hood");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(user);
        mockMvc.perform(put("/addUser").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User has created Successfully!!!"));
        verify(userService, times(1)).addUser(user);
    }

    @Test
    public void editUserTest() throws Exception, MyException {
        User user = new User("u1", "Robin", "Hood");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(user);
        mockMvc.perform(post("/editUser").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User has edited Successfully!!!"));
        verify(userService, times(1)).editUser(user);
    }

    @Test
    public void deleteUserTest() throws Exception, MyException {
        mockMvc.perform(delete("/deleteUser")
                .param("userNumber", "u1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User has deleted Successfully!!!"));
        verify(userService, times(1)).deleteUser("u1");
    }
}
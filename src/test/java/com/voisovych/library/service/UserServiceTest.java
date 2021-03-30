package com.voisovych.library.service;

import com.voisovych.library.model.User;
import com.voisovych.library.myexception.MyException;
import com.voisovych.library.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void allUsersTest() throws MyException {
        User userOne = new User("u1", "Robin", "Hood");
        User userTwo = new User("u2", "Small", "Jon");
        List<User> list = new ArrayList<>();
        list.add(userOne);
        list.add(userTwo);

        when(userRepository.findAll()).thenReturn(list);
        Assert.assertEquals(list, userService.allUsers());
        verify(userRepository,times(1)).findAll();
    }

    @Test(expected = MyException.class)
    public void allUsersWithExceptionTest() throws MyException {
        List<User> list = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(list);
        userService.allUsers();
    }

    @Test
    public void findUserTest() throws MyException {
        User user = new User("u1", "Robin", "Hood");
        when(userRepository.findByUserNumber("u1")).thenReturn(Optional.ofNullable(user));
        Assert.assertEquals(user, userService.findUser("u1"));
    }

    @Test(expected = MyException.class)
    public void findUserWithException() throws MyException {
        when(userRepository.findByUserNumber("u1")).thenReturn(Optional.empty());
        userService.findUser("u1");
    }

    @Test
    public void deleteUserTest() throws MyException {
        User user = new User("u1", "Robin", "Hood");
        when(userRepository.findByUserNumber("u1")).thenReturn(Optional.ofNullable(user));
        userService.deleteUser("u1");
        verify(userRepository, times(1)).delete(user);
    }

    @Test(expected = MyException.class)
    public void deleteUserWithExceptionTest() throws MyException {
        when(userRepository.findByUserNumber("u1")).thenReturn(Optional.empty());
        userService.deleteUser("u1");
    }

    @Test
    public void addUserTest() throws MyException {
        User userOne = new User("u1", "Robin", "Hood");
        User userTwo = new User("u1", "Robin", "Hood");
        when(userRepository.findByUserNumber(userOne.getUserNumber())).thenReturn(Optional.empty());
        when(userRepository.save(userOne)).thenReturn(userOne);
        userService.addUser(userOne);
        Assert.assertEquals(userTwo, userRepository.save(userOne));
        verify(userRepository, times(2)).save(userOne);
        verify(userRepository, times(1)).findByUserNumber("u1");
    }

    @Test(expected = MyException.class)
    public void addUserWithExceptionTest() throws MyException {
        User user = new User("u1", "Robin", "Hood");
        when(userRepository.findByUserNumber(user.getUserNumber())).thenReturn(Optional.ofNullable(user));
        userService.addUser(user);
    }

    @Test
    public void editUserTest() throws MyException {
        User userOne = new User("u1", "Robin", "Hood");
        userOne.setId(1l);
        User userTwo = new User("u1", "Robin", "Hood");

        when(userRepository.findByUserNumber(userOne.getUserNumber())).thenReturn(Optional.ofNullable(userOne));
        userService.editUser(userTwo);
        Assert.assertEquals(userTwo, userOne);
        verify(userRepository, times(1)).findByUserNumber("u1");
    }

    @Test(expected = MyException.class)
    public void editUserWithException() throws MyException {
        User user = new User("u1", "Robin", "Hood");
        when(userRepository.findByUserNumber("u1")).thenReturn(Optional.empty());
        userService.editUser(user);
    }
}
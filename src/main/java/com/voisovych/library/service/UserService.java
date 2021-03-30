package com.voisovych.library.service;

import com.voisovych.library.model.Book;
import com.voisovych.library.model.User;
import com.voisovych.library.myexception.MyException;
import com.voisovych.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() throws MyException {
        List<User> list = userRepository.findAll();
        if(list.isEmpty()) {
            throw new MyException("List is empty");
        }
        return list;
    }

    public User findUser(String userNumber) throws MyException {
        Optional<User> optionalUser = userRepository.findByUserNumber(userNumber);
        User user = optionalUser.orElseThrow(() -> new MyException("User doesn't exists"));
        return user;
    }

    public void deleteUser(String userNumber) throws MyException {
        userRepository.delete(findUser(userNumber));
    }

    public void addUser(User user) throws MyException {
        if (userRepository.findByUserNumber(user.getUserNumber()).isEmpty()) {
            userRepository.save(user);
        }else throw new MyException("User with user number: " + user.getUserNumber() + " exists!!!!!");
    }

    public void editUser(User user) throws MyException {
        user.setId(findUser(user.getUserNumber()).getId());
        userRepository.save(user);
    }
}

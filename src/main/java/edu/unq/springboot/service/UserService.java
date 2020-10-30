package edu.unq.springboot.service;

import edu.unq.springboot.IntegrationTest.models.Job;
import edu.unq.springboot.IntegrationTest.models.User;
import edu.unq.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User usuario) {
        return userRepository.save(usuario);
    }

    public User addJob(Job trabajo, User usuario) {
        usuario.addJob(trabajo);
        return userRepository.save(usuario);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Boolean validateUser(String username, String password) {
        User usuario = userRepository.findByUsername(username);
        return usuario != null && password.equals(usuario.getPassword());
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

}

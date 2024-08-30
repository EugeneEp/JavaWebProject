package ru.eugene.JavaWebProject.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.eugene.JavaWebProject.models.CustomErrorsModel;
import ru.eugene.JavaWebProject.models.UserModel;
import ru.eugene.JavaWebProject.models.errors.Errors;
import ru.eugene.JavaWebProject.repositories.UserRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserModel> getUsers(int pageNum, int size) {
        Pageable page = PageRequest.of(pageNum, size);
        return userRepository.findAll(page);
    }

    public UserModel getUserById(String id){
        return userRepository.findById(id).orElse(null);
    }

    public UserModel createUser(UserModel user) throws NoSuchAlgorithmException {
        if (userRepository.findByEmail(user.getEmail()) != null) throw new CustomErrorsModel(Errors.ERR_USER_NOT_FOUND);
        user.setPasswordHash(this.getSHA256hash(user.getPasswordHash()));
        return userRepository.save(user);
    }

    public UserModel updateUser(String id, String username, String password) throws NoSuchAlgorithmException {
        UserModel user = this.getUserById(id);
        if (user == null) {
            throw new CustomErrorsModel(Errors.ERR_USER_NOT_FOUND);
        }

        boolean changes = false;

        if (username != null && !username.isEmpty()){
            user.setUsername(username);
            changes = true;
        }
        if (password != null && !password.isEmpty()){
            user.setPasswordHash(getSHA256hash(password));
            changes = true;
        }
        if (!changes) {
            return user;
        }

        return userRepository.save(user);
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    private String getSHA256hash(String value) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageDigest = md.digest(value.getBytes());
        return  Base64.getEncoder().encodeToString(messageDigest);
    }
}

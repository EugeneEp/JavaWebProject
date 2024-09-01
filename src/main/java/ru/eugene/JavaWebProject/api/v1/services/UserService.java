package ru.eugene.JavaWebProject.api.v1.services;

import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.eugene.JavaWebProject.api.v1.models.CustomErrorsModel;
import ru.eugene.JavaWebProject.api.v1.models.UserModel;
import ru.eugene.JavaWebProject.api.v1.enums.Errors;
import ru.eugene.JavaWebProject.api.v1.repositories.UserRepository;

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
        if (userRepository.findByEmail(user.getEmail()) != null) throw new CustomErrorsModel(Errors.ERR_USER_EXISTS);
        user.setPasswordHash(this.getSHA256hash(user.getPasswordHash()));
        return userRepository.save(user);
    }

    public UserModel updateUser(String id, String username, String password) throws NoSuchAlgorithmException {
        UserModel user = this.getUserById(id);
        if (user == null) {
            throw new CustomErrorsModel(Errors.ERR_USER_NOT_FOUND);
        }

        boolean changes = false;

        if (!StringUtils.isEmpty(username)){
            user.setUsername(username);
            changes = true;
        }
        if (!StringUtils.isEmpty(password)){
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

    // Метод для получения SHA256 хеша из переданной строки
    private String getSHA256hash(String value) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageDigest = md.digest(value.getBytes());
        return  Base64.getEncoder().encodeToString(messageDigest);
    }
}

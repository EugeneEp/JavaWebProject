package ru.eugene.JavaWebProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.eugene.JavaWebProject.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, String> {
    UserModel findByEmail(String email);
}

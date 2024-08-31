package ru.eugene.JavaWebProject.api.v1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.eugene.JavaWebProject.api.v1.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, String> {
    UserModel findByEmail(String email);
}

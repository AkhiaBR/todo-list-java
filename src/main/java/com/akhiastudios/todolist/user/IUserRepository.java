package com.akhiastudios.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserModel, UUID> { // define pra interface JpaRepository a entidade (userModel) e o tipo de chave primária (UUID)
    
}

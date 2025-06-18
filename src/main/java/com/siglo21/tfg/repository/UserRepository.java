package com.siglo21.tfg.repository;

import com.siglo21.tfg.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

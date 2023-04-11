package com.umapp.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umapp.core.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}

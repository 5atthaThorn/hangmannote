package com.hangman.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.hangman.Entities.Users;

public interface UsersRepository extends CrudRepository<Users, Integer> {
    
}

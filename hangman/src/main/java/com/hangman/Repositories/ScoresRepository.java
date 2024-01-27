package com.hangman.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.hangman.Entities.Scores;

public interface ScoresRepository extends CrudRepository<Scores, Integer>{
    
}
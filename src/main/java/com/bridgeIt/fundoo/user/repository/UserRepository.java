package com.bridgeIt.fundoo.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgeIt.fundoo.notes.model.Collaborator;
import com.bridgeIt.fundoo.user.model.User;

public interface UserRepository extends JpaRepository<User,Long> 
{
public Optional<User>findByEmailId(String emailId);

public Optional<User>findByUserId(long userId);

//public Optional<Collaborator>findByEmailId1(String emailId);
}

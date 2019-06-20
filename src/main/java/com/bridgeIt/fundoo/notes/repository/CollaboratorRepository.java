package com.bridgeIt.fundoo.notes.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgeIt.fundoo.notes.model.Collaborator;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator,Long>
{
 Optional<Collaborator>findByEmailId(String emailId);
 Optional<Collaborator>deleteByEmailId(String emailId);
 Optional<Collaborator> removeByEmailId(String emailId);
}

package com.bridgeIt.fundoo.notes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bridgeIt.fundoo.notes.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long>
{
	public List<Note> findByUserId(long userId);
    public Note findByNoteIdAndUserId(long noteId,long userId);
    public Note findByTitleAndDescription(String title,String description);
    public Note findByNoteId(long noteId);
}


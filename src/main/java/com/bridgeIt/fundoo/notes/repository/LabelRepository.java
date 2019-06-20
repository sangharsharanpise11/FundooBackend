package com.bridgeIt.fundoo.notes.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.bridgeIt.fundoo.notes.model.Label;
import com.bridgeIt.fundoo.notes.model.Note;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long>
{
  public Label findByLabelIdAndUserId(long labelId,long userId);
  public List<Label>findByUserId(long userId);
  public Optional<Label>findByUserIdAndLabelName(long userId,String labelName);
  
  @Query(value="select * from Note_Table where note_note_id=:noteId",nativeQuery=true)
  public List<Label>findAllLabelsByNotes(@Param("noteId")long notes);
  public List<Note>findAllNotesByLabelId(long labelId);
}

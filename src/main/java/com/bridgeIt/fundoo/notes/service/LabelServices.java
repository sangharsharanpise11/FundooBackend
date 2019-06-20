package com.bridgeIt.fundoo.notes.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.springframework.stereotype.Service;
import com.bridgeIt.fundoo.notes.dto.LabelDto;
import com.bridgeIt.fundoo.notes.dto.NoteDto;
import com.bridgeIt.fundoo.notes.model.Label;
import com.bridgeIt.fundoo.response.Response;
@Service
public interface LabelServices 
{
   public Response createLabel(LabelDto labeldto,String token) throws IllegalArgumentException, UnsupportedEncodingException;
   public Response deleteLabel(long labelId,String token) throws IllegalArgumentException, UnsupportedEncodingException;
   public Response updateLabel(long labelId,String token,LabelDto labelDto) throws IllegalArgumentException, UnsupportedEncodingException;
   public List<Label>getAllLabel(String token) throws IllegalArgumentException, UnsupportedEncodingException;
   public Response addLabelToNote(long labelId,long noteId,String token) throws IllegalArgumentException, UnsupportedEncodingException;
   public List<Label>getLabelsOfNote(String token,long noteId) throws IllegalArgumentException, UnsupportedEncodingException;
   public Response removeLabelfromNote(String token,long labelId,long noteId) throws IllegalArgumentException, UnsupportedEncodingException;
   public List<NoteDto>getNotesOfLabel(String token,long labelId) throws IllegalArgumentException, UnsupportedEncodingException;
   
}

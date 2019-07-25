package com.bridgeIt.fundoo.notes.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bridgeIt.fundoo.notes.dto.CollaboratorDto;
import com.bridgeIt.fundoo.notes.dto.NoteDto;
import com.bridgeIt.fundoo.notes.model.Note;
import com.bridgeIt.fundoo.response.Response;
import com.bridgeIt.fundoo.user.model.User;

@Component
@Service
public interface NoteServices
{
	Response createNote(NoteDto noteDto, String token) throws IllegalArgumentException, UnsupportedEncodingException, IOException, Exception;

	Response updateNote(String token, NoteDto noteDto, long noteId)throws IllegalArgumentException, UnsupportedEncodingException, IOException, Exception;

	Response deleteNote(long noteId, String token) throws IllegalArgumentException, UnsupportedEncodingException, Exception;

	public List<Note> getAllNotes(String token) throws IllegalArgumentException, UnsupportedEncodingException;

	Response pinAndUnPin(String token, long noteId) throws IllegalArgumentException, UnsupportedEncodingException;

	Response archiveAndUnArchive(String token, long noteId)throws IllegalArgumentException, UnsupportedEncodingException;

	public Response trashAndUnTrash(String token, long noteId)throws IllegalArgumentException, UnsupportedEncodingException;

	public Response deletePermanently(String token, long noteId)throws IllegalArgumentException, UnsupportedEncodingException, Exception;

	public List<Note> getArchiveNotes(String token) throws IllegalArgumentException, UnsupportedEncodingException;

	public List<Note> getTrashNotes(String token) throws IllegalArgumentException, UnsupportedEncodingException;

	public List<Note> getPinnedNotes(String token) throws IllegalArgumentException, UnsupportedEncodingException;
	
	public List<Note> getRemainderNotes(String token) throws IllegalArgumentException, UnsupportedEncodingException;
	
	Response setColorToNote(String token, String color, long noteId)throws IllegalArgumentException, UnsupportedEncodingException;
    
	Note findNote(String title,String description) throws IllegalArgumentException, UnsupportedEncodingException;

	Response setRemainder(long noteId, String token, String remainder) throws UnsupportedEncodingException, IllegalArgumentException, ParseException;

	Response deleteRemainder(long noteId, String token) throws IllegalArgumentException, UnsupportedEncodingException;
 
	Response addCollaborator(String token,long noteId,String emailId) throws IllegalArgumentException, UnsupportedEncodingException;
	
	Response deleteCollaborator(String token,long noteId,String emailId) throws IllegalArgumentException, UnsupportedEncodingException;
	
	public  List<User> getCollaboratedUser(String token, long noteId) throws IllegalArgumentException, UnsupportedEncodingException;

	public  List<Note> getCollaboratedNotes(String token) throws IllegalArgumentException, UnsupportedEncodingException;

	Note getRedisUserData(long id);
	
    //public Response addImage(MultipartFile imageFile,long noteId,String token) throws IOException;
}

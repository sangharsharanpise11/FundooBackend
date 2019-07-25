package com.bridgeIt.fundoo.notes.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgeIt.fundoo.notes.dto.NoteDto;
import com.bridgeIt.fundoo.notes.model.Note;
import com.bridgeIt.fundoo.notes.service.NoteServices;
import com.bridgeIt.fundoo.response.Response;
import com.bridgeIt.fundoo.user.model.User;

 
@RestController
@RequestMapping("/user/note")
@PropertySource("message.properties")
@CrossOrigin(allowedHeaders = "*" ,origins = "*")
public class NoteController
{
	Logger logger = LoggerFactory.getLogger(NoteController.class);

	@Autowired
    public NoteServices noteService;

@PostMapping("/create")
public ResponseEntity<Response>createNote(@RequestBody NoteDto noteDto,@RequestHeader String token) throws Exception 
{
	logger.info(noteDto.toString());
	Response response=noteService.createNote(noteDto, token);
	return new ResponseEntity<>(response,HttpStatus.OK);
}

@PutMapping("/update")
public ResponseEntity<Response>updateNote(@RequestHeader String token,@RequestBody NoteDto noteDto,@RequestParam long noteId) throws Exception
{
	logger.info(noteDto.toString());
	Response response=noteService.updateNote(token, noteDto, noteId);
	return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
}

@PutMapping("/delete")
public ResponseEntity<Response>deleteNote(@RequestParam long noteId,@RequestHeader String token) throws Exception
{
	Response response=noteService.deleteNote(noteId,token);
	return new ResponseEntity<>(response,HttpStatus.OK);
}

@GetMapping("/getAllNotes")
public List<Note>getAllNotes(@RequestHeader String token) throws IllegalArgumentException, UnsupportedEncodingException
{
	List<Note>listNotes=noteService.getAllNotes(token);
	return listNotes;
}

@PutMapping("/pin")
public ResponseEntity<Response>pinNote(@RequestHeader String token,@RequestParam long noteId) throws IllegalArgumentException, UnsupportedEncodingException
{
	Response response=noteService.pinAndUnPin(token, noteId);
	return new ResponseEntity<>(response,HttpStatus.OK);	
}

@PutMapping("/archive")
public ResponseEntity<Response>archiveNote(@RequestHeader String token,@RequestParam long noteId) throws IllegalArgumentException, UnsupportedEncodingException
{
	Response response=noteService.archiveAndUnArchive(token, noteId);
	return new ResponseEntity<>(response,HttpStatus.OK);
}

@PutMapping("/trash")
public ResponseEntity<Response>trashNote(@RequestHeader String token,@RequestParam long noteId) throws IllegalArgumentException, UnsupportedEncodingException
{
	Response response=noteService.trashAndUnTrash(token, noteId);
	return new ResponseEntity<>(response,HttpStatus.OK);
}

@DeleteMapping("/deletePermenently")
public ResponseEntity<Response>deletePermenently(@RequestHeader String token,@RequestParam long noteId) throws Exception
{
	Response response=noteService.deletePermanently(token, noteId);
	return new ResponseEntity<>(response,HttpStatus.OK);
}

@GetMapping("/getArchiveNotes")
public List<Note>getArchiveNotes(@RequestHeader String token) throws IllegalArgumentException, UnsupportedEncodingException
{
	List<Note>note=noteService.getArchiveNotes(token);
	return note;
}         

@GetMapping("/getTrashNotes")
public List<Note>getTrashNotes(@RequestHeader String token) throws IllegalArgumentException, UnsupportedEncodingException
{
	List<Note>note=noteService.getTrashNotes(token);
	return note;
}

@GetMapping("/getPinnedNotes")
public List<Note>getPinnedNotes(@RequestHeader String token) throws IllegalArgumentException, UnsupportedEncodingException
{
	List<Note>note=noteService.getPinnedNotes(token);
	return note;
}

@GetMapping("/getRemainderNotes")
public List<Note>geReminderNotes(@RequestHeader String token) throws IllegalArgumentException, UnsupportedEncodingException
{
	List<Note>note=noteService.getRemainderNotes(token);
	return note;
}

@PutMapping("/setColor")
public ResponseEntity<Response>setColorToNote(@RequestHeader String token,@RequestParam String color,@RequestParam long noteId) throws IllegalArgumentException, UnsupportedEncodingException
{
	Response response=noteService.setColorToNote(token,color,noteId);
	return new ResponseEntity<>(response,HttpStatus.OK);	
}


@PostMapping("/findNote")
public Note findNote(@RequestHeader String title,@RequestHeader String description) throws IllegalArgumentException, UnsupportedEncodingException
{
	Note note=noteService.findNote(title,description);
	return note;
}

@PostMapping("/reminder")
public ResponseEntity<Response>setRemainder(@RequestParam long noteId,@RequestHeader String token,@RequestParam String remainder) throws UnsupportedEncodingException, IllegalArgumentException, ParseException
{
	Response response=noteService.setRemainder(noteId,token,remainder);
	return new ResponseEntity<>(response,HttpStatus.OK);
}

@PostMapping("/deleteRemainder")
public ResponseEntity<Response>deleteRemainder(@RequestParam long noteId,@RequestHeader String token) throws IllegalArgumentException, UnsupportedEncodingException
{
  Response response=noteService.deleteRemainder(noteId,token);	
  return new ResponseEntity<Response>(response,HttpStatus.OK);
}



@PostMapping("/addCollaborator") 
public ResponseEntity<Response>addCollaborator(@RequestHeader String token,@RequestParam long noteId,@RequestParam String emailId) throws IllegalArgumentException, UnsupportedEncodingException
{
	Response response=noteService.addCollaborator(token, noteId,emailId);
	return new ResponseEntity<Response>(response,HttpStatus.OK);
}

@PostMapping("/deleteCollaborator")
public ResponseEntity<Response>deleteCollaborator(@RequestHeader String token,@RequestParam long noteId,@RequestParam String emailId) throws IllegalArgumentException, UnsupportedEncodingException
{
	Response response=noteService.deleteCollaborator(token,noteId,emailId);
	return new ResponseEntity<Response>(response,HttpStatus.OK);
}
@GetMapping("/getAllCollaboratedUser")
public List<User> getAllCollaboratedUser(@RequestHeader String token, @RequestParam long noteId) throws IllegalArgumentException, UnsupportedEncodingException 
{
	List<User> response = noteService.getCollaboratedUser(token, noteId);
	return response;
}

@GetMapping("/getAllCollaboratedNotes")
public List<Note> getAllCollaboratedNotes(@RequestHeader String token) throws IllegalArgumentException, UnsupportedEncodingException
{
	List<Note> notes = noteService.getCollaboratedNotes(token);
	return notes;
}
@GetMapping("redisToken")					
public Note redisData(@RequestParam long noteId) {
   return noteService.getRedisUserData(noteId);
}
}
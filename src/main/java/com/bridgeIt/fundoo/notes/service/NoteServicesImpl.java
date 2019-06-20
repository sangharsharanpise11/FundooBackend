package com.bridgeIt.fundoo.notes.service;

import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bridgeIt.fundoo.exception.NoteException;
import com.bridgeIt.fundoo.exception.UserException;
import com.bridgeIt.fundoo.notes.dto.CollaboratorDto;
import com.bridgeIt.fundoo.notes.dto.NoteDto;
import com.bridgeIt.fundoo.notes.model.Collaborator;
import com.bridgeIt.fundoo.notes.model.Note;
import com.bridgeIt.fundoo.notes.repository.CollaboratorRepository;
import com.bridgeIt.fundoo.notes.repository.NoteRepository;
import com.bridgeIt.fundoo.response.Response;
import com.bridgeIt.fundoo.user.model.EmailId;
import com.bridgeIt.fundoo.user.model.User;
import com.bridgeIt.fundoo.user.repository.UserRepository;
import com.bridgeIt.fundoo.util.ResponseStatus;
//import com.bridgeIt.fundoo.util.ResponseStatus;
import com.bridgeIt.fundoo.util.TokenGenerators;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

@Service("noteService")
@PropertySource("message.properties")
public class NoteServicesImpl implements NoteServices {
	Logger logger = LoggerFactory.getLogger(NoteServices.class);

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public NoteRepository noteRepository;

	@Autowired
	public Environment environment;

	@Autowired
	public NoteServices noteService;

	@Autowired
	public ModelMapper modelMapper;

	@Autowired
	public TokenGenerators tokengenerators;

	@Autowired
	public CollaboratorRepository collaboratorRepository;

	@Override
	public Response createNote(NoteDto noteDto, String token)
			throws IllegalArgumentException, UnsupportedEncodingException {
		long id = tokengenerators.decodeToken(token);
		logger.info(noteDto.toString());

		if (noteDto.getTitle().isEmpty() && noteDto.getDescription().isEmpty()) {
			throw new NoteException("Title and Description are empty...", -5);
		}

		Note note = modelMapper.map(noteDto, Note.class);

		Optional<User> user = userRepository.findById(id);

		note.setUserId(id);
		note.setCreated(LocalDateTime.now());
		note.setModified(LocalDateTime.now());
		user.get().getNotes().add(note);

		noteRepository.save(note);
		userRepository.save(user.get());
		Response response = com.bridgeIt.fundoo.util.ResponseStatus.statusInformation(
				environment.getProperty("status.notes.createdSuccessfull"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		System.out.println("note is created");
		return response;
	}

	@Override
	public Response updateNote(String token, NoteDto noteDto, long noteId)
			throws IllegalArgumentException, UnsupportedEncodingException {
		if (noteDto.getTitle().isEmpty() && noteDto.getDescription().isEmpty()) {
			throw new NoteException("Title and Description are empty...", -5);
		}

		long id = tokengenerators.decodeToken(token);
		Note note = noteRepository.findByNoteIdAndUserId(noteId, id);
		note.setTitle(noteDto.getTitle());
		note.setDescription(noteDto.getDescription());
		note.setModified(LocalDateTime.now());
		noteRepository.save(note);
		Response response = com.bridgeIt.fundoo.util.ResponseStatus.statusInformation(
				environment.getProperty("status.notes.updated"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	@Override
	public Response deleteNote(long noteId, String token)
			throws IllegalArgumentException, UnsupportedEncodingException {
		long id = tokengenerators.decodeToken(token);
		Note note = noteRepository.findByNoteIdAndUserId(noteId, id);

		if (note == null) {
			throw new NoteException("Invalid Input...", -5);
		}
		if (note.isTrash() == false) {
			note.setTrash(true);
			note.setModified(LocalDateTime.now());
			noteRepository.save(note);
			Response response = com.bridgeIt.fundoo.util.ResponseStatus.statusInformation(
					environment.getProperty("status.note.trashed"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
		Response response = com.bridgeIt.fundoo.util.ResponseStatus.statusInformation(
				environment.getProperty("status.note.trashError"),
				Integer.parseInt(environment.getProperty("status.note.errorCode")));
		return response;
	}

	@Override
	public List<Note> getAllNotes(String token) throws IllegalArgumentException, UnsupportedEncodingException {
		long id = tokengenerators.decodeToken(token);
		List<Note> notes = noteRepository.findByUserId(id);
//         List<NoteDto>listNotes=new ArrayList<>();//
//         
//         for(Note userNotes: notes)
//         {
//        	 NoteDto notesDto = modelMapper.map(userNotes, NoteDto.class);
// 			if(userNotes.isArchive() == false && userNotes.isTrash() == false) 
// 			{
// 				listNotes.add(notesDto);
// 			}
//         }
		return notes;
	}

	@Override
	public Response pinAndUnPin(String token, long noteId)
			throws IllegalArgumentException, UnsupportedEncodingException {
		long id = tokengenerators.decodeToken(token);
		Note note = noteRepository.findByNoteIdAndUserId(noteId, id);
		if (note == null) {
			throw new NoteException("invalid input..", -5);
		}

		if (note.isPin() == false) {
			note.setPin(true);
			noteRepository.save(note);
			Response response = com.bridgeIt.fundoo.util.ResponseStatus.statusInformation(
					environment.getProperty("status.note.pinned"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		} else {
			note.setPin(false);
			noteRepository.save(note);
			Response response = com.bridgeIt.fundoo.util.ResponseStatus.statusInformation(
					environment.getProperty("status.note.unpinned"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}

	}

	@Override
	public Response archiveAndUnArchive(String token, long noteId)
			throws IllegalArgumentException, UnsupportedEncodingException {
		long id = tokengenerators.decodeToken(token);
		Note note = noteRepository.findByNoteIdAndUserId(noteId, id);
		if (note == null) {
			throw new NoteException("Invalid input...", -5);
		}
		if (note.isArchive() == false) {
			note.setArchive(true);
			noteRepository.save(note);
			Response response = com.bridgeIt.fundoo.util.ResponseStatus.statusInformation(
					environment.getProperty("status.note.archieved"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		} else {
			note.setArchive(false);
			noteRepository.save(note);
			Response response = com.bridgeIt.fundoo.util.ResponseStatus.statusInformation(
					environment.getProperty("status.note.unarchieved"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}

	}

	@Override
	public Response trashAndUnTrash(String token, long noteId)
			throws IllegalArgumentException, UnsupportedEncodingException {
		long id = tokengenerators.decodeToken(token);
		Note note = noteRepository.findByNoteIdAndUserId(noteId, id);
		if (note.isTrash() == false) {
			note.setTrash(true);
			noteRepository.save(note);
			Response response = com.bridgeIt.fundoo.util.ResponseStatus.statusInformation(
					environment.getProperty("status.note.trashed"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		} else {
			note.setTrash(false);
			noteRepository.save(note);
			Response response = com.bridgeIt.fundoo.util.ResponseStatus.statusInformation(
					environment.getProperty("status.note.untrashed"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}

	}

	@Override
	public Response deletePermanently(String token, long noteId)
			throws IllegalArgumentException, UnsupportedEncodingException {
		long id = tokengenerators.decodeToken(token);
		Note note = noteRepository.findByNoteIdAndUserId(noteId, id);
		if (note.isTrash() == true) {
			noteRepository.delete(note);
			Response response = com.bridgeIt.fundoo.util.ResponseStatus.statusInformation(
					environment.getProperty("status.note.deleted"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		} else {
			Response response = com.bridgeIt.fundoo.util.ResponseStatus.statusInformation(
					environment.getProperty("status.note.notdeleted"),
					Integer.parseInt(environment.getProperty("status.note.errorCode")));
			return response;
		}

	}

	@Override
	public List<Note> getArchiveNotes(String token) throws IllegalArgumentException, UnsupportedEncodingException {
		long id = tokengenerators.decodeToken(token);
		List<Note> note = noteRepository.findByUserId(id);
		List<Note> listNotes = new ArrayList<>();
		for (Note userNotes : note) {
			Note note1 = modelMapper.map(userNotes, Note.class);
			if (userNotes.isArchive() == true) {
				listNotes.add(note1);
			}
		}
		return listNotes;
	}

	@Override
	public List<Note> getPinnedNotes(String token) throws IllegalArgumentException, UnsupportedEncodingException {
		long id = tokengenerators.decodeToken(token);
		List<Note> note = noteRepository.findByUserId(id);
		List<Note> listNotes = new ArrayList<>();
		for (Note userNotes : note) {
			Note note1 = modelMapper.map(userNotes, Note.class);
			if (userNotes.isPin() == true) {
				listNotes.add(note1);
			}
		}
		return listNotes;
	}

	@Override
	public List<Note> getTrashNotes(String token) throws IllegalArgumentException, UnsupportedEncodingException {
		long id = tokengenerators.decodeToken(token);
		List<Note> note = noteRepository.findByUserId(id);
		List<Note> listNotes = new ArrayList<>();
		for (Note userNotes : note) {
			Note note1 = modelMapper.map(userNotes, Note.class);
			if (userNotes.isTrash() == true) {
				listNotes.add(note1);
			}
		}
		return listNotes;
	}

	@Override
	public Response setColorToNote(String token, String color, long noteId)
			throws IllegalArgumentException, UnsupportedEncodingException {
		System.out.println("in setcolor************************************************************");
		System.out.println("****************************information:" + token + "" + color + "" + noteId);
		long userId = tokengenerators.decodeToken(token);
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new UserException("user is not present", -3);
		}
		Note noteAvailable = noteRepository.findByNoteIdAndUserId(noteId, userId);
		if (noteAvailable == null) {
			throw new NoteException("Note Not Exist", -5);
		}

		noteAvailable.setColor(color);
		noteAvailable.setCreated(LocalDateTime.now());
		noteAvailable.setModified(LocalDateTime.now());
		noteRepository.save(noteAvailable);

		Response response = ResponseStatus.statusInformation(environment.getProperty("status.setColor.success"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	@Override
	public Note findNote(String title, String description)
			throws IllegalArgumentException, UnsupportedEncodingException {
		Note note = noteRepository.findByTitleAndDescription(title, description);
		System.out.println(note);
		if (note == null) {
			System.out.println("not not present");
			throw new NoteException("Note Not Exist", -5);
		}
		System.out.println("note is present");
		return note;
	}

	@Override
	public Response setRemainder(long noteId, String token, String remainder)
			throws IllegalArgumentException, UnsupportedEncodingException {

		long userid = tokengenerators.decodeToken(token);
		Optional<User> user = userRepository.findById(userid);

		if (!user.isPresent()) {
			throw new UserException("user is not present", -3);
		}

		Note note = noteRepository.findByNoteId(noteId);

		note.setRemainder(remainder);
		noteRepository.save(note);

		Response response = ResponseStatus.statusInformation(environment.getProperty("status.remainder.success"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	@Override
	public Response deleteRemainder(long noteId, String token)
			throws IllegalArgumentException, UnsupportedEncodingException {
		long userId = tokengenerators.decodeToken(token);
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new UserException("user is not present", -3);
		}
		Note note = noteRepository.findByNoteIdAndUserId(noteId, userId);
		if (note == null) {
			throw new NoteException("note not exist", -5);
		}

		note.setRemainder(null);
		noteRepository.save(note);

		Response response = ResponseStatus.statusInformation(environment.getProperty("status.remainderdelete.success"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	@Override
	public Response addCollaborator(String token, long noteId, CollaboratorDto collaboratorDto)
			throws IllegalArgumentException, UnsupportedEncodingException {
		long userId = tokengenerators.decodeToken(token);
		Note note = noteRepository.findByNoteIdAndUserId(noteId, userId);
		if (note == null) {
			throw new NoteException("Note not exist", -5);
		}

		Optional<Collaborator> userToCollaborate = collaboratorRepository.findByEmailId(collaboratorDto.getEmailId());
		if (userToCollaborate.isPresent()) {
			throw new UserException(environment.getProperty("status.collaborator.failure"),
					Integer.parseInt(environment.getProperty("status.success.code")));
		}

		Collaborator collaborator = modelMapper.map(collaboratorDto, Collaborator.class);
//        collaborator.setEmailId(collaboratorDto.getEmailId()); 
//        collaborator.setCreatedDate(LocalDateTime.now());
//        collaborator.setNoteId(noteId);
//        collaborator.setUserId(userId);
		collaboratorRepository.save(collaborator);
		noteRepository.save(note);

		Response response = ResponseStatus.statusInformation(environment.getProperty("status.collaborator.success"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	@Override
	public Response deleteCollaborator(String token, long noteId, CollaboratorDto collaboratorDto)
			throws IllegalArgumentException, UnsupportedEncodingException {
		long userId = tokengenerators.decodeToken(token);
		Note note = noteRepository.findByNoteIdAndUserId(noteId, userId);
		if (note == null) {
			throw new NoteException("Note not exist", -5);
		}

		Optional<Collaborator> deleteCollaborate = collaboratorRepository.findByEmailId(collaboratorDto.getEmailId());
		if (deleteCollaborate.isPresent()) {
			collaboratorRepository.delete(deleteCollaborate.get());
			collaboratorRepository.removeByEmailId(collaboratorDto.getEmailId());

			Response response = ResponseStatus.statusInformation(
					environment.getProperty("status.delete.success.collaborator"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}

		Response response = ResponseStatus.statusInformation(environment.getProperty("status.collaborator.failure"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	@Override
	public List<Note>getRemainderNotes(String token) throws IllegalArgumentException, UnsupportedEncodingException {
		long userId = tokengenerators.decodeToken(token);
		User user = userRepository.findById(userId).orElseThrow(() -> new UserException("Sorry! User not available",-3));
//      List<Note>reminderedNotes=user.getNotes().stream().filter(data ->(data.ge))
//		List<Note> reminderedNotes = user.getNotes().stream()
//		.filter(data -> (data.getReminder() != null && !data.getReminder().isEmpty()))
//		.collect(Collectors.toList());
//      return reminderedNotes;

		return null;
	}
}

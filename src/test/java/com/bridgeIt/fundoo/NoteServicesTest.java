package com.bridgeIt.fundoo;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.bridgeIt.fundoo.FundooAppApplication;
import com.bridgeIt.fundoo.notes.dto.NoteDto;
import com.bridgeIt.fundoo.notes.model.Note;
import com.bridgeIt.fundoo.notes.repository.NoteRepository;
import com.bridgeIt.fundoo.notes.service.NoteServices;
import com.bridgeIt.fundoo.response.Response;
import com.bridgeIt.fundoo.user.model.User;
import com.bridgeIt.fundoo.user.repository.UserRepository;
import com.bridgeIt.fundoo.user.service.UserService;
import com.bridgeIt.fundoo.util.TokenGenerators;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FundooAppApplication.class)
class NoteServicesTest {

	@InjectMocks
	NoteServices noteServices;
	
	@Mock
	TokenGenerators tokenGenerators;
	
	@Mock
	ModelMapper modelMapper;
	
    @Mock
    Response response;
    
	@Rule 
	public MockitoRule rule=MockitoJUnit.rule();
	
	@Test
	public void createNoteTest() throws IOException, Exception 
	{
		System.out.println("**********in create test");
		
		NoteDto noteDto=new NoteDto("createTest", "testing create note");
		Note note=new Note();
		note.setTitle("java");
		note.setDescription("java lang");
		note.setUserId(3);
        note.setNoteId(4);
        note.setPin(false);
    	note.setCreated(LocalDateTime.now());
        note.setModified(LocalDateTime.now());
        note.setRemainder(null);
        note.setTrash(false);
		note.setArchive(false);
		note.setColor("red");
	
		
		String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJJRCI6M30.fEFyWkVPtLeLNtbVhSWDhPDCdKGia2orWA2aZa9M6fg"; 
		 
	    Response response = noteServices.createNote(noteDto,token);
	  
	    Response desiredOutput = new Response("Note is successfully created", 21);
		 
	    assertEquals(desiredOutput,response);
	
		
//		when(tokenGenerators.decodeToken(Mockito.anyString())).thenReturn(1l);
//		when(modelMapper.map(noteDto,Note.class)).thenReturn(note);
//		when(noteRepository.save(note)).thenReturn(note);
//
//		System.out.println("note is in testing :"+note);
		
	
	}


}

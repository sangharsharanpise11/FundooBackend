package com.bridgeIt.fundoo.notes.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgeIt.fundoo.exception.LabelException;
import com.bridgeIt.fundoo.notes.dto.LabelDto;
import com.bridgeIt.fundoo.notes.dto.NoteDto;
import com.bridgeIt.fundoo.notes.model.Label;
import com.bridgeIt.fundoo.notes.model.Note;
import com.bridgeIt.fundoo.notes.repository.LabelRepository;
import com.bridgeIt.fundoo.notes.repository.NoteRepository;
import com.bridgeIt.fundoo.response.Response;
import com.bridgeIt.fundoo.user.model.User;
import com.bridgeIt.fundoo.user.repository.UserRepository;
import com.bridgeIt.fundoo.util.ResponseStatus;
import com.bridgeIt.fundoo.util.TokenGenerators;


@PropertySource("message.properties")
@Service
public class LabelServicesImpl implements LabelServices
{
   @Autowired
   public Environment environment;
   
   @Autowired
   public LabelRepository labelRepository;
   
   @Autowired
   public LabelServices labelServices;
    
   @Autowired
   public ModelMapper modelMapper;
   
   @Autowired
   public NoteRepository noteRepository;
   
   @Autowired
   public UserRepository userRepository;
   
   @Autowired
   public TokenGenerators tokenGenerators;
   
	@Override
	public Response createLabel(LabelDto labelDto, String token) throws IllegalArgumentException, UnsupportedEncodingException
	{
		long uid=tokenGenerators.decodeToken(token);
		Optional<User>user=userRepository.findById(uid);
		if(!user.isPresent())
		{
			System.out.println("user not present");
			throw new LabelException("Invalid Input...",-6);
		}
		if(labelDto.getLabelName().isEmpty())
		{
			throw new LabelException("Label has no name",-6);
		}
		Optional<Label>labelAvailable=labelRepository.findByUserIdAndLabelName(uid, labelDto.getLabelName());
		if(labelAvailable.isPresent())
		{
			throw new LabelException("Label is already exist",-6);
		}
		System.out.println("user present");
		Label label=modelMapper.map(labelDto,Label.class);
//		label.setLabelName(labelDto.getLabelName());
    	label.setUserId(uid);
		label.setCreatedDate(LocalDateTime.now());
		label.setModifiedDate(LocalDateTime.now());
		labelRepository.save(label);
		user.get().getLabel().add(label);
		userRepository.save(user.get());
		Response response=ResponseStatus.statusInformation(environment.getProperty("status.label.created"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	@Override
	public Response deleteLabel(long labelId, String token) throws IllegalArgumentException, UnsupportedEncodingException 
	{
		 long uid=tokenGenerators.decodeToken(token);
         Optional<User>userAvailable=userRepository.findById(uid);
         if(!userAvailable.isPresent())
         {
        	throw new LabelException("Invalid Input",-6);
         }
         Label label=labelRepository.findByLabelIdAndUserId(labelId, uid);
         if(label==null)
         {
        	 throw new LabelException("Invalid Input",-6);
         }
         labelRepository.delete(label);
         Response response=ResponseStatus.statusInformation(environment.getProperty("status.label.deleted"),
        		 Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	@Override
	public Response updateLabel(long labelId, String token, LabelDto labelDto) throws IllegalArgumentException, UnsupportedEncodingException 
	{
		System.out.println("label id:"+labelId);
		long uid=tokenGenerators.decodeToken(token);
		Optional<User>userAvailable=userRepository.findById(uid);
		if(!userAvailable.isPresent())
		{
			throw new LabelException("Invalid Input",-6);
		}
//        Optional<Label>labelAvailable=labelRepository.findByUserIdAndLabelName(uid, labelDto.getLabelName());
//        if(!labelAvailable.isPresent())
//        {
//        	throw new LabelException("Label is not exist", -6);
//        }
        Label label=labelRepository.findByLabelIdAndUserId(labelId, uid);
        if(label==null)
        {
        	throw new LabelException("Invalid Input",-6);
        }
        //Label labelObj=modelMapper.map(labelDto,Label.class);
        label.setLabelName(labelDto.getLabelName());
        label.setModifiedDate(LocalDateTime.now());
        labelRepository.save(label);
        
        Response response=ResponseStatus.statusInformation(environment.getProperty("status.label.updated"),
        		Integer.parseInt(environment.getProperty("status.success.code"))); 
        return response;
	}

	@Override
	public List<Label> getAllLabel(String token) throws IllegalArgumentException, UnsupportedEncodingException 
	{
		long uid=tokenGenerators.decodeToken(token);
		Optional<User>userAvailable=userRepository.findById(uid);
        if(!userAvailable.isPresent())
        {
        	throw new LabelException("Invalid Input",-6);
        }
        List<Label>labels=labelRepository.findByUserId(uid);
//        List<Label>listLabel=new ArrayList<>();
//        for(Label noteLabel:labels)
//        {
//        	Label label = modelMapper.map(noteLabel, LabelDto.class);
//        	listLabel.add(label);
//        }
		return labels;
	}

	@Override
	public Response addLabelToNote(long labelId, long noteId, String token) throws IllegalArgumentException, UnsupportedEncodingException 
	{
		long uid=tokenGenerators.decodeToken(token);
        Optional<User>userAvailable= userRepository.findByUserId(uid);
        if(!userAvailable.isPresent())
        {
        	throw new LabelException("Invalid Input",-6);
        }
        Label label=labelRepository.findByLabelIdAndUserId(labelId,uid);
        if(label==null)
        {
        	throw new LabelException("Invalid input", -6);
        }
        Note note=noteRepository.findByNoteIdAndUserId(noteId, uid);
        if(note==null)
        {
        	throw new LabelException("No such note exist", -6);
        }
//        label.setModifiedDate(LocalDateTime.now());
//        label.getNotes().add(note);
        if (note.getListLabel().contains(label)) 
        {
			throw new LabelException("Label already exist", -6);
		}
        note.getListLabel().add(label);
        note.setModified(LocalDateTime.now());
//        labelRepository.save(label);
        noteRepository.save(note);
        
        Response response=ResponseStatus.statusInformation(environment.getProperty("status.label.addedtonote"),
        		Integer.parseInt(environment.getProperty("status.success.code")));

		return response;
	}

	@Override
	public List<Label> getLabelsOfNote(String token, long noteId) throws IllegalArgumentException, UnsupportedEncodingException 
	{
		long  uid=tokenGenerators.decodeToken(token);
		Optional<User>user=userRepository.findByUserId(uid);
		if(!user.isPresent())
		{
			throw new LabelException("User does not exist",-6);
		}
        Note note=noteRepository.findByNoteIdAndUserId(noteId,uid);
        if(note==null)
        {
        	throw new LabelException("Note does not exist", -6);
        }
        List<Label> label = note.getListLabel();
//        List<LabelDto>labelList=new ArrayList<>();
//        for(Label noteLabels:label)
//        {
//        	LabelDto labelDto=modelMapper.map(noteLabels,LabelDto.class);
//        	labelList.add(labelDto);
//        }
  
		return label;
	}

	@Override
	public Response removeLabelfromNote(String token, long labelId, long noteId) throws IllegalArgumentException, UnsupportedEncodingException 
	{
		long uid=tokenGenerators.decodeToken(token);
        Optional<User>user=userRepository.findByUserId(uid);		
        if(!user.isPresent())
		{
			throw new LabelException("User does not exist",-6);
		}
        Note note=noteRepository.findByNoteIdAndUserId(noteId, uid);
        if(note==null)
        {
        	throw new LabelException("no such a Note exist", -6);
        }
        Label label=labelRepository.findByLabelIdAndUserId(labelId, uid);
        if(label==null)
        {
        	throw new LabelException("no such a Label exist", -6);
        }
        
        //label.setModifiedDate(LocalDateTime.now());
        note.getListLabel().remove(label);
        note.setModified(LocalDateTime.now());
        //labelRepository.save(label);
        noteRepository.save(note);
        
        Response response=ResponseStatus.statusInformation(environment.getProperty("status.label.removedfromnote"), 
        		Integer.parseInt(environment.getProperty("status.success.code")));
        return response;
	}

	@Override
	public List<NoteDto> getNotesOfLabel(String token, long labelId) throws IllegalArgumentException, UnsupportedEncodingException
	{
		long uid=tokenGenerators.decodeToken(token);
        Optional<User>user=userRepository.findByUserId(uid);		
        if(!user.isPresent())
		{
			throw new LabelException("User does not exist",-6);
		}
        Optional<Label>label=labelRepository.findById(uid);
       if(!label.isPresent())
       {
    	   throw new LabelException("No Label exist", -6);
       }
       List<Note>note=label.get().getNotes();
       List<NoteDto>noteList=new ArrayList<>();
       for(Note userNotes:note)
       {
    	   NoteDto noteDto=modelMapper.map(userNotes,NoteDto.class);
    	   noteList.add(noteDto);
       }
		return noteList;
	}

	

}

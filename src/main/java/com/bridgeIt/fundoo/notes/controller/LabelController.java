package com.bridgeIt.fundoo.notes.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.bridgeIt.fundoo.notes.dto.LabelDto;
import com.bridgeIt.fundoo.notes.model.Label;
import com.bridgeIt.fundoo.notes.service.LabelServices;
import com.bridgeIt.fundoo.response.Response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = false) 
@RestController
@RequestMapping("/user/label")
@CrossOrigin(allowedHeaders = "*" ,origins = "*")
public class LabelController 
{
  @Autowired
  public LabelServices labelServices;
  
  @PostMapping("/createLabel")
  ResponseEntity<Response>createLabel(@RequestBody LabelDto labelDto,@RequestHeader String token) throws IllegalArgumentException, UnsupportedEncodingException
  {
    Response response=labelServices.createLabel(labelDto, token);
	  return new ResponseEntity<Response>(response,HttpStatus.ACCEPTED);
  }
  
  @DeleteMapping("/deleteLabel")
  ResponseEntity<Response>deleteLabel(@RequestParam long labelId,@RequestHeader String token) throws IllegalArgumentException, UnsupportedEncodingException
  {
	  Response response=labelServices.deleteLabel(labelId, token);
	return new ResponseEntity<Response>(response,HttpStatus.OK);
	  
  }
  
  @PutMapping("/updateLabel")
  ResponseEntity<Response>updateLabel(@RequestParam long labelId,@RequestHeader String token,@RequestBody LabelDto labelDto) throws IllegalArgumentException, UnsupportedEncodingException
  {
	  Response response=labelServices.updateLabel(labelId, token, labelDto);
	return new ResponseEntity<Response>(response,HttpStatus.OK);
	  
  }
  
  @GetMapping("/getlabel")
  List<Label>getLabel(@RequestHeader String token) throws IllegalArgumentException, UnsupportedEncodingException
  {
	  List<Label>labelList=labelServices.getAllLabel(token);
	  return labelList;
  }
  
  @PutMapping("/addLabelToNote")
  ResponseEntity<Response>addLabelToNote(@RequestParam long labelId,@RequestHeader String token,@RequestParam long noteId) throws IllegalArgumentException, UnsupportedEncodingException
  {
	  Response response=labelServices.addLabelToNote(labelId, noteId, token);
	  return new ResponseEntity<Response>(response,HttpStatus.OK);
  }
  
  @GetMapping("/getLabelOfNote")
  List<Label>getLabelOfNote(@RequestHeader String token,@RequestParam long noteId) throws IllegalArgumentException, UnsupportedEncodingException
  {
	  List<Label>labelList=labelServices.getLabelsOfNote(token, noteId);
	  return labelList;
  }
  
  @PutMapping("/removeFromNote")
  ResponseEntity<Response>removeFromNote(@RequestHeader String token,@RequestParam long labelId,@RequestParam long noteId) throws IllegalArgumentException, UnsupportedEncodingException
  {
	  Response response=labelServices.removeLabelfromNote(token, labelId, noteId);
	  return new ResponseEntity<Response>(response,HttpStatus.OK);
  }
  
}

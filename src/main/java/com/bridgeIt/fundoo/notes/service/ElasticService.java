package com.bridgeIt.fundoo.notes.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MultiMatchQuery;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bridgeIt.fundoo.notes.model.AutoCompleteResponse;
import com.bridgeIt.fundoo.notes.model.Note;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticService {

		private RestHighLevelClient client;
	
		private ObjectMapper objectMapper;

		@Autowired
		public ElasticService(RestHighLevelClient client, ObjectMapper objectMapper) {
			this.client = client;
			this.objectMapper = objectMapper;
		}
		
		 static String INDEX="notedata";
		 static String TYPE="note";

	public String createNote(Note note) throws Exception {

		System.out.println("in elastic");
		Map<String, Object> documentMapper = objectMapper.convertValue(note, Map.class);
	
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, String.valueOf(note.getNoteId()))
		.source(documentMapper); //.index(INDEX).type(TYPE);
	 
		System.out.println("****"+indexRequest);
		System.out.println("after request");
		IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
	
		System.out.println("****"+indexResponse);
		System.out.println("note is :"+indexResponse.getResult().name());
		return indexResponse.getResult().name();
		
	}

	public Note findById(String noteId) throws Exception {

		GetRequest getRequest = new GetRequest(INDEX, TYPE, noteId);
	
		GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		Map<String, Object> resultMap = getResponse.getSource();
	
		return objectMapper.convertValue(resultMap, Note.class);

	}

	private List<Note> getSearchResult(SearchResponse response) {

		SearchHit[] searchHit = response.getHits().getHits();
	
		List<Note> note = new ArrayList<>();
	
		if (searchHit.length > 0) {
	
		Arrays.stream(searchHit)
		.forEach(hit -> note.add(objectMapper.convertValue(hit.getSourceAsMap(), Note.class)));
		}
	
		return note;
		
//		private SearchResultDto getSuggestions(SearchResponse response) {
//			SearchResultDto dto = new SearchResultDto();
//			Suggest suggest = response.getSuggest();
//			Suggestion<Entry<Option>> suggestion = suggest.getSuggestion(SUGGESTION_NAME);
//			for(Entry<Option> entry: suggestion.getEntries()) {
//			      for (Option option: entry.getOptions()) {
//			        dto.add(option.getText().toString());
//			      }
//			}
//			return dto;
//		}
	}
    
   
    
	public List<Note> searchByTitle(String title) throws Exception {

		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	
		//fuzzey search
        //Page<Book> books = bookService.findByAuthor("Rambabu", new PageRequest(0, 10));
		
		QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("title", "*"+title+"*"));
	
		searchSourceBuilder.query(queryBuilder);
		
		searchRequest.source(searchSourceBuilder);
	
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
	
		return getSearchResult(response);
		
	}

	public List<Note> searchByWord(String word) throws Exception {

		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	
		QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("word", "*"+word+"*"));
	   		
		searchSourceBuilder.query(queryBuilder);
		
		searchRequest.source(searchSourceBuilder);
	
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
						
		return getSearchResult(response);
		
//		QueryBuilder queryBuilder = multiMatchQuery(searchInput, "id","firstName","lastName","title","nickName","location")
//	            .type(MatchQueryBuilder.Type.PHRASE_PREFIX).analyzer("standard");
		
//		SearchQuery searchQuery = new NativeSearchQueryBuilder()
//				  .withQuery(multiMatchQuery("tutorial")
//				    .field("title")
//				    .field("tags")
//				    .type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
//				  .build();

	}
	public String deleteNote(Long noteId) throws Exception {

		System.out.println("delete elastic");
		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, String.valueOf(noteId));//.index(INDEX).type(TYPE);
		DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);
	
		return response.getResult().name();

	}

	public String updateNote(Note note) throws Exception {

		Note resultDocument = findById(String.valueOf(note.getNoteId()));
		Map<String, Object> documentMapper = objectMapper.convertValue(note, Map.class);
	
		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, String.valueOf(resultDocument.getNoteId()));
	
		updateRequest.doc(documentMapper);
	
		UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
	    
		return updateResponse.getResult().name();

	}

	
}

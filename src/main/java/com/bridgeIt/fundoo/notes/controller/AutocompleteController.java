package com.bridgeIt.fundoo.notes.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgeIt.fundoo.notes.model.AutoCompleteResponse;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class AutocompleteController {

	private RestHighLevelClient client;

	private ObjectMapper objectMapper;

	@Autowired
	public AutocompleteController(RestHighLevelClient client, ObjectMapper objectMapper) {
		this.client = client;
		this.objectMapper = objectMapper;
	}
	
	 static String INDEX="notedata";
	 static String TYPE="note";
	 
    @RequestMapping("/autocomplete")
    public AutoCompleteResponse autoComplete(@RequestParam(value="search-entity") String name, @RequestParam(value="search-query") String query) throws Exception 
    {
        SearchRequest searchRequest = new SearchRequest();  // name of the index to search
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String[] includeFields = new String[] {"name"};
        String[] excludeFields = new String[] {};
        searchSourceBuilder.query(QueryBuilders.matchQuery(name, query)).fetchSource(includeFields, excludeFields);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse=client.search(searchRequest);
        SearchHits hits = searchResponse.getHits();
        AutoCompleteResponse<String> autoCompleteResponse = new AutoCompleteResponse<String>();
        autoCompleteResponse.setTotalHits(hits.getTotalHits());
        Iterator<SearchHit> iterator = hits.iterator();
        List<String> values = new ArrayList<String>();
        autoCompleteResponse.setValues(values);
        int i = 0;
        while (iterator.hasNext()) {
            i++;
            SearchHit hit = iterator.next();
            values.add((String)hit.getSourceAsMap().get("name"));
            if (i == 10) {   // max 10, can be made configurable
                break;
            }
        }
        return autoCompleteResponse;
    }
}
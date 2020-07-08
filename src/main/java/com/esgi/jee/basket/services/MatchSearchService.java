package com.esgi.jee.basket.services;

import com.esgi.jee.basket.db.Match;
import com.esgi.jee.basket.db.MatchSearch;
import com.esgi.jee.basket.db.MatchSearchRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class MatchSearchService {

    private final ObjectMapper objectMapper;
    private final MatchSearchRepository matchSearchRepository;

    private final RestHighLevelClient elasticClient;

    public List<Match> search(String search){

        SearchRequest request = new SearchRequest("basket");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        QueryStringQueryBuilder query = QueryBuilders.queryStringQuery(search);
        builder.query(query);
        request.source(builder);

        try{
            SearchResponse response = elasticClient.search(request, RequestOptions.DEFAULT);

            return StreamSupport.stream(response.getHits().spliterator(), false)
                                                        .map(SearchHit::getSourceAsMap)
                                                        .map(element -> objectMapper.convertValue(element, Match.class))
                                                        .collect(Collectors.toList());
        }catch(Exception e){

            System.out.println("Error elastic" + e.getMessage());

            return new ArrayList<>();
        }
    }

    public MatchSearch createHistory(List<Match> findMatch, String search, String userSubject, String ipAddress){

        MatchSearch matchSearch = MatchSearch.builder()
                                                .research(search)
                                                .totalResult(findMatch.size())
                                                .ipAddress(ipAddress)
                                                .userId(userSubject)
                                                .result(findMatch)
                                            .build();

        return matchSearchRepository.save(matchSearch);
    }

    public Page<MatchSearch> searchHistory(String userSubject, Pageable pageable){

        return matchSearchRepository.findAllByUserId(pageable, userSubject);
    }

    public Optional<MatchSearch> getHistoryItem(String userSubject, String searchId){

//        System.out.println(userSubject);
//        System.out.println(searchId);

        return matchSearchRepository.findById(searchId);
    }
}

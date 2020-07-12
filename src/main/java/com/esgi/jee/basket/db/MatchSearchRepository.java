package com.esgi.jee.basket.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import java.util.Optional;

public interface MatchSearchRepository extends ElasticsearchCrudRepository<MatchSearch, String> {

    Page<MatchSearch> findAllByUserId(Pageable pageable, String id);

    @Query("{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"?0\",\"fields\":[\"_id\"]}},{\"query_string\":{\"query\":\"?1\",\"fields\":[\"userId\"]}}]}}")
    Optional<MatchSearch> findByIdAndUserId(String id, String userId);
}

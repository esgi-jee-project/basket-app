package com.esgi.jee.basket.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import java.util.Optional;

public interface MatchSearchRepository extends ElasticsearchCrudRepository<MatchSearch, String> {

    Page<MatchSearch> findAllByUserId(Pageable pageable, String id);

    Optional<MatchSearch> findById(String userId);
}

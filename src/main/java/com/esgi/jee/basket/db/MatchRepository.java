package com.esgi.jee.basket.db;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

public interface MatchRepository extends ElasticsearchCrudRepository<Match, String> {

}

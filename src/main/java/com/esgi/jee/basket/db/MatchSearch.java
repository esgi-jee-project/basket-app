package com.esgi.jee.basket.db;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Document(indexName = "search", type = "match")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchSearch {

    @Id
    private String id;

    private String userId;

    private String research;

    private String ipAddress;

    private int totalResult;

    private List<Match> result;
}

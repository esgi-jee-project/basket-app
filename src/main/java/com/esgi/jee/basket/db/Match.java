package com.esgi.jee.basket.db;

import com.esgi.jee.basket.team.infrastructure.dao.HibernateTeam;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Document(indexName = "basket", type = "match")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    private String id;

    private String date;

    private String place;

    private HibernateTeam idNameLocal;

    private HibernateTeam idNameOpponent;

    private Integer scoreLocal;

    private Integer scoreOpponent;

    private List<Player> playerTeamLocal;

    private List<Player> playerTeamOpponent;
}

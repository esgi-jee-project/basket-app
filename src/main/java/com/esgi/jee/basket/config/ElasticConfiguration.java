package com.esgi.jee.basket.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(
        basePackages = "com.esgi.jee.basket"
)
public class ElasticConfiguration extends AbstractElasticsearchConfiguration {

    @Value("${basket.elastic.host}")
    private String host;

    @Value("${basket.elastic.user}")
    private String user;

    @Value("${basket.elastic.password}")
    private String password;

    @Value("${basket.elastic.isLocal:false}")
    private boolean local;


    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient(){


        return RestClients.create(local
            ? ClientConfiguration.builder().connectedToLocalhost().build()
            : ClientConfiguration.builder().connectedTo(host).usingSsl().withBasicAuth(user, password).build())
            .rest();
    }
}

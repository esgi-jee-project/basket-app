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

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient(){

        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                                                                        .connectedTo(this.host)
                                                                        .build();
        return RestClients.create(clientConfiguration).rest();
    }
}

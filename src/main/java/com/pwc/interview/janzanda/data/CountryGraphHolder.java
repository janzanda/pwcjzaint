package com.pwc.interview.janzanda.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwc.interview.janzanda.dto.CountryDto;
import lombok.AllArgsConstructor;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Loads data from countries.json and holds them in graph structure.
 *
 * @author zandajan
 */
@Component
public class CountryGraphHolder {

    private Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

    @Value("classpath:countries.json")
    private InputStream jsonFile;

    @PostConstruct
    public void init() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<CountryDto> countries = objectMapper.readValue(jsonFile, new TypeReference<>() {
        });
        if (CollectionUtils.isEmpty(countries)) {
            throw new RuntimeException("No source data to compute with.");
        }
        countries.stream().filter(country -> !CollectionUtils.isEmpty(country.getBorders())).forEach(country ->
                addToGraph(country));
    }

    public Graph<String, DefaultEdge> getGraph() {
        return graph;
    }

    private void addToGraph(CountryDto country) {
        addVertex(country.getCca3());
        country.getBorders().forEach(border -> addEdge(country.getCca3(), border));
    }

    private void addVertex(String countryCode) {
        if (!graph.containsVertex(countryCode)) {
            graph.addVertex(countryCode);
        }
    }

    private void addEdge(String v1, String v2) {
        if (!graph.containsVertex(v1)) {
            addVertex(v1);
        }
        if (!graph.containsVertex(v2)) {
            addVertex(v2);
        }
        if (!graph.containsEdge(v1, v2)) {
            graph.addEdge(v1, v2);
        }
    }
}

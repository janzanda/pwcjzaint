package com.pwc.interview.janzanda.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwc.interview.janzanda.dto.CountryDto;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

    @Value("classpath:countries.json")
    private InputStream jsonFile;

    @PostConstruct
    void init() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<CountryDto> countries = objectMapper.readValue(jsonFile, new TypeReference<>() {
        });
        if (CollectionUtils.isEmpty(countries)) {
            throw new RuntimeException("No source data to compute with.");
        }
        countries.stream().filter(country -> !CollectionUtils.isEmpty(country.getBorders())).forEach(this::addToGraph);
        log.info("Loaded {} countries into graph. ", countries.size());
    }

    /**
     * Returns graph object instance.
     *
     * @return Graph with countries.
     */
    public Graph<String, DefaultEdge> getGraph() {
        return graph;
    }

    private void addToGraph(CountryDto country) {
        country.getBorders().forEach(border -> addVertexesAndEdge(country.getCca3(), border));
    }

    private void addVertexesAndEdge(String v1, String v2) {
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

    private void addVertex(String countryCode) {
        if (!graph.containsVertex(countryCode)) {
            graph.addVertex(countryCode);
        }
    }

}

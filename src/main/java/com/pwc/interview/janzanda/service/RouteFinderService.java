package com.pwc.interview.janzanda.service;

import com.pwc.interview.janzanda.data.CountryGraphHolder;
import com.pwc.interview.janzanda.exception.RouteNotFoundException;
import lombok.AllArgsConstructor;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Service for operation above country data.
 *
 * @author zandajan
 */
@Component
@AllArgsConstructor
public class RouteFinderService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final CountryGraphHolder graphHolder;

    /**
     * Returns route between two countries.
     *
     * @param from Origin country.
     * @param to   Destination country.
     * @return List of countries in order from origin to destination.
     */
    public List<String> getRoute(String from, String to) {
        log.info("Searching for route from {} to {}", from, to);
        DijkstraShortestPath<String, DefaultEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graphHolder.getGraph());
        GraphPath<String, DefaultEdge> path = dijkstraShortestPath.getPath(from, to);
        if (path == null) {
            log.error("Route from {} to {} not found. Throwing exception.", from, to);
            throw new RouteNotFoundException("Route from " + from + " to " + to + " not found.");
        }
        log.info("Route found: {}", path.getVertexList());
        return path.getVertexList();
    }

}

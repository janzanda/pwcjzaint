package com.pwc.interview.janzanda.service;

import com.pwc.interview.janzanda.data.CountryGraphHolder;
import lombok.AllArgsConstructor;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
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

    private final CountryGraphHolder graphHolder;

    /**
     * Returns route between two countries.
     *
     * @param from Origin country.
     * @param to   Destination country.
     * @return List of countries in order from origin to destination.
     */
    public List<String> getRoute(String from, String to) {
        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(graphHolder.getGraph());
        return dijkstraShortestPath.getPath(from, to).getVertexList();
    }

}

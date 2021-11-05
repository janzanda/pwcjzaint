package com.pwc.interview.janzanda.rest;

import com.pwc.interview.janzanda.service.RouteFinderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class RouteFinderController {

    private final RouteFinderService routeFinderService;

    @GetMapping("/routing/{from}/{to}")
    public List<String> findRounte(@PathVariable("from") String from, @PathVariable("to") String to) {
        return routeFinderService.getRoute(from, to);
    }

}

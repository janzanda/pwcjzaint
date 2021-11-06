package com.pwc.interview.janzanda;

import com.pwc.interview.janzanda.exception.RouteNotFoundException;
import com.pwc.interview.janzanda.service.RouteFinderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Tests
 *
 * @author zandajan
 */
@SpringBootTest
class JanzandaApplicationTests {

    private final List<String> positiveRouteResult = List.of("USA",
            "MEX",
            "GTM",
            "HND",
            "NIC",
            "CRI",
            "PAN",
            "COL",
            "BRA",
            "ARG");

    @Autowired
    private RouteFinderService routeFinderService;

    @Test
    void testPositiveFindRoute() {
        List<String> route = routeFinderService.getRoute("USA", "ARG");
        Assertions.assertNotNull(route);
        Assertions.assertFalse(route.isEmpty());
        Assertions.assertEquals(positiveRouteResult, route);
    }

    @Test
    void testRouteNotFound() {
        Assertions.assertThrows(RouteNotFoundException.class, () -> routeFinderService.getRoute("CZE", "USA"));
    }

}

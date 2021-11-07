package com.pwc.interview.janzanda.rest;

import com.pwc.interview.janzanda.exception.RouteNotFoundException;
import com.pwc.interview.janzanda.service.RouteFinderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller.
 *
 * @author zandajan
 */

@RestController
@AllArgsConstructor
public class RouteFinderController {

    private final RouteFinderService routeFinderService;

    @Operation(description = "Finds route (if possible) between two countries. CCA3 country codes must be used.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Route found."),
            @ApiResponse(responseCode = "400", description = "Route NOT found.", content = @Content)
    })
    @GetMapping(path = "/routing/{from}/{to}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> findRounte(@Parameter(description = "CCA3 code of country of origin.") @PathVariable("from") String from,
                                   @Parameter(description = "CCA3 code of destination country.") @PathVariable("to") String to) {
        return routeFinderService.getRoute(from, to);
    }

    @ExceptionHandler(RouteNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handlePathNotFound(RouteNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

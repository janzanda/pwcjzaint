package com.pwc.interview.janzanda.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * POJO used for loading countries from countries.json file. Uses only 'cca3' and 'borders' params. Others are not needed.
 *
 * @author zandajan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
public class CountryDto {

    /**
     * Cca3 value from source json.
     */
    private String cca3;
    /**
     * List of borders.
     */
    private List<String> borders;

}

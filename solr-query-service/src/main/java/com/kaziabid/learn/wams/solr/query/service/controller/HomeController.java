package com.kaziabid.learn.wams.solr.query.service.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaziabid.learn.wams.common.dto.AppStatusDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * @author Kazi Abid
 */
@RestController
@RequestMapping(path = { "/", "/home", "health" })
public class HomeController {

    @Operation(summary = "Base path. Indicates the running status of the app.")
    @ApiResponses(
            value = { @ApiResponse(
                    responseCode = "200", description = "Application is running.",
                    content = { @Content(
                            mediaType = org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AppStatusDTO.class)) }) })
    @GetMapping
    public Object home() {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("status", "UP!");
        responseMap.put("name", "Solr Query Service.");
        responseMap.put("info", "/docs");
        return responseMap;
    }

}

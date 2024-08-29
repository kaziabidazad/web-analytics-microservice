package com.kaziabid.learn.wams.solr.query.service.controller.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kaziabid.learn.wams.common.dto.CommonHttpResponse;
import com.kaziabid.learn.wams.exceptions.api.WamsRestServiceException;

/**
 * @author Kazi Abid
 */
@RestControllerAdvice(basePackages = "com.kaziabid.learn.wams")
public class GenericControllerAdvice {

    @ExceptionHandler(WamsRestServiceException.class)
    public ResponseEntity<CommonHttpResponse>
            handleException(WamsRestServiceException exception) {
        CommonHttpResponse response =
                new CommonHttpResponse(exception.getHttpCode(), exception.getMessage());
        return new ResponseEntity<>(response, new HttpHeaders(), exception.getHttpCode());
    }

}

package com.kaziabid.learn.wams.common.dto;

/**
 * @author Kazi Abid
 */
public record CommonHttpResponse(int status, String message) implements CommonDTO {

}

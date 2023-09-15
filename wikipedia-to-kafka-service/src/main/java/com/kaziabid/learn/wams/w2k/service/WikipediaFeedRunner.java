package com.kaziabid.learn.wams.w2k.service;

import java.time.LocalDate;

/**
 * @author Kazi
 */
public interface WikipediaFeedRunner {

    public void start(LocalDate startDate);
    public void stop();

}

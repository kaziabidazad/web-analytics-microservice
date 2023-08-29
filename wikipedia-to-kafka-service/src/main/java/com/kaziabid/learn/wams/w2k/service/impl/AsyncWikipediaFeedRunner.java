package com.kaziabid.learn.wams.w2k.service.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.admin.AdminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.kaziabid.learn.wams.w2k.component.WikipediaDataExtractor;
import com.kaziabid.learn.wams.w2k.service.WikipediaFeedRunner;

/**
 * @author Kazi
 */
@Component
public class AsyncWikipediaFeedRunner implements WikipediaFeedRunner, Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(AsyncWikipediaFeedRunner.class);
    private WikipediaDataExtractor wikipediaDataExtractor;
    private Thread worker;
    private AtomicBoolean running = new AtomicBoolean(true);
    private LocalDate startDate;
    private ExecutorService taskExecutor;
    private final AdminClient adminClient;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * @param wikipediaDataExtractor
     * @param taskExecutor
     */
    public AsyncWikipediaFeedRunner(WikipediaDataExtractor wikipediaDataExtractor, ExecutorService taskExecutor,
            AdminClient adminClient) {
        super();
        this.wikipediaDataExtractor = wikipediaDataExtractor;
        this.taskExecutor = taskExecutor;
        this.adminClient = adminClient;
    }

    private void interrupt() {
        running.set(false);
        worker.interrupt();
        taskExecutor.shutdown();
        try {
            taskExecutor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOGGER.error("Error awaiting termination");
        }
        taskExecutor.shutdownNow();
        adminClient.close(Duration.ofSeconds(2));
        // Exit the Application
        SpringApplication.exit(applicationContext, () -> 0);
    }

    @Override
    public void start(LocalDate startDate) {
        this.startDate = startDate;
        worker = new Thread(this, "FeedRunnerThread");
        worker.start();
    }

    @Override
    public void stop() {
        String message = """
                *****************************************************************************
                *****************************************************************************
                ******                                                                 ******
                ******                                                                 ******
                ******                               STOPPING                          ******
                ******                                                                 ******
                ******                                                                 ******
                *****************************************************************************
                *****************************************************************************
                """;
        interrupt();
        LOGGER.error(message);
    }

    @Override
    public void run() {
        LOGGER.info("Starting to extract Wikimedia Feed..");
        running.set(true);
        LocalDate localDate = startDate;
        while (running.get()) {
            try {
                LOGGER.debug("Executing Wikipedia feed");
                wikipediaDataExtractor.extractWikipediaPagePerDay(localDate);
                localDate = localDate.plusDays(1);
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.info("Interrupted the thread..");
                LOGGER.info("Last extracted date: {} ", localDate.toString());
            }
        }

    }

}

package ru.rsreu.TrafficCodeServer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.rsreu.TrafficCodeServer.service.data.CringeDataInserterServer;

import java.io.IOException;

@Component
public class DataInserter implements ApplicationListener<ApplicationReadyEvent> {
    private final CringeDataInserterServer cringeDataInserterServer;

    @Autowired
    public DataInserter(CringeDataInserterServer cringeDataInserterServer) {
        this.cringeDataInserterServer = cringeDataInserterServer;
    }

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        try {
            cringeDataInserterServer.insertDataFromJson();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
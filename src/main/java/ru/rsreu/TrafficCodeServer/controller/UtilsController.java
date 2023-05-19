package ru.rsreu.TrafficCodeServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rsreu.TrafficCodeServer.service.data.CringeDataInserterServer;

import java.io.IOException;

@Controller
@RequestMapping("/utils/")
public class UtilsController {

    private final CringeDataInserterServer cringeDataInserterServer;

    @Autowired
    public UtilsController(CringeDataInserterServer cringeDataInserterServer) {
        this.cringeDataInserterServer = cringeDataInserterServer;
    }

    @GetMapping("/insertData")
    public void showSignUpForm() throws IOException {
        cringeDataInserterServer.insertDataFromJson();
    }
}
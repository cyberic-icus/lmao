package ru.rsreu.TrafficCodeServer.service.data;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import ru.rsreu.TrafficCodeServer.database.entity.*;
import ru.rsreu.TrafficCodeServer.database.repository.AnswerRepository;
import ru.rsreu.TrafficCodeServer.database.repository.QuestionRepository;
import ru.rsreu.TrafficCodeServer.database.repository.RoleRepository;
import ru.rsreu.TrafficCodeServer.service.entity.AnswerService;
import ru.rsreu.TrafficCodeServer.service.entity.TicketService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CringeDataInserterServer {
    private final TicketService ticketService;
    private final AnswerService answerService;
    private final RoleRepository roleRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public CringeDataInserterServer(TicketService ticketService, AnswerService answerService, RoleRepository roleRepository,
                                    QuestionRepository questionRepository,
                                    AnswerRepository answerRepository) {
        this.ticketService = ticketService;
        this.answerService = answerService;
        this.roleRepository = roleRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }


    @Transactional
    public void insertDataFromJson() throws IOException {
        String str = "";
        ClassPathResource cpr = new ClassPathResource("static/full_dump.json");
        try {
            byte[] bstr = FileCopyUtils.copyToByteArray(cpr.getInputStream());
            str = new String(bstr, StandardCharsets.UTF_8);
            ticketService.deleteAll();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = new JSONArray(str);
        List<Ticket> ticketList = new ArrayList<>();
        Set<Question> questionLizt = null;
        Set<Answer> answerLizt = null;
        for (Object ticket : jsonArray) {
            JSONObject ticketObject = (JSONObject) ticket;
            Ticket ticketEntity = new Ticket();
            ticketEntity.setYear(ticketObject.getInt("year"));
            ticketEntity.setType(ticketObject.getString("type"));
            ticketList.add(ticketEntity);
            JSONArray tickets = ticketObject.getJSONArray("tickets");
            questionLizt = new HashSet<>();
            for (Object question : tickets) {
                JSONObject questionObject = (JSONObject) question;
                Question questionEntity = new Question();
                questionEntity.setExplanation(questionObject.getString("explanation"));
                questionEntity.setImage((questionObject.get("image") == null || questionObject.get("image").equals(JSONObject.NULL)) ? "" : questionObject.getString("image"));
                questionEntity.setQuestionText(questionObject.getString("question"));
                questionEntity.setTicket(ticketEntity);
                JSONArray answers = questionObject.getJSONArray("answers");
                answerLizt = new HashSet<>();
                for (Object answer : answers) {
                    JSONObject answerObject = (JSONObject) answer;
                    Answer answerEntity = new Answer();
                    answerEntity.setCorrect(answerObject.getBoolean("correct"));
                    answerEntity.setText(answerObject.getString("text"));
                    answerEntity.setQuestion(questionEntity);
                    answerLizt.add(answerEntity);
                }
                questionEntity.setAnswers(answerLizt);
                questionLizt.add(questionEntity);
            }
            ticketEntity.setQuestions(questionLizt);
        }
        ticketService.saveAll(ticketList);
        roleRepository.saveAll(
                List.of(
                        new RoleEntity(1, ERole.ROLE_USER),
                        new RoleEntity(2, ERole.ROLE_MODERATOR),
                        new RoleEntity(3, ERole.ROLE_ADMIN)
                )
        );
    }
}

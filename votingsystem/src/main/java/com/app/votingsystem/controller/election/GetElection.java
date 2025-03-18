package com.app.votingsystem.controller.election;

import com.app.votingsystem.model.election.Election;
import com.app.votingsystem.service.jdbc.ElectionDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;

@WebServlet(name = "GetElection", value = "/get_election")
public class GetElection extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject resJson = new JSONObject();
        resp.setContentType("application/json");

        try {

            String electionId = req.getParameter("electionId");
            Election election = ElectionDB.getElection(electionId);

            if (election != null) {
                JSONObject jsonObject = new JSONObject();
                for (Field field : election.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    jsonObject.put(field.getName(), field.get(election));
                }
                resJson.put("election", jsonObject);
                resJson.put("status", true);
                resJson.put("message", "Election found");

            } else {
                resJson.put("status", false);
                resJson.put("message", "Election not found");
            }

            resp.getWriter().write(resJson.toString());
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        resJson.put("status", false);
        resJson.put("message", "Election Retrival Failed With Error");
        resp.getWriter().write(resJson.toString());

    }
}

package com.app.votingsystem.controller.candidate;

import com.app.votingsystem.model.candidate.Candidate;
import com.app.votingsystem.service.jdbc.CandidateDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetCandidates", value = "/getcandidates")
public class GetCandidates extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject resJson = new JSONObject();
        resp.setContentType("application/json");
        try {
            String electionId = req.getParameter("eleId");
            System.out.println("Election ID: " + electionId);
            JSONArray candidates = CandidateDB.getCandidatesJson(electionId);

            if (!candidates.isEmpty()) {
                resJson.put("status", true);
                resJson.put("candidates", candidates);
                resJson.put("message", "Candidates Getted");
            } else {
                resJson.put("status", false);
                resJson.put("message", "No Candidates Found");
            }
            resp.getWriter().write(resJson.toString());
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        resJson.put("status", false);
        resJson.put("message", "Error on getting candidates");
        resp.getWriter().write(resJson.toString());
    }
}

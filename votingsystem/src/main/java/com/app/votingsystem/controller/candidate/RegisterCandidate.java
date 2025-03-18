package com.app.votingsystem.controller.candidate;

import com.app.votingsystem.model.candidate.Candidate;
import com.app.votingsystem.service.jdbc.CandidateDB;
import com.app.votingsystem.utils.utility;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;

@WebServlet(name = "RegisterCandidate", value = "/registercandidate")
public class RegisterCandidate extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONObject resJson = new JSONObject();
        resp.setContentType("application/json");

        try {
            JSONObject jsonObject = utility.getResponseData(req.getReader());
            Candidate candidate = new Candidate();
            candidate.setCandidateId("CID_2025_0" + CandidateDB.lastIdx() + 1);
            candidate.setVoterId(jsonObject.getString("voterId"));
            candidate.setParty(jsonObject.getString("party"));
            candidate.setManifesto(jsonObject.getString("manifesto"));
            candidate.setElectionId(jsonObject.getString("electionId"));

            if (CandidateDB.addCandidate(candidate)) {
                resJson.put("status", true);
                resJson.put("candidateId", candidate.getCandidateId());
                resJson.put("voterId", candidate.getVoterId());
                resp.getWriter().write(resJson.toString());
            }
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        resJson.put("status", false);
        resp.getWriter().write(resJson.toString());
    }
}

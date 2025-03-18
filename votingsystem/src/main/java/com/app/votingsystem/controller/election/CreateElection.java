package com.app.votingsystem.controller.election;

import com.app.votingsystem.model.election.Election;
import com.app.votingsystem.model.election.ElectionStatus;
import com.app.votingsystem.service.jdbc.ElectionDB;
import com.app.votingsystem.utils.utility;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;


@WebServlet(name = "CreateElection", value = "/create_election")
public class CreateElection extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject resJson = new JSONObject();
        resp.setContentType("application/json");
        try {

            JSONObject responseData = utility.getResponseData(req.getReader());
            Election election = new Election();
            election.setElectionId("ElectionID_2025_0" + (ElectionDB.lastIdx() + 1));
            election.setElectionName(responseData.getString("electionName"));
            election.setStartDate(responseData.getString("startDate"));
            election.setEndDate(responseData.getString("endDate"));
            election.setStatus(ElectionStatus.valueOf(responseData.getString("status")));

            if (ElectionDB.createElection(election)) {
                resJson.put("message", "Election Created");
                resJson.put("status", true);
            } else {
                resJson.put("message", "Election Creating Failed");
                resJson.put("status", false);
            }
            resp.getWriter().write(resJson.toString());
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        resJson.put("message", "Election Creation Failed With Error");
        resJson.put("status", false);
        resp.getWriter().write(resJson.toString());
    }
}

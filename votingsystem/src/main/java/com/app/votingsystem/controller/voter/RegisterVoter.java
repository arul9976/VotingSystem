package com.app.votingsystem.controller.voter;

import com.app.votingsystem.model.voter.Voter;
import com.app.votingsystem.service.jdbc.VotingSystemDB;
import com.app.votingsystem.utils.Validation;
import com.app.votingsystem.utils.utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;


@WebServlet(name = "RegisterVoter", value = "/registervoter")
public class RegisterVoter extends HttpServlet {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try {
            System.out.println("RegisterVoter");
            JSONObject responseData = utility.getResponseData(req.getReader());

            Voter voter = new Voter();
            voter.setVoterId("VOTER_2025_0" + (VotingSystemDB.lastIdx() + 1));
            voter.setName(responseData.getString("name"));
            voter.setEmail(responseData.getString("email"));
            voter.setPassword(Validation.hashPassword(responseData.getString("password")));
            voter.setEligible(responseData.getBoolean("isEligible"));
            voter.setAge((byte) responseData.getInt("age"));
            voter.setUserRole("user");


            utility.printResponseDatas(voter.toString(), responseData);
            if (voter != null && VotingSystemDB.ValidateRegisterUser(voter)) {
                JSONObject responseJson = new JSONObject();
                responseJson.put("status", true);
                responseJson.put("voterId", voter.getVoterId());
                resp.getWriter().write(responseJson.toString());
            }
            return;
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
        JSONObject response = new JSONObject();
        resp.setStatus(401);
        response.put("status", false);
        resp.getWriter().write(response.toString());
    }
}

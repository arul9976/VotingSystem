package com.app.votingsystem.controller.voter;

import com.app.votingsystem.model.voter.Voter;
import com.app.votingsystem.service.jdbc.VotingSystemDB;
import com.app.votingsystem.utils.utility;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;


@WebServlet(name = "SignInVoter", value = "/signvoter")
public class SignInVoter extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject responseData = new JSONObject();
        resp.setContentType("application/json");

        System.out.println("In SignInVoter");

        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            System.out.println("email: " + email + " password: " + password);
            Voter voter;
            if ((voter = VotingSystemDB.ValidateLoginUser(email, password)) != null) {
                utility.printResponseDatas(voter.toString(), responseData);
                responseData.put("status", true);
                responseData.put("email", email);
                responseData.put("role", voter.getUserRole());

                resp.getWriter().write(responseData.toString());
            }
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        resp.setStatus(401);
        responseData.put("status", "success");
        responseData.put("email", "null");
        resp.getWriter().write(responseData.toString());

    }
}

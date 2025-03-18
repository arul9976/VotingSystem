package com.app.votingsystem.controller.election;

import com.app.votingsystem.model.election.Election;
import com.app.votingsystem.service.jdbc.ElectionDB;
import com.app.votingsystem.utils.utility;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;


@WebServlet(name = "GetElections", value = "/getelections")
public class GetElections extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject respJson = new JSONObject();
        resp.setContentType("application/json");
        try{
           String eleType = req.getParameter("electionType");
            JSONArray elections = ElectionDB.showAndGetElections(eleType);
            System.out.println(elections);

            if(!elections.isEmpty()){
                respJson.put("status", true);
                respJson.put("elections", elections);
                respJson.put("message", "Elections found");
            }else {
                respJson.put("status", false);
                respJson.put("message", "No elections found");
            }
            resp.getWriter().print(respJson.toString());
            return;


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        respJson.put("status", false);
        respJson.put("message", "Election Retrival Failed With Exception");
        resp.getWriter().print(respJson.toString());
    }
}

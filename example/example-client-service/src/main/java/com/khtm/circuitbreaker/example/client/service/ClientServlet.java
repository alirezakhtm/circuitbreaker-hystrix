package com.khtm.circuitbreaker.example.client.service;

import com.google.gson.GsonBuilder;
import com.khtm.circuitbreaker.example.client.adapter.DataFakeGenerator;
import com.khtm.circuitbreaker.example.client.model.Client;
import com.khtm.circuitbreaker.example.client.model.UserList;
import lombok.Builder;
import lombok.Data;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "client", urlPatterns = {"/client-service"})
public class ClientServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserList userList = UserList.builder().clients(DataFakeGenerator.getClients()).build();
        String jsonData = new GsonBuilder().create().toJson(userList, UserList.class);
        resp.setContentType("application/json;charset=UTF-8");
        try(PrintWriter printWriter = resp.getWriter()){
            printWriter.print(jsonData);
        }
    }



}

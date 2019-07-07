package com.khtm.circuitbreaker.example.ui.service;

import com.khtm.circuitbreaker.Feign;
import com.khtm.circuitbreaker.example.ui.generator.DataFakeGenerator;
import com.khtm.circuitbreaker.example.ui.model.UserList;
import com.khtm.eureka.impl.EurekaService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ui", urlPatterns = {"/"})
public class UIServlet extends HttpServlet {

    private Feign feign;
    private EurekaService eurekaService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        eurekaService = new EurekaService("http://10.12.47.125", 8761, "10.12.46.147");
        UserList fallBackResponse = UserList.builder().clients(DataFakeGenerator.getClients()).build();
        feign = Feign.builder()
                .eurekaService(eurekaService)
                .applicationName("example-client-service")
                .url("/client-service")
                .method(Feign.REQUEST_METHOD_GET)
                .parameters(null)
                .isThereAppOnTomcatStandalone(true)
                .fallBackResponse(fallBackResponse)
                .build();
        UserList userList = (UserList) feign.callService();
        req.setAttribute("userlist", userList.getClients());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/home.jsp");
        requestDispatcher.forward(req, resp);
    }
}

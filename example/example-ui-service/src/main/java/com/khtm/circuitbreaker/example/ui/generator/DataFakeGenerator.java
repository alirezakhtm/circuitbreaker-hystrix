package com.khtm.circuitbreaker.example.ui.generator;



import com.khtm.circuitbreaker.example.ui.model.Client;

import java.util.ArrayList;
import java.util.List;

public class DataFakeGenerator {

    private static List<Client> clients = null;


    public static List<Client> getClients(){

        if(clients != null){
            return clients;
        }

        DataFakeGenerator.clients = new ArrayList<>();

        Client client01 = Client.builder().firstname("Unknown").lastname("Unknown").tel("Unknown").build();

        DataFakeGenerator.clients.add(client01);

        return clients;
    }

}

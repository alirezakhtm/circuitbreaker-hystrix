package com.khtm.circuitbreaker.example.client.adapter;


import com.khtm.circuitbreaker.example.client.model.Client;
import sun.tools.jar.CommandLine;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class DataFakeGenerator {

    private static List<Client> clients = null;


    public static List<Client> getClients(){

        if(clients != null){
            return clients;
        }

        DataFakeGenerator.clients = new ArrayList<>();

        Client client01 = Client.builder().firstname("alireza").lastname("khatatmi doost").tel("09190000000").build();
        Client client02 = Client.builder().firstname("majid").lastname("vakili").tel("09190000000").build();
        Client client03 = Client.builder().firstname("morteza").lastname("mosavi").tel("09190000000").build();
        Client client04 = Client.builder().firstname("hamed").lastname("mirzaei").tel("09190000000").build();
        Client client05 = Client.builder().firstname("alireza").lastname("mesgari").tel("09190000000").build();

        DataFakeGenerator.clients.add(client01);
        DataFakeGenerator.clients.add(client02);
        DataFakeGenerator.clients.add(client03);
        DataFakeGenerator.clients.add(client04);
        DataFakeGenerator.clients.add(client05);

        return clients;
    }

}

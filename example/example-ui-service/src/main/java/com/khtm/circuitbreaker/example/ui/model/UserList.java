package com.khtm.circuitbreaker.example.ui.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserList {
    private List<Client> clients;
}

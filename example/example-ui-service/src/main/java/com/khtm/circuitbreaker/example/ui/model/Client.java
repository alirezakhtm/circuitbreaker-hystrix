package com.khtm.circuitbreaker.example.ui.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Client {

    private String firstname, lastname, tel;

}

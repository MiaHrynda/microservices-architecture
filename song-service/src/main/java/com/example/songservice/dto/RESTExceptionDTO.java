package com.example.songservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class RESTExceptionDTO implements Serializable {
    private String title;
    private int code;
    private String detail;
}

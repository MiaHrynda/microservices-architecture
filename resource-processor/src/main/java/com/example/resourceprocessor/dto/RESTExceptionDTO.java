package com.example.resourceprocessor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RESTExceptionDTO {
    private String title;
    private int code;
    private String detail;
}

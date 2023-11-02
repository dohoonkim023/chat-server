package com.example.sockettest.domain;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Dialog {

    private LocalDateTime date;
    private String sender;
    private String message;
}

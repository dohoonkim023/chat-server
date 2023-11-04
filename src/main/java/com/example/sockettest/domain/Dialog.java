package com.example.sockettest.domain;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dialog {

    private LocalDateTime date;
    private String sender;
    private String message;
}

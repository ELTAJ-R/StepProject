package org.eltaj.step.Entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Message {

    private int id;
    private String sender;
    private String message;
    private String isSent;
    private String date;


}

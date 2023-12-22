package com.example.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDetailsDTO {
    private String to;
    private String from;
    private String message;
    private String subject;

}

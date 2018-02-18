package com.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailProperties {

    private String to;
    private String subject;
    private String content;

    public MailProperties(String to, String subject) {
        this.to = to;
        this.subject = subject;
    }
}

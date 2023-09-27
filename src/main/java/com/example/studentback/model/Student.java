package com.example.studentback.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {

    private Integer id;
    private String firstName;
    private String lastName;
    private Integer mark = 0;
}

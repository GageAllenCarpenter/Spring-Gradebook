package com.school.gradebook.model;

import lombok.*;

/**
 *  the people who are taking the courses or have taken a course in the past
 *
 * @author Gage Carpenter
 * @Version 2022.04.23.01
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Student extends EntityBase
{
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String phoneNumber = "";
    private String address = "";
}

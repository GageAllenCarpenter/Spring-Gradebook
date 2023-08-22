package com.school.gradebook.model;

import lombok.*;

import java.util.Date;

/**
 *  Enrollment information for the student
 *
 * @author Gage Carpenter
 * @Version 2022.04.23.01
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Enrollment extends EntityBase
{
    private Date enrollDate;
    private int currentGrade;
    private int midtermGrade;
    private int finalGrade;
    Division division;
    Student student;
}

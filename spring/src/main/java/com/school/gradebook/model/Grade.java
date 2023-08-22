package com.school.gradebook.model;

import lombok.*;

/**
 *  Grades within a classroom or course
 *
 * @author Gage Carpenter
 * @Version 2022.04.23.01
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Grade extends EntityBase
{
    private int points;
    private Assignment assignment;
    private Division division;
    private Student student;
    private Professor professor;
}

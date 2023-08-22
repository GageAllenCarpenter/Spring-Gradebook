package com.school.gradebook.model;

import lombok.*;

/**
 *  The division of study for a particular course
 *
 * @author Gage Carpenter
 * @Version 2022.04.23.01
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Division extends EntityBase
{
    private int time;
    Professor professor;
    Semester semester;
    Course course;
    Room room;
}

package com.school.gradebook.model;

import lombok.*;

/**
 *  The course represents the room where professors teach students (a class)
 *
 * @author Gage Carpenter
 * @Version 2022.04.23.01
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Course extends EntityBase
{
    private String name = "";
    private String description = "";
    private int creditHour;
    Professor professor;
}

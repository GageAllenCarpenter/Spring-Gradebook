package com.school.gradebook.model;

import lombok.*;

/**
 *  Assignment represents assigned work given to the student
 *
 * @author Gage Carpenter
 * @version 2022.04.23.01
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Assignment extends EntityBase
{
    private String name = "";
    private String type = "";
    private int pointsPossible;
}

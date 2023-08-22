package com.school.gradebook.model;

import lombok.*;

/**
 *  Room that holds the course with the professor(s) and student(s)
 *
 * @author Gage Carpenter
 * @Version 2022.04.23.01
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Room extends EntityBase
{
    private Building building;
}

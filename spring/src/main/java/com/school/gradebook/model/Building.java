package com.school.gradebook.model;

import lombok.*;

/**
 *  Building represents the building that houses that particular course
 *
 * @author Gage Carpenter
 * @Version 2022.04.23.01
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Building extends EntityBase
{
    private String name = "";
    private String city = "";
    private String state = "";
    private int zipcode;

}

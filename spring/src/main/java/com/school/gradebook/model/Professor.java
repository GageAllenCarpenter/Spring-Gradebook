package com.school.gradebook.model;

import lombok.*;

/**
 *  Professors information
 *
 * @author Gage Carpenter
 * @Version 2022.04.23.01
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Professor extends EntityBase
{
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String phoneNumber = "";
    private String address = "";
}

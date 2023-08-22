package com.school.gradebook.model;

import lombok.*;

/**
 *  A historical marker that declares what time a course was held
 *
 * @author Gage Carpenter
 * @Version 2022.04.23.01
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Semester extends EntityBase
{
    private String startDate;
    private String endDate;
}

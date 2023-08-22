package com.school.gradebook.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * EntityBase is the base class for all objects in the application <br/>
 * It provides an ID field to act as the primary key in the database and provides the
 * corresponding getters/setters
 *
 * It also provides a compareTo for sorting on the PK, but use with caution because if you
 * compare two different object types their PK may have the same value since they are from
 * different tables.
 *
 * @author Gage Carpenter
 * @version 2022.04.23.01
 */
@NoArgsConstructor
@Getter
@Setter
public abstract class EntityBase implements Comparable<Object>
{
    private int ID = 0;

    /**
     * Compares the ID field of different objects
     */
    @Override
    public int compareTo(Object o)
    {
        EntityBase that = (EntityBase)o;
        return Integer.compare(this.ID, that.ID);
    }
}

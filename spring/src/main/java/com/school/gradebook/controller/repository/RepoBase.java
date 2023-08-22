package com.school.gradebook.controller.repository;

import com.school.gradebook.model.EntityBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * RepoBase is a base class implementing common Repository API methods that don't require something
 * unique about their class type. It is a generic and must by typed with a class that inherits from
 * EntityBase.
 */

public abstract class RepoBase<T extends EntityBase>
{
    private Connection conn;
    private String schema;
    private String table;

    /**
     * The above code is creating a connection to the database.
     */
    public RepoBase(String schema, String table)
    {
        setSchema(schema);
        setTable(table);
        // Ideally, load this from a file instead of hard coding it.  Don't include the file in source control for security reasons
        String connectionString = "jdbc:sqlserver://localhost;DatabaseName=Gradebook;user=sa;password=Wvup2021;MultipleActiveResultSets=True";

        try
        {
            conn = DriverManager.getConnection(connectionString);
        }
        catch (SQLException ex)
        {
            throw new IllegalStateException("Could not establish a database connection.  Check your connection string. " + ex.getMessage());
        }
    }

    /**
     * @return the number of records in the datastore
     */
    public int count()
    {
        String sql = "select count(*) from " + getSchema() + getTable() + ";";
        Statement statement = null;
        ResultSet rs = null;
        int count = 0;

        try
        {
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            rs.next();
            if (rs != null)
            {
                count = rs.getInt(1);
            }
        }
        catch (SQLException sqlex)
        {
            System.out.println(sqlex.getMessage());
        }
        return count;
    }

    public abstract T get(int ID);

    public abstract Collection<T> getPage(int pageNumber, int pageSize);

    public abstract Collection<T> search(String sql);

    public abstract boolean add(T eb);

    public abstract boolean update(T eb);

    /**
     * Delete a category record from the datastore.
     * Foreign Key constraints with the Trip table may cause this to fail
     * if the TripCategory record isn't deleted first
     * @return true if successful, false if not
     */
    public boolean delete(T eb)
    {
        try
        {
            Statement statement = conn.createStatement();
            String sql = "DELETE FROM " + getSchema() + getTable() + " where ID=" + eb.getID() + ";";
            statement.execute(sql);
            return true;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Add a collection of objects to the datastore as new records
     *   This is accomplished by looping over each record and calling
     *   the specific class's add method as defined in the Repository interface
     * @param addList A list of elements to add to the datastore
     * @return The number of elements that had errors.  If 0, all elements were processed.
     */
    public int addCollection(Collection<T> addList)
    {
        int elementsProcessed = 0;

        for (Object o : addList)
        {
            T element = (T)o;
            if (add(element))
            {
                elementsProcessed++;
            }
        }

        return (Math.abs(addList.size() - elementsProcessed));
    }

    /**
     * Update a collection of existing objects in the datastore
     *   This is accomplished by looping over each record and calling
     *   the specific class's update method as defined in the Repository interface
     * @param updateList The list of elements to be updated in the datastore
     * @return The number of elements that had errors.  If 0, all elements were processed.
     */
    public int updateCollection(Collection<T> updateList)
    {
        int elementsProcessed = 0;

        for (Object o : updateList)
        {
            T element = (T)o;
            if (update(element))
            {
                elementsProcessed++;
            }
        }
        return (Math.abs(updateList.size() - elementsProcessed));
    }

    /**
     * Delete a collection of existing objects in the datastore
     *   This is accomplished by looping over each record and calling
     *   the specific class's delete method as defined in the Repository interface
     * @param deleteList The list with elements to be updated in the datastore
     * @return The number of elements that had errors.  If 0, all elements were processed.
     */
    public int deleteCollection(Collection<T> deleteList)
    {
        int elementsProcessed = 0;

        for (Object o : deleteList)
        {
            T element = (T)o;
            if (delete(element))
            {
                elementsProcessed++;
            }
        }
        return (deleteList.size() - elementsProcessed);
    }

    /**
     * @return the connection object for interacting with the database
     */
    public Connection getConnection()
    {
        return conn;
    }

    /**
     * @return the name of the schema
     */
    public String getSchema()
    {
        return schema;
    }

    /**
     * @param schema the schema name to set
     */
    public void setSchema(String schema)
    {
        if (schema.length() == 0 || schema ==  null)
        {
            this.schema = "";
        }
        else
        {
            if (schema.charAt(schema.length() - 1) != '.')
            {
                this.schema = schema + ".";
            }
            else
            {
                this.schema = schema;
            }
        }
    }

    /**
     * @return the name of the table
     */
    public String getTable()
    {
        return table;
    }

    /**
     * @param table the table name to set
     */
    public void setTable(String table)
    {
        if (table.isEmpty() || table == null)
        {
            throw new IllegalStateException("Table cannot be empty or null");
        }
        this.table = table;
    }
}

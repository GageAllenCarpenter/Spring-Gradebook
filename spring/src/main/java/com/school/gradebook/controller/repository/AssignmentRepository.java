package com.school.gradebook.controller.repository;

import com.school.gradebook.model.Assignment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A repository object for managing CRUD operations for Assignment objects
 * The CustomerRepo extends RepoBase and is typed to a Customer object.
 * It provides an entire API to create, read, update, and delete Assignments from the datastore
 *
 * @author Gage Carpenter
 * @version 2022.04.23.01
 */
public class AssignmentRepository extends RepoBase<Assignment> implements Repository<Assignment>
{
    private Connection conn;

    /**
     * Constructor for objects of class AssignmentRepository
     */
    public AssignmentRepository()
    {
        super("dbo", "Assignment");
        conn = super.getConnection();
    }

    /**
     * get will retrieve an assignment from the datastore with the given ID,
     *  or return null if not found
     * @return the Customer record with the given ID, or null if not found
     */
    public Assignment get(int ID)
    {
        Statement statement = null;
        ResultSet rs = null;
        Assignment assignment = new Assignment();

        try
        {
            String sql = "select * from " + getSchema() + getTable() + " where ID=" + ID + ";";
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            if (rs != null)
            {
                rs.next();
                assignment.setID(rs.getInt("ID"));
                assignment.setName(rs.getString("Name"));
                assignment.setType(rs.getString("Type"));
                assignment.setPointsPossible(rs.getInt("PointsPossible"));
                return assignment;
            }
        }
        catch (SQLException sqlex)
        {
            System.out.println(sqlex.getMessage());
        }
        return null;
    }

    /**
     * Retrieves a page of records from the database.  If there are no records the list is empty
     * @param pageNumber The page number to display, starting at 1
     * @param pageSize The number of records on each page
     * @return A list of Customers within the range specified from the database
     */
    public ArrayList<Assignment> getPage(int pageNumber, int pageSize)
    {
        ArrayList<Assignment> assignments;
        // Syntax from https://social.technet.microsoft.com/wiki/contents/articles/23811.paging-a-query-with-sql-server.aspx
        String sql = "SELECT * FROM " + getSchema() + getTable() + " ORDER BY ID ";
        sql += " OFFSET " + (pageNumber - 1) + " ROWS"; // 0 is first row index using offset
        sql += " FETCH NEXT " + pageSize + " ROWS ONLY;";
        // Calling getCollection with the above SQL
        assignments = new ArrayList<Assignment>( getCollection(sql) );
        return assignments;
    }

    /**
     * Add an assignment record to the datastore.
     * This uses the getters of the customer parameter to build an
     *   insert SQL string and execute it
     * @return True if successful or false if the insert was unsuccessful
     */
    @Override
    public boolean add(Assignment assignment)
    {
        try
        {
            int primaryKey = 0;
            String sql = "INSERT INTO " + getSchema() + getTable();
            sql += " (Name, Type, PointsPossible) VALUES('";
            sql += assignment.getName()+ "','" + assignment.getType()+ "','" + assignment.getPointsPossible()+ "');";
            Statement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            primaryKey = rs.getInt(1);
            assignment.setID(primaryKey);
            return true;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Update an existing address record in the datastore.
     *   This uses the getters of the add parameter to build an
     *   update SQL string and execute it
     * @return true if successful, false if not
     */
    @Override
    public boolean update(Assignment assignment)
    {
        try
        {
            Statement statement = conn.createStatement();
            String sql = "UPDATE " + getSchema() + getTable() + " SET ";
            sql += "Name = '" + assignment.getName();
            sql += "', Type = '" + assignment.getType();
            sql += ", PointsPossible = " + assignment.getPointsPossible();
            sql += "' where ID = " + assignment.getID()  + ";";
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
     * search will do an SQL lookup matching an guide record with the where clause provided.
     * Calls getCollection to retrieve results, and returns an ArrayList of the Guide instances
     *   that match.
     *   Examples:
     *   String matching: where address like '%1600%'
     *   PostalCode: where Zip &gt; 33333
     * @return ArrayList with records matching the sql where clause parameter
     */
    public ArrayList<Assignment> search(String whereClause)
    {
        ArrayList<Assignment> assignments;

        String sql = "SELECT * FROM " + getSchema() + getTable() + " " + whereClause + ";";
        assignments = new ArrayList<Assignment>( getCollection(sql) );

        return assignments;
    }

    /*
     * getCollection will retrieve a group of Guides that match a given SQL select statement
     *   Used by search and getPage
     * @return ArrayList with records matching the sql select query
     */
    private ArrayList<Assignment> getCollection(String sql)
    {
        ArrayList<Assignment> list = new ArrayList<Assignment>();
        Statement statement = null;
        ResultSet rs = null;

        try
        {
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            while(rs.next())
            {
                Assignment assignment = new Assignment();
                assignment.setID(rs.getInt("ID"));
                assignment.setName(rs.getString("Name"));
                assignment.setType(rs.getString("Type"));
                assignment.setPointsPossible(rs.getInt("PointsPossible"));

                list.add(assignment);
            }
        }
        catch (SQLException sqlex)
        {
            System.out.println(sqlex.getMessage());
        }
        return list;
    }
}

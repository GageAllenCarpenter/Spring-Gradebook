package com.school.gradebook.controller.repository;

import com.school.gradebook.model.Semester;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A repository object for managing CRUD operations for Semester objects
 * The SemesterRepository extends RepoBase and is typed to a Semester object.
 * It provides an entire API to create, read, update, and delete Semesters from the datastore
 *
 * @author Gage Carpenter
 * @Version 2022.04.23.01
 */
public class SemesterRepository extends RepoBase<Semester> implements Repository<Semester>
{
    private Connection conn;

    /**
     * Constructor for objects of class SemesterRepository
     */
    public SemesterRepository()
    {
        super("dbo", "Semester");
        conn = super.getConnection();
    }

    /**
     * get will retrieve a Semester from the datastore with the given ID,
     *  or return null if not found
     * @return the Semester record with the given ID, or null if not found
     */
    public Semester get(int ID)
    {
        Semester semester = new Semester();
        Statement statement = null;
        ResultSet rs = null;

        try
        {
            String sql = "select * from " + getSchema() + getTable() + " where ID=" + ID + ";";
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            if (rs != null)
            {
                rs.next();
                semester.setID(rs.getInt("ID"));
                semester.setStartDate(rs.getString("StartDate"));
                semester.setEndDate(rs.getString("EndDate"));
                return semester;
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
     * @return A list of Students within the range specified from the database
     */
    public ArrayList<Semester> getPage(int pageNumber, int pageSize)
    {
        ArrayList<Semester> semesters;

        String sql = "SELECT * FROM " + getSchema() + getTable() + " ORDER BY ID ";
        sql += " OFFSET " + (pageNumber - 1) + " ROWS"; // 0 is first row index using offset
        sql += " FETCH NEXT " + pageSize + " ROWS ONLY;";

        semesters = new ArrayList<Semester>( getCollection(sql) );
        return semesters;
    }

    /**
     * Add an trip record to the datastore.
     * This uses the getters of the trip parameter to build an
     *   insert SQL string and execute it
     * @return True if successful or false if the insert was unsuccessful
     */
    @Override
    public boolean add(Semester semester)
    {
        try
        {
            int primaryKey = 0;
            String sql = "INSERT INTO " + getSchema() + getTable() + "(StartDate, EndDate) VALUES('";
            sql += semester.getStartDate() + "','";
            sql += semester.getEndDate() + "');";
            Statement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            primaryKey = rs.getInt(1);
            semester.setID(primaryKey);
            return true;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Update an existing category record in the datastore.
     *   This uses the getters of the addr parameter to build an
     *   update SQL string and execute it
     * @return true if successful, false if not
     */
    @Override
    public boolean update(Semester semester)
    {
        try
        {
            Statement statement = conn.createStatement();
            String sql = "UPDATE " + getSchema() + getTable() + " SET ";
            sql +=" StartDate = '" + semester.getStartDate();
            sql +="' EndDate = '"+ semester.getEndDate();
            sql +="' where ID = " + semester.getID()  + ";";
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
    public ArrayList<Semester> search(String whereClause)
    {
        ArrayList<Semester> semesters;

        String sql = "SELECT * FROM " + getSchema() + getTable() + " " + whereClause + ";";
        semesters = new ArrayList<Semester>( getCollection(sql) );

        return semesters;
    }

    /*
     * getCollection will retrieve a group of Guides that match a given SQL select statement
     *   Used by search and getPage
     * @return ArrayList with records matching the sql select query
     */
    private ArrayList<Semester> getCollection(String sql)
    {
        ArrayList<Semester> list = new ArrayList<Semester>();
        Statement statement = null;
        ResultSet rs = null;

        try
        {
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            while(rs.next())
            {
                Semester semester = new Semester();
                semester.setID(rs.getInt("ID"));
                semester.setStartDate(rs.getString("StartDate"));
                semester.setEndDate(rs.getString("EndDate"));
                list.add(semester);
            }
        }
        catch (SQLException sqlex)
        {
            System.out.println(sqlex.getMessage());
        }
        return list;
    }
}

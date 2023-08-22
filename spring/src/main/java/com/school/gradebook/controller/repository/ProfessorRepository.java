package com.school.gradebook.controller.repository;

import com.school.gradebook.model.Professor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A repository object for managing CRUD operations for Professor objects
 * The ProfessorRepository extends RepoBase and is typed to a Professor object.
 * It provides an entire API to create, read, update, and delete Professors from the datastore
 *
 * @author Gage Carpenter
 * @Version 2022.04.23.01
 */
public class ProfessorRepository extends RepoBase<Professor> implements Repository<Professor>
{
    private Connection conn;

    /**
     * Constructor for objects of class ProfessorRepository
     */
    public ProfessorRepository() {
        super("dbo", "Professor");
        conn = super.getConnection();
    }

    /**
     * get will retrieve a professor from the datastore with the given ID,
     *  or return null if not found
     * @return the professors record with the given ID, or null if not found
     */
    public Professor get(int ID)
    {
        Statement statement = null;
        ResultSet rs = null;
        Professor professor = new Professor();

        try
        {
            String sql = "select * from " + getSchema() + getTable() + " where ID=" + ID + ";";
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            if (rs != null)
            {
                rs.next();
                professor.setID(rs.getInt("ID"));
                professor.setFirstName(rs.getString("FirstName"));
                professor.setLastName(rs.getString("LastName"));
                professor.setEmail(rs.getString("Email"));
                professor.setPhoneNumber(rs.getString("PhoneNumber"));
                professor.setAddress(rs.getString("Address"));
                return professor;
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
    public ArrayList<Professor> getPage(int pageNumber, int pageSize)
    {
        ArrayList<Professor> professors;

        String sql = "SELECT * FROM " + getSchema() + getTable() + " ORDER BY ID ";
        sql += " OFFSET " + (pageNumber - 1) + " ROWS"; // 0 is first row index using offset
        sql += " FETCH NEXT " + pageSize + " ROWS ONLY;";

        professors = new ArrayList<Professor>( getCollection(sql) );
        return professors;
    }

    /**
     * Add an trip record to the datastore.
     * This uses the getters of the trip parameter to build an
     *   insert SQL string and execute it
     * @return True if successful or false if the insert was unsuccessful
     */
    @Override
    public boolean add(Professor professor)
    {
        try
        {
            int primaryKey = 0;
            String sql = "INSERT INTO " + getSchema() + getTable() + "(FirstName, LastName, Email, PhoneNumber, Address) VALUES('";
            sql += professor.getFirstName()+ "','";
            sql += professor.getLastName()+ "','";
            sql += professor.getEmail()+ "','";
            sql += professor.getPhoneNumber()+ "','";
            sql += professor.getAddress()+ "');";
            Statement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            primaryKey = rs.getInt(1);
            professor.setID(primaryKey);
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
    public boolean update(Professor professor)
    {
        try
        {
            Statement statement = conn.createStatement();
            String sql = "UPDATE " + getSchema() + getTable() + " SET ";
            sql +=" FirstName = '" + professor.getFirstName();
            sql +="', LastName = '" + professor.getLastName();
            sql +="', Email = '" + professor.getEmail();
            sql +="', PhoneNumber = '" + professor.getPhoneNumber();
            sql +="', Address = '" + professor.getAddress();
            sql +="' where ID = " + professor.getID()  + ";";
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
    public ArrayList<Professor> search(String whereClause)
    {
        ArrayList<Professor> professors;

        String sql = "SELECT * FROM " + getSchema() + getTable() + " " + whereClause + ";";
        professors = new ArrayList<Professor>( getCollection(sql) );

        return professors;
    }

    /*
     * getCollection will retrieve a group of Guides that match a given SQL select statement
     *   Used by search and getPage
     * @return ArrayList with records matching the sql select query
     */
    private ArrayList<Professor> getCollection(String sql)
    {
        ArrayList<Professor> list = new ArrayList<Professor>();
        Statement statement = null;
        ResultSet rs = null;

        try
        {
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            while(rs.next())
            {
                Professor professor = new Professor();
                professor.setID(rs.getInt("ID"));
                professor.setFirstName(rs.getString("FirstName"));
                professor.setLastName(rs.getString("LastName"));
                professor.setEmail(rs.getString("Email"));
                professor.setPhoneNumber(rs.getString("PhoneNumber"));
                professor.setAddress(rs.getString("Address"));
                list.add(professor);
            }
        }
        catch (SQLException sqlex)
        {
            System.out.println(sqlex.getMessage());
        }
        return list;
    }
}

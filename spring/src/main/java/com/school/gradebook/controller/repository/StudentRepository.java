package com.school.gradebook.controller.repository;

import com.school.gradebook.model.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A repository object for managing CRUD operations for Student objects
 * The StudentRepository extends RepoBase and is typed to a Student object.
 * It provides an entire API to create, read, update, and delete Students from the datastore
 *
 * @author Gage Carpenter
 * @Version 2022.04.23.01
 */
public class StudentRepository extends RepoBase<Student> implements Repository<Student>
{
    private Connection conn;

    /**
     * Constructor for objects of class StudentRepository
     */
    public StudentRepository()
    {
        super("dbo", "Student");
        conn = super.getConnection();
    }

    /**
     * get will retrieve a student from the datastore with the given ID,
     *  or return null if not found
     * @return the student record with the given ID, or null if not found
     */
    public Student get(int ID)
    {
        Student student = new Student();
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
                student.setID(rs.getInt("ID"));
                student.setFirstName(rs.getString("FirstName"));
                student.setLastName(rs.getString("LastName"));
                student.setEmail(rs.getString("Email"));
                student.setPhoneNumber(rs.getString("PhoneNumber"));
                student.setAddress(rs.getString("Address"));
                return student;
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
    public ArrayList<Student> getPage(int pageNumber, int pageSize)
    {
        ArrayList<Student> students;

        String sql = "SELECT * FROM " + getSchema() + getTable() + " ORDER BY ID ";
        sql += " OFFSET " + (pageNumber - 1) + " ROWS"; // 0 is first row index using offset
        sql += " FETCH NEXT " + pageSize + " ROWS ONLY;";

        students = new ArrayList<Student>( getCollection(sql) );
        return students;
    }

    /**
     * Add an trip record to the datastore.
     * This uses the getters of the trip parameter to build an
     *   insert SQL string and execute it
     * @return True if successful or false if the insert was unsuccessful
     */
    @Override
    public boolean add(Student student)
    {
        try
        {
            int primaryKey = 0;
            String sql = "INSERT INTO " + getSchema() + getTable() + "(FirstName, LastName, Email, PhoneNumber, Address) VALUES('";
            sql += student.getFirstName()+ "','";
            sql += student.getLastName()+ "','";
            sql += student.getEmail()+ "','";
            sql += student.getPhoneNumber()+ "','";
            sql += student.getAddress()+ "');";
            Statement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            primaryKey = rs.getInt(1);
            student.setID(primaryKey);
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
     *   This uses the getters of the add parameter to build an
     *   update SQL string and execute it
     * @return true if successful, false if not
     */
    @Override
    public boolean update(Student student)
    {
        try
        {
            Statement statement = conn.createStatement();
            String sql = "UPDATE " + getSchema() + getTable() + " SET ";
            sql +=" FirstName = '" + student.getFirstName();
            sql +="', LastName = '" + student.getLastName();
            sql +="', Email = '" + student.getEmail();
            sql +="', PhoneNumber = '" + student.getPhoneNumber();
            sql +="', Address = '" + student.getAddress();
            sql += "' where ID = " + student.getID()  + ";";
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
     * search will do an SQL lookup matching an address record with the where clause provided.
     * Calls getCollection to retrieve results, and returns an ArrayList of the Address instances
     *   that match.
     *   Examples:
     *   String matching: where address like '%1600%'
     *   PostalCode: where Zip &gt; 33333
     * @return ArrayList with records matching the sql where clause parameter
     */
    public ArrayList<Student> search(String whereClause)
    {
        ArrayList<Student> students;

        String sql = "SELECT * FROM " + getSchema() + getTable() + " " + whereClause + ";";
        // Calling getCollection with the above SQL
        students = new ArrayList<Student>( getCollection(sql) );

        return students;
    }

    /*
     * getCollection will retrieve a group of Guides that match a given SQL select statement
     *   Used by search and getPage
     * @return ArrayList with records matching the sql select query
     */
    private ArrayList<Student> getCollection(String sql)
    {
        ArrayList<Student> list = new ArrayList<Student>();
        Statement statement = null;
        ResultSet rs = null;

        try
        {
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            while(rs.next())
            {
                Student student = new Student();
                student.setID(rs.getInt("ID"));
                student.setFirstName(rs.getString("FirstName"));
                student.setLastName(rs.getString("LastName"));
                student.setEmail(rs.getString("Email"));
                student.setPhoneNumber(rs.getString("PhoneNumber"));
                student.setAddress(rs.getString("Address"));
                list.add(student);
            }
        }
        catch (SQLException sqlex)
        {
            System.out.println(sqlex.getMessage());
        }
        return list;
    }
}

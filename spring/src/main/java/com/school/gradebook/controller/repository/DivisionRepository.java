package com.school.gradebook.controller.repository;

import com.school.gradebook.model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A repository object for managing CRUD operations for Division objects
 * The DivisionRepository extends RepoBase and is typed to a Division object.
 * It provides an entire API to create, read, update, and delete Divisions from the datastore
 *
 * @author Gage Carpenter
 * @Version 2022.04.23.01
 */
public class DivisionRepository extends RepoBase<Division> implements Repository<Division>
{
    private Connection conn;

    /**
     * Constructor for objects of class DivisionRepository
     */
    public DivisionRepository()
    {
        super("dbo", "Division");
        conn = super.getConnection();
    }

    /**
     * get will retrieve a Division from the datastore with the given ID,
     *  or return null if not found
     * @return the Division record with the given ID, or null if not found
     */
    public Division get(int ID)
    {

        Statement statement = null;
        ResultSet rs = null;
        Division division = new Division();
        ProfessorRepository pr = new ProfessorRepository();
        SemesterRepository sr = new SemesterRepository();
        CourseRepository cr = new CourseRepository();
        RoomRepository rr = new RoomRepository();

        try
        {
            String sql = "select * from " + getSchema() + getTable() + " where ID=" + ID + ";";
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            if (rs != null)
            {
                rs.next();
                division.setID(rs.getInt("ID"));
                division.setTime(rs.getInt("Time"));
                Professor professor = pr.get(rs.getInt("ProfessorID"));
                division.setProfessor(professor);
                Semester semester = sr.get(rs.getInt("SemesterID"));
                division.setSemester(semester);
                Course course = cr.get(rs.getInt("CourseID"));
                division.setCourse(course);
                Room room = rr.get(rs.getInt("RoomID"));
                division.setRoom(room);
                return division;
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
    public ArrayList<Division> getPage(int pageNumber, int pageSize)
    {
        ArrayList<Division> divisions;

        String sql = "SELECT * FROM " + getSchema() + getTable() + " ORDER BY ID ";
        sql += " OFFSET " + (pageNumber - 1) + " ROWS"; // 0 is first row index using offset
        sql += " FETCH NEXT " + pageSize + " ROWS ONLY;";

        divisions = new ArrayList<Division>( getCollection(sql) );
        return divisions;
    }

    /**
     * Add an division record to the datastore.
     * This uses the getters of the division parameter to build an
     *   insert SQL string and execute it
     * @return True if successful or false if the insert was unsuccessful
     */
    @Override
    public boolean add(Division division)
    {
        try
        {
            int primaryKey = 0;
            String sql = "INSERT INTO " + getSchema() + getTable() + "(Time, ProfessorID, SemesterID, CourseID, RoomID) VALUES('";
            sql += division.getTime()+ "','";
            sql += division.getProfessor().getID()+ "','";
            sql += division.getSemester().getID()+ "','";
            sql += division.getCourse().getID()+ "','";
            sql += division.getRoom().getID() + "');";
            Statement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            primaryKey = rs.getInt(1);
            division.setID(primaryKey);
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
    public boolean update(Division division)
    {
        try
        {
            Statement statement = conn.createStatement();
            String sql = "UPDATE " + getSchema() + getTable() + " SET ";
            sql +=" Time = '" + division.getTime();
            sql +="', ProfessorID = '" + division.getProfessor().getID();
            sql +="', SemesterID = '" + division.getSemester().getID() ;
            sql +="', CourseID = '" + division.getCourse().getID() ;
            sql +="', RoomID = '" + division.getRoom().getID();
            sql +="'  where ID = " + division.getID()  + ";";
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
    public ArrayList<Division> search(String whereClause)
    {
        ArrayList<Division> divisions;

        String sql = "SELECT * FROM " + getSchema() + getTable() + " " + whereClause + ";";
        divisions = new ArrayList<Division>( getCollection(sql) );

        return divisions;
    }

    /*
     * getCollection will retrieve a group of Guides that match a given SQL select statement
     *   Used by search and getPage
     * @return ArrayList with records matching the sql select query
     */
    private ArrayList<Division> getCollection(String sql)
    {
        ArrayList<Division> list = new ArrayList<Division>();
        Statement statement = null;
        ResultSet rs = null;
        ProfessorRepository pr = new ProfessorRepository();
        SemesterRepository sr = new SemesterRepository();
        CourseRepository cr = new CourseRepository();
        RoomRepository rr = new RoomRepository();

        try
        {
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            while(rs.next())
            {
                Division division = new Division();
                division.setID(rs.getInt("ID"));
                division.setTime(rs.getInt("Time"));
                int professorID = rs.getInt("ProfessorID");
                Professor professor = pr.get(professorID);
                division.setProfessor(professor);
                int semesterID = rs.getInt("SemesterID");
                Semester semester = sr.get(semesterID);
                division.setSemester(semester);
                int courseID = rs.getInt("CourseID");
                Course course = cr.get(courseID);
                division.setCourse(course);
                int roomID = rs.getInt("RoomID");
                Room room = rr.get(roomID);
                division.setRoom(room);
                list.add(division);
            }
        }
        catch (SQLException sqlex)
        {
            System.out.println(sqlex.getMessage());
        }
        return list;
    }
}

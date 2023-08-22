package com.school.gradebook.controller.repository;

import com.school.gradebook.model.Course;
import com.school.gradebook.model.Professor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A repository object for managing CRUD operations for Course objects
 * The CourseRepository extends RepoBase and is typed to a Course object.
 * It provides an entire API to create, read, update, and delete Courses from the datastore
 *
 * @author Gage Carpenter
 * @Version 2022.04.23.01
 */
public class CourseRepository extends RepoBase<Course> implements Repository<Course>
{
    private Connection conn;


    /**
     * Constructor for objects of class CourseRepository
     */
    public CourseRepository() {
        super("dbo", "Division");
        conn = super.getConnection();
    }

    /**
     * get will retrieve a Course from the datastore with the given ID,
     *  or return null if not found
     * @return the Course record with the given ID, or null if not found
     */
    public Course get(int ID)
    {
        Statement statement = null;
        ResultSet rs = null;
        Course course = new Course();
        ProfessorRepository pr = new ProfessorRepository();

        try
        {
            String sql = "select * from " + getSchema() + getTable() + " where ID=" + ID + ";";
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            if (rs != null)
            {
                rs.next();
                course.setID(rs.getInt("ID"));
                course.setName(rs.getString("Name"));
                course.setDescription(rs.getString("Description"));
                course.setCreditHour(rs.getInt("CreditHour"));
                Professor professor = pr.get(rs.getInt("ProfessorID"));
                course.setProfessor(professor);
                return course;
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
    public ArrayList<Course> getPage(int pageNumber, int pageSize)
    {
        ArrayList<Course> courses;

        String sql = "SELECT * FROM " + getSchema() + getTable() + " ORDER BY ID ";
        sql += " OFFSET " + (pageNumber - 1) + " ROWS"; // 0 is first row index using offset
        sql += " FETCH NEXT " + pageSize + " ROWS ONLY;";

        courses = new ArrayList<Course>( getCollection(sql) );
        return courses;
    }

    /**
     * Add an trip record to the datastore.
     * This uses the getters of the trip parameter to build an
     *   insert SQL string and execute it
     * @return True if successful or false if the insert was unsuccessful
     */
    @Override
    public boolean add(Course course)
    {
        try
        {
            int primaryKey = 0;
            String sql = "INSERT INTO " + getSchema() + getTable() + "(Name, Description, CreditHour, ProfessorID) VALUES('";
            sql +=  course.getName()+ "','";
            sql += course.getDescription()+ "','";
            sql += course.getCreditHour()+ "','";
            sql += course.getProfessor().getID()+ "');";
            Statement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            primaryKey = rs.getInt(1);
            course.setID(primaryKey);
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
    public boolean update(Course course)
    {
        try
        {
            Statement statement = conn.createStatement();
            String sql = "UPDATE " + getSchema() + getTable() + " SET " ;
            sql +="Name = '" + course.getName();
            sql +="', Description = '" + course.getDescription();
            sql +=", CreditHour = " + course.getCreditHour();
            sql +=", ProfessorID = " + course.getProfessor().getID();
            sql += "'  where ID = " + course.getID()  + ";";
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
    public ArrayList<Course> search(String whereClause)
    {
        ArrayList<Course> courses;

        String sql = "SELECT * FROM " + getSchema() + getTable() + " " + whereClause + ";";
        courses = new ArrayList<Course>( getCollection(sql) );

        return courses;
    }

    /*
     * getCollection will retrieve a group of Guides that match a given SQL select statement
     *   Used by search and getPage
     * @return ArrayList with records matching the sql select query
     */
    private ArrayList<Course> getCollection(String sql)
    {
        ArrayList<Course> list = new ArrayList<Course>();
        Statement statement = null;
        ResultSet rs = null;
        ProfessorRepository pr = new ProfessorRepository();

        try
        {
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            while(rs.next())
            {
                Course course = new Course();
                course.setID(rs.getInt("ID"));
                course.setName(rs.getString("Name"));
                course.setDescription(rs.getString("Description"));
                course.setCreditHour(rs.getInt("CreditHour"));
                int professorID = rs.getInt("ProfessorID");
                Professor professor = pr.get(professorID);
                course.setProfessor(professor);
                list.add(course);
            }
        }
        catch (SQLException sqlex)
        {
            System.out.println(sqlex.getMessage());
        }
        return list;
    }
}

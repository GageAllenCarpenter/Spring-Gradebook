package com.school.gradebook.controller.repository;

import com.school.gradebook.model.Division;
import com.school.gradebook.model.Enrollment;
import com.school.gradebook.model.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A repository object for managing CRUD operations for Enrollment objects
 * The EnrollmentRepository extends RepoBase and is typed to a Enrollment object.
 * It provides an entire API to create, read, update, and delete Enrollment from the datastore
 *
 * @author Gage Carpenter
 * @Version 2022.04.23.01
 */
public class EnrollmentRepository extends RepoBase<Enrollment> implements Repository<Enrollment>
{
    private Connection conn;
    /**
     * Constructor for objects of class EnrollmentRepository
     */
    public EnrollmentRepository()
    {
        super("dbo", "Enrollment");
        conn = super.getConnection();
    }

    /**
     * get will retrieve an Enrollment from the datastore with the given ID,
     *  or return null if not found
     * @return the Enrollment record with the given ID, or null if not found
     */
    public Enrollment get(int ID)
    {
        Enrollment enrollment = new Enrollment();
        DivisionRepository dr = new DivisionRepository();
        StudentRepository sr = new StudentRepository();
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
                enrollment.setID(rs.getInt("ID"));
                enrollment.setEnrollDate(rs.getDate("EnrollDate"));
                enrollment.setCurrentGrade(rs.getInt("CurrentGrade"));
                enrollment.setMidtermGrade(rs.getInt("MidtermGrade"));
                enrollment.setFinalGrade(rs.getInt("FinalGrade"));
                Division division = dr.get(rs.getInt("DivisionID"));
                enrollment.setDivision(division);
                Student student = sr.get(rs.getInt("StudentID"));
                enrollment.setStudent(student);
                return enrollment;
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
    public ArrayList<Enrollment> getPage(int pageNumber, int pageSize)
    {
        ArrayList<Enrollment> enrollments;

        String sql = "SELECT * FROM " + getSchema() + getTable() + " ORDER BY ID ";
        sql += " OFFSET " + (pageNumber - 1) + " ROWS"; // 0 is first row index using offset
        sql += " FETCH NEXT " + pageSize + " ROWS ONLY;";

        enrollments = new ArrayList<Enrollment>( getCollection(sql) );
        return enrollments;
    }

    /**
     * Add an enrollment record to the datastore.
     * This uses the getters of the enrollment parameter to build an
     *   insert SQL string and execute it
     * @return True if successful or false if the insert was unsuccessful
     */
    @Override
    public boolean add(Enrollment enrollment)
    {
        try
        {
            int primaryKey = 0;
            String sql = "INSERT INTO " + getSchema() + getTable() + "(EnrollDate, CurrentGrade, MidtermGrade, FinalGrade, DivisionID, StudentID) VALUES('";
            sql += enrollment.getEnrollDate()+ "','";
            sql += enrollment.getCurrentGrade()+ "','";
            sql += enrollment.getMidtermGrade()+ "','";
            sql += enrollment.getFinalGrade()+ "','";
            sql += enrollment.getDivision().getID()+ "','";
            sql += enrollment.getStudent().getID()+ "');";
            Statement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            primaryKey = rs.getInt(1);
            enrollment.setID(primaryKey);
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
    public boolean update(Enrollment enrollment)
    {
        try
        {
            Statement statement = conn.createStatement();
            String sql = "UPDATE " + getSchema() + getTable() + " SET ";
            sql+= " EnrollDate = '" + enrollment.getEnrollDate();
            sql+= "', CurrentGrade = '" + enrollment.getCurrentGrade();
            sql+= "', MidtermGrade = '" + enrollment.getMidtermGrade();
            sql+= "', FinalGrade = '" + enrollment.getFinalGrade();
            sql+= "', DivisionID = '" + enrollment.getDivision().getID();
            sql+= "', StudentID ='"  + enrollment.getStudent().getID();
            sql+= "'  where ID =" + enrollment.getID()  + ";";
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
    public ArrayList<Enrollment> search(String whereClause)
    {
        ArrayList<Enrollment> enrollments;

        String sql = "SELECT * FROM " + getSchema() + getTable() + " " + whereClause + ";";
        enrollments = new ArrayList<Enrollment>( getCollection(sql) );

        return enrollments;
    }


    /*
     * getCollection will retrieve a group of Guides that match a given SQL select statement
     *   Used by search and getPage
     * @return ArrayList with records matching the sql select query
     */
    private ArrayList<Enrollment> getCollection(String sql)
    {
        ArrayList<Enrollment> list = new ArrayList<Enrollment>();
        Statement statement = null;
        ResultSet rs = null;
        DivisionRepository dr = new DivisionRepository();
        StudentRepository sr = new StudentRepository();

        try
        {
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            while(rs.next())
            {
                Enrollment enrollment = new Enrollment();
                enrollment.setID(rs.getInt("ID"));
                enrollment.setEnrollDate(rs.getDate("EnrollDate"));
                enrollment.setCurrentGrade(rs.getInt("CurrentGrade"));
                enrollment.setMidtermGrade(rs.getInt("MidtermGrade"));
                enrollment.setFinalGrade(rs.getInt("FinalGrade"));
                int divisionID = rs.getInt("DivisionID");
                Division division = dr.get(divisionID);
                enrollment.setDivision(division);
                int studentID = rs.getInt("StudentID");
                Student student = sr.get(studentID);
                enrollment.setStudent(student);
                list.add(enrollment);
            }
        }
        catch (SQLException sqlex)
        {
            System.out.println(sqlex.getMessage());
        }
        return list;
    }
}

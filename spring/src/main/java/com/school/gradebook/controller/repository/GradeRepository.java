package com.school.gradebook.controller.repository;

import com.school.gradebook.model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A repository object for managing CRUD operations for Grade objects
 * The GradeRepository extends RepoBase and is typed to a Grade object.
 * It provides an entire API to create, read, update, and delete Grades from the datastore
 *
 * @author Gage Carpenter
 * @Version 2022.04.23.01
 */
public class GradeRepository extends RepoBase<Grade> implements Repository<Grade> {
    private Connection conn;

    /**
     * Constructor for objects of class GradeRepository
     */
    public GradeRepository()
    {
        super("dbo", "Grade");
        conn = super.getConnection();
    }

    /**
     * get will retrieve a trip from the datastore with the given ID,
     *  or return null if not found
     * @return the Trip record with the given ID, or null if not found
     */
    public Grade get(int ID)
    {
        Grade grade = new Grade();
        AssignmentRepository ar = new AssignmentRepository();
        DivisionRepository dr = new DivisionRepository();
        StudentRepository sr = new StudentRepository();
        ProfessorRepository pr = new ProfessorRepository();
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
                grade.setID(rs.getInt("ID"));
                grade.setPoints(rs.getInt("Points"));
                Assignment assignment = ar.get(rs.getInt("AssignmentID"));
                grade.setAssignment(assignment);
                Division division = dr.get(rs.getInt("DivisionID"));
                grade.setDivision(division);
                Student student = sr.get(rs.getInt("StudentID"));
                grade.setStudent(student);
                Professor professor = pr.get(rs.getInt("ProfessorID"));
                grade.setProfessor(professor);
                return grade;
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
    public ArrayList<Grade> getPage(int pageNumber, int pageSize)
    {
        ArrayList<Grade> grades;

        String sql = "SELECT * FROM " + getSchema() + getTable() + " ORDER BY ID ";
        sql += " OFFSET " + (pageNumber - 1) + " ROWS"; // 0 is first row index using offset
        sql += " FETCH NEXT " + pageSize + " ROWS ONLY;";

        grades = new ArrayList<Grade>( getCollection(sql) );
        return grades;
    }

    /**
     * Add an trip record to the datastore.
     * This uses the getters of the trip parameter to build an
     *   insert SQL string and execute it
     * @return True if successful or false if the insert was unsuccessful
     */
    @Override
    public boolean add(Grade grade)
    {
        try
        {
            int primaryKey = 0;
            String sql = "INSERT INTO " + getSchema() + getTable() +"(Points, AssignmentID, DivisionID, StudentID, ProfessorID) VALUES('";
            sql += grade.getPoints() + "','";
            sql += grade.getAssignment().getID() + "','";
            sql += grade.getDivision().getID() + "','";
            sql += grade.getStudent().getID() + "','";
            sql += grade.getProfessor().getID()+ ");";
            Statement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            primaryKey = rs.getInt(1);
            grade.setID(primaryKey);
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
    public boolean update(Grade grade)
    {
        try
        {
            Statement statement = conn.createStatement();
            String sql = "UPDATE " + getSchema() + getTable() + " SET ";
            sql += "points = '" + grade.getPoints();
            sql +=", AssignmentID = " + grade.getAssignment().getID();
            sql += ", DivisionID = " + grade.getDivision().getID();
            sql +=", StudentID = " + grade.getStudent().getID();
            sql +=", ProfessorID = " + grade.getProfessor().getID();
            sql +="' where ID = " + grade.getID()  + ";";
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
    public ArrayList<Grade> search(String whereClause)
    {
        ArrayList<Grade> grades;

        String sql = "SELECT * FROM " + getSchema() + getTable() + " " + whereClause + ";";
        grades = new ArrayList<Grade>( getCollection(sql) );

        return grades;
    }

    /*
     * getCollection will retrieve a group of Guides that match a given SQL select statement
     *   Used by search and getPage
     * @return ArrayList with records matching the sql select query
     */
    private ArrayList<Grade> getCollection(String sql)
    {
        ArrayList<Grade> list = new ArrayList<Grade>();
        Statement statement = null;
        ResultSet rs = null;
        AssignmentRepository ar = new AssignmentRepository();
        DivisionRepository dr = new DivisionRepository();
        StudentRepository sr = new StudentRepository();
        ProfessorRepository pr = new ProfessorRepository();

        try
        {
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            while(rs.next())
            {
                Grade grade = new Grade();
                grade.setID(rs.getInt("ID"));
                grade.setPoints(rs.getInt("Points"));
                int assignmentID = rs.getInt("AssignmentID");
                Assignment assignment = ar.get(assignmentID);
                grade.setAssignment(assignment);
                int divisionID = rs.getInt("DivisionID");
                Division division = dr.get(divisionID);
                grade.setDivision(division);
                int studentID = rs.getInt("StudentID");
                Student student = sr.get(studentID);
                grade.setStudent(student);
                int professorID = rs.getInt("ProfessorID");
                Professor professor = pr.get(professorID);
                grade.setProfessor(professor);
                list.add(grade);
            }
        }
        catch (SQLException sqlex)
        {
            System.out.println(sqlex.getMessage());
        }
        return list;
    }
}

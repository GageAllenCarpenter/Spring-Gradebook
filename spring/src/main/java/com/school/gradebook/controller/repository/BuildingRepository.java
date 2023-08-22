package com.school.gradebook.controller.repository;

import com.school.gradebook.model.Building;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A repository object for managing CRUD operations for Building objects
 * The BuildingRepository extends RepoBase and is typed to a Building object.
 * It provides an entire API to create, read, update, and delete Buildings from the datastore
 *
 * @author Gage Carpenter
 * @Version 2022.04.23.01
 */
public class BuildingRepository extends RepoBase<Building> implements Repository<Building>
{
    private Connection conn;

    /**
     * Constructor for objects of class BuildingRepository
     */
    public BuildingRepository()
    {
        super("dbo", "Building");
        conn = super.getConnection();
    }

    /**
     * get will retrieve a Building from the datastore with the given ID,
     *  or return null if not found
     * @return the Building record with the given ID, or null if not found
     */
    public Building get(int ID)
    {
        Statement statement = null;
        ResultSet rs = null;
        Building building = new Building();

        try
        {
            String sql = "select * from " + getSchema() + getTable() + " where ID=" + ID + ";";
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            if (rs != null)
            {
                rs.next();
                building.setID(rs.getInt("ID"));
                building.setName(rs.getString("Name"));
                building.setCity(rs.getString("City"));
                building.setState(rs.getString("State"));
                building.setZipcode(rs.getInt("Zipcode"));
                return building;
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
    public ArrayList<Building> getPage(int pageNumber, int pageSize)
    {
        ArrayList<Building> buildings;

        String sql = "SELECT * FROM " + getSchema() + getTable() + " ORDER BY ID ";
        sql += " OFFSET " + (pageNumber - 1) + " ROWS"; // 0 is first row index using offset
        sql += " FETCH NEXT " + pageSize + " ROWS ONLY;";

        buildings = new ArrayList<Building>( getCollection(sql) );
        return buildings;
    }

    /**
     * Add an trip record to the datastore.
     * This uses the getters of the trip parameter to build an
     *   insert SQL string and execute it
     * @return True if successful or false if the insert was unsuccessful
     */
    @Override
    public boolean add(Building building)
    {
        try
        {
            int primaryKey = 0;
            String sql = "INSERT INTO " + getSchema() + getTable() + "(Name, City, State, Zipcode) VALUES('";
            sql +=  building.getName() + "', '";
            sql += building.getCity() + "', '";
            sql += building.getState() + "', '";
            sql += building.getZipcode()+ "');";
            Statement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            primaryKey = rs.getInt(1);
            building.setID(primaryKey);
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
    public boolean update(Building building)
    {
        try
        {
            Statement statement = conn.createStatement();
            String sql = "UPDATE " + getSchema() + getTable() + " SET ";
            sql += " Name = '" + building.getName();
            sql += "', City = '" + building.getCity();
            sql += "', State = '" + building.getState();
            sql += "', Zipcode = '" + building.getZipcode();
            sql += "' where ID = " + building.getID()  + ";";
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
    public ArrayList<Building> search(String whereClause)
    {
        ArrayList<Building> buildings;

        String sql = "SELECT * FROM " + getSchema() + getTable() + " " + whereClause + ";";
        buildings = new ArrayList<Building>( getCollection(sql) );

        return buildings;
    }

    /*
     * getCollection will retrieve a group of Guides that match a given SQL select statement
     *   Used by search and getPage
     * @return ArrayList with records matching the sql select query
     */
    private ArrayList<Building> getCollection(String sql)
    {
        ArrayList<Building> list = new ArrayList<Building>();
        Statement statement = null;
        ResultSet rs = null;

        try
        {
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            while(rs.next())
            {
                Building building = new Building();
                building.setID(rs.getInt("ID"));
                building.setName(rs.getString("Name"));
                building.setCity(rs.getString("City"));
                building.setState(rs.getString("State"));
                building.setZipcode(rs.getInt("Zipcode"));

                list.add(building);
            }
        }
        catch (SQLException sqlex)
        {
            System.out.println(sqlex.getMessage());
        }
        return list;
    }
}

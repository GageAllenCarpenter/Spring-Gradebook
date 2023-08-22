package com.school.gradebook.controller.repository;

import com.school.gradebook.model.Building;
import com.school.gradebook.model.Room;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A repository object for managing CRUD operations for Room objects
 * The RoomRepository extends RepoBase and is typed to a Room object.
 * It provides an entire API to create, read, update, and delete Rooms from the datastore
 *
 * @author Gage Carpenter
 * @Version 2022.04.23.01
 */
public class RoomRepository extends RepoBase<Room> implements Repository<Room>
{
    private Connection conn;
    /**
     * Constructor for objects of class RoomRepository
     */
    public RoomRepository()
    {
        super("dbo", "Room");
        conn = super.getConnection();
    }

    /**
     * get will retrieve a room from the datastore with the given ID,
     *  or return null if not found
     * @return the room record with the given ID, or null if not found
     */
    public Room get(int ID)
    {
        Statement statement = null;
        ResultSet rs = null;
        Room room = new Room();
        BuildingRepository br = new BuildingRepository();

        try
        {
            String sql = "select * from " + getSchema() + getTable() + " where ID=" + ID + ";";
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            if (rs != null)
            {
                rs.next();
                room.setID(rs.getInt("ID"));
                Building building = br.get(rs.getInt("BuildingID"));
                room.setBuilding(building);
                return room;
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
    public ArrayList<Room> getPage(int pageNumber, int pageSize)
    {
        ArrayList<Room> rooms;

        String sql = "SELECT * FROM " + getSchema() + getTable() + " ORDER BY ID ";
        sql += " OFFSET " + (pageNumber - 1) + " ROWS"; // 0 is first row index using offset
        sql += " FETCH NEXT " + pageSize + " ROWS ONLY;";

        rooms = new ArrayList<Room>( getCollection(sql) );
        return rooms;
    }

    /**
     * Add an room record to the datastore.
     * This uses the getters of the room parameter to build an
     *   insert SQL string and execute it
     * @return True if successful or false if the insert was unsuccessful
     */
    @Override
    public boolean add(Room room)
    {
        try
        {
            int primaryKey = 0;
            String sql = "INSERT INTO " + getSchema() + getTable() + "(BuildingID) VALUES('";
            sql += room.getBuilding().getID() + "');";
            Statement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            primaryKey = rs.getInt(1);
            room.setID(primaryKey);
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
    public boolean update(Room room)
    {
        try
        {
            Statement statement = conn.createStatement();
            String sql = "UPDATE " + getSchema() + getTable() + " SET " ;
            sql += "BuildingID = '" + room.getBuilding().getID();
            sql += "' where ID = " + room.getID()  + ";";
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
    public ArrayList<Room> search(String whereClause)
    {
        ArrayList<Room> rooms;

        String sql = "SELECT * FROM " + getSchema() + getTable() + " " + whereClause + ";";
        rooms = new ArrayList<Room>( getCollection(sql) );

        return rooms;
    }

    /*
     * getCollection will retrieve a group of Guides that match a given SQL select statement
     *   Used by search and getPage
     * @return ArrayList with records matching the sql select query
     */
    private ArrayList<Room> getCollection(String sql)
    {
        ArrayList<Room> list = new ArrayList<Room>();
        Statement statement = null;
        ResultSet rs = null;
        BuildingRepository br = new BuildingRepository();

        try
        {
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            while(rs.next())
            {
                Room room = new Room();
                room.setID(rs.getInt("ID"));
                int buildingID = rs.getInt("BuildingID");
                Building building = br.get(buildingID);
                room.setBuilding(building);
                list.add(room);
            }
        }
        catch (SQLException sqlex)
        {
            System.out.println(sqlex.getMessage());
        }
        return list;
    }
}

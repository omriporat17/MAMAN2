package mimounot;

import mimounot.business.Mimouna;
import mimounot.business.MimounaList;
import mimounot.business.ReturnValue;
import mimounot.business.User;
import mimounot.data.DBConnector;

import java.sql.*;
import java.util.ArrayList;


public class Solution {

    private static ReturnValue ExceptionHandler(SQLException exception){

        return ReturnValue.OK;
    }

    private static void close_stmnt_and_conn_aux(Connection connection, Statement statement)
    {
        assert statement != null;
        try {
            statement.close();
        } catch (SQLException e) {
            //  e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            // e.printStackTrace();
        }
    }

    private static ReturnValue execute_stmnt_aux(String SQL)
    {

            Connection connection=DBConnector.getConnection();
            PreparedStatement statement=null;

        try {
            statement=connection.prepareStatement(SQL);
            statement.execute();
        }
        catch (SQLException e)
        {
            return ExceptionHandler(e);
        }
        finally {
            close_stmnt_and_conn_aux(connection,statement);
        }

        return ReturnValue.OK;
    }

    /**
     *  here is the creation, deleting and droping the 3 tables we are working on them
     *
     *  ************************************************************
     *
     *
     */
    public static void createTables(){
        Connection connection= DBConnector.getConnection();
        PreparedStatement statement=null;

            assert connection != null;
            // this try is to create the Users table
            execute_stmnt_aux("CREATE TABLE Users\n" +
                            "(\n" +
                            "    id integer NOT NULL,\n" +
                            "    name text NOT NULL,\n" +
                            "    city text NOT NULL,\n" +
                            "    politician boolean NOT NULL,\n" +
                            "    CONSTRAINT \"Users_pkey \" PRIMARY KEY (id),\n" +
                            "    CONSTRAINT id CHECK (id > 0)\n" +
                            ")");

            ///for mimuna Table...
            execute_stmnt_aux("CREATE TABLE Mimounas\n" +
                    "(\n" +
                    "    id integer NOT NULL,\n" +
                    "    username text NOT NULL,\n" +
                    "    familyname text NOT NULL,\n" +
                    "    city text, \n" +
                    "    guestCount integer, \n" +
                    "    isPoliticianComing boolean, \n" +
                    " CONSTRAINT \"Mimounas_pkey \" PRIMARY KEY (id),\n" +
                    " CONSTRAINT id CHECK (id > 0)\n" +
                    " CONSTRAINT guestCount CHECK (guestCount > 0)\n" +
                    ")");
            execute_stmnt_aux("CREATE TABLE MimounaList\n" +
                    "(\n" +
                    "    id integer NOT NULL,\n" +
                    "    city text NOT NULL, \n" +
                    "    guestCount integer \n" +
                    "    isPoliticianComing boolean \n" +
                    " CONSTRAINT \"MimounaList_pkey \" PRIMARY KEY (id),\n" +
                    " CONSTRAINT id CHECK (id > 0)\n" +
                    ")");

            execute_stmnt_aux("CREATE TABLE Follows\n" +
                    "(\n" +
                    "    id_user integer NOT NULL,\n" +
                    "    id_mimounaList integer NOT NULL,\n"+
                    " CONSTRAINT \"Follows_pkey \" PRIMARY KEY (id_user),\n" +
                    " CONSTRAINT id CHECK (id_user > 0)\n" +
                    " CONSTRAINT \"Follows_pkey \" PRIMARY KEY (id_mimounaList),\n" +
                    " CONSTRAINT id CHECK (id_mimounaList > 0)\n" +
                    ")");

                /*
                statement = connection.prepareStatement("CREATE TABLE Users\n" +
                        "(\n" +
                        "    id integer NOT NULL,\n" +
                        "    name text NOT NULL,\n" +
                        "    city text NOT NULL,\n" +
                        "    politician boolean NOT NULL,\n" +
                        " CONSTRAINT \"Users_pkey \" PRIMARY KEY (id),\n" +
                        "    CONSTRAINT id CHECK (id > 0)\n" +
                        ")");
                statement.execute();

            try {     // this try is to create the Mimuna table
                statement = connection.prepareStatement("CREATE TABLE Mimounas\n" +
                        "(\n" +
                        "    id integer NOT NULL,\n" +
                        "    username text NOT NULL,\n" +
                        "    familyname text NOT NULL,\n" +
                        "    city text, \n" +
                        "    guestCount integer, \n" +
                        "    isPoliticianComing boolean, \n" +
                        " CONSTRAINT \"Mimounas_pkey \" PRIMARY KEY (id),\n" +
                        " CONSTRAINT id CHECK (id > 0)\n" +
                        " CONSTRAINT guestCount CHECK (guestCount > 0)\n" +
                        ")");
                statement.execute();
            } catch (SQLException e) {
                /// the table were already created
            }
            try {     // this try is to create the MimunaList table
                statement = connection.prepareStatement("CREATE TABLE MimounaList\n" +
                        "(\n" +
                        "    id integer NOT NULL,\n" +
                        "    city text NOT NULL, \n" +
                        "    guestCount integer \n" +
                        "    isPoliticianComing boolean \n" +
                        " CONSTRAINT \"MimounaList_pkey \" PRIMARY KEY (id),\n" +
                        " CONSTRAINT id CHECK (id > 0)\n" +
                        ")");
                statement.execute();
            }
            catch (SQLException e) {
                /// the table were already created
            }
            ///TODO: we probably need a table that contains the followers after mimunalist
            try {     // this try is to create the follows relation table
                statement = connection.prepareStatement("CREATE TABLE Follows\n" +
                                "(\n" +
                                "    id_user integer NOT NULL,\n" +
                                "    id_mimounaList integer NOT NULL,\n"+
                                " CONSTRAINT \"Follows_pkey \" PRIMARY KEY (id_user),\n" +
                                " CONSTRAINT id CHECK (id_user > 0)\n" +
                                " CONSTRAINT \"Follows_pkey \" PRIMARY KEY (id_mimounaList),\n" +
                                " CONSTRAINT id CHECK (id_mimounaList > 0)\n" +
                                ")");
                statement.execute();
            } catch (SQLException e) {
                /// the table were already created
            }

        } finally {
            close_stmnt_and_conn_aux(connection,statement);
        }
        */


    }

    public static void clearTables() {
       // Connection connection = DBConnector.getConnection();
       // PreparedStatement statement = null;
        execute_stmnt_aux("DELETE FROM Users");
        execute_stmnt_aux("DELETE FROM Mimounas");
        execute_stmnt_aux("DELETE FROM MimounaList");
        execute_stmnt_aux("DELETE FROM Follows");

        /*
        try {
            statement = connection.prepareStatement("DELETE FROM Users");  // delete user table
            statement.execute();
            statement = connection.prepareStatement("DELETE FROM Mimounas");   //delete mimuna table
            statement.execute();
            statement = connection.prepareStatement("DELETE FROM MimunaList");  // delete mimunaList table
            statement.execute();
            statement=connection.prepareStatement("DELETE FROM Follows");
            statement.execute();
        }
        catch(SQLException e)
        { }
        finally {
            close_stmnt_and_conn_aux(connection,statement);
        }
        */
    }

    public static void dropTables() {
        execute_stmnt_aux("DROP TABLE Users");
        execute_stmnt_aux("DROP TABLE Mimounas");
        execute_stmnt_aux("DROP TABLE MimounaList");
        execute_stmnt_aux("DROP TABLE Follows");



        /*
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DROP TABLE Users");  // drop user table
            statement.executeUpdate();
            statement = connection.prepareStatement("DROP TABLE Mimounas");   //drop mimuna table
            statement.executeUpdate();
            statement = connection.prepareStatement("DROP TABLE MimunaList");  // drop mimunaList table
            statement.executeUpdate();
            statement=connection.prepareStatement("DROP TABLE Follows");
            statement.executeUpdate();
        }
        catch(SQLException e)
        { }
        finally {
            close_stmnt_and_conn_aux(connection,statement);
        }
        */
    }
    /**
     * The API provides the following functions:
     *
     *  ************************************************************
     *  CRUD API:
     *
     */
    public static ReturnValue addUser(User user) {

        Connection connection= DBConnector.getConnection();
        PreparedStatement statement=null;
        try{
            assert connection != null;
           // statement=connection.prepareStatement("INSERT INTO Users(\n" +
            //        "\tid, name, City, Politician)\n" +
            //        "\tVALUES (?, ?, ?, ?);");

            statement.execute("INSERT INTO Users(\n" +
                    "\tid, name, City, Politician)\n" +
                    "\tVALUES (?, ?, ?, ?);");
            assert statement != null;
            statement.setInt(1,user.getId());
            statement.setString(2,user.getName());
            statement.setString(3,user.getCity());
            statement.setBoolean(4,user.getPolitician());

            // I think that from here till the end of function, we can extract all the tries and catch to some function
        } catch (SQLException e) {
            return ReturnValue.ERROR;
        }
/*
        finally {     /// this is to close all the connections and the statement
            close_stmnt_and_conn_aux(connection,statement);
        }
*/
        return ReturnValue.OK;

    }

    public static User getUserProfile(Integer userId) {

        Connection connection= DBConnector.getConnection();
        PreparedStatement statement=null;
        User user=new User();
        try {
            assert connection != null;
            statement=connection.prepareStatement("SELECT name City Politician FROM Users WHERE id= ?");
            statement.setInt(1,userId);
            ResultSet res=statement.executeQuery();
            if(!res.next())
            {
                return user.badUser();
            }
            user.setId(userId);
            user.setName(res.getString(1));
            user.setCity(res.getString(2));
            user.setPolitician(res.getBoolean(3));
            // execute_stmnt_aux(statement);
        } catch (SQLException e) {
            return user.badUser();
        }
        finally {
            close_stmnt_and_conn_aux(connection,statement);
        }
        return user;

    }

    public static ReturnValue deleteUser(User user) {
        Connection connection= DBConnector.getConnection();
        PreparedStatement statement=null;
        try {
            statement=connection.prepareStatement("DELETE FROM Users WHERE id = ?");
            statement.setInt(1,user.getId());
            int deleted=statement.executeUpdate();
            if(deleted==0)   // nothing deleted
            {
                return ReturnValue.NOT_EXISTS;
            }
        } catch (SQLException e) {
            return ReturnValue.ERROR;
        }
        finally {
            close_stmnt_and_conn_aux(connection,statement);
        }
        return ReturnValue.OK;
    }

    public static ReturnValue addMimouna(Mimouna mimouna) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        try {
            assert connection != null;
            statement = connection.prepareStatement("INSERT INTO Mimunas\n\" +\n" +
                    "                    \"\tid, username, familyname , City, guestCount)\n\" +\n" +
                    "                    \"\tVALUES (?, ?, ?, ?, ?);");
            statement.setInt(1,mimouna.getId());
            statement.setString(2,mimouna.getUserName());
            statement.setString(3,mimouna.getFamilyName());
            statement.setString(4,mimouna.getCity());
            statement.setInt(5,mimouna.getGuestCount());
            try {
                statement.execute();
            }
            catch (SQLException e)
            {
                return ExceptionHandler(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ReturnValue.OK;
    }

    public static Mimouna getMimouna(Integer mimounaId) {
        Connection connection= DBConnector.getConnection();
        PreparedStatement statement=null;
        Mimouna mimouna=new Mimouna();
        try {
            assert connection != null;
            statement = connection.prepareStatement("SELECT username familyname City guestCount FROM Mimunas WHERE id= ?");
            statement.setInt(1,mimounaId);
            ResultSet res=statement.executeQuery();
            if(!res.next())
            {
                return mimouna.badMimouna();
            }
            mimouna.setId(mimounaId);
            mimouna.setUserName(res.getString(2));
            mimouna.setFamilyName(res.getString(3));
            mimouna.setCity(res.getString(4));
            mimouna.setGuestCount(res.getInt(5));

        } catch (SQLException e) {
            return mimouna.badMimouna();
        }
        finally {
            close_stmnt_and_conn_aux(connection,statement);
        }
        return mimouna;
    }

    public static ReturnValue deleteMimouna(Mimouna mimouna) {
        Connection connection= DBConnector.getConnection();
        PreparedStatement statement=null;
        try {
            statement = connection.prepareStatement("DELETE FROM Mimounas WHERE id = ?");
            statement.setInt(1,mimouna.getId());
            int deleted=statement.executeUpdate();
            if(deleted==0)   // nothing deleted
            {
                return ReturnValue.NOT_EXISTS;
            }
        }
        catch (SQLException e) {
            return ReturnValue.ERROR;
        }
        finally {
            close_stmnt_and_conn_aux(connection,statement);
        }

        return ReturnValue.OK;
    }

    public static ReturnValue addMimounalist(MimounaList mimounaList) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        try {
            assert connection != null;
            statement = connection.prepareStatement("INSERT INTO MimounaList\n\" +\n" +
                    "                    \"\t id,city)\n\" +\n" +
                    "                    \"\tVALUES (?, ?);");
            statement.setInt(1,mimounaList.getId());
            statement.setString(2,mimounaList.getCity());
            try {
                statement.execute();
            }
            catch (SQLException e)
            {
                return ExceptionHandler(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {     /// this is to close all the connections and the statement
            close_stmnt_and_conn_aux(connection,statement);
        }
        return ReturnValue.OK;

    }

    public static MimounaList getMimounalist(Integer mimounalistId) {
        Connection connection= DBConnector.getConnection();
        PreparedStatement statement=null;
        MimounaList mimounaList=new MimounaList();
        try {
            assert connection != null;
            statement = connection.prepareStatement("SELECT city FROM MimounaList WHERE id= ?");
            statement.setInt(1,mimounalistId);

            ResultSet res=statement.executeQuery();
            if(!res.next())
            {
                return mimounaList.badMimounalist();
            }
            mimounaList.setId(mimounalistId);
            mimounaList.setCity(res.getString(2));

        } catch (SQLException e) {
            return mimounaList.badMimounalist();
        }
        finally {
            close_stmnt_and_conn_aux(connection,statement);
        }
        return mimounaList;
    }

    public static ReturnValue deleteMimounalist(MimounaList mimounalist) {

        Connection connection= DBConnector.getConnection();
        PreparedStatement statement=null;
        try {
            statement = connection.prepareStatement("DELETE FROM MimounaList WHERE id = ?");
            statement.setInt(1,mimounalist.getId());
            int deleted=statement.executeUpdate();
            if(deleted==0)   // nothing deleted
            {
                return ReturnValue.NOT_EXISTS;
            }
        }
        catch (SQLException e) {
            return ReturnValue.ERROR;
        }
        finally {
            close_stmnt_and_conn_aux(connection,statement);
        }

        return ReturnValue.OK;

    }

    /**
     * The API provides the following functions:
     *
     *  ************************************************************
     *  Basic API:
     *
     */


    public static ReturnValue attendMimouna(Integer mimounaId, Integer guests){
        // Mimouna mimouna=null;
        Mimouna mimouna=getMimouna(mimounaId);
        if(mimouna==Mimouna.badMimouna())     // if the mimouna not exist
        {
            return ReturnValue.NOT_EXISTS;
        }
        int prev_guest=mimouna.getGuestCount();
        if(prev_guest+guests<0 || mimounaId<=0)    /// if after the change in guest num, the num is negative
        {
            return ReturnValue.BAD_PARAMS;
        }
        try
        {
            Connection connection= DBConnector.getConnection();
            PreparedStatement statement=null;
            statement=connection.prepareStatement("UPDATE Mimounas SET guestCount=guestCount+guests WHERE id=?");
            statement.setInt(1,mimounaId);
            statement.executeUpdate();
        } catch (SQLException e) {
            return ReturnValue.ERROR;
        }

        return ReturnValue.OK;

    }

    public static ReturnValue confirmAttendancePoliticianToMimouna(Integer mimounaId, Integer userId){
        Mimouna mimouna=getMimouna(mimounaId);
        User user=getUserProfile(userId);
        if(mimouna==Mimouna.badMimouna() || user==User.badUser())
        {
            return ReturnValue.NOT_EXISTS;
        }
        if(!user.getPolitician())
        {
            return ReturnValue.BAD_PARAMS;
        }
        try
        {
            Connection connection= DBConnector.getConnection();
            PreparedStatement statement=null;
            statement=connection.prepareStatement("UPDATE Mimounas SET guestCount=guestCount+1 isPoliticianComing=true");
            statement.executeUpdate();
        } catch (SQLException e) {
            return ReturnValue.ERROR;
        }
        return ReturnValue.OK;

    }

    public static ReturnValue addMimounaToMimounalist(Integer mimounaId, Integer mimounalistId){
        Mimouna mimouna=getMimouna(mimounaId);
        MimounaList mimounaList=getMimounalist(mimounalistId);
        if(mimouna==Mimouna.badMimouna() || mimounaList==MimounaList.badMimounalist())
        {
            return ReturnValue.NOT_EXISTS;
        }
        if(!(mimouna.getCity().equals(mimounaList.getCity())))
        {
            return ReturnValue.BAD_PARAMS;
        }
        try
        {
            Connection connection= DBConnector.getConnection();
            PreparedStatement statement=null;
            statement=connection.prepareStatement("INSERT INTO MimounaList (\n\" +\n" +
                    "                    \"\tid, city)\n\" +\n" +
                    "                    \"\tVALUES (?, ?);");
            statement.setInt(1,mimounaId);
            statement.setString(2,mimouna.getCity());
            statement.execute();
        } catch (SQLException e) {
            return ReturnValue.ERROR;
        }
        return ReturnValue.OK;
    }

    public static ReturnValue removeMimounaFromMimounalist(Integer mimounaId, Integer mimounalistId){

        Mimouna mimouna=getMimouna(mimounaId);
        MimounaList mimounaList=getMimounalist(mimounalistId);
        if(mimouna==Mimouna.badMimouna() || mimounaList==MimounaList.badMimounalist())
        {
            return ReturnValue.NOT_EXISTS;
        }
        try
        {
            Connection connection= DBConnector.getConnection();
            PreparedStatement statement=null;
            statement=connection.prepareStatement("REMOVE MimounaList ");   ////TODO: find how in SQL I remove to the list the mimouna
            statement.execute();
        } catch (SQLException e) {
            return ReturnValue.ERROR;
        }
        return ReturnValue.OK;
    }

    public static ReturnValue followMimounalist(Integer userId, Integer mimounalistId){
        MimounaList mimounaList=getMimounalist(mimounalistId);
        User user=getUserProfile(userId);
        if(mimounaList==MimounaList.badMimounalist() || user==User.badUser())
        {
            return ReturnValue.NOT_EXISTS;
        }
        try
        {
            Connection connection= DBConnector.getConnection();
            PreparedStatement statement=null;
            statement=connection.prepareStatement("SELECT id_user FROM Follows WHERE id_mimounaList=?");
            statement.setInt(1,mimounalistId);
            boolean res=statement.execute(); /// checks. if res true, it means that the user already exist. else, put him inside
            if(res==true)
            {
                return ReturnValue.ALREADY_EXISTS;
            }
            else
            {
                /// here I want to add the tuple (userId,MimounaListID) to the Follows table
                statement=connection.prepareStatement("INSERT INTO Follows\n\" +\n" +
                        "                    \"\t id_user , id_mimounaList)\n\" +\n" +
                        "                    \"\tVALUES (?, ?);");
                statement.setInt(1,userId);
                statement.setInt(2,mimounalistId);
            }
            close_stmnt_and_conn_aux(connection,statement);
        }
        catch (SQLException e)
        {
            return ReturnValue.ERROR;
        }
        return ReturnValue.OK;
    }

    public static ReturnValue stopFollowMimounalist(Integer userId, Integer mimounalistId){
        MimounaList mimounaList=getMimounalist(mimounalistId);
        User user=getUserProfile(userId);
        if(mimounaList==MimounaList.badMimounalist() || user==User.badUser())
        {
            return ReturnValue.NOT_EXISTS;
        }
        try
        {
            Connection connection= DBConnector.getConnection();
            PreparedStatement statement=null;
            statement=connection.prepareStatement("SELECT id_user FROM Follows WHERE id_mimounaList=? AND id_user=?");
            statement.setInt(1,mimounalistId);
            statement.setInt(2,userId);
            boolean res=statement.execute(); /// checks. if res true, it means that the user already exist. else, put him inside
            if(res==false)
            {
                return ReturnValue.NOT_EXISTS;
            }
            else
            {
                /// here I want to add the tuple (userId,MimounaListID) to the Follows table
                statement=connection.prepareStatement("DELETE FROM Follows WHERE id_mimounaList=? AND id_user=?");
                statement.setInt(1,mimounalistId);
                statement.setInt(2,userId);
                statement.execute();
                // statement.setInt(1,userId);
                // statement.setInt(2,mimounalistId);
            }
        }
        catch (SQLException e)
        {
            return ReturnValue.ERROR;
        }

        return ReturnValue.OK;

    }

    //TODO: getMimounaListTotalGuest
    public static Integer getMimounalistTotalGuests(Integer mimounalistId){

        int sum_guest=0;
        MimounaList mimounaList=getMimounalist(mimounalistId);
        if(mimounaList!=MimounaList.badMimounalist()) /// mimounaList is Legal
        {
            //TODO: How do I know which Mimounas Are in the MimounaList?
        }

        return sum_guest;
    }

    public static Integer getMimounalistFollowersCount(Integer mimounalistId) {
        int sum_followers = 0;
        MimounaList mimounaList = getMimounalist(mimounalistId);
        if (mimounaList != MimounaList.badMimounalist()) {
            try {
                Connection connection = DBConnector.getConnection();
                PreparedStatement statement = null;
                statement = connection.prepareStatement("COUNT id_users FROM Follows WHERE id_mimounaList=? ");

            } catch (SQLException e) {

            }

        }
        return sum_followers;
        }

        public static String getMostKnownMimouna(){

            return null;
        }

        public static Integer getMostPopularMimounalist(){
            return 0;
        }

        public static ArrayList<Integer> getMostRatedMimounaList(){
            return null;
        }

        public static ArrayList<Integer> getCloseUsers(Integer userId){



            return null;
        }

        public static ArrayList<Integer> getMimounaListRecommendation (Integer userId){

            return null;
        }

        public static ArrayList<Integer> getTopPoliticianMimounaList(Integer userId) {

        return null;

        }




    }



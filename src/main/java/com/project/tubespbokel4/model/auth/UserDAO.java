package com.project.tubespbokel4.model.auth;

import com.project.tubespbokel4.model.CRUDInterface;
import com.project.tubespbokel4.model.Koneksi;
import com.project.tubespbokel4.model.auth.User;

import java.sql.*;

public class UserDAO implements CRUDInterface {
    protected Connection connection;
    private String insertSql = "INSERT INTO `user`(`name`, `role`, `username`, `password`) VALUES (?,?,?,?)";
    private String updateSql = "UPDATE `user` SET `name` = ?, `wallet` = ?, `password` = ? WHERE `user`.`id` = ? AND `user`.`username` = ?";
    private String deleteSql = "DELETE FROM `user` WHERE `user`.`id` = ?";
    private String loginSql = "SELECT * FROM `user` WHERE `username` = ? && `password` = ?";
    private String getUserFromIdSql = "SELECT * FROM `user` WHERE `user`.`id` = ?";

    public UserDAO() {
        this.connection = Koneksi.connection();
    }

    /**
     * <B>insert</B> <P>
     * input yang diterima berupa sebuah Object <B>Class User</B> dengan attribute yang wajib diisi adalah sebagai berikut :
     * <ul>
     *    <li>name</li>
     *    <li>role</li>
     *    <li>username</li>
     *    <li>password</li>
     * </ul>
     *
     * @param obj
     * @return <B>Integer (Id User)</B>
     * @throws SQLException
     */
    @Override
    public int insert(Object obj) throws SQLException{

        PreparedStatement pstm = null;
        ResultSet rs = null;

        int id = -1;
        try{
            User user = (User) obj;
            pstm = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, user.getName());
            pstm.setString(2, user.getRole().toString());
            pstm.setString(3, user.getUsername());
            pstm.setString(4, user.getPassword());

            pstm.executeUpdate();
            rs = pstm.getGeneratedKeys();
            if(rs.next()) {
                id = rs.getInt(1);
            }

            pstm.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    /**
     * <B>update</B> <P>
     * input yang diterima berupa sebuah Object <B>Class User</B> dengan attribute yang wajib diisi adalah sebagai berikut :
     * <ul>
     *    <li>id</li>
     *    <li>name</li>
     *    <li>wallet</li>
     *    <li>username</li>
     *    <li>password</li>
     * </ul>
     *
     * @param obj
     * @return <B>Boolean</B>
     * @throws SQLException
     */
    @Override
    public boolean update(Object obj) throws SQLException{
        /*
            Mengembalikan nilai
            - True jika Update User Berhasil
            - False jika Update User Gagal
        */
        PreparedStatement pstm = null;
        boolean isSuccess = false;
        try{
            User user = (User) obj;
            pstm = connection.prepareStatement(updateSql);

            pstm.setString(1, user.getName());
            pstm.setInt(2, user.getWallet());
            pstm.setString(3, user.getPassword());
            pstm.setInt(4, user.getId());
            pstm.setString(5, user.getUsername());

            isSuccess = pstm.executeUpdate() == 1;
            pstm.close();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e){
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * <B>delete</B> <P>
     * input yang diterima berupa sebuah id dari <B>User</B>
     *
     * @param id
     * @return <B>Boolean</B>
     * @throws SQLException
     */
    @Override
    public boolean delete(int id) throws SQLException{
        /*
            Mengembalikan nilai
            - True jika Delete User Berhasil
            - False jika Delete User Gagal
        */
        PreparedStatement pstm = null;
        boolean isSuccess = false;
        try{
            pstm = connection.prepareStatement(deleteSql);

            pstm.setInt(1, id);

            isSuccess = pstm.executeUpdate() == 1;
            pstm.close();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e){
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * <B>login</B> <P>
     * input yang diterima berupa sebuah Username dan Password dari <B>User</B>
     *
     * @param username
     * @param password
     * @return Object <B>Class User</B>
     * @throws SQLException
     */
    public User login(String username, String password) throws SQLException{
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;
        try{
            pstm = connection.prepareStatement(loginSql);
            pstm.setString(1, username);
            pstm.setString(2, password);

            rs = pstm.executeQuery();

            if(rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        Roles.valueOf(rs.getString("role")),
                        rs.getInt("wallet"),
                        rs.getString("username"),
                        null
                );
            }
            pstm.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    /**
     * <B>getUserFromId</B> <P>
     * input yang diterima berupa sebuah id <B>User</B>
     *
     * @param id
     * @return Object <B>Class User</B>
     * @throws SQLException
     */
    public User getUserFromId(int id) throws SQLException{
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;
        try{
            pstm = connection.prepareStatement(getUserFromIdSql);
            pstm.setInt(1, id);

            rs = pstm.executeQuery();

            if(rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        Roles.valueOf(rs.getString("role")),
                        rs.getInt("wallet"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
            pstm.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * <B>closeConnection</B> <P>
     * menutup koneksi yang terjadi antara database dengan aplikasi
     */
    @Override
    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package com.project.tubespbokel4.model.auth;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserStoreDAO extends UserDAO{
    private String insertSql = "INSERT INTO `toko` (`id`, `store_name`, `description`) VALUES (?, ?, ?)";
    private String updateSql = "UPDATE `toko` SET `store_name` = ?,`description` = ? WHERE `toko`.`id` = ?";
    private String deleteSql = "DELETE FROM `toko` WHERE `toko`.`id` = ?";
    private String getUserFromIdSql = "SELECT * FROM `toko` WHERE `toko`.`id` = ?";
    public UserStoreDAO() {
        super();
    }

    /**
     * <B>insert</B> <P>
     * input yang diterima berupa sebuah Object <B>Class UserStore</B> dengan attribute yang wajib diisi adalah sebagai berikut :
     * <ul>
     *     <li>Object <B>Class User</B>
     *      <ul>
     *         <li>name</li>
     *         <li>role</li>
     *         <li>username</li>
     *         <li>password</li>
     *      </ul>
     *     </li>
     *     <li>store_name</li>
     *     <li>description</li>
     * </ul>
     *
     * @param userStore
     * @return <B>Integer (Id User)</B>
     * @throws SQLException
     */
    public int insert(UserStore userStore) throws SQLException {
        /*
            Jika berhasil mengembalikan nilai id dari hasil insert
        */
        PreparedStatement pstm = null;
        ResultSet rs = null;

        int id = -1;
        try{
            super.connection.setAutoCommit(false);
            pstm = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

            pstm.setInt(1, super.insert(userStore));
            pstm.setString(2, userStore.getStoreName());
            pstm.setString(3, userStore.getDescription());

            pstm.executeUpdate();
            rs = pstm.getGeneratedKeys();
            if(rs.next()) id = rs.getInt(1);

            super.connection.commit();
            pstm.close();
            rs.close();
        } catch (SQLException ex) {
            try{
                if(super.connection != null){
                    super.connection.rollback();
                }
            }catch (SQLException e){
                throw e;
            }
            throw ex;
        } catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    /**
     * <B>update</B> <P>
     * input yang diterima berupa sebuah Object <B>Class UserStore</B> dengan attribute yang wajib diisi adalah sebagai berikut :
     * <ul>
     *     <li>Object <B>Class User</B>
     *      <ul>
     *          <li>id</li>
     *          <li>name</li>
     *          <li>username</li>
     *          <li>password</li>
     *      </ul>
     *     </li>
     *     <li>store_name</li>
     *     <li>description</li>
     * </ul>
     *
     * @param userStore
     * @return <B>Boolean</B>
     * @throws SQLException
     */
    public boolean update(UserStore userStore) throws SQLException {
        /*
            Mengembalikan nilai
            - True jika Update User Berhasil
            - False jika Update User Gagal
        */
        PreparedStatement pstm = null;
        boolean isSuccess = false;
        try{
            super.connection.setAutoCommit(false);
            pstm = connection.prepareStatement(updateSql);

            if(super.update(userStore)){
                pstm.setString(1, userStore.getStoreName());
                pstm.setString(2, userStore.getDescription());
                pstm.setInt(3, userStore.getId());
            }

            isSuccess = pstm.executeUpdate() == 1;
            super.connection.commit();
            pstm.close();
        } catch (SQLException ex) {
            try{
                if(super.connection != null){
                    super.connection.rollback();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            throw ex;
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
    public boolean delete(int id) throws SQLException {

        PreparedStatement pstm = null;
        boolean isSuccess = false;
        try{
            super.connection.setAutoCommit(false);
            pstm = connection.prepareStatement(deleteSql);

            pstm.setInt(1, id);
            isSuccess = pstm.executeUpdate() == 1;

            if(isSuccess){
                isSuccess = super.delete(id);
            }

            super.connection.commit();
            pstm.close();
        } catch (SQLException ex) {
            try{
                if(super.connection != null){
                    super.connection.rollback();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            throw ex;
        } catch (Exception e){
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * <B>getUserFromId</B> <P>
     * input yang diterima berupa sebuah id User
     *
     * @param id
     * @return Object <B>Class UserStore</B>
     * @throws SQLException
     */
    public UserStore getUserFromId(int id) throws SQLException{
        PreparedStatement pstm = null;
        ResultSet rs = null;
        UserStore user = null;
        try{
            super.connection.setAutoCommit(true);
            pstm = connection.prepareStatement(getUserFromIdSql);
            pstm.setInt(1, id);

            rs = pstm.executeQuery();

            if(rs.next()) {
                user = new UserStore(
                        super.getUserFromId(id),
                        rs.getString("store_name"),
                        rs.getString("description")
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
}

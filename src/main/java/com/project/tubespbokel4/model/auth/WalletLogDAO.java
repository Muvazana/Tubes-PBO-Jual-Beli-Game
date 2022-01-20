package com.project.tubespbokel4.model.auth;

import com.project.tubespbokel4.model.CRUDInterface;
import com.project.tubespbokel4.model.Koneksi;
import com.project.tubespbokel4.model.toko.Transaction;
import com.project.tubespbokel4.model.toko.TransactionStatus;

import java.sql.*;
import java.util.ArrayList;

public class WalletLogDAO implements CRUDInterface {
    private Connection connection;
    private String insertSql = "INSERT INTO `tb_wallet_log`(`user_req_id`, `wallet`) VALUES (?,?)";
    private String updateSql = "UPDATE `tb_wallet_log` SET `status`=? WHERE `tb_wallet_log`.`id`=?";
    private String deleteSql = "";
    private String getAllWalletLogsSql = "SELECT `tb_wallet_log`.*, `user`.`name` AS 'user_req_name' FROM `tb_wallet_log` JOIN `user` on `tb_wallet_log`.`user_req_id` = `user`.`id`";

    public WalletLogDAO() {
        this.connection = Koneksi.connection();
    }

    /**
     * <B>insert</B> <P>
     * input yang diterima berupa sebuah Object <B>Class WalletLog</B> dengan attribute yang wajib diisi adalah sebagai berikut :
     *
     * <ul>
     *    <li>user_req_id</li>
     *    <li>wallet</li>
     * </ul>
     *
     * @param obj
     * @return <B>Integer (Id WalletLog)</B>
     * @throws SQLException
     */
    @Override
    public int insert(Object obj) throws SQLException {
        /*
            Jika berhasil mengembalikan nilai id dari hasil insert
        */
        PreparedStatement pstm = null;
        ResultSet rs = null;

        int id = -1;
        try{
            WalletLog walletLog = (WalletLog) obj;
            pstm = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

            pstm.setInt(1, walletLog.getUser_req_id());
            pstm.setInt(2, walletLog.getWallet());

            pstm.executeUpdate();
            rs = pstm.getGeneratedKeys();
            if(rs.next()) id = rs.getInt(1);
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
     * input yang diterima berupa sebuah Object <B>Class WalletLog</B> dengan attribute yang wajib diisi adalah sebagai berikut :
     * <ul>
     *    <li>id</li>
     *    <li>status</li>
     * </ul>
     *
     * @param obj
     * @return <B>Boolean</B>
     * @throws SQLException
     */
    @Override
    public boolean update(Object obj) throws SQLException {
        /*
            Mengembalikan nilai
            - True jika Update WalletLogDAO Berhasil
            - False jika Update WalletLogDAO Gagal
        */
        PreparedStatement pstm = null;
        boolean isSuccess = false;
        try{
            WalletLog walletLog = (WalletLog) obj;
            pstm = connection.prepareStatement(updateSql);

            pstm.setString(1, walletLog.getStatus().toString());
            pstm.setInt(2, walletLog.getId());

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
     * Fungsi <B>delete</B> tidak digunakan pada Class ini!
     */
    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    /**
     * <B>getAllWalletLogs</B> <P>
     * mengambil seluruh data WalletLog dari database
     *
     * @return Object <B>ArrayList (WalletLog)</B>
     * @throws SQLException
     */
    public ArrayList<WalletLog> getAllWalletLogs() throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<WalletLog> walletLogs = new ArrayList<>();
        try{
            pstm = connection.prepareStatement(getAllWalletLogsSql);

            rs = pstm.executeQuery();

            while(rs.next()) {
                WalletLog walletLog = new WalletLog(
                        rs.getInt("id"),
                        rs.getInt("user_req_id"),
                        rs.getInt("wallet"),
                        WalletLogStatus.valueOf(rs.getString("status"))
                );
                walletLog.getMember().setName(rs.getString("user_req_name"));
                walletLogs.add(walletLog);
            }
            pstm.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e){
            e.printStackTrace();
        }
        return walletLogs;
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

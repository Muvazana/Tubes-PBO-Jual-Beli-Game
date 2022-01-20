package com.project.tubespbokel4.model.toko;

import com.project.tubespbokel4.model.CRUDInterface;
import com.project.tubespbokel4.model.Koneksi;
import com.project.tubespbokel4.model.auth.UserStore;

import java.sql.*;
import java.util.ArrayList;

public class TransactionDAO implements CRUDInterface {
    private Connection connection;
    private String insertSql = "INSERT INTO `tb_transaction`(`user_member_id`, `product_id`, `total_price`, `note`) VALUES (?,?,?,?)";
    private String updateSql = "UPDATE `tb_transaction` SET `redeem_code`=?,`product_rate`=?,`status`=? WHERE `tb_transaction`.`id` = ?";
    private String deleteSql = "";
    private String getAllTransactionsSql = "SELECT `tb_transaction`.*, `tb_products`.`name` as 'product_name', `user`.`name` as 'user_member_name', `toko`.`store_name` FROM `tb_transaction` JOIN `tb_products` ON `tb_transaction`.`product_id` = `tb_products`.`id` JOIN `user` ON `tb_transaction`.`user_member_id` = `user`.`id` JOIN `toko` ON `tb_products`.`store_id` = `toko`.`id`";
    private String getTransactionByMemberIdSQL = "SELECT `tb_transaction`.*, `tb_products`.`name` as 'product_name', `user`.`name` as 'user_member_name', `toko`.`id` AS 'store_id', `toko`.`store_name` FROM `tb_transaction` JOIN `tb_products` ON `tb_transaction`.`product_id` = `tb_products`.`id` JOIN `user` ON `tb_transaction`.`user_member_id` = `user`.`id` JOIN `toko` ON `tb_products`.`store_id` = `toko`.`id` WHERE `tb_transaction`.`user_member_id` = ?";
    private String getTransactionByStoreIdSQL = "SELECT `tb_transaction`.*, `tb_products`.`name` as 'product_name', `user`.`name` as 'user_member_name', `toko`.`store_name` FROM `tb_transaction` JOIN `tb_products` ON `tb_transaction`.`product_id` = `tb_products`.`id` JOIN `user` ON `tb_transaction`.`user_member_id` = `user`.`id` JOIN `toko` ON `tb_products`.`store_id` = `toko`.`id` WHERE `toko`.`id` = ?";
    private String getFinalProductRateSql = "SELECT SUM(`product_rate`) / COUNT(`id`) AS 'final_product_rate' FROM `tb_transaction` WHERE `tb_transaction`.`product_id` = ? AND`tb_transaction`.`status` = 'done'";
    private String getCountProductSoldSql = "SELECT COUNT(`id`) AS 'product_sold' FROM `tb_transaction` WHERE `tb_transaction`.`product_id` = ? AND `tb_transaction`.`status` = 'done'";

    public TransactionDAO() {
        this.connection = Koneksi.connection();
    }

    /**
     * <B>insert</B> <P>
     * input yang diterima berupa sebuah Object <B>Class Transaction</B> dengan attribute yang wajib diisi adalah sebagai berikut :
     * <ul>
     *    <li>user_member_id</li>
     *    <li>product_id</li>
     *    <li>note</li>
     * </ul>
     *
     * @param obj
     * @return <B>Integer (Id Transaction)</B>
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
            Transaction transaction = (Transaction) obj;
            pstm = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

            pstm.setInt(1, transaction.getUser_member_id());
            pstm.setInt(2, transaction.getProduct_id());
            pstm.setInt(3, transaction.getTotal_price());
            pstm.setString(4, transaction.getNote());

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
     * input yang diterima berupa sebuah Object <B>Class Transaction</B> dengan attribute yang wajib diisi adalah sebagai berikut :
     * <ul>
     *    <li>id</li>
     *    <li>redeeom_code</li>
     *    <li>product_rate</li>
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
            - True jika Update TransactionDAO Berhasil
            - False jika Update TransactionDAO Gagal
        */
        PreparedStatement pstm = null;
        boolean isSuccess = false;
        try{
            Transaction transaction = (Transaction) obj;
            pstm = connection.prepareStatement(updateSql);

            pstm.setString(1, transaction.getRedeeom_code());
            pstm.setInt(2, transaction.getProduct_rate());
            pstm.setString(3, transaction.getStatus().toString());
            pstm.setInt(4, transaction.getId());

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
     * <B>getTransactionByMemberId</B> <P>
     * mengambil seluruh data Transaction dari database by Member ID
     *
     * @return Object <B>ArrayList (Transaction)</B>
     * @throws SQLException
     */
    public ArrayList<Transaction> getTransactionByMemberId(int id) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<Transaction> transactions = new ArrayList<>();
        try{
            pstm = connection.prepareStatement(getTransactionByMemberIdSQL);
            pstm.setInt(1, id);

            rs = pstm.executeQuery();

            while(rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("id"),
                        rs.getInt("user_member_id"),
                        rs.getInt("product_id"),
                        rs.getInt("total_price"),
                        rs.getString("note"),
                        rs.getString("redeem_code"),
                        rs.getInt("product_rate"),
                        TransactionStatus.valueOf(rs.getString("status"))
                );
                transaction.getProduct().setName(rs.getString("product_name"));
                transaction.getMember().setName(rs.getString("user_member_name"));
                transaction.getSeller().setId(rs.getInt("store_id"));
                transaction.getSeller().setStoreName(rs.getString("store_name"));
                transactions.add(transaction);
            }
            pstm.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e){
            e.printStackTrace();
        }
        return transactions;
    }

    /**
     * <B>getTransactionByStoreId</B> <P>
     * mengambil seluruh data Transaction dari database by Store ID
     *
     * @return Object <B>ArrayList (Transaction)</B>
     * @throws SQLException
     */
    public ArrayList<Transaction> getTransactionByStoreId(int id) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<Transaction> transactions = new ArrayList<>();
        try{
            pstm = connection.prepareStatement(getTransactionByStoreIdSQL);
            pstm.setInt(1, id);

            rs = pstm.executeQuery();

            while(rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("id"),
                        rs.getInt("user_member_id"),
                        rs.getInt("product_id"),
                        rs.getInt("total_price"),
                        rs.getString("note"),
                        rs.getString("redeem_code"),
                        rs.getInt("product_rate"),
                        TransactionStatus.valueOf(rs.getString("status"))
                );
                transaction.getProduct().setName(rs.getString("product_name"));
                transaction.getMember().setName(rs.getString("user_member_name"));
                transaction.getSeller().setName(rs.getString("store_name"));
                transactions.add(transaction);
            }
            pstm.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e){
            e.printStackTrace();
        }
        return transactions;
    }

    /**
     * <B>getFinalProductRate</B> <P>
     * mengambil final rating dari sebuah produk yang telah diulas
     *
     * @param product_id
     * @return <B>Double</b>
     */
    public double getFinalProductRate(int product_id){

        PreparedStatement pstm = null;
        ResultSet rs = null;
        double rate = 0.0;
        try{
            pstm = connection.prepareStatement(getFinalProductRateSql);
            pstm.setInt(1, product_id);

            rs = pstm.executeQuery();

            if(rs.next()) {
                rate = rs.getDouble("final_product_rate");
            }
            pstm.close();
            rs.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return rate;
    }

    /**
     * <B>getCountProductSold</B> <P>
     * mengambil jumlah product yang telah terjual
     *
     * @param product_id
     * @return <B>Integer</b>
     */
    public int getCountProductSold(int product_id){

        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;
        try{
            pstm = connection.prepareStatement(getCountProductSoldSql);
            pstm.setInt(1, product_id);

            rs = pstm.executeQuery();

            if(rs.next()) {
                count = rs.getInt("product_sold");
            }
            pstm.close();
            rs.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return count;
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

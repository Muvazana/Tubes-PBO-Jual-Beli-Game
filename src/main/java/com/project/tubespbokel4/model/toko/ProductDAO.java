package com.project.tubespbokel4.model.toko;

import com.project.tubespbokel4.model.CRUDInterface;
import com.project.tubespbokel4.model.Koneksi;
import com.project.tubespbokel4.model.auth.User;
import com.project.tubespbokel4.model.auth.UserStore;

import java.sql.*;
import java.util.ArrayList;

public class ProductDAO implements CRUDInterface {
    private Connection connection;
    private String insertSql = "INSERT INTO `tb_products`(`store_id`, `name`, `type`, `stock`, `price`, `rate`, `description`) VALUES (?,?,?,?,?,?,?)";
    private String updateSql = "UPDATE `tb_products` SET `name`=?,`type`=?,`stock`=?,`price`=?,`rate`=?,`description`=? WHERE `tb_products`.`id` = ?";
    private String deleteSql = "DELETE FROM `tb_products` WHERE `tb_products`.`id`=?";
    private String getProductByIdSql = "SELECT `tb_products`.*, `toko`.`store_name`, `toko`.`description` as 'store_description' FROM `tb_products` JOIN `toko` ON `tb_products`.`store_id` = `toko`.`id` WHERE `tb_products`.`id` = ?";
    private String getProductsByTypeSql = "SELECT `tb_products`.*, `toko`.`store_name`, `toko`.`description` as 'store_description' FROM `tb_products` JOIN `toko` ON `tb_products`.`store_id` = `toko`.`id` WHERE `tb_products`.`type` = ?";
    private String getProductsByStoreIdSql = "SELECT * FROM `tb_products` WHERE `tb_products`.`store_id` = ?";

    public ProductDAO() {
        this.connection = Koneksi.connection();
    }

    /**
     * <B>insert</B> <P>
     * input yang diterima berupa sebuah Object <B>Class Product</B> dengan attribute yang wajib diisi adalah sebagai berikut :
     * <ul>
     *    <li>store_id</li>
     *    <li>name</li>
     *    <li>type</li>
     *    <li>stock</li>
     *    <li>price</li>
     *    <li>rate</li>
     *    <li>description</li>
     * </ul>
     *
     * @param obj
     * @return <B>Integer (Id Product)</B>
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
            Product product = (Product) obj;
            pstm = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

            pstm.setInt(1, product.getStore_id());
            pstm.setString(2, product.getName());
            pstm.setString(3, product.getType().toString());
            pstm.setInt(4, product.getStock());
            pstm.setInt(5, product.getPrice());
            pstm.setString(6, product.getRate());
            pstm.setString(7, product.getDescription());

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
     * input yang diterima berupa sebuah Object <B>Class Product</B> dengan attribute yang wajib diisi adalah sebagai berikut :
     * <ul>
     *    <li>id</li>
     *    <li>name</li>
     *    <li>type</li>
     *    <li>stock</li>
     *    <li>price</li>
     *    <li>rate</li>
     *    <li>description</li>
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
            - True jika Update Product Berhasil
            - False jika Update Product Gagal
        */
        PreparedStatement pstm = null;
        boolean isSuccess = false;
        try{
            Product product = (Product) obj;
            pstm = connection.prepareStatement(updateSql);

            pstm.setString(1, product.getName());
            pstm.setString(2, product.getType().toString());
            pstm.setInt(3, product.getStock());
            pstm.setInt(4, product.getPrice());
            pstm.setString(5, product.getRate());
            pstm.setString(6, product.getDescription());
            pstm.setInt(7, product.getId());

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
     * input yang diterima berupa sebuah id dari <B>Product</B>
     *
     * @param id
     * @return <B>Boolean</B>
     * @throws SQLException
     */
    @Override
    public boolean delete(int id) throws SQLException {
        /*
            Mengembalikan nilai
            - True jika Delete Product Berhasil
            - False jika Delete Product Gagal
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
     * <B>getProductById</B> <P>
     * input yang diterima berupa sebuah id <B>Product</B>
     *
     * @param id
     * @return Object <B>Class Product</B>
     * @throws SQLException
     */
    public Product getProductById(int id) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Product product = null;
        try{
            pstm = connection.prepareStatement(getProductByIdSql);
            pstm.setInt(1, id);

            rs = pstm.executeQuery();

            if(rs.next()) {
                product = new Product(
                        rs.getInt("id"),
                        rs.getInt("store_id"),
                        rs.getString("name"),
                        ProductType.valueOf(rs.getString("type")),
                        rs.getInt("stock"),
                        rs.getInt("price"),
                        rs.getString("rate"),
                        rs.getString("description")
                );
                product.setSeller(new UserStore(
                        new User(),
                        rs.getString("store_name"),
                        rs.getString("store_description")
                ));
            }
            pstm.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e){
            e.printStackTrace();
        }
        return product;
    }

    /**
     * <B>getProductsByType</B> <P>
     * mengambil seluruh data Product dari database dengan filter pada kolom <B>type</B>
     *
     * @param type
     * @return Object <B>ArrayList (Product)</B>
     * @throws SQLException
     */
    public ArrayList<Product> getProductsByType(ProductType type) throws SQLException{

        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<Product> products = new ArrayList<>();
        try{
            pstm = connection.prepareStatement(getProductsByTypeSql);
            pstm.setString(1, type.toString());

            rs = pstm.executeQuery();

            while(rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getInt("store_id"),
                        rs.getString("name"),
                        ProductType.valueOf(rs.getString("type")),
                        rs.getInt("stock"),
                        rs.getInt("price"),
                        rs.getString("rate"),
                        rs.getString("description")
                );
                product.setSeller(new UserStore(
                        new User(),
                        rs.getString("store_name"),
                        rs.getString("store_description")
                ));
                products.add(product);
            }
            pstm.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e){
            e.printStackTrace();
        }
        return products;
    }

    /**
     * <B>getProductsByStoreId</B> <P>
     * mengambil seluruh data Product dari database dengan filter pada kolom <B>store_id</B>
     *
     * @param store_id
     * @return Object <B>ArrayList (Product)</B>
     * @throws SQLException
     */
    public ArrayList<Product> getProductsByStoreId(int store_id) throws SQLException{

        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<Product> products = new ArrayList<>();
        try{
            pstm = connection.prepareStatement(getProductsByStoreIdSql);
            pstm.setInt(1, store_id);

            rs = pstm.executeQuery();

            while(rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getInt("store_id"),
                        rs.getString("name"),
                        ProductType.valueOf(rs.getString("type")),
                        rs.getInt("stock"),
                        rs.getInt("price"),
                        rs.getString("rate"),
                        rs.getString("description")
                );
                products.add(product);
            }
            pstm.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e){
            e.printStackTrace();
        }
        return products;
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

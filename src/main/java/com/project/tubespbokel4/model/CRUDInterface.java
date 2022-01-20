package com.project.tubespbokel4.model;

import java.sql.SQLException;

public interface CRUDInterface {
    int insert(Object obj) throws SQLException;
    boolean update(Object obj) throws SQLException;
    boolean delete(int id) throws SQLException;

    void closeConnection();
}

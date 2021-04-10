/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat.utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author macbook
 */
public class DBUtils {

    public static Connection getConnection() throws NamingException, SQLException {
        Connection cn = null;
        Context context = (Context) new InitialContext();
        Context end = (Context) context.lookup("java:comp/env");
        DataSource ds = (DataSource) end.lookup("DBCon");
        cn = ds.getConnection();
        return cn;
    }
}

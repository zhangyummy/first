package cn.itcast.test;

import oracle.jdbc.OracleTypes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleTest {
    private Connection connection;
    private CallableStatement callableStatement;

    @Before
    public void init() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.25.168:1521:xe", "scott", "tiger");
    }

    @After
    public void destroy() throws ClassNotFoundException, SQLException {
        callableStatement.close();
        connection.close();
    }

    /**
     * {?= call <procedure-name>[(<arg1>,<arg2>, ...)]}
     * {call <procedure-name>[(<arg1>,<arg2>, ...)]}
     */

    //p_yearsal(in_empno number, yearsal out number)
    @Test
    public void testP() throws SQLException {
        callableStatement = connection.prepareCall("{call p_yearsal(?, ?)}");
        callableStatement.setObject(1, 7788);
        callableStatement.registerOutParameter(2, OracleTypes.NUMBER);
        callableStatement.execute();
        System.out.println(callableStatement.getObject(2));
    }

    @Test
    public void testF() throws SQLException {
        callableStatement = connection.prepareCall("{? = call f_yearsal(?)}");
        callableStatement.registerOutParameter(1, OracleTypes.NUMBER);
        callableStatement.setObject(2, 7788);
        callableStatement.execute();
        System.out.println(callableStatement.getObject(1));
    }


}

package com.manu.jdbc.helper;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by ManuGarg on 17/9/14.
 */
public interface ISqlHelper<T> {

    public Statement getExecutableStatement(Connection conn, T event);

}

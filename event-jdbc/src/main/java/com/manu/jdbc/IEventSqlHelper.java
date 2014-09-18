package com.manu.jdbc;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by ManuGarg on 19/9/14.
 */
public interface IEventSqlHelper<T> {

    public Statement getExecutableStatement(Connection conn, T event);

}

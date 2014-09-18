package com.manu.jdbc.com.manu.jdbc.impl;

import com.manu.jdbc.IEventSqlHelper;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by ManuGarg on 19/9/14.
 */
public class EventSqlHelper<T> implements IEventSqlHelper<T> {

    @Override
    public Statement getExecutableStatement(Connection conn, T event) {
        return null;
    }
}

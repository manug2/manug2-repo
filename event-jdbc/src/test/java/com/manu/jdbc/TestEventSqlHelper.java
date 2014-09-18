package com.manu.jdbc;

import com.manu.jdbc.com.manu.jdbc.event.FileArrivalEvent;
import com.manu.jdbc.com.manu.jdbc.impl.EventSqlHelper;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Statement;
import java.util.Date;

/**
 * Created by ManuGarg on 19/9/14.
 */
public class TestEventSqlHelper {

    @Test
    public void ShouldInvokeHelper() {
        FileArrivalEvent ev = new FileArrivalEvent(1, new Date(System.currentTimeMillis()), "Firstfile");
        IEventSqlHelper<FileArrivalEvent> sh = new EventSqlHelper<FileArrivalEvent>();
        Statement stmt = sh.getExecutableStatement(null, ev);
        Assert.assertNotNull(stmt);
    }

}

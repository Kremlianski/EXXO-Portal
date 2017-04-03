package ru.EXXO;

import javax.sql.DataSource;

public class DBSOURCE {

    public static final DBSOURCE INSTANCE = new DBSOURCE();

    public DataSource getSource() {
        org.postgresql.ds.PGSimpleDataSource source = new org.postgresql.ds.PGSimpleDataSource();
        source.setServerName(ru.EXXO.LOGIN.Context.getDBHost());
        source.setDatabaseName(ru.EXXO.LOGIN.Context.getDBName());
        source.setUser(ru.EXXO.LOGIN.Context.getDBUser());
        source.setPassword(ru.EXXO.LOGIN.Context.getDBPass());

        return source;
    }

}

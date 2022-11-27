package com.tryout.config;


import org.hibernate.dialect.PostgreSQL95Dialect;

import java.sql.Types;


public class PostgreSQLCustomDialect extends PostgreSQL95Dialect {


  @Override
  protected void registerColumnType (int code, String name) {

    super.registerColumnType(code, name);
    super.registerColumnType(Types.JAVA_OBJECT, "jsonb");

    registerHibernateType(Types.ARRAY, StringSetType.class.getName());
  }
}

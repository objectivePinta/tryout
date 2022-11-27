package com.tryout.config;




import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class StringSetType implements UserType {

  private static final String POSTGRES_VARCHAR_TYPE = "VARCHAR";


  public int[] sqlTypes () {

    return new int[]{Types.ARRAY};
  }


  public Class<Set> returnedClass () {

    return Set.class;
  }


  public boolean equals (Object o1, Object o2) {

    return Objects.equals(o1, o2);
  }


  public int hashCode (Object o) {

    return o == null ? 0 : o.hashCode();
  }


  public Object nullSafeGet (
      ResultSet result,
      String[] columns,
      SharedSessionContractImplementor sharedSessionContractImplementor,
      Object o
  ) throws SQLException {

    if (columns != null && columns.length > 0 &&
        result != null && result.getArray(columns[0]) != null) {

      Set<String> set = new HashSet<>();
      Collections.addAll(set, (String[]) result.getArray(columns[0]).getArray());
      return set;
    }

    return null;
  }


  public void nullSafeSet (
      PreparedStatement preparedStatement,
      Object o,
      int index,
      SharedSessionContractImplementor sharedSessionContractImplementor
  ) throws SQLException {

    if (o == null) {
      preparedStatement.setNull(index, Types.ARRAY);
    } else {
      preparedStatement.setArray(
          index,
          preparedStatement.getConnection().createArrayOf(
              POSTGRES_VARCHAR_TYPE,
              ((Set) o).toArray()
          )
      );
    }
  }


  public Object deepCopy (Object value) {

    if (value == null) {
      return null;
    }

    Set set = (Set) value;
    Set<String> clone = new HashSet<>();
    for (Object intOn : set) {
      clone.add((String) intOn);
    }

    return clone;
  }


  public boolean isMutable () {

    return true;
  }


  public Serializable disassemble (Object value) {

    return (Serializable) value;
  }


  public Object assemble (Serializable cached, Object owner) {

    return this.deepCopy(cached);
  }


  public Object replace (Object o, Object target, Object owner) {

    return o;
  }
}

package com.tryout.enums.mapping;


import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class PostgreSQLEnumType extends EnumType {

    private static final long serialVersionUID = -2338626292552177485L;

    @Override
    public void nullSafeSet (
            PreparedStatement st,
            Object value,
            int index,
            SharedSessionContractImplementor session
    ) throws SQLException {

        st.setObject(
                index,
                value != null ? ((Enum) value).name() : null,
                Types.OTHER
        );
    }
}

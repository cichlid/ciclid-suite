package alpha114.tool.cichlid.autogenerationtool.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kurisakisatoshi
 */
public enum DBColumnDataTypeEnum {

    CHAR {
        @Override
        public String getColumnType() {
            return "Types.CHAR";
        }
    },
    VARCHAR {
        @Override
        public String getColumnType() {
            return "Types.VARCHAR";
        }
    },
    VARCHAR2 {
        @Override
        public String getColumnType() {
            return "Types.VARCHAR";
        }
    },
    NUMBER {
        @Override
        public String escape(String value) {
            return value;
        }

        @Override
        public String getClassType() {
            return "int";
        }

        @Override
        public String getColumnType() {
            return "Types.NUMERIC";
        }
    },
    DATE {
        @Override
        public String escape(String value) {
            if (value.toLowerCase().equals("sysdate")) {
                return value.toLowerCase();
            }
            return "'" + value + "'";
        }

        @Override
        public String getClassType() {
            return "java.util.Date";
        }

        @Override
        public String getColumnType() {
            return "Types.DATE";
        }
    },
    TIMESTAMP {
        @Override
        public String escape(String value) {
            return value;
        }

        @Override
        public String getClassType() {
            return "java.util.Date";
        }

        @Override
        public String getColumnType() {
            return "Types.TIMESTAMP";
        }
    },
    CLOB {
        @Override
        public String escape(String value) {
            return "EMPTY_CLOB()";
        }

        @Override
        public String getColumnType() {
            return "Types.CLOB";
        }
    },
    _NULL {     // NULLオブジェクト
        @Override
        public String escape(String value) {
            return "";
        }

        @Override
        public String getColumnType() {
            return "Types.NULL";
        }
    },
    INTEGER {
        @Override
        public String escape(String value) {
            return value;
        }

        @Override
        public String getClassType() {
            return "int";
        }

        @Override
        public String getColumnType() {
            return "Types.NUMERIC";
        }
    },
    TEXT {
        @Override
        public String getColumnType() {
            return "Types.VARCHAR";
        }
    },
    BIGINT {
        @Override
        public String escape(String value) {
            return value;
        }

        @Override
        public String getClassType() {
            return "long";
        }

        @Override
        public String getColumnType() {
            return "Types.BIGINT";
        }
    };

    private static Map<String, DBColumnDataTypeEnum> dataTypes = null;
    static {
        dataTypes = new HashMap<String, DBColumnDataTypeEnum>();
        for (DBColumnDataTypeEnum dataType : values()) {
            if (dataType == _NULL) {
                continue;
            }
            dataTypes.put(dataType.name().toLowerCase(), dataType);
        }
    }

    public static DBColumnDataTypeEnum getType(String type) {
        DBColumnDataTypeEnum dataType = dataTypes.get(type.toLowerCase());
        return dataType == null ? _NULL : dataType;
    }

    public String escape(String value) {
        return "'" + value.replaceAll("'", "''") + "'";
    }

    public String getClassType() {
        return "String";
    }

    public abstract String getColumnType();
}

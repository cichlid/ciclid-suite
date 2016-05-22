package alpha114.tool.cichlid.autogenerationtool.data;

/**
 * @author kurisakisatoshi
 */
public class PropertyData {

    public String key;
    public String description;
    public boolean abbreviation;
    public String value;
    public String defaultValue;
    public DataType dataType;
    public int min;
    public int max;
    public String note;

    public void setDataType(String dataType) {
        this.dataType = DataType.is(dataType);
    }

    public boolean isNumeric() {
        return dataType == DataType.NUMERIC;
    }

    public boolean isHankaku() {
        return dataType == DataType.HANKAKU;
    }

    public boolean isZenkaku() {
        return dataType == DataType.ZENKAKU;
    }

    public boolean isFile() {
        return dataType == DataType.FILE;
    }


    enum DataType {

        NUMERIC("半角数値"),
        HANKAKU("半角英数記号"),
        ZENKAKU("全角／半角"),
        FILE("ファイルパス"),
        _NULL("");


        private final String typeName;

        private DataType(String typeName) {
            this.typeName = typeName;
        }

        String text() {
            return typeName;
        }

        static DataType is(String dataType) {

            for (DataType type : values()) {
                if (type.typeName.equals(dataType)) {
                    return type;
                }
            }

            return _NULL;
        }
    }
}

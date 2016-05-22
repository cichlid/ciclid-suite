package alpha114.tool.cichlid.autogenerationtool.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kurisakisatoshi
 */
public class MetaData {

    public final String tableName;

    private final List<ColumnData> columnList;

    private final Map<MetaType, List<String>> data;

    public MetaData(String tableName, List<ColumnData> columnList) {
        this.tableName = tableName;
        this.columnList = new ArrayList<ColumnData>(columnList);
        this.data = new HashMap<MetaData.MetaType, List<String>>();
        for (MetaType m : MetaType.values()) {
            data.put(m, new ArrayList<String>());
        }
    }

    public List<ColumnData> getColumnList() {
        return columnList;
    }

    public List<String> getMetadata(MetaType metaType) {
        return data.get(metaType);
    }

    public void addData(String metaType, List<String> metaData) {
        if (columnList.size() != metaData.size()) {
            throw new IllegalArgumentException("カラム数がちがう " + tableName + " " + columnList.size() + " " + metaData);
        }
        for (MetaType type : MetaType.values()) {
            if (type.typeName.equals(metaType)) {
                data.put(type, metaData);
                return;
            }
        }
        throw new IllegalArgumentException("種別がおかしい " + metaType);
    }

    public enum MetaType {

        COMMON("初期データ"),
        SYANAI("初期データ(社内)"),
        KENSYOU("初期データ(検証)"),
        SYOUYOU("初期データ(商用)"),
        TEST_DATA("試験データ");
        ;

        public final String typeName;

        private MetaType(String typeName) {
            this.typeName = typeName;
        }
    }
}

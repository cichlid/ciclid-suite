package alpha114.tool.cichlid.autogenerationtool.schema.builder;

import java.util.List;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.data.ColumnData;
import alpha114.tool.cichlid.autogenerationtool.data.MetaData;
import alpha114.tool.cichlid.autogenerationtool.data.MetaData.MetaType;

/**
 * @author kurisakisatoshi
 */
public class InitialMetaDataBuilder implements Builder {

    private final MetaType metaType;
    private final List<MetaData> metaDataList;

    public InitialMetaDataBuilder(MetaType metaType, List<MetaData> metaDataList) {
        this.metaType = metaType;
        this.metaDataList = metaDataList;
    }

    @Override
    public String build() {

        StringBuilder sb = new StringBuilder();

        for (MetaData metaData : metaDataList) {

            if (metaData.getMetadata(metaType).isEmpty()) {
                continue;
            }

            StringBuilder column_sb = new StringBuilder();
            StringBuilder value_sb = new StringBuilder();

            List<ColumnData> columnDatas = metaData.getColumnList();
            List<String> values = metaData.getMetadata(metaType);

            for (int i=0; i < columnDatas.size(); i++) {

                ColumnData columnData = columnDatas.get(i);
                String value = values.get(i);

                column_sb.append(columnData.name);

                if (value.equals("NEXTVAL")) {
                    value_sb.append("NEXTVAL('SYSID_SEQ')");

                } else {
                    value_sb.append(columnData.dataType.escape(value));
                }

                if ((i+1) < columnDatas.size()) {
                    column_sb.append(", ");
                    value_sb.append(", ");
                }
            }

            sb.append("INSERT INTO ").append(metaData.tableName)
              .append(" (")
              .append(column_sb.toString())
              .append(") VALUES (")
              .append(value_sb.toString())
              .append(");").append("\n");
        }

        return sb.toString();
    }
}

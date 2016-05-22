package alpha114.tool.cichlid.autogenerationtool.schema.builder;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.output.StringBuilderWriter;

import alpha114.tool.cichlid.autogenerationtool.data.ColumnData;
import alpha114.tool.cichlid.autogenerationtool.data.DBColumnDataTypeEnum;
import alpha114.tool.cichlid.autogenerationtool.data.TableData;

/**
 * @author kurisakisatoshi
 */
public class SchemaBuilder implements DDLBuilder {

    private final List<TableData> tables;

    public SchemaBuilder(List<TableData> tables) {
        this.tables = tables;
    }

    @Override
    public String buildCreateDDL() {

        StringBuilderWriter w = new StringBuilderWriter();

        for (TableData tableData : tables) {

            try {
                createDDL(tableData, w);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        return w.toString();
    }

    @Override
    public String buildDropDDL() {

        StringBuilderWriter w = new StringBuilderWriter();

        for (TableData tableData : tables) {

            try {
                createDropDDL(tableData, w);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        return w.toString();
    }

    private void createDDL(TableData tableData, Writer out) throws IOException {

        StringBuilder sb = new StringBuilder();

        int counter = 0;

        List<ColumnData> fk = new ArrayList<ColumnData>();

        sb.append("CREATE TABLE ").append(tableData.instanceName.toUpperCase()).append(".").append(tableData.name.toUpperCase()).append(" (").append("\n");
        sb.append("\t");

        for (Iterator<ColumnData> iter = tableData.getColumns().iterator(); iter.hasNext();) {
            ColumnData column = (ColumnData) iter.next();

            // カラム名
            sb.append(column.name).append(" ");

            // 型とサイズ
            sb.append(column.dataType.name());
            if (!column.dataType.equals(DBColumnDataTypeEnum.DATE)
                    && !column.dataType.equals(DBColumnDataTypeEnum.CLOB)
                    && !column.dataType.equals(DBColumnDataTypeEnum.INTEGER)
                    && !column.dataType.equals(DBColumnDataTypeEnum.TIMESTAMP)
                    && !column.dataType.equals(DBColumnDataTypeEnum.TEXT)
                    && !column.dataType.equals(DBColumnDataTypeEnum.BIGINT)) {
                sb.append("(").append(column.bytes).append(")");
            }

            // DEFAULT値
            if (column.isUseSeq) {
                sb.append(" DEFAULT nextval('").append(column.useSeq).append("') ");

            } else {
                if (column.defaultValue != null && !column.defaultValue.equals("")) {
                    sb.append(" DEFAULT ").append(column.dataType.escape(column.defaultValue));

                } else {
                    if (column.notNull
                            && (column.dataType.equals(DBColumnDataTypeEnum.VARCHAR2)
                                    || column.dataType.equals(DBColumnDataTypeEnum.VARCHAR)
                                    || column.dataType.equals(DBColumnDataTypeEnum.CHAR)
                                    || column.dataType.equals(DBColumnDataTypeEnum.TEXT))) {
                        sb.append(" DEFAULT ''");
                    }
                }
            }

            // NOT NULL属性
            if (column.notNull) {
                String nnName = "TBL" + tableData.no + "_" + "COL" + counter;
                sb.append(" CONSTRAINT ").append(nnName).append("_NN");
                sb.append(" NOT NULL");
            }

            // FKEY
            if (!column.foreignKey.equals("")) {
                fk.add(column);
            }

            if (iter.hasNext()) {
                sb.append("\n");
                sb.append("\t");
                sb.append(", ");
            }

            counter++;
        }

        // PKEY
        if (!tableData.getPrimaryKeys().isEmpty()) {
            sb.append("\n");
            sb.append("\t");
            sb.append(", CONSTRAINT TBL").append(tableData.no).append("_PK PRIMARY KEY (");
            for (Iterator<ColumnData> iter2 = tableData.getPrimaryKeys().iterator(); iter2.hasNext();) {
                ColumnData pkey = (ColumnData) iter2.next();
                sb.append(pkey.name);
                if (iter2.hasNext()) {
                    sb.append(", ");
                }
            }
            sb.append(")");
        }

        // UNIQUE
        if (!tableData.getUniqKeys().isEmpty()) {
            sb.append("\n");
            sb.append("\t");
            for (List<ColumnData> uniqkeys : tableData.getUniqKeys()) {
                sb.append(", UNIQUE (");
                    for (Iterator<ColumnData> iter2 = uniqkeys.iterator(); iter2.hasNext();) {
                        ColumnData unique = (ColumnData) iter2.next();
                        sb.append(unique.name);
                        if (iter2.hasNext()) {
                            sb.append(", ");
                        }
                    }
                sb.append(")");
            }
        }

        // FKEY
        if (!fk.isEmpty()) {
            sb.append("\n");
            sb.append("\t");
            int cnt = 0;
            for (Iterator<ColumnData> iter2 = fk.iterator(); iter2.hasNext();) {
                ColumnData fkey = (ColumnData) iter2.next();
                sb.append(", CONSTRAINT ");
                sb.append("TBL").append(tableData.no).append("_FK").append(cnt);
                sb.append(" FOREIGN KEY (").append(fkey.name).append(") ");
                sb.append("REFERENCES ").append(fkey.foreignKey.replaceAll("\r|\n", " "));

                cnt++;
            }
        }

        sb.append(")");


        // INDEX1
        if (!tableData.getIndex1List().isEmpty()) {
            String indexName = "TBL" + tableData.no + "_IDX1";
            sb.append("CREATE INDEX ").append(indexName);
            sb.append(" ON ").append(tableData.instanceName.toUpperCase()).append(".").append(tableData.name).append("(");
            for (Iterator<ColumnData> iter2 = tableData.getIndex1List().iterator(); iter2.hasNext();) {
                ColumnData index = iter2.next();
                sb.append(index.name);
                if (iter2.hasNext()) {
                    sb.append(",");
                }
            }
            sb.append(")");
        }

        // INDEX2
        if (!tableData.getIndex2List().isEmpty()) {
            String indexName = "TBL" + tableData.no + "_IDX2";
            sb.append("CREATE INDEX ").append(indexName);
            sb.append(" ON ").append(tableData.instanceName.toUpperCase()).append(".").append(tableData.name).append("(");
            for (Iterator<ColumnData> iter2 = tableData.getIndex2List().iterator(); iter2.hasNext();) {
                ColumnData index = iter2.next();
                sb.append(index.name);
                if (iter2.hasNext()) {
                    sb.append(",");
                }
            }
            sb.append(")");
        }

        // INDEX3
        if (!tableData.getIndex3List().isEmpty()) {
            String indexName = "TBL" + tableData.no + "_IDX3";
            sb.append("CREATE INDEX ").append(indexName);
            sb.append(" ON ").append(tableData.instanceName.toUpperCase()).append(".").append(tableData.name).append("(");
            for (Iterator<ColumnData> iter2 = tableData.getIndex3List().iterator(); iter2.hasNext();) {
                ColumnData index = iter2.next();
                sb.append(index.name);
                if (iter2.hasNext()) {
                    sb.append(",");
                }
            }
            sb.append(")");
        }

        sb.append("\n");

        out.write(sb.toString());

        out.flush();
    }

    private void createDropDDL(TableData tableData, Writer out) throws IOException {

        StringBuilder sb = new StringBuilder();

        if (!tableData.getIndex1List().isEmpty()) {
            String indexName = "TBL" + tableData.no + "_IDX1";
            sb.append("DROP INDEX ").append(indexName).append(";").append("\n");
        }

        if (!tableData.getIndex2List().isEmpty()) {
            String indexName = "TBL" + tableData.no + "_IDX2";
            sb.append("DROP INDEX ").append(indexName).append(";").append("\n");
        }

        if (!tableData.getIndex3List().isEmpty()) {
            String indexName = "TBL" + tableData.no + "_IDX3";
            sb.append("DROP INDEX ").append(indexName).append(";").append("\n");
        }

        sb.append("DROP TABLE ").append(tableData.instanceName.toUpperCase()).append(".").append(tableData.name).append(" CASCADE CONSTRAINTS;").append("\n");

        out.write(sb.toString());

        out.flush();
    }
}

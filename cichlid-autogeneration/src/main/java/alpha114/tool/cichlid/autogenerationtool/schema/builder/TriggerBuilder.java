package alpha114.tool.cichlid.autogenerationtool.schema.builder;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.output.StringBuilderWriter;

import alpha114.tool.cichlid.autogenerationtool.data.TableData;
import alpha114.tool.cichlid.autogenerationtool.data.TriggerData;

/**
 * @author kurisakisatoshi
 */
public class TriggerBuilder implements DDLBuilder {

    private final List<TriggerData> trigs;
    private final Map<String, TableData> tables;

    public TriggerBuilder(List<TriggerData> trigs, List<TableData> tables) {
        this.trigs = trigs;
        this.tables = new HashMap<String, TableData>();
        for (TableData table : tables) {
            this.tables.put(table.tableName, table);
        }
    }

    @Override
    public String buildCreateDDL() {

        StringBuilderWriter w = new StringBuilderWriter();

        for (TriggerData triggerData : trigs) {

            try {
                createDDL(triggerData, w);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        return w.toString();
    }

    @Override
    public String buildDropDDL() {

        StringBuilderWriter w = new StringBuilderWriter();

        for (TriggerData triggerData : trigs) {

            try {
                createDropDDL(triggerData, w);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        return w.toString();
    }


    private void createDDL(TriggerData triggerData, Writer o) throws IOException {

        TableData tableData = triggerData.tableData;

        String procName = tableData.name+"_"+triggerData.trigger.name()+"_FUNCTION";

        StringBuilder sb = new StringBuilder();

        sb.append("CREATE OR REPLACE FUNCTION ")
          .append(procName).append("()")
          .append(" RETURNS TRIGGER AS $").append(procName).append("$").append("\n");

        sb.append("BEGIN").append("\n");

        for (String info : triggerData.info.split("\n|\r")) {

            String delTable = info.split("\\.")[0];
            String delKey = info.split("\\.")[1];

            sb.append("\t")
              .append("DELETE FROM ")
              .append(delTable)
              .append(" WHERE ")
              .append(delKey)
              .append(" = ")
              .append("old.").append(tableData.getPrimaryKeys().get(0).name).append(";")
              .append("\n");
        }

        sb.append("\tRETURN OLD;").append("\n");
        sb.append("END;").append("\n");
        sb.append("$").append(procName).append("$ LANGUAGE plpgsql;").append("\n");

        sb.append("\n");

        sb.append("CREATE TRIGGER ")
          .append(triggerData.triggerName).append(" ").append("\n")
          .append("\t").append(triggerData.trigger.getTriggerName())
          .append(" ON ").append(tableData.name)
          .append(" FOR EACH ROW").append("\n")
          .append("\t").append("EXECUTE PROCEDURE ").append(procName).append("();").append("\n\n");

        sb.append("\n");

        o.write(sb.toString());
        o.flush();
    }

    private void createDropDDL(TriggerData triggerData, Writer o) throws IOException {

        TableData tableData = triggerData.tableData;

        String procName = tableData.name+"_"+triggerData.trigger.name()+"_FUNCTION";

        StringBuilder sb = new StringBuilder();

        sb.append("DROP TRIGGER ").append(triggerData.triggerName).append(" ON ").append(tableData.name).append(";\n");
        sb.append("DROP FUNCTION ").append(procName).append("();").append("\n");

        sb.append("\n");

        o.write(sb.toString());
        o.flush();
    }
}

package alpha114.tool.cichlid.autogenerationtool.schema;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.CreateSrc;
import alpha114.tool.cichlid.autogenerationtool.data.ColumnData;
import alpha114.tool.cichlid.autogenerationtool.data.SequenceData;
import alpha114.tool.cichlid.autogenerationtool.data.TableData;
import alpha114.tool.cichlid.autogenerationtool.data.TriggerData;
import alpha114.tool.cichlid.autogenerationtool.schema.builder.DDLBuilder;
import alpha114.tool.cichlid.autogenerationtool.schema.builder.SchemaBuilder;
import alpha114.tool.cichlid.autogenerationtool.schema.builder.SequenceBuilder;
import alpha114.tool.cichlid.autogenerationtool.schema.builder.TriggerBuilder;

/**
 * @author kurisakisatoshi
 */
public class CreateSchema extends CreateSrc {

    private final String distDir;

    public CreateSchema(String xls, String distDir) {
        this.distDir = distDir;

        Workbook wb = createWorkbook(xls);
        if (wb == null) {
            return;
        }

        List<TableData> all = new ArrayList<TableData>();

        for (String instanceName : Constants.DB_INSTANCE_NAME) {

            List<TableData> tables = createTableData(wb, instanceName);
            List<SequenceData> seqs = createSequenceData(wb, instanceName);

            // ddl
            createDDL(instanceName, tables, seqs);

            // Trigger
            createTrigger(wb, distDir, instanceName, tables);

            all.addAll(tables);
        }

        // メモ
        memo(all);
    }




    private void write(String distDir, String createFile, String dropFile, DDLBuilder builder) {

        new File(distDir).mkdirs();

        OutputStreamWriter c = null;
        OutputStreamWriter d = null;
        try {

            c = new OutputStreamWriter(new FileOutputStream(distDir + "\\" + createFile), "UTF-8");
            d = new OutputStreamWriter(new FileOutputStream(distDir + "\\" + dropFile), "UTF-8");

            c.write(builder.buildCreateDDL());
            d.write(builder.buildDropDDL());

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            IOUtils.closeQuietly(c);
            IOUtils.closeQuietly(d);
        }
    }


    private void createDDL(String instanceName, List<TableData> tables, List<SequenceData> seqs) {

        String dist = distDir + "\\ddl";
        String name = instanceName.toLowerCase() + ".ddl";

        write(dist, "create.table." + name, "drop.table." + name, new SchemaBuilder(tables));
        write(dist, "create.seq." + name, "drop.seq." + name, new SequenceBuilder(seqs));
    }


    private void memo(List<TableData> tables) {


        Map<String, Map<String, List<String>>> mp = new HashMap<String, Map<String, List<String>>>();

        for (TableData tableData : tables) {

            String ins = tableData.insertTrigger;
            String up = tableData.updateTrigger;
            String del = tableData.deleteTrigger;

            for (String trig : new String[]{ins, up, del}) {

                if (trig.equals("")) {
                    continue;
                }

                String[] ss = trig.split("\r|\n");
                for (int i = 0; i < ss.length; i++) {
                    String s = ss[i].trim();

                    if (s.startsWith("[機能]")) {
                        String f = s.replace("[機能]", "");

                        if (i + 1 < ss.length) {
                            s = ss[i + 1].trim();
                            i++;
                            if (s.startsWith("[契機]")) {
                                String t = s.replace("[契機]", "");

                                Map<String, List<String>> m = mp.get(f);
                                if (m == null) {
                                    m = new HashMap<String, List<String>>();
                                    mp.put(f, m);
                                }

                                List<String> c = m.get(t);
                                if (c == null) {
                                    c = new ArrayList<String>();
                                    m.put(t, c);
                                }

                                StringBuilder sb = new StringBuilder();

                                sb.append(tableData.tableName)
                                  .append("テーブル(").append(tableData.name).append(")");

                                if (trig.equals(ins)) {
                                    sb.append(" に追加する");
                                    if (tableData.notifyByInsert) {
                                        sb.append("\t★通知あり");
                                    }

                                } else if (trig.equals(up)) {
                                    sb.append(" を更新する");
                                    if (tableData.notifyByUpdate) {
                                        sb.append("\t★通知あり");
                                    }

                                } else if (trig.equals(del)) {
                                    sb.append(" を削除する");
                                    if (tableData.notifyByDelete) {
                                        sb.append("\t★通知あり");
                                    }
                                }

                                c.add(sb.toString());
                            }
                        }
                    }
                }
            }

            for (ColumnData columnData : tableData.getColumns()) {

                if (!columnData.update) {
                    continue;
                }

                String updateFunc = columnData.updateFunc;
                if (updateFunc.equals("")) {

                    continue;
                }

                String[] func = updateFunc.split("\n|\r");

                for (int i = 0; i < func.length; i++) {
                    String f = func[i];

                    Map<String, List<String>> m = mp.get(f);
                    if (m == null) {
                        m = new HashMap<String, List<String>>();
                        mp.put(f, m);
                    }

                    String updateTrig = columnData.updateTrigger.split("\n|\r")[i];

                    List<String> c = m.get(updateTrig);
                    if (c == null) {
                        c = new ArrayList<String>();
                        m.put(updateTrig, c);
                    }

                    StringBuilder sb = new StringBuilder();

                    sb.append(tableData.tableName)
                      .append("テーブル(").append(tableData.name).append(")")
                      .append(" の ")
                      .append(columnData.columnName)
                      .append("(").append(columnData.name).append(") を更新する")
                      .append(columnData.notify ? "\t★通知あり" : "");

                    c.add(sb.toString());
                }
            }
        }


        final Map<String, Map<String, List<String>>> mmm = mp;

        Builder builder = new Builder() {

            @Override
            public String build() {

                StringBuilder sb = new StringBuilder();

                sb.append("機能＆契機ごとのテーブル・カラムの追加・更新・削除").append("\n").append("\n");

                for (Map.Entry<String, Map<String, List<String>>> me : mmm.entrySet()) {

                    sb.append("【").append(me.getKey()).append("】\n\n");

                    for (Map.Entry<String, List<String>> mme: me.getValue().entrySet()) {

                        sb.append("■").append(mme.getKey()).append("(↓順不同)\n");

                        for (String data : mme.getValue()) {

                            sb.append("・").append(data).append("\n");
                        }

                        sb.append("\n");
                    }

                    sb.append("\n\n");
                }

                return sb.toString();
            }
        };

        write(distDir, distDir + "\\memo.txt", builder);
    }

    private void createTrigger(Workbook wb, String distDir, String instanceName, List<TableData> tables) {

        int triggerSheet = 7;
        Sheet trigSheet = getSheetAt(wb, triggerSheet);
        if (trigSheet == null) {
            return;
        }

        Map<String, TableData> map = new HashMap<String, TableData>();
        for (TableData data : tables) {
            map.put(data.name, data);
        }

        List<TriggerData> trigs = new ArrayList<TriggerData>();

        Row row = null;
        int i = 3;
        while ((row = trigSheet.getRow(i)) != null) {

            String no = getStringValue(row, 1);
            if (no.equals("")) {
                break;
            }
            if (!no.matches("\\d+")) {
                i++;
                continue;
            }

            String trigName = getStringValue(row, 2);
            String insName = getStringValue(row, 3);
            if (!insName.equals(instanceName)) {
                i++;
                continue;
            }
            String tableName = getStringValue(row, 4).split("\r|\n")[1];
            String trigger = getStringValue(row, 5);
            String info = getStringValue(row, 7);

            TriggerData trigData = new TriggerData();
            trigData.triggerName = trigName;
            trigData.setTrigger(trigger);
            trigData.info = info;
            trigData.tableData = map.get(tableName);

            trigs.add(trigData);

            i++;
        }


        String dist = distDir + "\\ddl";
        String name = instanceName.toLowerCase() + ".ddl";

        write(dist, "create.trigger." + name, "drop.trigger." + name, new TriggerBuilder(trigs, tables));
    }





    /**
     * @param args
     */
    public static void main(String[] args) {

        for (String arg : args) {
            System.out.println(arg);
        }

        String xls = null;
        String distDir = null;

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if (arg.equals("-xls")) {
                if (i + 1 < args.length) {
                    xls = args[i + 1];
                    i++;
                }

            } else if (arg.equals("-distdir")) {
                if (i + 1 < args.length) {
                    distDir = args[i + 1];
                    i++;
                }
            }
        }

        System.out.println("xls -> " + xls);
        System.out.println("dist -> " + distDir);

        if (StringUtils.isEmpty(xls)
                || StringUtils.isEmpty(distDir)) {

            System.out.println("引数異常");
            return;
        }

        if (!distDir.endsWith("\\")) {
            distDir += "\\";
        }
        distDir += "IM_dbschema";

        try {
            FileUtils.deleteDirectory(new File(distDir));
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }

        new CreateSchema(xls, distDir);

        System.out.println("");
        System.out.println("done.");
    }
}

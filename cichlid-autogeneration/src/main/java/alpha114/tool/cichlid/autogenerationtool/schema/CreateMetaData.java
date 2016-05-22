package alpha114.tool.cichlid.autogenerationtool.schema;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.CreateSrc;
import alpha114.tool.cichlid.autogenerationtool.data.ColumnData;
import alpha114.tool.cichlid.autogenerationtool.data.DBColumnDataTypeEnum;
import alpha114.tool.cichlid.autogenerationtool.data.MetaData;
import alpha114.tool.cichlid.autogenerationtool.data.MetaData.MetaType;
import alpha114.tool.cichlid.autogenerationtool.data.TableData;
import alpha114.tool.cichlid.autogenerationtool.schema.builder.InitialMetaDataBuilder;

/**
 * @author kurisakisatoshi
 */
public class CreateMetaData extends CreateSrc {

    private final String distDir;

    public CreateMetaData(String xls, String distDir) {
        this.distDir = distDir;

        Workbook wb = createWorkbook(xls);
        if (wb == null) {
            return;
        }

        for (String instanceName : Constants.DB_INSTANCE_NAME) {

            List<MetaData> metaDataList = new ArrayList<MetaData>();

            List<TableData> tables = createTableData(wb, instanceName);

            for (TableData tableData : tables) {

                Sheet tableSheet = wb.getSheet(tableData.tableName);

                List<ColumnData> clms = new ArrayList<ColumnData>();

                String clm = null;
                int rowIndex = 11;
                while (!(clm = getStringValue(tableSheet, rowIndex, 2)).equals("")) {

                    DBColumnDataTypeEnum dataType = DBColumnDataTypeEnum.getType(getStringValue(tableSheet, rowIndex, 3));

                    ColumnData columnData = new ColumnData();

                    columnData.name = clm;
                    columnData.dataType = dataType;

                    clms.add(columnData);

                    rowIndex++;
                }

                int cellIndex = 0;
                while (!getStringValue(tableSheet, 10, cellIndex).equals("備考")) {
                    cellIndex++;
                }

                cellIndex = cellIndex + 2;
                String metaType = null;
                while (!(metaType = getStringValue(tableSheet, 10, cellIndex)).equals("")) {

                    List<String> metaDatas = new ArrayList<String>();

                    String data = null;
                    int idx = 11;
                    while (!(data = getStringValue(tableSheet, idx, cellIndex)).equals("")) {
                        metaDatas.add(data);
                        idx++;
                    }

                    MetaData metaData = new MetaData(tableData.name, clms);
                    metaData.addData(metaType, metaDatas);

                    metaDataList.add(metaData);

                    cellIndex++;
                }
            }

            writeMetaData(metaDataList, instanceName);
        }
    }

    private void writeMetaData(List<MetaData> metaDataList, String instanceName) {

        String dist = distDir + "\\metadata";

        for (MetaType metaType : MetaType.values()) {

            if (metaType == MetaType.TEST_DATA) {

                String name = distDir + "\\testdata" + "\\testdata." + instanceName + ".sql";

                write(distDir + "\\testdata", name, new InitialMetaDataBuilder(metaType, metaDataList));

            } else {

                String name = dist + "\\metadata." + instanceName + "." + metaType.name().toLowerCase() + ".sql";

                write(dist, name, new InitialMetaDataBuilder(metaType, metaDataList));
            }
        }
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

        for (int i=0; i<args.length; i++) {
            String arg = args[i];

            if (arg.equals("-xls")) {
                if (i+1 < args.length) {
                    xls = args[i+1];
                    i++;
                }

            } else if (arg.equals("-distdir")) {
                if (i+1 < args.length) {
                    distDir = args[i+1];
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
        distDir += "dbdata";

//        try {
//            FileUtils.deleteDirectory(new File(distDir));
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//            return;
//        }

        new CreateMetaData(xls, distDir);

        System.out.println("");
        System.out.println("done.");
    }
}

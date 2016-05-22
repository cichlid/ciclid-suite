package alpha114.tool.cichlid.autogenerationtool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import alpha114.tool.cichlid.autogenerationtool.data.ColumnData;
import alpha114.tool.cichlid.autogenerationtool.data.DBColumnDataTypeEnum;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData.FrameType;
import alpha114.tool.cichlid.autogenerationtool.data.SequenceData;
import alpha114.tool.cichlid.autogenerationtool.data.TableData;

/**
 * @author kurisakisatoshi
 */
public abstract class CreateSrc {

    protected void write(String distDir, String filePath, Builder builder) {

        new File(distDir).mkdirs();

        OutputStreamWriter w = null;
        try {

            w = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");

            w.write(builder.build());

            w.flush();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            IOUtils.closeQuietly(w);
        }
    }

    protected List<TableData> createTableData(Workbook wb, String instanceName) {

        List<TableData> tables = new ArrayList<TableData>();

        int cnt = 0;
        int sheetIndex = 8;     // テーブルのシート開始番号
        Sheet tableSheet = null;
        while ((tableSheet = getSheetAt(wb, sheetIndex)) != null) {

            // インスタンス名
            String insName = getStringValue(tableSheet, 0, 2);
            if (!insName.equals(instanceName)) {
                sheetIndex++;
                continue;
            }

            // テーブル名(和名)
            String tableName_ja = getStringValue(tableSheet, 1, 2);

            // テーブル名
            String tableName = getStringValue(tableSheet, 2, 2);

            // 1レコードのサイズ(byte)
            String tableRecordSize = getStringValue(tableSheet, 4, 2);

            // 想定されるレコード数
            String tableRecordNum = getStringValue(tableSheet, 5, 2);

            // insert契機
            String insTrigger = getStringValue(tableSheet, 6, 2);
            String notifyByIns = getStringValue(tableSheet, 6, 8);

            // update契機
            String upTrigger = getStringValue(tableSheet, 7, 2);
            String notifyByUp = getStringValue(tableSheet, 7, 8);

            // delete契機
            String delTrigger = getStringValue(tableSheet, 8, 2);
            String notifyByDel = getStringValue(tableSheet, 8, 8);


            TableData tableData = new TableData();
            tableData.no = cnt;
            tableData.instanceName = insName;
            tableData.tableName = tableName_ja;
            tableData.name = tableName;
            tableData.recordSize = Integer.parseInt(tableRecordSize);
            tableData.recordNum = Integer.parseInt(tableRecordNum);
            tableData.insertTrigger = insTrigger;
            tableData.notifyByInsert = notifyByIns.equals("○");
            tableData.updateTrigger = upTrigger;
            tableData.notifyByUpdate = notifyByUp.equals("○");
            tableData.deleteTrigger = delTrigger;
            tableData.notifyByDelete = notifyByDel.equals("○");

            tables.add(tableData);

            List<ColumnData> columns = new ArrayList<ColumnData>();
            tableData.setColumns(columns);

            int headerRowIndex = 9;     // ヘッダ行
            Row headerRow = tableSheet.getRow(headerRowIndex);
            int i = 0;
            int uniqCont = 0;
            while (headerRow.getCell(i) != null) {
                // 一意の列は複数ある

                if (getStringCellValue(((Cell) headerRow.getCell(i))).equals("一意")) {
                    uniqCont++;
                }

                i++;
            }

            int rowIndex = headerRowIndex + 1;       // カラムの定義開始行
            Row columnRow = null;
            while ((columnRow = tableSheet.getRow(rowIndex)) != null) {

                if (columnRow.getCell(0) == null) {
                    break;
                }

                String no = getStringCellValue(((Cell) columnRow.getCell(0)));

                if (no.equals("")) {
                    break;
                }

                String columnName = getStringCellValue(((Cell) columnRow.getCell(1)));
                String name = getStringCellValue(((Cell) columnRow.getCell(2)));

                DBColumnDataTypeEnum dataType = DBColumnDataTypeEnum.getType(getStringCellValue(((Cell) columnRow.getCell(3))));
                String length = getStringCellValue(((Cell) columnRow.getCell(4)));
                String bytes = getStringCellValue(((Cell) columnRow.getCell(5)));
                String notNull = getStringCellValue(((Cell) columnRow.getCell(6)));
                String defaultValue = getStringCellValue(((Cell) columnRow.getCell(7)));
                String pkey = getStringCellValue(((Cell) columnRow.getCell(8)));
                String[] unique = new String[uniqCont];
                for (i = 0; i < uniqCont; i++) {
                    unique[i] = getStringCellValue(((Cell) columnRow.getCell(8 + i + 1)));
                }

                String index1 = getStringCellValue(((Cell) columnRow.getCell(8 + uniqCont + 1)));
                String index2 = getStringCellValue(((Cell) columnRow.getCell(8 + uniqCont + 2)));
                String index3 = getStringCellValue(((Cell) columnRow.getCell(8 + uniqCont + 3)));

                String fkey = getStringCellValue(((Cell) columnRow.getCell(8 + uniqCont + 3 + 1)));
                String range = getStringCellValue(((Cell) columnRow.getCell(8 + uniqCont + 3 + 4)));
                String seq = getStringCellValue(((Cell) columnRow.getCell(8 + uniqCont + 3 + 5)));
                String update = getStringCellValue(((Cell) columnRow.getCell(8 + uniqCont + 3 + 6)));
                String updateTrig = getStringCellValue(((Cell) columnRow.getCell(8 + uniqCont + 3 + 7)));
                String updateFunc = getStringCellValue(((Cell) columnRow.getCell(8 + uniqCont + 3 + 8)));
                String notify = getStringCellValue(((Cell) columnRow.getCell(8 + uniqCont + 3 + 9)));

                ColumnData column = new ColumnData();

                column.tableName = tableName;
                column.no = no;
                column.columnName = columnName;
                column.name = name;
                column.dataType = dataType;
                column.length = length;
                column.bytes = bytes;
                column.notNull = notNull.equals("○");
                column.defaultValue = defaultValue;
                if (pkey.length() != 0) {
                    int idx = pkey.equals("○") ? 1 : Integer.parseInt(pkey);
                    tableData.addPrimaryKey(column, idx);
                }
                for (i = 0; i < uniqCont; i++) {
                    if (unique[i].length() == 0) {
                        continue;
                    }
                    int idx = unique[i].equals("○") ? 1 : Integer.parseInt(unique[i]);
                    tableData.addUniqKey(column, i, idx);
                }
                if (index1.equals("○")) {
                    tableData.addIndex1Column(column);
                }
                if (index2.equals("○")) {
                    tableData.addIndex2Column(column);
                }
                if (index3.equals("○")) {
                    tableData.addIndex3Column(column);
                }
                column.foreignKey = fkey;
                column.range = range;
                column.useSeq = seq;
                column.update = !update.equals("");
                column.updateTrigger = updateTrig;
                column.updateFunc = updateFunc;
                column.notify = !notify.equals("");

                columns.add(column);

                rowIndex++;
            }

            // 関連
            rowIndex = 1;
            int cellIndex = 24; // Z列
            while ((columnRow = tableSheet.getRow(rowIndex)) != null) {

                String relation = getStringValue(columnRow, cellIndex);

                if (relation.equals("")) {
                    break;
                }

                if (relation.indexOf("(1)") > 0) {
                    tableData.addOneToOne(relation.replaceAll("\\(1\\)", ""));

                } else if (relation.indexOf("(N)") > 0) {
                    tableData.addOneToMany(relation.replaceAll("\\(N\\)", ""));
                }

                rowIndex++;
            }

            sheetIndex++;
            cnt++;
        }

        return tables;
    }

    protected List<SequenceData> createSequenceData(Workbook wb, String instanceName) {

        List<SequenceData> seqs = new ArrayList<SequenceData>();

        Sheet seqSheet = getSheetAt(wb, 3);
        if (seqSheet == null) {
            return seqs;
        }

        int seqIndex = 2;
        Row seqRow = null;
        while ((seqRow = seqSheet.getRow(seqIndex)) != null) {

            String no = getStringCellValue(seqRow.getCell(1));
            if (no.length() == 0) {
                break;
            }

            String insName = getStringCellValue((Cell) seqRow.getCell(3));
            if (!insName.equals(instanceName)) {
                seqIndex++;
                continue;
            }

            String seqName = getStringCellValue((Cell) seqRow.getCell(2));
            String name = getStringCellValue((Cell) seqRow.getCell(4));
            String start = getStringCellValue((Cell) seqRow.getCell(6));
            String minValue = getStringCellValue((Cell) seqRow.getCell(7));
            String maxValue = getStringCellValue((Cell) seqRow.getCell(8));
            String increment = getStringCellValue((Cell) seqRow.getCell(9));
            String cycle = getStringCellValue((Cell) seqRow.getCell(10));
            String cache = getStringCellValue((Cell) seqRow.getCell(11));
            String order = getStringCellValue((Cell) seqRow.getCell(12));

            SequenceData seqData = new SequenceData();
            seqs.add(seqData);

            seqData.instanceName = insName;
            seqData.seqName = seqName;
            seqData.name = name;

            if (start.length() != 0 && !start.equals("指定なし")) {
                seqData.start = Integer.parseInt(start);
            }

            if (minValue.length() != 0 && !minValue.equals("指定なし")) {
                seqData.minValue = Integer.parseInt(minValue);
            }

            if (maxValue.length() != 0 && !maxValue.equals("指定なし")) {
                seqData.maxValue = Integer.parseInt(maxValue);
            } else {
                seqData.maxValue = Integer.MAX_VALUE;
            }

            if (increment.length() != 0 && !increment.equals("指定なし")) {
                seqData.increment = Integer.parseInt(increment);
            }

            seqData.cycle = (cycle.toUpperCase().equals("CYCLE"));

            if (cache.length() != 0 && !cache.equals("指定なし")) {
                seqData.cache = Integer.parseInt(cache);
            }

            seqData.order = order.toUpperCase().equals("ORDER");

            seqIndex++;
        }

        return seqs;
    }

    protected String getStringValue(Sheet tableSheet, int rowIndex, int cellIndex) {
        if (tableSheet == null) {
            return "";
        }
        return getStringValue(tableSheet.getRow(rowIndex), cellIndex);
    }

    protected String getStringValue(Row r, int cellIndex) {
        if (r == null) {
            return "";
        }
        return getStringCellValue((Cell) r.getCell(cellIndex));
    }

    protected String getStringCellValue(Cell cell) {

        try {
            String value = cell.getStringCellValue();
            return value.trim();
        } catch (Exception e) {
        }

        try {
            double d = cell.getNumericCellValue();
            return String.valueOf((int) d);
        } catch (Exception e) {
        }

        return "";
    }

    protected Sheet getSheetAt(Workbook wb, int sheetIndex) {

        try {
            return wb.getSheetAt(sheetIndex);
        } catch (Exception e) {
            return null;
        }
    }

    protected void native2Ascii(String srcFilePath, String destFilePath) {

        String native2ascii = System.getProperty("native2ascii");

        // Unicodeに変換
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    native2ascii,
                    "-encoding",
                    "UTF-8",
                    srcFilePath,
                    destFilePath);
            pb.start().waitFor();

            new File(srcFilePath).delete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Workbook createWorkbook(String xls) {

        InputStream in = null;
        Workbook wb = null;
        try {
            // エクセルファイルを読み込む
            in = new FileInputStream(xls);

            // ワークブックを開く
            wb = WorkbookFactory.create(in);

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            IOUtils.closeQuietly(in);
        }

        return wb;
    }


    protected void createFrameTypeMap(Workbook w, Map<String, FrameType> frameTypeMap) {

        Sheet sheet = w.getSheet("画面一覧");

        Row r = null;
        int i = 4;
        while ((r = sheet.getRow(i)) != null) {

            String type = getStringValue(r, 0);
            if (type.equals("")) {
                break;
            }

            String frameId = getStringValue(r, 3);

            if (frameId.equals("")) {
                i++;
                continue;
            }

            for (FrameType t : FrameType.values()) {
                if (t.name().toLowerCase().equals(type.toLowerCase())) {
                    frameTypeMap.put(frameId, t);
                }
            }

            if (!frameTypeMap.containsKey(frameId)) {
                System.out.println("画面の種別がなんかへん " + "frameId=" + frameId + ", type=" + type);
            }

            i++;
        }
    }
}

package alpha114.tool.cichlid.autogenerationtool.src;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.CreateSrc;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData.FrameType;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;
import alpha114.tool.cichlid.autogenerationtool.data.TableListItemData;
import alpha114.tool.cichlid.autogenerationtool.src.builder.SearchConditionFormBuilder;
import alpha114.tool.cichlid.autogenerationtool.src.builder.SearchConditionInterfaceBuilder;
import alpha114.tool.cichlid.autogenerationtool.src.builder.SearchConditionItemBeanXmlBuilder;
import alpha114.tool.cichlid.autogenerationtool.src.builder.SearchConditionJspBuilder;
import alpha114.tool.cichlid.autogenerationtool.src.builder.SingleEntitySearchListDaoImplBuilder;

/**
 * @author kurisakisatoshi
 */
public class CreateSearchCondition extends CreateSrc {

    final String distDir;

    Map<String, FrameType> frameTypeMap;

    public CreateSearchCondition(String frameListXls, String xls, String distDir) {
        this.distDir = distDir;
        this.frameTypeMap = new HashMap<String, FrameData.FrameType>();

        Workbook w = createWorkbook(frameListXls);
        if (w == null) {
            return;
        }

        createFrameTypeMap(w, frameTypeMap);

        Workbook wb = createWorkbook(xls);
        if (wb == null) {
            return;
        }

        List<FrameData> datas = new ArrayList<FrameData>();

        int sheetIndex = 0;
        Sheet sheet = null;
        while ((sheet = getSheetAt(wb, sheetIndex)) != null) {

            if (!getStringValue(sheet, 1, 1).equals("画面ID")) {
                sheetIndex++;
                continue;
            }

            String frameId = getStringValue(sheet, 1, 3);
            String frameName = getStringValue(sheet, 1, 9);

            if (!frameTypeMap.containsKey(frameId)) {
                sheetIndex++;
                continue;
            }

            FrameData frameData = new FrameData();

            frameData.setFrameId(frameId);
            frameData.setFrameName(frameName);
            frameData.setFrameType(frameTypeMap.get(frameId));


            int rowIndex = 5;
            Row row = sheet.getRow(0);
            while ((row = sheet.getRow(rowIndex)) != null) {

                String itemId = getStringValue(row, idxId);

                if (itemId.equals("")) {
                    break;
                }

                String itemName = getStringValue(row, idxItemName);

                if (itemName.equals("") || itemName.equals("-")) {
                    rowIndex++;
                    continue;
                }

                if (getStringValue(row, 2).equals("フレームタイトル")) {
                    frameData.setTitle(itemName);
                    frameData.setTitleId(itemId);
                    rowIndex++;
                    continue;
                }

                if (itemId.startsWith(FrameItemData.ItemType.SEARCH_CONDITION.type)) {
                    frameData.setSearchFlag(true);

                } else if (itemId.startsWith(FrameItemData.ItemType.TABLE_COLUMN.type)) {
                    frameData.setTableFlag(true);

                } else if (itemId.startsWith(FrameItemData.ItemType.SETTING_DATA.type)) {
                    frameData.setSettiongFlag(true);
                }

                frameData.addItem(itemId, itemName);

                FrameItemData item = frameData.getItemData(itemId);

                createItem(row, item, itemId);

                if (item.isTable()) {

                    createTableColumns(item, sheet);

                    if (frameData.isTableFlag()) {

                        String spec = getStringValue(row, idxSpec);

                        Pattern pattern = Pattern.compile("『(.*)』(昇順|降順)");
                        Matcher matcher = pattern.matcher(spec);

                        if (matcher.find()) {
                            String sortItemName = matcher.group(1);
                            String sortOrder = matcher.group(2);

                            frameData.setTableDefaultSortKey(sortItemName);
                            frameData.setTableDefaultSortOrder(sortOrder.equals("昇順") ? "ASCEND" : "DESCEND");
                        }
                    }
                }

                rowIndex++;
            }

            datas.add(frameData);

            sheetIndex++;
        }

        create(datas);
    }

//    String getItemPrefix() {
//        return "B";
//    }

    void create(List<FrameData> datas) {

        for (FrameData f : datas) {

            if (f.isSearchFlag()) {

                createSearchCondition(f);

                String tableName = "";
                for (FrameItemData item : f.getItems()) {
                    if (!item.isInput()) {
                        continue;
                    }
                    if (tableName.equals("") || item.getTableName().equals(tableName)) {
                        tableName = item.getTableName();
                    } else {
                        tableName = "";
                        break;
                    }
                }

                if (!tableName.equals("")) {
                    createSearchListDao(f, tableName);
                }
            }

            for (FrameItemData item : f.getItems()) {

                if (!item.getId().startsWith(FrameItemData.ItemType.SEARCH_CONDITION.type)) {
                    continue;
                }

                createSearchConditionItemJsp(f, item);
            }
        }

        createConditionNoBean(datas);
    }

    protected void createTableColumns(FrameItemData item, Sheet sheet) {

        String tableName = item.getLabel();
        String tableItemId = item.getId();

        boolean flag = false;

        int rowIndex = 5;
        Row row = sheet.getRow(0);
        while ((row = sheet.getRow(rowIndex)) != null) {

            String itemId = getStringValue(row, idxId);

            if (itemId.equals("")) {
                break;
            }

            if (itemId.endsWith(tableName) || itemId.equals(tableItemId)) {
                flag = true;
                rowIndex++;
                continue;
            }

            if (flag && itemId.startsWith(FrameItemData.ItemType.TABLE_COLUMN.type)) {

                TableListItemData clm = new TableListItemData();
                clm.setId(itemId);
                createItem(row, clm, itemId);
                item.addTableColumn(clm);
                clm.setSort(getStringValue(row, idxSort).equals("○") || getStringValue(row, idxSort).equals("◎"));
                if (getStringValue(row, idxSort).equals("◎")) {
                    clm.setSortOrder("DESCEND");
                }

                if (!clm.isHeader()) {
                    if (Utils.isDBEmpty(clm) && !clm.isCheckbox() && !clm.isRadioButton() && !clm.isText()) {
                        System.out.println("関連テーブル/カラムの定義なし\t\t" + sheet.getSheetName() + "\t" + clm.getId());
                    }
                }
            }


            if (!itemId.startsWith(FrameItemData.ItemType.TABLE_COLUMN.type)
                    && !itemId.startsWith(FrameItemData.ItemType.SEARCH_CONDITION.type)
                    && !itemId.startsWith(FrameItemData.ItemType.SETTING_DATA.type)
                    && flag) {
                break;
            }

            rowIndex++;
        }

        if (!flag) {
            System.out.println("表の定義なし\t\t" + sheet.getSheetName() + "\t" + tableName);
        }
    }



    static final int idxId = 1;                // 識別ID
    static final int idxItemType = 3;          // 種別
    static final int idxInput = 6;             // 入力
    static final int idxItemName = 2;          // 名称
    static final int idxTableName = 15;        // 関連DB
    static final int idxColumnName = 16;       // 関連カラム
    static final int idxInputDataType = 10;    // データ型
    static final int idxInputRange = 9;        // 入力桁数
    static final int idxInputCharType = 11;    // 文字種
    static final int idxInputRequired = 7;     // 入力必須
    static final int idxSort = 8;              // ソート可否
    static final int idxSpec = 4;              // 仕様(一覧のデフォルトソートを判断するためだけに使用している)


    protected void createItem(Row row, FrameItemData item, String itemId) {

        String inputType = getStringValue(row, idxItemType);
        String input = getStringValue(row, idxInput);
        String itemName = getStringValue(row, idxItemName);
        String tableName = getStringValue(row, idxTableName);
        String columnName = getStringValue(row, idxColumnName);
        String inputDataType = getStringValue(row, idxInputDataType);
        String inputRange = getStringValue(row, idxInputRange);
        String inputCharType = getStringValue(row, idxInputCharType);
        String inputRequired = getStringValue(row, idxInputRequired);

        item.setLabel(itemName);
        item.setInput(input.equals("○") || input.equals("△"));
        if (!tableName.equals("") && !tableName.equals("-")) {
            item.setTableName(tableName.replaceAll("\n", "_").replaceAll("\r", "_"));
            item.setColumnName(columnName.replaceAll("\n", "_").replaceAll("\r", "_"));
        }
        if (!inputDataType.equals("") && !inputDataType.equals("-")) {
            item.setInputDataType(inputDataType);
        }
        item.setInputType(inputType);
        if (inputRange.matches("\\d+")) {
            item.setRange(Integer.parseInt(inputRange));
        }
        item.setInputCharType(inputCharType);
        item.setRequired(inputRequired.equals("○"));
    }

    private void createSearchCondition(FrameData frameData) {

        String dir = distDir + "\\src\\main\\java_autogene\\" + Constants2.PRJ_WEB_CONDITION_FORM_PACKAGE_DIR;

        write(dir, dir+"\\"+frameData.getFrameId()+"_SearchConditionForm.java", new SearchConditionFormBuilder(frameData));

        dir = distDir + "\\src\\main\\java_autogene\\" + Constants.PRJ_BASE_PACKAGE_DIR+"\\dto";

        write(dir, dir+"\\"+frameData.getFrameId()+"_SearchCondition.java", new SearchConditionInterfaceBuilder(frameData));
    }

    private void createConditionNoBean(List<FrameData> datas) {

        String dir = distDir + "\\src\\main\\resources_autogene";

        write(dir, dir+"\\applicationContext-searchcondition.xml", new SearchConditionItemBeanXmlBuilder(datas));
    }


    private void createSearchListDao(FrameData frameData, String tableName) {

        String dir = distDir + "\\src\\main\\java_autogene\\" + Constants.PRJ_DAO_PACKAGE_DIR+"\\impl";

        write(dir, dir+"\\"+frameData.getFrameId()+"_SearchListDaoImpl.java", new SingleEntitySearchListDaoImplBuilder(frameData, tableName));
    }

    private void createSearchConditionItemJsp(FrameData frameData, FrameItemData item) {

        String dir = distDir + "\\src\\main\\webapp\\content_autogene\\" + frameData.getFrameId();

        write(dir, dir+"\\" + item.getId().toUpperCase() + ".jsp", new SearchConditionJspBuilder(frameData, item));
    }


    public static void main(String[] args) {

        for (String arg : args) {
            System.out.println(arg);
        }

        String xls = null;
        String distDir = null;
        String framelistxls = null;

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

            } else if (arg.equals("-framelistxls")) {
                if (i+1 < args.length) {
                    framelistxls = args[i+1];
                    i++;
                }
            }
        }

        System.out.println("xls -> " + xls);
        System.out.println("dist -> " + distDir);
        System.out.println("framelistxls -> " + framelistxls);

        if (StringUtils.isEmpty(xls)
                || StringUtils.isEmpty(distDir)
                || StringUtils.isEmpty(framelistxls)) {

            System.out.println("引数異常");
            return;
        }

        if (!distDir.endsWith("\\")) {
            distDir += "\\";
        }
        distDir += CreateSearchCondition.class.getSimpleName() + "\\" + Constants.DIST_DIR_WEB;

        try {
            FileUtils.deleteDirectory(new File(distDir));
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }

        new CreateSearchCondition(framelistxls, xls, distDir);

        System.out.println("");
        System.out.println("done.");
    }
}

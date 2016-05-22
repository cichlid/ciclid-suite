package alpha114.tool.cichlid.autogenerationtool;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;
import alpha114.tool.cichlid.autogenerationtool.data.TableData;
import alpha114.tool.cichlid.autogenerationtool.data.TableListItemData;

/**
 * @author kurisakisatoshi
 */
public final class Utils {

    public static String toLCC(String name) {

        String[] split = name.toLowerCase().split("_");

        boolean first = true;
        String ret = "";
        for (String string : split) {
            if (first) {
                ret += string;
            } else {
                ret += string.substring(0, 1).toUpperCase();
                ret += string.substring(1);
            }
            first = false;
        }

        return ret;
    }

    public static String toUCC(String name) {

        String[] split = name.toLowerCase().split("_");

        String ret = "";
        for (String string : split) {
            ret += string.substring(0, 1).toUpperCase();
            ret += string.substring(1);
        }

        return ret;
    }

    public static boolean isCUD(TableData tableData) {
        return isC(tableData) || isU(tableData) || isD(tableData);
    }

    public static boolean isC(TableData tableData) {
        return !StringUtils.isEmpty(tableData.insertTrigger) && !tableData.insertTrigger.equals("なし");
    }

    public static boolean isU(TableData tableData) {
        return !StringUtils.isEmpty(tableData.updateTrigger) && !tableData.updateTrigger.equals("なし");
    }

    public static boolean isD(TableData tableData) {
        return !StringUtils.isEmpty(tableData.deleteTrigger) && !tableData.deleteTrigger.equals("なし");
    }

    public static String getRadioButtonName(FrameData frameData, FrameItemData item) {

        for (FrameItemData d : frameData.getItems()) {

            if (!d.isRadioButton()) {
                continue;
            }

            if (item == d) {
                continue;
            }

            if (item.tableName.equals(d.tableName)
                    && item.columnName.equals(d.columnName)) {

                int c = item.id.compareTo(d.id);
                if (c == 0 || c < 0) {
                    return item.id;

                } else {
                    return d.id;
                }
            }
        }

        return item.id;
    }

    public static String getLabelId(FrameData frameData, FrameItemData item) {

        for (FrameItemData d : frameData.getItems()) {

            if (item == d) {
                continue;
            }

            if (item.label.equals(d.label)) {
                return d.id;
            }

            if (item.label.replaceFirst("\\(From\\)", "").replaceFirst("\\(YYYYMMDD\\)", "").equals(d.label.replaceFirst("\\(YYYYMMDD\\)", ""))
                    || item.label.replaceFirst("\\(To\\)", "").replaceFirst("\\(YYYYMMDD\\)", "").equals(d.label.replaceFirst("\\(YYYYMMDD\\)", ""))) {
                return d.id;
            }
        }

        throw new NullPointerException("同名のラベルが見つからない " + frameData.frameName + ", " + item.label);
    }

    public static String getLabelId_cal(FrameData frameData, FrameItemData item) {

        for (FrameItemData d : frameData.getItems()) {

            if (item.isCalendar() && item == d) {
                continue;
            }

            if ((item.isCalendar() && item.label.startsWith(d.label))
                    || (item.label.equals(d.label))) {
                return d.id;
            }
        }

        throw new NullPointerException("同名のラベルが見つからない " + frameData.frameName + ", " + item.label);
    }

    public static String getLabel_cal(FrameData frameData, FrameItemData item) {

        for (FrameItemData d : frameData.getItems()) {

            if (item.isCalendar() && item == d) {
                continue;
            }

            if ((item.isCalendar() && item.label.startsWith(d.label))
                    || (item.label.equals(d.label))) {
                return d.label;
            }
        }

        throw new NullPointerException("同名のラベルが見つからない " + frameData.frameName + ", " + item.label);
    }

    public static FrameItemData getTo(FrameData frameData, FrameItemData from) {

        for (FrameItemData item : frameData.getItems()) {

            if (item == from) {
                continue;
            }

            if (item.label.equals(from.label.replaceAll("開始", "終了"))) {
                return item;
            }
        }

        throw new NullPointerException("FromToが対でない " + frameData.frameName + ", " + from.label);
    }

    public static boolean isDBEmpty(FrameItemData item) {

        return item.tableName.equals("")
                || item.tableName.equals("-")
                || item.columnName.equals("")
                || item.columnName.equals("-");
    }

    public static boolean isCommonButton(FrameItemData item) {

        for (String name : Constants.COMMON_BUTTONS) {
            if (item.label.equals(name)) {
                return true;
            }
        }

        return false;
    }


    public static TableListItemData getHeaderItem(FrameData frameData, List<TableListItemData> clmList, TableListItemData clm) {

        for (TableListItemData item : clmList) {

            if (item.isHeader()) {
                if (item.label.equals(clm.label)) {
                    return item;
                }
            }
        }

        System.out.println(frameData.frameName + ", " + clm.id + " ," + clm.label);

        throw new NullPointerException();
    }

    public static String getDefaultSortKey(FrameData frameData) {

        for (FrameItemData item : frameData.getItems()) {

            if (!item.isTable()) {
                continue;
            }

            for (TableListItemData clm : item.getTableColumns()) {

                if (clm.isHeader()
                        && clm.sort) {

                    if (clm.label.startsWith("管理項目")) {
                        continue;
                    }

                    return clm.id;
                }
            }
        }

        return "";
    }

    public static String getDefaultSortOrder(FrameData frameData) {

        for (FrameItemData item : frameData.getItems()) {

            if (!item.isTable()) {
                continue;
            }

            for (TableListItemData clm : item.getTableColumns()) {

                if (clm.isHeader()
                        && clm.sort) {

                    if (clm.label.startsWith("管理項目")) {
                        continue;
                    }

                    return clm.sortOrder;
                }
            }
        }

        return "";
    }

    private Utils() {
    }
}

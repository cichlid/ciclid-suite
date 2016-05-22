package alpha114.tool.cichlid.autogenerationtool.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kurisakisatoshi
 */
public class FrameItemData {

    public String id;
    public String label;

    public boolean input;
    public String tableName = "";
    public String columnName = "";

    public int range = 0;
    public String inputDataType = "";
    public InputType inputType = InputType._NULL;
    public CharType inputCharType = CharType._NULL;
    public boolean required;

    private List<TableListItemData> tableColumns = new ArrayList<TableListItemData>();

    public enum ItemType {

        FRAME_TITLE("A"),
        TABLE_COLUMN("T"),
        SEARCH_CONDITION("K"),
        SETTING_DATA("S"),
        ETC("X");

        public final String type;

        private ItemType(String type) {
            this.type = type;
        }
    }

    public enum InputType {

        LABEL("ラベル"),
        LINK_TEXT_BLANK("テキストリンク(別窓)"),
        LINK_TEXT_SELF("テキストリンク"),
        TEXT("テキストボックス"),
        TEXTAREA("テキストエリア"),
        PASSWORD("パスワード"),
        PULLDOWN("プルダウン"),
        RADIOBUTTON("ラジオボタン"),
        CHECKBOX("チェックボックス"),
        TABLE("表"),
        BUTTON("ボタン"),
        _NULL(""),

        // 一覧画面
        HEADER("ヘッダ"),
        TEXTDATA("データ"),
        LINK("リンク"),
        LINK_OR_TEXT("データ/リンク");

        public final String typeName;

        private InputType(String typeName) {
            this.typeName = typeName;
        }

        String text() {
            return typeName;
        }

        static InputType is(String inputType) {

            inputType = inputType.replaceFirst("テーブルデータ/", "");

            for (InputType type : values()) {
                if (type.typeName.equals(inputType)) {
                    return type;
                }
                if (inputType.startsWith(HEADER.text())) {
                    return HEADER;
                }
            }

            if (inputType.equals("テーブル")) {
                return TABLE;
            }

            return _NULL;
        }
    }

    public enum CharType {

        NUMERIC("半角数字"),
        ALPHA_NUMERIC_SIGN("半角英数記号"),
        JA("全角／半角"),
        _NULL("");

        public final String charType;

        private CharType(String charType) {
            this.charType = charType;
        }

        String text() {
            return charType;
        }

        static CharType is(String charType) {

            for (CharType type : values()) {
                if (type.charType.equals(charType)) {
                    return type;
                }
            }

            return _NULL;
        }
    }

    public String getItem___Name() {
        if (label.equals("種別") && tableName.equals("-")) {
            return "type";
        }
        return tableName.substring(0, 1).toUpperCase() + tableName.substring(1).toLowerCase() + "___" + columnName.toLowerCase();
    }

    public boolean isCalendar() {
        return inputDataType.equals("日付");
    }

    public boolean isIpAddress() {
        return inputDataType.equals("IPアドレス");
    }

    public boolean isText() {
        return inputType == InputType.TEXT;
    }

    public boolean isTextArea() {
        return inputType == InputType.TEXTAREA;
    }

    public boolean isPulldown() {
        return inputType == InputType.PULLDOWN;
    }

    public boolean isPassword() {
        return inputType == InputType.PASSWORD;
    }

    public boolean isRadioButton() {
        return inputType == InputType.RADIOBUTTON;
    }

    public boolean isCheckbox() {
        return inputType == InputType.CHECKBOX;
    }

    public boolean isTable() {
        return inputType == InputType.TABLE;
    }

    public boolean isButton() {
        return inputType == InputType.BUTTON;
    }

    public boolean isLabel() {
        return inputType == InputType.LABEL;
    }

    public boolean isTextLink_self() {
        return inputType == InputType.LINK_TEXT_SELF;
    }

    public boolean isTextLink_blank() {
        return inputType == InputType.LINK_TEXT_BLANK;
    }

    public boolean isNumeric() {
        return inputCharType == CharType.NUMERIC;
    }

    public boolean isAscii() {
        return inputCharType == CharType.ALPHA_NUMERIC_SIGN;
    }



    public String getInputType() {
        return inputType.text();
    }

    public void setInputType(String inputType) {
        this.inputType = InputType.is(inputType);
    }

    public void setInputCharType(String inputCharType) {
        this.inputCharType = CharType.is(inputCharType);
    }



    public void addTableColumn(TableListItemData clm) {
        tableColumns.add(clm);
    }

    public List<TableListItemData> getTableColumns() {
        return tableColumns;
    }



}

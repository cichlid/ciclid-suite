package alpha114.tool.cichlid.autogenerationtool.data;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author kurisakisatoshi
 */
public class ColumnData {

    public String tableName;

    /** 項番 */
    public String no;

    /** 列名(和名) */
    public String columnName;

    /** 列名 */
    public String name;

    /** 桁数 */
    public String length;

    /** バイト数 */
    public String bytes;

    /** データ型 */
    public DBColumnDataTypeEnum dataType;

    /** NOT NULL */
    public boolean notNull;

    /** デフォルト */
    public String defaultValue;

    /** 外部キー */
    public String foreignKey;

    /** 入力範囲 */
    public String range;

    /** 使用シーケンス */
    public String useSeq;
    public boolean isUseSeq;

    /** 更新有無 */
    public boolean update;

    /** 更新契機 */
    public String updateTrigger;

    /** 更新機能 */
    public String updateFunc;

    /** 通知有無 */
    public boolean notify;

    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}

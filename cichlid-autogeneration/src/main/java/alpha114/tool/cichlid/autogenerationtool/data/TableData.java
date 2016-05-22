package alpha114.tool.cichlid.autogenerationtool.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author kurisakisatoshi
 */
public class TableData {

    /** テーブル番号 */
    public int no;

    /** インスタンス名 */
    public String instanceName;

    /** テーブル名(和名) */
    public String tableName;

    /** テーブル名 */
    public String name;

    /** 1レコードのサイズ */
    public int recordSize;

    /** 予想レコード数 */
    public int recordNum;

    /** カラム情報のリスト */
    private List<ColumnData> columns;

    /** プライマリキー 実際は1つしかない */
    private List<ColumnData> primaryKeys = new ArrayList<ColumnData>();

    /** 一意キー */
    private List<List<ColumnData>> uniqKeys = new ArrayList<List<ColumnData>>();

    /** インデックス1 */
    private List<ColumnData> index1List = new ArrayList<ColumnData>();

    /** インデックス2 */
    private List<ColumnData> index2List = new ArrayList<ColumnData>();

    /** インデックス3 */
    private List<ColumnData> index3List = new ArrayList<ColumnData>();

    private List<String> relationOneToMany = new ArrayList<String>();

    private List<String> relationOneToOne = new ArrayList<String>();



    // CUD契機
    /** insert契機 */
    public String insertTrigger;

    /** update契機 */
    public String updateTrigger;

    /** delete契機 */
    public String deleteTrigger;

    // 通知有無
    public boolean notifyByInsert;
    public boolean notifyByUpdate;
    public boolean notifyByDelete;


    public List<ColumnData> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnData> columns) {
        this.columns = columns;
    }

    public List<ColumnData> getPrimaryKeys() {
        return primaryKeys;
    }

    public List<List<ColumnData>> getUniqKeys() {
        return uniqKeys;
    }

    public List<ColumnData> getIndex1List() {
        return index1List;
    }

    public void addIndex1Column(ColumnData index) {
        this.index1List.add(index);
    }
    public List<ColumnData> getIndex2List() {
        return index2List;
    }

    public void addIndex2Column(ColumnData index) {
        this.index2List.add(index);
    }

    public List<ColumnData> getIndex3List() {
        return index3List;
    }

    public void addIndex3Column(ColumnData index) {
        this.index3List.add(index);
    }

    public void addPrimaryKey(ColumnData pKey, int index) {
        for (int i = index - primaryKeys.size(); i > 0; i--) {
            primaryKeys.add(new ColumnData());
        }
        primaryKeys.set(index - 1, pKey);
    }

    public void addUniqKey(ColumnData uniq, int no, int index) {
        for (int i = no - uniqKeys.size() + 1; i > 0; i--) {
            uniqKeys.add(new ArrayList<ColumnData>());
        }
        List<ColumnData> uniqList = uniqKeys.get(no);
        for (int i = index - uniqList.size(); i > 0; i--) {
            uniqList.add(new ColumnData());
        }
        uniqList.set(index - 1, uniq);
    }

    public void addOneToOne(String tableName) {
        relationOneToOne.add(tableName);
    }

    public List<String> getOneToOneRelations() {
        return relationOneToOne;
    }

    public void addOneToMany(String tableName) {
        relationOneToMany.add(tableName);
    }

    public List<String> getOneToManyRelations() {
        return relationOneToMany;
    }



    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}

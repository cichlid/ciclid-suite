package alpha114.tool.cichlid.autogenerationtool.src.builder;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.io.output.StringBuilderWriter;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.TableData;

/**
 * @author kurisakisatoshi
 */
public class DaoImplBuilder implements Builder {

    private final TableData tableData;
    private final String instanceName;

    public DaoImplBuilder(String instanceName, TableData tableData) {
        this.instanceName = instanceName;
        this.tableData = tableData;
    }

    @Override
    public String build() {

        StringBuilderWriter w = new StringBuilderWriter();

        try {
            createDao(w);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return w.toString();
    }

    private void createDao(Writer o) throws IOException {

        boolean insert = Utils.isC(tableData);
        boolean update = Utils.isU(tableData);
        boolean delete = Utils.isD(tableData);

        String tableName = Utils.toUCC(tableData.getName());

        // 看板のところ
        StringBuilder buffer = new StringBuilder();
        buffer.append("/* ").append("\n");
        buffer.append(" * ").append(Constants.COPYRIGHT).append("\n");
        buffer.append(" * ").append("\n");
        buffer.append(" * $Id").append(": $").append("\n");
        buffer.append(" * ").append("\n");
        buffer.append(" * 備考    ：DB設計書から自動生成").append("\n");
        buffer.append(" * 更新履歴：").append("\n");
        buffer.append(" *   日付         更新者            内容").append("\n");
        buffer.append(" *   ").append(Constants.DATE_CREATED).append("   kurisakisatoshi   新規作成").append("\n");
        buffer.append(" * ").append("\n");
        buffer.append(" */").append("\n");
        buffer.append("package ").append(Constants.PRJ_DAO_CUD_PACKAGE).append(".").append(tableData.getName().toLowerCase()).append(".impl;\n");
        buffer.append("").append("\n");


        // import
        buffer.append("import java.util.List;").append("\n");
        buffer.append("import java.util.Map;").append("\n");
        buffer.append("").append("\n");
        buffer.append("import javax.sql.DataSource;").append("\n");
        buffer.append("").append("\n");
        buffer.append("import ").append(Constants.DAO_PACKAGE).append(".DaoException;").append("\n");
        if (delete) {
            buffer.append("import ").append(Constants.DAO_PACKAGE).append(".CanNotDeleteException;").append("\n");
            buffer.append("import ").append(Constants.DAO_PACKAGE).append(".ForeignKeyConstraintException;").append("\n");
        }
        buffer.append("import ").append(Constants.DAO_PACKAGE).append(".UniqueConstraintException;").append("\n");
        buffer.append("import ").append(Constants.DAO_PACKAGE).append(".common.DefaultJdbcDaoSupport;").append("\n");
        buffer.append("import ").append(Constants.ENTITY_PACKAGE).append(".Column;").append("\n");
        if (update || delete) {
            buffer.append("import ").append(Constants.ENTITY_PACKAGE).append(".EntityNotFoundException;").append("\n");
        }
        buffer.append("import ").append(Constants.PRJ_DAO_CUD_PACKAGE).append(".").append(tableData.getName().toLowerCase()).append(".").append(tableName).append("Dao;").append("\n");
        buffer.append("import ").append(Constants.PRJ_ENTITY_PACKAGE).append(".").append(tableName).append(";\n");
        buffer.append("").append("\n");


        // class看板
        buffer.append("/**").append("\n");
        buffer.append(" * <p>").append(tableData.getTableName()).append("(").append(instanceName).append(".").append(tableData.getName()).append(")への").append("\n");
        if (insert) {
            buffer.append(" * ").append("<li>Create</li>\n");
        }
        if (update) {
            buffer.append(" * ").append("<li>Update</li>\n");
        }
        if (delete) {
            buffer.append(" * ").append("<li>Delete</li>\n");
        }
        buffer.append(" * ").append("をサポートします。\n");
        buffer.append(" * ").append("\n");
        buffer.append(" * ").append("<p>※注意）トランザクションの管理は呼び元で行うこと\n");
        buffer.append(" * ").append("\n");
        buffer.append(" * @see ").append(Constants.PRJ_DAO_CUD_PACKAGE).append(".").append(tableData.getName().toLowerCase()).append(".").append(tableName).append("Dao").append("\n");
        buffer.append(" * @author  kurisakisatoshi").append("\n");
        buffer.append(" * @version $Revision").append(": $").append("\n");
        buffer.append(" */").append("\n");

        // class
        buffer.append("public class ").append(tableName).append("DaoImpl implements ").append(tableName).append("Dao {").append("\n");
        buffer.append("    ").append("").append("\n");
        buffer.append("    ").append("// ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");
        buffer.append("    ").append("\n");
        buffer.append("    private DefaultJdbcDaoSupport defaultJdbcDaoSupport;").append("\n");
        buffer.append("    private DataSource dataSource;").append("\n");
        buffer.append("    ").append("\n");
        buffer.append("    public void setDataSource(DataSource dataSource) {").append("\n");
        buffer.append("        this.dataSource = dataSource;").append("\n");
        buffer.append("        defaultJdbcDaoSupport = new DefaultJdbcDaoSupport();").append("\n");
        buffer.append("        defaultJdbcDaoSupport.setDataSource(getDataSource());").append("\n");
        buffer.append("    }").append("\n");
        buffer.append("    ").append("\n");
        buffer.append("    public DataSource getDataSource() {").append("\n");
        buffer.append("        return dataSource;").append("\n");
        buffer.append("    }").append("\n");
        buffer.append("    ").append("\n");
        buffer.append("    ").append("\n");
        buffer.append("    ").append("\n");

        if (insert) {
            buffer.append("    // ---------------------------------------------------------------------------------------------------------------------").append("\n");
            buffer.append("    // INSERT --------------------------------------------------------------------------------------------------------------").append("\n");
            buffer.append("    // ---------------------------------------------------------------------------------------------------------------------").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    /**").append("\n");
            buffer.append("     * {@inheritDoc}").append("\n");
            buffer.append("     */").append("\n");
            buffer.append("    @Override").append("\n");
            buffer.append("    public void create(Map<Column, Object> valuesMap) throws UniqueConstraintException, DaoException {").append("\n");
            buffer.append("        defaultJdbcDaoSupport.create(").append(tableName).append(".class, valuesMap);").append("\n");
            buffer.append("    }").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    /**").append("\n");
            buffer.append("     * {@inheritDoc}").append("\n");
            buffer.append("     */").append("\n");
            buffer.append("    @Override").append("\n");
            buffer.append("    public long createAndReturnPKey(Map<Column, Object> valuesMap) throws UniqueConstraintException, DaoException {").append("\n");
            buffer.append("        return defaultJdbcDaoSupport.createAndReturnPKey(").append(tableName).append(".class, valuesMap);").append("\n");
            buffer.append("    }").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    /**").append("\n");
            buffer.append("     * {@inheritDoc}").append("\n");
            buffer.append("     */").append("\n");
            buffer.append("    @Override").append("\n");
            buffer.append("    public ").append(tableName).append(" createAndReturnEntity(Map<Column, Object> valuesMap) throws UniqueConstraintException, DaoException {").append("\n");
            buffer.append("        return defaultJdbcDaoSupport.createAndReturnEntity(").append(tableName).append(".class, valuesMap);").append("\n");
            buffer.append("    }").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    /**").append("\n");
            buffer.append("     * {@inheritDoc}").append("\n");
            buffer.append("     */").append("\n");
            buffer.append("    @Override").append("\n");
            buffer.append("    public void create(").append(tableName).append(" entity, List<Column> insertColumns) throws UniqueConstraintException, DaoException {").append("\n");
            buffer.append("        defaultJdbcDaoSupport.create(entity, insertColumns);").append("\n");
            buffer.append("    }").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    /**").append("\n");
            buffer.append("     * {@inheritDoc}").append("\n");
            buffer.append("     */").append("\n");
            buffer.append("    @Override").append("\n");
            buffer.append("    public void create(long sysid, ").append(tableName).append(" entity, List<Column> insertColumns) throws UniqueConstraintException, DaoException {").append("\n");
            buffer.append("        defaultJdbcDaoSupport.create(sysid, entity, insertColumns);").append("\n");
            buffer.append("    }").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    /**").append("\n");
            buffer.append("     * {@inheritDoc}").append("\n");
            buffer.append("     */").append("\n");
            buffer.append("    @Override").append("\n");
            buffer.append("    public long createAndReturnPKey(").append(tableName).append(" entity, List<Column> insertColumns) throws UniqueConstraintException, DaoException {").append("\n");
            buffer.append("        return defaultJdbcDaoSupport.createAndReturnPKey(entity, insertColumns);").append("\n");
            buffer.append("    }").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    /**").append("\n");
            buffer.append("     * {@inheritDoc}").append("\n");
            buffer.append("     */").append("\n");
            buffer.append("    @Override").append("\n");
            buffer.append("    public ").append(tableName).append(" createAndReturnEntity(").append(tableName).append(" entity, List<Column> insertColumns) throws UniqueConstraintException, DaoException {").append("\n");
            buffer.append("        return defaultJdbcDaoSupport.createAndReturnEntity(entity, insertColumns);").append("\n");
            buffer.append("    }").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    /**").append("\n");
            buffer.append("     * {@inheritDoc}").append("\n");
            buffer.append("     */").append("\n");
            buffer.append("    @Override").append("\n");
            buffer.append("    public void createBatch(List<").append(tableName).append("> entityList, List<Column> insertColumns) throws UniqueConstraintException, DaoException {").append("\n");
            buffer.append("        defaultJdbcDaoSupport.createBatch(entityList, insertColumns);").append("\n");
            buffer.append("    }").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    /**").append("\n");
            buffer.append("     * {@inheritDoc}").append("\n");
            buffer.append("     */").append("\n");
            buffer.append("    @Override").append("\n");
            buffer.append("    public List<Long> createBatchAndReturnPKeyList(List<").append(tableName).append("> entityList, List<Column> insertColumns) throws UniqueConstraintException, DaoException {").append("\n");
            buffer.append("        return defaultJdbcDaoSupport.createBatchAndReturnPKeyList(entityList, insertColumns);").append("\n");
            buffer.append("    }").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    /**").append("\n");
            buffer.append("     * {@inheritDoc}").append("\n");
            buffer.append("     */").append("\n");
            buffer.append("    @Override").append("\n");
            buffer.append("    public List<").append(tableName).append("> createBatchAndReturnEntityList(List<").append(tableName).append("> entityList, List<Column> insertColumns) throws UniqueConstraintException, DaoException {").append("\n");
            buffer.append("        return defaultJdbcDaoSupport.createBatchAndReturnEntityList(entityList, insertColumns);").append("\n");
            buffer.append("    }").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    ").append("\n");
        }

        if (update) {
            buffer.append("    // ---------------------------------------------------------------------------------------------------------------------").append("\n");
            buffer.append("    // UPDATE --------------------------------------------------------------------------------------------------------------").append("\n");
            buffer.append("    // ---------------------------------------------------------------------------------------------------------------------").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    /**").append("\n");
            buffer.append("     * {@inheritDoc}").append("\n");
            buffer.append("     */").append("\n");
            buffer.append("    @Override").append("\n");
            buffer.append("    public void update(long pKey, Map<Column, Object> valuesMap) throws EntityNotFoundException, UniqueConstraintException, DaoException {").append("\n");
            buffer.append("        defaultJdbcDaoSupport.update(").append(tableName).append(".class, pKey, valuesMap);").append("\n");
            buffer.append("    }").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    /**").append("\n");
            buffer.append("     * {@inheritDoc}").append("\n");
            buffer.append("     */").append("\n");
            buffer.append("    @Override").append("\n");
            buffer.append("    public ").append(tableName).append(" updateAndReturnEntity(long pKey, Map<Column, Object> valuesMap) throws EntityNotFoundException, UniqueConstraintException, DaoException {").append("\n");
            buffer.append("        return defaultJdbcDaoSupport.updateAndReturnEntity(").append(tableName).append(".class, pKey, valuesMap);").append("\n");
            buffer.append("    }").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    /**").append("\n");
            buffer.append("     * {@inheritDoc}").append("\n");
            buffer.append("     */").append("\n");
            buffer.append("    @Override").append("\n");
            buffer.append("    public void update(").append(tableName).append(" entity, List<Column> updateColumns) throws EntityNotFoundException, UniqueConstraintException, DaoException {").append("\n");
            buffer.append("        defaultJdbcDaoSupport.update(entity, updateColumns);").append("\n");
            buffer.append("    }").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    /**").append("\n");
            buffer.append("     * {@inheritDoc}").append("\n");
            buffer.append("     */").append("\n");
            buffer.append("    @Override").append("\n");
            buffer.append("    public ").append(tableName).append(" updateAndReturnEntity(").append(tableName).append(" entity, List<Column> updateColumns) throws EntityNotFoundException, UniqueConstraintException, DaoException {").append("\n");
            buffer.append("        return defaultJdbcDaoSupport.updateAndReturnEntity(entity, updateColumns);").append("\n");
            buffer.append("    }").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    /**").append("\n");
            buffer.append("     * {@inheritDoc}").append("\n");
            buffer.append("     */").append("\n");
            buffer.append("    @Override").append("\n");
            buffer.append("    public void updateBatch(List<").append(tableName).append("> entityList, List<Column> updateColumns) throws EntityNotFoundException, UniqueConstraintException, DaoException {").append("\n");
            buffer.append("        defaultJdbcDaoSupport.updateBatch(entityList, updateColumns);").append("\n");
            buffer.append("    }").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    /**").append("\n");
            buffer.append("     * {@inheritDoc}").append("\n");
            buffer.append("     */").append("\n");
            buffer.append("    @Override").append("\n");
            buffer.append("    public List<").append(tableName).append("> updateBatchAndReturnEntityList(List<").append(tableName).append("> entityList, List<Column> updateColumns) throws EntityNotFoundException, UniqueConstraintException, DaoException {").append("\n");
            buffer.append("        return defaultJdbcDaoSupport.updateBatchAndReturnEntityList(entityList, updateColumns);").append("\n");
            buffer.append("    }").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    ").append("\n");
        }

        if (delete) {
            buffer.append("    // ---------------------------------------------------------------------------------------------------------------------").append("\n");
            buffer.append("    // DELETE --------------------------------------------------------------------------------------------------------------").append("\n");
            buffer.append("    // ---------------------------------------------------------------------------------------------------------------------").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    /**").append("\n");
            buffer.append("     * {@inheritDoc}").append("\n");
            buffer.append("     */").append("\n");
            buffer.append("    @Override").append("\n");
            buffer.append("    public void delete(long pKey) throws CanNotDeleteException, EntityNotFoundException, ForeignKeyConstraintException, DaoException {").append("\n");
            buffer.append("        defaultJdbcDaoSupport.delete(").append(tableName).append(".class, pKey);").append("\n");
            buffer.append("    }").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    /**").append("\n");
            buffer.append("     * {@inheritDoc}").append("\n");
            buffer.append("     */").append("\n");
            buffer.append("    @Override").append("\n");
            buffer.append("    public void delete(").append(tableName).append(" entity) throws CanNotDeleteException, EntityNotFoundException, ForeignKeyConstraintException, DaoException {").append("\n");
            buffer.append("        defaultJdbcDaoSupport.delete(entity);").append("\n");
            buffer.append("    }").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    /**").append("\n");
            buffer.append("     * {@inheritDoc}").append("\n");
            buffer.append("     */").append("\n");
            buffer.append("    @Override").append("\n");
            buffer.append("    public void deleteByCondition(Map<Column, Object> conditionMap) throws CanNotDeleteException, EntityNotFoundException, ForeignKeyConstraintException, DaoException {").append("\n");
            buffer.append("        defaultJdbcDaoSupport.deleteByCondition(").append(tableName).append(".class, conditionMap);").append("\n");
            buffer.append("    }").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    /**").append("\n");
            buffer.append("     * {@inheritDoc}").append("\n");
            buffer.append("     */").append("\n");
            buffer.append("    @Override").append("\n");
            buffer.append("    public int deleteBatchByPKeys(List<Long> pKeyList) throws CanNotDeleteException, EntityNotFoundException, ForeignKeyConstraintException, DaoException {").append("\n");
            buffer.append("        return defaultJdbcDaoSupport.deleteBatch(").append(tableName).append(".class, pKeyList);").append("\n");
            buffer.append("    }").append("\n");
            buffer.append("    ").append("\n");
            buffer.append("    /**").append("\n");
            buffer.append("     * {@inheritDoc}").append("\n");
            buffer.append("     */").append("\n");
            buffer.append("    @Override").append("\n");
            buffer.append("    public int deleteBatch(List<").append(tableName).append("> entityList) throws CanNotDeleteException, EntityNotFoundException, ForeignKeyConstraintException, DaoException {").append("\n");
            buffer.append("        return defaultJdbcDaoSupport.deleteBatch(entityList);").append("\n");
            buffer.append("    }").append("\n");
        }

        buffer.append("}").append("\n");

        o.write(buffer.toString());
        o.flush();
    }
}


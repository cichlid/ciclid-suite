package alpha114.tool.cichlid.autogenerationtool.src;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;

import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.CreateSrc;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.ColumnData;
import alpha114.tool.cichlid.autogenerationtool.data.TableData;
import alpha114.tool.cichlid.autogenerationtool.src.builder.ComparatorBeanConfBuilder;
import alpha114.tool.cichlid.autogenerationtool.src.builder.ComparatorBuilder;
import alpha114.tool.cichlid.autogenerationtool.src.builder.DaoBeanConfBuilder;
import alpha114.tool.cichlid.autogenerationtool.src.builder.DaoBuilder;
import alpha114.tool.cichlid.autogenerationtool.src.builder.DaoImplBuilder;
import alpha114.tool.cichlid.autogenerationtool.src.builder.EntityBuilder;
import alpha114.tool.cichlid.autogenerationtool.src.builder.EntityWithRelationBuilder;

/**
 * @author kurisakisatoshi
 */
public class CreateEntityComparatorDao extends CreateSrc {

    private final String distDir;

    public CreateEntityComparatorDao(String xls, String distDir) {
        this.distDir = distDir;

        Workbook wb = createWorkbook(xls);
        if (wb == null) {
            return;
        }

        List<TableData> allTables = new ArrayList<TableData>();

        for (String instanceName : Constants.DB_INSTANCE_NAME) {

            List<TableData> tables = createTableData(wb, instanceName);

            // Entity
            createEntity(instanceName, tables);

            // comparator
            createComparator(tables);

            // dao
            createDao(instanceName, tables);

            // dao-bean
            createDaoBean(instanceName, tables);

            allTables.addAll(tables);
        }

        // comparator-bean
        createComparatorBean(allTables);

        // notification-target-bean
//        createNotificationTargetBean(allTables);
    }


    private void createEntity(String instanceName, List<TableData> tables) {

        String dir = distDir+"\\src\\main\\java_autogene\\" + Constants.PRJ_ENTITY_PACKAGE_DIR;

        for (TableData tableData: tables) {
            write(dir, dir+"\\"+Utils.toUCC(tableData.name)+".java", new EntityBuilder(instanceName, tableData));
            write(dir+"\\relation", dir+"\\relation\\"+Utils.toUCC(tableData.name)+"WithRelation.java", new EntityWithRelationBuilder(instanceName, tableData, tables));
        }
    }




    private void createComparator(List<TableData> tables) {

        for (TableData tableData: tables) {

            String dir = distDir+"\\src\\main\\java_autogene\\" + Constants.PRJ_COMPARATOR_PACKAGE_DIR + "\\" + tableData.name.toLowerCase();

            for (ColumnData column : tableData.getColumns()) {
                write(dir, dir+"\\"+Utils.toUCC(column.name)+"Comparator.java", new ComparatorBuilder(tableData, column));
            }
        }
    }


    private void createComparatorBean(List<TableData> allTables) {

        String dir = distDir+"\\src\\main\\resources_autogene";

        write(dir, dir+"\\applicationContext-comparator.xml", new ComparatorBeanConfBuilder(allTables));
    }

//    private void createNotificationTargetBean(List<TableData> allTables) {
//
//        String dir = distDir+"\\conf_autogene";
//
//        write(dir, dir+"\\applicationContext-notification.xml", new NotificationTargetBeanXmlBuilder(allTables));
//    }

    private void createDao(String instanceName, List<TableData> tables) {

        for (TableData tableData: tables) {

            if (!Utils.isCUD(tableData)) {
                continue;
            }

            String dir = distDir+"\\src\\main\\java_autogene\\" + Constants.PRJ_DAO_CUD_PACKAGE_DIR + "\\" + tableData.name.toLowerCase();

            write(dir, dir+"\\"+Utils.toUCC(tableData.name)+"Dao.java", new DaoBuilder(tableData));

            dir += "\\impl";

            write(dir, dir+"\\"+Utils.toUCC(tableData.name)+"DaoImpl.java", new DaoImplBuilder(instanceName, tableData));
        }
    }

    private void createDaoBean(String instanceName, List<TableData> tables) {

        String dir = distDir+"\\src\\main\\resources_autogene";

        write(dir, dir+"\\applicationContext-dao_"+instanceName+".xml", new DaoBeanConfBuilder(instanceName, tables));
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
        distDir += CreateEntityComparatorDao.class.getSimpleName() + "\\" + Constants.DIST_DIR_COMMON;

        try {
            FileUtils.deleteDirectory(new File(distDir));
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }

        new CreateEntityComparatorDao(xls, distDir);

        System.out.println("");
        System.out.println("done.");
    }
}

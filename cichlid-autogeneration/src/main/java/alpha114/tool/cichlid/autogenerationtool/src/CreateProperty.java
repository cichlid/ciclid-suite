package alpha114.tool.cichlid.autogenerationtool.src;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.CreateSrc;
import alpha114.tool.cichlid.autogenerationtool.data.PropertyData;
import alpha114.tool.cichlid.autogenerationtool.src.builder.PropertyConfBuilder;
import alpha114.tool.cichlid.autogenerationtool.src.builder.PropertyFileBuilder;

/**
 * @author kurisakisatoshi
 */
public class CreateProperty extends CreateSrc {

    final String distDir;

    public CreateProperty(String propertyXls, String distDir) {
        this.distDir = distDir;

        Workbook w = createWorkbook(propertyXls);
        if (w == null) {
            return;
        }

        List<String> propFiles = new ArrayList<String>();

        int sheetIndex = 0;
        Sheet sheet = null;
        while ((sheet = getSheetAt(w, sheetIndex)) != null) {

            String fileName = getStringValue(sheet, 1, 2);

            List<PropertyData> propList = new ArrayList<PropertyData>();

            int rowIndex = 9;
            Row row = null;
            while ((row = sheet.getRow(rowIndex)) != null) {
                PropertyData data = createPropertyData(row);
                if (data == null) {
                    break;
                }

                propList.add(data);

                rowIndex++;
            }

            createProperty(fileName, propList);
            createPropConf(fileName, propList);

            propFiles.add(fileName);

            sheetIndex++;
        }

        createPropXml(propFiles);
    }

    private void createProperty(String fileName, List<PropertyData> propList) {

        String dir = distDir + "\\src\\main\\resources_autogene";

        write(dir, dir + "\\" + fileName + "." + "UTF-8", new PropertyFileBuilder(fileName, propList));

        native2Ascii(dir + "\\" + fileName + "." + "UTF-8", dir + "\\" + fileName);
    }

    private void createPropConf(String fileName, List<PropertyData> propList) {

        String dir = distDir + "\\src\\main\\java_autogene\\" + Constants.PRJ_PROPRTY_CONF_PACKAGE_DIR;

        write(dir, dir + "\\" + fileName.replaceFirst(".properties", "PropertyConf.java"), new PropertyConfBuilder(fileName, propList));
    }

    private void createPropXml(List<String> propFiles) {
//
//        String dir = distDir + "\\conf";
//
//        write(dir, dir + "\\applicationContext-config.xml", new PropertyXmlConfBuilder(propFiles));
    }

    private PropertyData createPropertyData(Row row) {

        String key = getStringValue(row, 1);
        String description = getStringValue(row, 2);
        String abbreviation = getStringValue(row, 3);
        String dataType = getStringValue(row, 4);
        String value = getStringValue(row, 5);
        String defaultValue = getStringValue(row, 6);
        String min = getStringValue(row, 7);
        String max = getStringValue(row, 8);
        String note = getStringValue(row, 9);

        if (key.equals("")) {
            return null;
        }

        PropertyData data = new PropertyData();

        data.key = key;
        data.description = description;
        data.abbreviation = abbreviation.equals("○");
        data.setDataType(dataType);
        data.value = value;
        data.defaultValue = defaultValue;
        try {
            data.min = Integer.parseInt(min);

        } catch (NumberFormatException e) {
        }
        try {
            data.max = Integer.parseInt(max);

        } catch (NumberFormatException e) {
        }
        data.note = (note);

        return data;
    }





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
        distDir += CreateProperty.class.getSimpleName() + "\\" + Constants.DIST_DIR_COMMON;

        try {
            FileUtils.deleteDirectory(new File(distDir));
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }

        new CreateProperty(xls, distDir);

        System.out.println("");
        System.out.println("done.");
    }
}

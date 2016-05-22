package alpha114.tool.cichlid.autogenerationtool.frameitem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.CreateSrc;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.frameitem.builder.FrameItemPropertyBuilder;

/**
 * @author kurisakisatoshi
 */
public class CreateFrameProperty extends CreateSrc {

    private final String distDir;

    public CreateFrameProperty(String xls, String distDir) {
        this.distDir = distDir;

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

            if (frameId.equals("")) {
                sheetIndex++;
                continue;
            }

            FrameData frameData = new FrameData();

            frameData.frameId = frameId;
            frameData.frameName = frameName;

            final int idxId = 1;
            final int idxItemName = 14;

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

                if (contains(itemName)) {
                    rowIndex++;
                    continue;
                }

                if (getStringValue(row, 2).equals("フレームタイトル")) {
                    frameData.title = itemName;
                    frameData.titleId = itemId;
                }

                frameData.addItem(itemId, itemName);

                rowIndex++;
            }

            datas.add(frameData);

            sheetIndex++;
        }


        createProperty(datas);
    }


    private void createProperty(List<FrameData> frameDatas) {

        StringBuilder sb = new StringBuilder("");

        Iterator<FrameData> ite = frameDatas.iterator();
        while (ite.hasNext()) {

            FrameData data = ite.next();

            String dir = distDir+"\\src\\main\\resources_autogene";

            write(dir, dir + "\\" + data.frameId + "_ja.properties" + "." + "UTF-8", new FrameItemPropertyBuilder(data));

            native2Ascii(dir + "\\" + data.frameId + "_ja.properties" + "." + "UTF-8", dir + "\\" + data.frameId + "_ja.properties");

            sb.append(data.frameId);
            sb.append("\n");

            if (ite.hasNext()) {
                sb.append("                     , ");
            }
        }

        System.out.println(sb.toString());
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
        distDir += CreateFrameProperty.class.getSimpleName() + "\\" + Constants.DIST_DIR_WEB;

        try {
            FileUtils.deleteDirectory(new File(distDir));
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }

        new CreateFrameProperty(xls, distDir);

        System.out.println("");
        System.out.println("done.");
    }

    private boolean contains(String val) {
        for (String s : common) {
            if (s.equals(val)) {
                return true;
            }
        }
        return false;
    }

    public static final String[] common =
        new String[]{
//    	"閉じる", "OK", "キャンセル", "検索", "検索条件クリア",
//                     "<<", "<", ">", ">>", "ページ移動", "ページ", "全ページ数", "全件数",
//                     "CSV出力", "XML出力", "(カレンダーアイコン)", "全 ｘページ\n※xには表のページ数を表示", "(全 ｘｘｘ件)\n※ｘｘｘには表の件数を表示"
                     };
}

package alpha114.tool.cichlid.autogenerationtool.src;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.src.builder.SearchResultCssBuilder;
import alpha114.tool.cichlid.autogenerationtool.src.builder.SearchResultTableBeanXmlBuilder;
import alpha114.tool.cichlid.autogenerationtool.src.builder.SearchResultTableComparatorBeanXmlBuilder;

/**
 * @author kurisakisatoshi
 */
public class CreateSearchResult extends CreateSearchCondition {

    public CreateSearchResult(String frameListXls, String xls, String distDir) {
        super(frameListXls, xls, distDir);
    }

    /* (非 Javadoc)
     * @see alpha114_117.tool.cichlid.autogenerationtool2.src.CreateSearchCondition#create(java.util.List)
     */
    @Override
    void create(List<FrameData> datas) {

        for (FrameData f : datas) {

            if (!f.isTableFlag()) {
                continue;
            }

            // 最初に一発だけ
//            createCss(f);
        }

        createBeanXml(datas);
    }



//    private void createViewModel(FrameData frameData) {
//
//        String dir = distDir + "\\src_autogene\\" + Constants.VIEW_MODEL_PACKAGE_SEARCHRESULT_DIR;
//
//        write(dir, dir+"\\"+frameData.getFrameId()+"_ViewModel.java", new SearchResultViewModelBuilder(frameData));
//    }

    private void createBeanXml(List<FrameData> datas) {

        String dir = distDir + "\\src\\main\\resources_autogene";

        write(dir, dir + "\\applicationContext-searchresult.xml", new SearchResultTableBeanXmlBuilder(datas));

        // ベースだけ自動生成とするためコメントアウト
        write(dir, dir + "\\applicationContext-searchresult_comparator.xml", new SearchResultTableComparatorBeanXmlBuilder(datas));
    }

    @SuppressWarnings("unused")
    private void createCss(FrameData frameData) {

        String dir = distDir + "\\src\\main\\webapp\\css\\tblclm_width";

        write(dir, dir+"\\"+frameData.getFrameId()+".css", new SearchResultCssBuilder(frameData));
    }

//    private void createButtonJsp(FrameData frameData) {
//
////      String dir = distDir + "\\content\\searchresult\\"+frameData.getFrameId();
////
////      write(dir, dir+"\\button.jsp", new SearchResultButtonJspBuilder(frameData));
//
//        // ボタン個々でバラバラでjspファイルを出力するようにする
//        String dir = distDir + "\\content\\searchresult\\"+frameData.getFrameId();
//        for (FrameItemData item : frameData.getItems()) {
//
//            if (item.isButton()) {
//                write(
//                        dir,
//                        dir+"\\" + item.getId().toUpperCase() + ".jsp",
//                        new SearchResultButtonPieceJspBuilder(frameData, item));
//            }
//
//        }
//
//    }
//
//    private void createSearchListDao(FrameData frameData, String tableName) {
//
//        String dir = distDir + "\\共通部のsrc\\" + Constants.DAO_PACKAGE_DIR+"\\common\\impl";
//
//        write(dir, dir+"\\"+frameData.getFrameId()+"_SearchListDaoImpl.java", new SingleEntitySearchListDaoImplBuilder(frameData, tableName));
//    }



    public static void main(String[] args) {

        for (String arg : args) {
            System.out.println(arg);
        }

        String xls = null;
        String distDir = null;
        String framelistxls = null;

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

            } else if (arg.equals("-framelistxls")) {
                if (i + 1 < args.length) {
                    framelistxls = args[i + 1];
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
        distDir += CreateSearchResult.class.getSimpleName() + "\\" + Constants.DIST_DIR_WEB;

        try {
            FileUtils.deleteDirectory(new File(distDir));
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }

        new CreateSearchResult(framelistxls, xls, distDir);

        System.out.println("");
        System.out.println("done.");
    }
}

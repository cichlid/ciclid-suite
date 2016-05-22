package alpha114.tool.cichlid.autogenerationtool.src;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;
import alpha114.tool.cichlid.autogenerationtool.data.TableListItemData;
import alpha114.tool.cichlid.autogenerationtool.src.builder.SettingDataDtoBuilder;
import alpha114.tool.cichlid.autogenerationtool.src.builder.SettingFormBuilder;
import alpha114.tool.cichlid.autogenerationtool.src.builder.SettingItemJspBuilder;
import alpha114.tool.cichlid.autogenerationtool.src.builder.SettingTableRowFormBuilder;

/**
 * @author kurisakisatoshi
 */
public class CreateSetting extends CreateSearchCondition {

    public CreateSetting(String frameListXls, String xls, String distDir) {
        super(frameListXls, xls, distDir);
    }

    /* (非 Javadoc)
     * @see alpha114_117.tool.cichlid.autogenerationtool2.src.CreateSearchCondition#create(java.util.List)
     */
    @Override
    void create(List<FrameData> datas) {

        for (FrameData f : datas) {

            if (f.isSettiongFlag() || f.isTableFlag()) {
                createJSP(f);
                createForm(f);
                createDto(f);
            }
//
//            if (f.getFrameType() == FrameType.Setting) {
//
//                createViewModel(f);
//
//                createJSP(f, false);
//
//                createForm(f);
//
//                createJS(f);
//
//
//            } else if (f.getFrameType() == FrameType.Detail) {
//
//                createViewModel_detail(f);
//
//                createJSP(f, true);
//            }
        }
    }

//    private void createViewModel(FrameData frameData) {
//
//        String dir = distDir + "\\src\\" + Constants.VIEW_MODEL_PACKAGE_SETTING_DIR;
//
//        write(dir, dir+"\\"+frameData.getFrameId()+"_ViewModel.java", new SettingDataViewModelBuilder(frameData));
//    }
//
//    private void createViewModel_detail(FrameData frameData) {
//
//        String dir = distDir + "\\src\\" + Constants.VIEW_MODEL_PACKAGE_DETAIL_DIR;
//
//        write(dir, dir+"\\"+frameData.getFrameId()+"_ViewModel.java", new DetailViewModelBuilder(frameData));
//    }


    private void createJSP(FrameData frameData) {

        String dir = distDir + "\\src\\main\\webapp\\content_autogene\\" + frameData.getFrameId();

        boolean isReadOnly = false;

        for (FrameItemData item : frameData.getItems()) {

            if (!item.getId().startsWith(FrameItemData.ItemType.SETTING_DATA.type)) {
                continue;
            }

            // 入力不可ならReadOnly
            isReadOnly = !(item.isInput());

            if (item.isLabel()
                    || item.isTextLink_blank()
                    || item.isTextLink_self()
                    || item.isText()
                    || item.isTextArea()
                    || item.isPulldown()
                    || item.isPassword()
                    || item.isRadioButton()
                    || item.isCheckbox()
                    || item.isTable()
                    || item.isButton()) {

                write(dir, dir + "\\" + item.getId().toUpperCase() + ".jsp", new SettingItemJspBuilder(frameData, item, isReadOnly));

            }

        }
    }

//    private void createJS(FrameData frameData) {
//
//        String dir = distDir + "\\js\\setting";
//
//        write(dir, dir+"\\" + frameData.getFrameId() + ".js", new SettingItemJsBuilder());
//    }

    private void createForm(FrameData frameData) {

        String dir = distDir + "\\src\\main\\java_autogene\\" + Constants.PRJ_WEB_SETTING_FORM_PACKAGE_DIR;

        write(dir, dir + "\\" + frameData.getFrameId() + "_SettingForm.java", new SettingFormBuilder(frameData));

        for (FrameItemData item : frameData.getItems()) {

            if (!item.isTable()) {
                continue;
            }

            for (TableListItemData tableItem : item.getTableColumns()) {
                if (!tableItem.isInput()) {
                    continue;
                }

                write(dir, dir + "\\" + frameData.getFrameId() + "_TableRowForm.java", new SettingTableRowFormBuilder(frameData));

                break;
            }
        }
    }

    private void createDto(FrameData frameData) {

        String dir = distDir + "\\src\\main\\java_autogene\\" + Constants.PRJ_DTO_PACKAGE_DIR;

        write(dir, dir + "\\" + frameData.getFrameId() + "_SettingData.java", new SettingDataDtoBuilder(frameData));
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
        distDir += CreateSetting.class.getSimpleName() + "\\" + Constants.DIST_DIR_WEB;

        try {
            FileUtils.deleteDirectory(new File(distDir));
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }

        new CreateSetting(framelistxls, xls, distDir);

        System.out.println("");
        System.out.println("done.");
    }
}

package alpha114.tool.cichlid.autogenerationtool;

/**
 * @author kurisakisatoshi
 */
public class Constants {

    public static final String COPYRIGHT = "COPYRIGHT";
    public static final String DATE_CREATED = "TODO";


    public static final String DIST_DIR_COMMON = "prj-enquete-common";
    public static final String DIST_DIR_WEB = "prj-enquete-web";



    public static final String[] DB_INSTANCE_NAME = {"enquete"};

    public final static String BASE_PACKAGE = "alpha114.tool.cichlid";
    public final static String BASE_PACKAGE_DIR = BASE_PACKAGE.replaceAll("\\.", "\\\\");
    public final static String PRJ_BASE_PACKAGE = "alpha114.tool.cichlid.prj";
    public final static String PRJ_BASE_PACKAGE_DIR = PRJ_BASE_PACKAGE.replaceAll("\\.", "\\\\");

    public final static String ENTITY_PACKAGE = BASE_PACKAGE + ".common.entity";
    public final static String ENTITY_PACKAGE_DIR = ENTITY_PACKAGE.replaceAll("\\.", "\\\\");
    public final static String PRJ_ENTITY_PACKAGE = PRJ_BASE_PACKAGE + ".entity";
    public final static String PRJ_ENTITY_PACKAGE_DIR = PRJ_ENTITY_PACKAGE.replaceAll("\\.", "\\\\");

    public final static String COMPARATOR_PACKAGE = BASE_PACKAGE + ".common.util.comparator";
    public final static String COMPARATOR_PACKAGE_DIR = COMPARATOR_PACKAGE.replaceAll("\\.", "\\\\");
    public final static String PRJ_COMPARATOR_PACKAGE = PRJ_BASE_PACKAGE + ".util.comparator";
    public final static String PRJ_COMPARATOR_PACKAGE_DIR = PRJ_COMPARATOR_PACKAGE.replaceAll("\\.", "\\\\");

    public final static String RELATION_PACKAGE = ENTITY_PACKAGE + ".relation";
    public final static String RELATION_PACKAGE_DIR = RELATION_PACKAGE.replaceAll("\\.", "\\\\");
    public final static String PRJ_RELATION_PACKAGE = PRJ_ENTITY_PACKAGE + ".relation";
    public final static String PRJ_RELATION_PACKAGE_DIR = PRJ_RELATION_PACKAGE.replaceAll("\\.", "\\\\");

    public final static String DAO_PACKAGE = BASE_PACKAGE + ".common.dao";
    public final static String DAO_PACKAGE_DIR = DAO_PACKAGE.replaceAll("\\.", "\\\\");
    public final static String PRJ_DAO_PACKAGE = PRJ_BASE_PACKAGE + ".dao";
    public final static String PRJ_DAO_PACKAGE_DIR = PRJ_DAO_PACKAGE.replaceAll("\\.", "\\\\");

    public final static String DAO_CUD_PACKAGE = BASE_PACKAGE + ".common.dao.cud";
    public final static String DAO_CUD_PACKAGE_DIR = DAO_CUD_PACKAGE.replaceAll("\\.", "\\\\");
    public final static String PRJ_DAO_CUD_PACKAGE = PRJ_BASE_PACKAGE + ".dao.cud";
    public final static String PRJ_DAO_CUD_PACKAGE_DIR = PRJ_DAO_CUD_PACKAGE.replaceAll("\\.", "\\\\");

    public final static String WEB_MESSAGE_ID_PACKAGE = BASE_PACKAGE + ".web.view";
    public final static String WEB_MESSAGE_ID_PACKAGE_DIR = WEB_MESSAGE_ID_PACKAGE.replaceAll("\\.", "\\\\");
    public final static String PRJ_WEB_MESSAGE_ID_PACKAGE = PRJ_BASE_PACKAGE + ".web.view";
    public final static String PRJ_WEB_MESSAGE_ID_PACKAGE_DIR = PRJ_WEB_MESSAGE_ID_PACKAGE.replaceAll("\\.", "\\\\");

    public final static String WEB_CONDITION_FORM_PACKAGE = BASE_PACKAGE + ".web.form.searchcondition";
    public final static String WEB_CONDITION_FORM_PACKAGE_DIR = WEB_CONDITION_FORM_PACKAGE.replaceAll("\\.", "\\\\");
    public final static String PRJ_WEB_CONDITION_FORM_PACKAGE = PRJ_BASE_PACKAGE + ".web.form.searchcondition";
    public final static String PRJ_WEB_CONDITION_FORM_PACKAGE_DIR = PRJ_WEB_CONDITION_FORM_PACKAGE.replaceAll("\\.", "\\\\");

    public final static String WEB_SETTING_FORM_PACKAGE = BASE_PACKAGE + ".web.form.setting";
    public final static String WEB_SETTING_FORM_PACKAGE_DIR = WEB_SETTING_FORM_PACKAGE.replaceAll("\\.", "\\\\");
    public final static String PRJ_WEB_SETTING_FORM_PACKAGE = PRJ_BASE_PACKAGE + ".web.form.setting";
    public final static String PRJ_WEB_SETTING_FORM_PACKAGE_DIR = PRJ_WEB_SETTING_FORM_PACKAGE.replaceAll("\\.", "\\\\");

    public final static String VIEW_MODE_PACKAGE = BASE_PACKAGE + ".viewmodel";
    public final static String PRJ_VIEW_MODE_PACKAGE = PRJ_BASE_PACKAGE + ".viewmodel";
    public final static String PRJ_VIEW_MODE_PACKAGE_DIR = PRJ_VIEW_MODE_PACKAGE.replaceAll("\\.", "\\\\");

    public final static String VIEW_MODE_PACKAGE_SETTING = BASE_PACKAGE + ".viewmodel.setting";
    public final static String VIEW_MODE_PACKAGE_SETTING_DIR = VIEW_MODE_PACKAGE_SETTING.replaceAll("\\.", "\\\\");
    public final static String PRJ_VIEW_MODE_PACKAGE_SETTING = PRJ_BASE_PACKAGE + ".viewmodel.setting";
    public final static String PRJ_VIEW_MODE_PACKAGE_SETTING_DIR = PRJ_VIEW_MODE_PACKAGE_SETTING.replaceAll("\\.", "\\\\");

    public final static String VIEW_MODE_PACKAGE_DETAIL = BASE_PACKAGE + ".viewmodel.detail";
    public final static String VIEW_MODE_PACKAGE_DETAIL_DIR = VIEW_MODE_PACKAGE_DETAIL.replaceAll("\\.", "\\\\");
    public final static String PRJ_VIEW_MODE_PACKAGE_DETAIL = PRJ_BASE_PACKAGE + ".viewmodel.detail";
    public final static String PRJ_VIEW_MODE_PACKAGE_DETAIL_DIR = PRJ_VIEW_MODE_PACKAGE_DETAIL.replaceAll("\\.", "\\\\");

    public final static String VIEW_MODE_PACKAGE_SEARCHRESULT = BASE_PACKAGE + ".viewmodel.searchresult";
    public final static String VIEW_MODE_PACKAGE_SEARCHRESULT_DIR = VIEW_MODE_PACKAGE_SEARCHRESULT.replaceAll("\\.", "\\\\");
    public final static String PRJ_VIEW_MODE_PACKAGE_SEARCHRESULT = PRJ_BASE_PACKAGE + ".viewmodel.searchresult";
    public final static String PRJ_VIEW_MODE_PACKAGE_SEARCHRESULT_DIR = PRJ_VIEW_MODE_PACKAGE_SEARCHRESULT.replaceAll("\\.", "\\\\");

    public final static String VIEW_MODE_PACKAGE_TABLE = BASE_PACKAGE + ".viewmodel.table";
    public final static String VIEW_MODE_PACKAGE_TABLE_DIR = VIEW_MODE_PACKAGE_TABLE.replaceAll("\\.", "\\\\");
    public final static String PRJ_VIEW_MODE_PACKAGE_TABLE = PRJ_BASE_PACKAGE + ".viewmodel.table";
    public final static String PRJ_VIEW_MODE_PACKAGE_TABLE_DIR = PRJ_VIEW_MODE_PACKAGE_TABLE.replaceAll("\\.", "\\\\");

    public final static String VIEW_MODE_PACKAGE_ASSOC = BASE_PACKAGE + ".viewmodel.assoc";
    public final static String VIEW_MODE_PACKAGE_ASSOC_DIR = VIEW_MODE_PACKAGE_ASSOC.replaceAll("\\.", "\\\\");
    public final static String PRJ_VIEW_MODE_PACKAGE_ASSOC = PRJ_BASE_PACKAGE + ".viewmodel.assoc";
    public final static String PRJ_VIEW_MODE_PACKAGE_ASSOC_DIR = PRJ_VIEW_MODE_PACKAGE_ASSOC.replaceAll("\\.", "\\\\");

    public final static String PROPRTY_CONF_PACKAGE = BASE_PACKAGE + ".common.property";
    public final static String PROPRTY_CONF_PACKAGE_DIR = PROPRTY_CONF_PACKAGE.replaceAll("\\.", "\\\\");
    public final static String PRJ_PROPRTY_CONF_PACKAGE = PRJ_BASE_PACKAGE + ".property";
    public final static String PRJ_PROPRTY_CONF_PACKAGE_DIR = PRJ_PROPRTY_CONF_PACKAGE.replaceAll("\\.", "\\\\");

    public final static String DTO_PACKAGE = BASE_PACKAGE + ".common.dto";
    public final static String DTO_PACKAGE_DIR = DTO_PACKAGE.replaceAll("\\.", "\\\\");
    public final static String PRJ_DTO_PACKAGE = PRJ_BASE_PACKAGE + ".dto";
    public final static String PRJ_DTO_PACKAGE_DIR = PRJ_DTO_PACKAGE.replaceAll("\\.", "\\\\");


    public static final String[] date = new String[]{"YYYY", "MM", "DD", "HH", "MI"};


    public static final String[] COMMON_BUTTONS = new String[]{"カレンダー", "検索", "検索条件クリア", "先頭のページ", "前ページ", "次ページ", "最後のページ", "ページ移動", "CSV出力"};

    private Constants() {
    }
}

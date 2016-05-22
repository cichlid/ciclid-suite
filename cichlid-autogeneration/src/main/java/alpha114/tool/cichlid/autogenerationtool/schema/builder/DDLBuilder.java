package alpha114.tool.cichlid.autogenerationtool.schema.builder;

/**
 * @author kurisakisatoshi
 */
public interface DDLBuilder {

    String buildCreateDDL();
    String buildDropDDL();
}

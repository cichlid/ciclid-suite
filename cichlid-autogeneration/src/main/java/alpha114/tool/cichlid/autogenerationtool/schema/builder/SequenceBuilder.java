package alpha114.tool.cichlid.autogenerationtool.schema.builder;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.apache.commons.io.output.StringBuilderWriter;

import alpha114.tool.cichlid.autogenerationtool.data.SequenceData;

/**
 * @author kurisakisatoshi
 */
public class SequenceBuilder implements DDLBuilder {

    private final List<SequenceData> seqs;

    public SequenceBuilder(List<SequenceData> seqs) {
        this.seqs = seqs;
    }

    @Override
    public String buildCreateDDL() {

        StringBuilderWriter w = new StringBuilderWriter();

        for (SequenceData seq : seqs) {

            try {
                createDDL(seq, w);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        return w.toString();
    }

    @Override
    public String buildDropDDL() {

        StringBuilderWriter w = new StringBuilderWriter();

        for (SequenceData seq : seqs) {

            try {
                createDropDDL(seq, w);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        return w.toString();
    }

    private void createDDL(SequenceData seqData, Writer out) throws IOException {

        StringBuilder sb = new StringBuilder();

        sb.append("CREATE SEQUENCE ").append(seqData.instanceName.toUpperCase()).append(".").append(seqData.name.toUpperCase());
        sb.append(" START WITH ").append(seqData.start);
        sb.append(" MINVALUE ").append(seqData.minValue);
        if (seqData.maxValue != 0) {
            sb.append(" MAXVALUE ").append(seqData.maxValue);
        }
        sb.append(" INCREMENT BY ").append(seqData.increment);
        sb.append(seqData.cycle ? " CYCLE" : "");
//        sb.append(seqData.isOrder() ? " ORDER" : " NOORDER");
        if (seqData.cache != 0) {
            sb.append(" CACHE ").append(seqData.cache);
        }
        sb.append(";\n");

        out.write(sb.toString());

        out.flush();
    }

    private void createDropDDL(SequenceData seqData, Writer out) throws IOException {

        StringBuilder sb = new StringBuilder();

        sb.append("DROP SEQUENCE ").append(seqData.instanceName.toUpperCase()).append(".").append(seqData.name).append(";\n");

        out.write(sb.toString());

        out.flush();
    }
}

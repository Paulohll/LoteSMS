package cepa.lote.lote.modelo;

/**
 * Reflejo de la tabla 'meta' en la base de datos
 */
public class sms {

    private static final String TAG = sms.class.getSimpleName();
    /*
        Atributos
         */
    private String rep_sms_id;
    private String rep_sms_tipo;
    private String rep_sms_fec;
    private String rep_sms_cli_id;
    private String rep_sms_gsm;
    private String rep_sms_det;
    private String rep_sms_est;


    public sms(String rep_sms_id, String rep_sms_tipo, String rep_sms_fec, String rep_sms_cli_id, String rep_sms_gsm, String rep_sms_det, String rep_sms_est) {
        this.rep_sms_id = rep_sms_id;
        this.rep_sms_tipo = rep_sms_tipo;
        this.rep_sms_fec = rep_sms_fec;
        this.rep_sms_cli_id = rep_sms_cli_id;
        this.rep_sms_gsm = rep_sms_gsm;
        this.rep_sms_det = rep_sms_det;
        this.rep_sms_est = rep_sms_est;
    }

    public static String getTAG() {
        return TAG;
    }

    public String getRep_sms_id() {
        return rep_sms_id;
    }

    public void setRep_sms_id(String rep_sms_id) {
        this.rep_sms_id = rep_sms_id;
    }

    public String getRep_sms_tipo() {
        return rep_sms_tipo;
    }

    public void setRep_sms_tipo(String rep_sms_tipo) {
        this.rep_sms_tipo = rep_sms_tipo;
    }

    public String getRep_sms_fec() {
        return rep_sms_fec;
    }

    public void setRep_sms_fec(String rep_sms_fec) {
        this.rep_sms_fec = rep_sms_fec;
    }

    public String getRep_sms_cli_id() {
        return rep_sms_cli_id;
    }

    public void setRep_sms_cli_id(String rep_sms_cli_id) {
        this.rep_sms_cli_id = rep_sms_cli_id;
    }

    public String getRep_sms_gsm() {
        return rep_sms_gsm;
    }

    public void setRep_sms_gsm(String rep_sms_gsm) {
        this.rep_sms_gsm = rep_sms_gsm;
    }

    public String getRep_sms_det() {
        return rep_sms_det;
    }

    public void setRep_sms_det(String rep_sms_det) {
        this.rep_sms_det = rep_sms_det;
    }

    public String getRep_sms_est() {
        return rep_sms_est;
    }

    public void setRep_sms_est(String rep_sms_est) {
        this.rep_sms_est = rep_sms_est;
    }
}

package EXXOlib;

public class DOCTYPES {

    public static String getType(String t, String n) {
        String type = "noresult";
        if (t.startsWith("image")) {
            type = "image";
        } else if (t.startsWith("text")) {
            type = "text";
        } else if (t.endsWith("pdf")) {
            type = "pdf";
        } else if (t.contains("word")) {
            type = "doc";
        } else if (t.contains("opendocument.text")) {
            type = "ooo_doc";
        } else if (t.contains("opendocument.spreadsheet")) {
            type = "ooo_ss";
        } else if (t.contains("excel") || t.contains("officedocument.spreadsheetml")) {
            type = "xls";
        } else if (t.contains("powerpoint")) {
            type = "pp";
        } else if (n.toLowerCase().endsWith(".zip")) {
            type = "zip";
        } else if (t.startsWith("audio")) {
            type = "audio";
        } else if (t.startsWith("video")) {
            type = "video";
        } else if (n != null && n.endsWith(".rar")) {
            type = "rar";
        } else if (n != null && (n.toLowerCase().endsWith(".doc") || n.toLowerCase().endsWith(".docx"))) {
            type = "doc";
        } else if (n != null && (n.toLowerCase().endsWith(".xls") || n.toLowerCase().endsWith(".xlsx"))) {
            type = "xls";
        } else if (n != null && (n.toLowerCase().endsWith(".ods"))) {
            type = "ooo_ss";
        } else if (n != null && (n.toLowerCase().endsWith(".odt"))) {
            type = "ooo_doc";
        }
        return type;
    }
}

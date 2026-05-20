import java.sql.*;

public class DbSchemaProbe {
  public static void main(String[] args) throws Exception {
    String url = "jdbc:mysql://localhost:3306/db_admissionssystem?useSSL=false&serverTimezone=Asia/Ho_Chi_Minh&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";
    try (Connection c = DriverManager.getConnection(url, "root", "")) {
      String[] tables = {
        "users",
        "xt_bangquydoi",
        "xt_diemthixettuyen",
        "xt_diemvsat",
        "xt_nganh",
        "xt_nganh_tohop",
        "xt_nguyenvongxettuyen",
        "xt_thisinhxettuyen25",
        "xt_tohop_monthi"
      };
      DatabaseMetaData md = c.getMetaData();
      for (String table : tables) {
        System.out.println("== " + table + " ==");
        try (ResultSet rs = md.getColumns(c.getCatalog(), null, table, "%")) {
          while (rs.next()) {
            String col = rs.getString("COLUMN_NAME");
            String type = rs.getString("TYPE_NAME");
            int size = rs.getInt("COLUMN_SIZE");
            int dec = rs.getInt("DECIMAL_DIGITS");
            int nullable = rs.getInt("NULLABLE");
            String autoInc = rs.getString("IS_AUTOINCREMENT");
            System.out.printf("%s | %s | size=%d dec=%d | nullable=%s | autoinc=%s%n",
                col, type, size, dec,
                (nullable == DatabaseMetaData.columnNoNulls ? "NO" : "YES"),
                autoInc);
          }
        }
      }
    }
  }
}

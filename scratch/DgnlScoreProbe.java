import java.sql.*;
public class DgnlScoreProbe {
  public static void main(String[] args) throws Exception {
    String url = "jdbc:mysql://localhost:3306/db_admissionssystem?useSSL=false&serverTimezone=Asia/Ho_Chi_Minh&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";
    try (Connection c = DriverManager.getConnection(url, "root", "")) {
      String[] qs = {
        "select min(NL1), max(NL1), count(*) from xt_diemthixettuyen where NL1 is not null",
        "select min(N1_THI), max(N1_THI), count(*) from xt_diemthixettuyen where N1_THI is not null",
        "select min(N1_CC), max(N1_CC), count(*) from xt_diemthixettuyen where N1_CC is not null",
        "select cccd, NL1, N1_THI, N1_CC from xt_diemthixettuyen where NL1 is not null or N1_THI is not null or N1_CC is not null limit 20"
      };
      for (String q: qs) {
        System.out.println("--- " + q);
        try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery(q)) {
          ResultSetMetaData md = rs.getMetaData();
          int cols = md.getColumnCount();
          while (rs.next()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= cols; i++) {
              if (i > 1) sb.append(" | ");
              sb.append(md.getColumnLabel(i)).append("=").append(rs.getString(i));
            }
            System.out.println(sb);
          }
        }
      }
    }
  }
}

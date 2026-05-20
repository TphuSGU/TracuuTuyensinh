import java.sql.*;
public class NvProbe {
  public static void main(String[] args) throws Exception {
    String url = "jdbc:mysql://localhost:3306/db_admissionssystem?useSSL=false&serverTimezone=Asia/Ho_Chi_Minh&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";
    try (Connection c = DriverManager.getConnection(url, "root", "")) {
      String[] qs = {
        "select tt_phuongthuc, count(*) cnt from xt_nguyenvongxettuyen group by tt_phuongthuc order by cnt desc",
        "select tt_thm, count(*) cnt from xt_nguyenvongxettuyen group by tt_thm order by cnt desc limit 20",
        "select tt_phuongthuc, tt_thm, nn_cccd from xt_nguyenvongxettuyen where tt_phuongthuc is not null and tt_thm is not null limit 30"
      };
      for (String q: qs) {
        System.out.println("---" + q);
        try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery(q)) {
          while (rs.next()) {
            ResultSetMetaData md = rs.getMetaData();
            StringBuilder sb = new StringBuilder();
            for (int i=1;i<=md.getColumnCount();i++) {
              if (i>1) sb.append(" | ");
              sb.append(md.getColumnLabel(i)).append("=").append(rs.getString(i));
            }
            System.out.println(sb);
          }
        }
      }
    }
  }
}

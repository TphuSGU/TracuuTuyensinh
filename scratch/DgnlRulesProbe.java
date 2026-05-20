import java.sql.*;

public class DgnlRulesProbe {
  public static void main(String[] args) throws Exception {
    String url = "jdbc:mysql://localhost:3306/db_admissionssystem?useSSL=false&serverTimezone=Asia/Ho_Chi_Minh&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";
    try (Connection c = DriverManager.getConnection(url, "root", "")) {
      String q1 = "select d_tohop, count(*) cnt from xt_bangquydoi where d_phuongthuc='DGNL' group by d_tohop order by d_tohop";
      try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery(q1)) {
        System.out.println("== DGNL groups ==");
        while (rs.next()) {
          System.out.println(rs.getString("d_tohop") + " => " + rs.getInt("cnt"));
        }
      }

      String q2 = "select idqd,d_tohop,d_mon,d_diema,d_diemb,d_diemc,d_diemd,d_maquydoi,d_phanvi from xt_bangquydoi where d_phuongthuc='DGNL' order by d_tohop,d_diema limit 40";
      try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery(q2)) {
        System.out.println("== DGNL sample ==");
        while (rs.next()) {
          System.out.printf("id=%d tohop=%s mon=%s A=%s B=%s C=%s D=%s maq=%s phanvi=%s%n",
              rs.getInt("idqd"), rs.getString("d_tohop"), rs.getString("d_mon"),
              rs.getBigDecimal("d_diema"), rs.getBigDecimal("d_diemb"),
              rs.getBigDecimal("d_diemc"), rs.getBigDecimal("d_diemd"),
              rs.getString("d_maquydoi"), rs.getString("d_phanvi"));
        }
      }
    }
  }
}

import java.sql.*;

public class TraceDataProbe {
  public static void main(String[] args) throws Exception {
    String url = "jdbc:mysql://localhost:3306/db_admissionssystem?useSSL=false&serverTimezone=Asia/Ho_Chi_Minh&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";
    try (Connection c = DriverManager.getConnection(url, "root", "")) {
      String[] qs = {
        "select count(*) from xt_thisinhxettuyen25",
        "select count(*) from xt_diemthixettuyen",
        "select count(*) from xt_nguyenvongxettuyen",
        "select cccd from xt_thisinhxettuyen25 where cccd is not null limit 10",
        "select cccd from xt_diemthixettuyen where cccd is not null limit 10",
        "select nn_cccd from xt_nguyenvongxettuyen where nn_cccd is not null limit 10",
        "select t.cccd, count(distinct d.iddiemthi) as diem_rows, count(distinct n.idnv) as nv_rows " +
        "from xt_thisinhxettuyen25 t " +
        "left join xt_diemthixettuyen d on d.cccd=t.cccd " +
        "left join xt_nguyenvongxettuyen n on n.nn_cccd=t.cccd " +
        "where t.cccd is not null group by t.cccd order by nv_rows desc, diem_rows desc limit 20"
      };
      for (String q : qs) {
        System.out.println("--- " + q);
        try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery(q)) {
          ResultSetMetaData md = rs.getMetaData();
          int cols = md.getColumnCount();
          int row = 0;
          while (rs.next() && row < 20) {
            row++;
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= cols; i++) {
              if (i > 1) sb.append(" | ");
              sb.append(md.getColumnLabel(i)).append("=").append(rs.getString(i));
            }
            System.out.println(sb);
          }
          if (row == 0) System.out.println("(no rows)");
        }
      }
    }
  }
}

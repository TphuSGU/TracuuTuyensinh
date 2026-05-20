import java.sql.*;
public class OneUserProbe {
  public static void main(String[] args) throws Exception {
    String url = "jdbc:mysql://localhost:3306/db_admissionssystem?useSSL=false&serverTimezone=Asia/Ho_Chi_Minh&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";
    try (Connection c = DriverManager.getConnection(url, "root", "")) {
      String q = "select cccd,password from xt_thisinhxettuyen25 where cccd is not null and password is not null limit 1";
      try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery(q)) {
        while (rs.next()) {
          System.out.println(rs.getString(1) + "|" + rs.getString(2));
        }
      }
    }
  }
}

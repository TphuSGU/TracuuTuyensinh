import java.nio.file.*;
import java.nio.charset.*;
import java.util.*;

public class FixJavaStrings {
    public static void main(String[] args) throws Exception {
        Path path = Paths.get("c:\\tphu\\PL_TRACUU\\tracuu\\src\\main\\java\\org\\example\\tracuu\\controller\\TraCuuController.java");
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        for (int i=0; i<lines.size(); i++) {
            if (lines.get(i).contains("successMessage")) {
                lines.set(i, "            model.addAttribute(\"successMessage\", \"Đăng nhập thành công! Chào mừng bạn quay trở lại.\");");
            }
        }
        Files.write(path, lines, StandardCharsets.UTF_8);
        
        Path authPath = Paths.get("c:\\tphu\\PL_TRACUU\\tracuu\\src\\main\\java\\org\\example\\tracuu\\controller\\AuthController.java");
        List<String> authLines = Files.readAllLines(authPath, StandardCharsets.UTF_8);
        for (int i=0; i<authLines.size(); i++) {
            if (authLines.get(i).contains("error") && authLines.get(i).contains("addAttribute")) {
                authLines.set(i, "            model.addAttribute(\"error\", \"Tên đăng nhập hoặc mật khẩu không đúng!\");");
            }
            if (authLines.get(i).contains("success") && authLines.get(i).contains("addAttribute")) {
                authLines.set(i, "            model.addAttribute(\"success\", \"Đăng xuất thành công!\");");
            }
        }
        Files.write(authPath, authLines, StandardCharsets.UTF_8);
        System.out.println("Fixed Java files");
    }
}

import java.nio.file.*;
import java.nio.charset.*;
import java.util.*;

public class FixJavaStringsUnicode {
    public static void main(String[] args) throws Exception {
        Path path = Paths.get("c:\\tphu\\PL_TRACUU\\tracuu\\src\\main\\java\\org\\example\\tracuu\\controller\\TraCuuController.java");
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        for (int i=0; i<lines.size(); i++) {
            if (lines.get(i).contains("successMessage")) {
                lines.set(i, "            model.addAttribute(\"successMessage\", \"\\u0110\\u0103ng nh\\u1EADp th\\u00E0nh c\\u00F4ng! Ch\\u00E0o m\\u1EEBng b\\u1EA1n quay tr\\u1EDF l\\u1EA1i.\");");
            }
        }
        Files.write(path, lines, StandardCharsets.UTF_8);
        
        Path authPath = Paths.get("c:\\tphu\\PL_TRACUU\\tracuu\\src\\main\\java\\org\\example\\tracuu\\controller\\AuthController.java");
        List<String> authLines = Files.readAllLines(authPath, StandardCharsets.UTF_8);
        for (int i=0; i<authLines.size(); i++) {
            if (authLines.get(i).contains("error") && authLines.get(i).contains("addAttribute")) {
                authLines.set(i, "            model.addAttribute(\"error\", \"T\\u00EAn \\u0111\\u0103ng nh\\u1EADp ho\\u1EB7c m\\u1EADt kh\\u1EA9u kh\\u00F4ng \\u0111\\u00FAng!\");");
            }
            if (authLines.get(i).contains("success") && authLines.get(i).contains("addAttribute")) {
                authLines.set(i, "            model.addAttribute(\"success\", \"\\u0110\\u0103ng xu\\u1EA5t th\\u00E0nh c\\u00F4ng!\");");
            }
        }
        Files.write(authPath, authLines, StandardCharsets.UTF_8);
        System.out.println("Fixed Java files with Unicode");
    }
}

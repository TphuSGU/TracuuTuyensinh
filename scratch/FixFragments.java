import java.nio.file.*;
import java.nio.charset.*;
import java.util.*;

public class FixFragments {
    public static void main(String[] args) throws Exception {
        Path path = Paths.get("c:\\tphu\\PL_TRACUU\\tracuu\\src\\main\\resources\\templates\\fragments.html");
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        List<String> newLines = new ArrayList<>();
        boolean inCommentedBlock = false;

        for (int i=0; i<lines.size(); i++) {
            String line = lines.get(i);
            
            if (line.contains("<!-- <div class=\"flex flex-col items-end\">")) {
                newLines.add("              <div class=\"flex flex-col items-end\">");
                inCommentedBlock = true;
                continue;
            }
            if (inCommentedBlock && line.contains("</div> -->")) {
                newLines.add("              </div>");
                inCommentedBlock = false;
                continue;
            }
            if (inCommentedBlock && line.contains("ThA- sinh")) {
                // Fix the mangled "Thí sinh"
                newLines.add("                  <span class=\"text-[10px] text-on-surface-variant uppercase tracking-wider\">Thí sinh</span>");
                continue;
            }
            
            newLines.add(line);
        }
        Files.write(path, newLines, StandardCharsets.UTF_8);
        System.out.println("Fixed fragments.html");
    }
}

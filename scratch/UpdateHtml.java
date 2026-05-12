import java.nio.file.*;
import java.nio.charset.*;
import java.util.*;

public class UpdateHtml {
    public static void main(String[] args) throws Exception {
        updateTracuu();
        updateLogin();
    }

    private static void updateTracuu() throws Exception {
        Path path = Paths.get("c:\\tphu\\PL_TRACUU\\tracuu\\src\\main\\resources\\templates\\tracuu.html");
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        List<String> newLines = new ArrayList<>();
        boolean injected = false;

        for (String line : lines) {
            newLines.add(line);
            if (!injected && line.contains("<main")) {
                // Also check if the next lines contain the end of the tag
                // But it's simpler to just inject after <div class="grid grid-cols-1 lg:grid-cols-12 gap-stack-lg">
                // Let's find that div instead.
            }
        }
        
        // Let's restart and look for the specific div
        newLines.clear();
        injected = false;
        boolean successMessageExists = false;
        for (String line : lines) {
            if (line.contains("successMessage != null")) {
                successMessageExists = true;
            }
        }

        if (!successMessageExists) {
            for (String line : lines) {
                newLines.add(line);
                if (!injected && line.contains("class=\"grid grid-cols-1 lg:grid-cols-12 gap-stack-lg\"")) {
                    newLines.add("    <!-- Success Message Alert -->");
                    newLines.add("    <div th:if=\"${successMessage != null}\" class=\"bg-success-container border border-success-container rounded-lg p-stack-sm flex items-center gap-stack-sm mb-stack-sm animate-pulse col-span-1 lg:col-span-12\">");
                    newLines.add("        <span class=\"material-symbols-outlined text-on-success-container\" style=\"font-variation-settings: 'FILL' 1;\">check_circle</span>");
                    newLines.add("        <p class=\"font-body-md text-on-success-container\" th:text=\"${successMessage}\">Đăng nhập thành công!</p>");
                    newLines.add("    </div>");
                    injected = true;
                }
            }
            Files.write(path, newLines, StandardCharsets.UTF_8);
            System.out.println("Updated tracuu.html");
        } else {
            System.out.println("tracuu.html already updated");
        }
    }

    private static void updateLogin() throws Exception {
        Path path = Paths.get("c:\\tphu\\PL_TRACUU\\tracuu\\src\\main\\resources\\templates\\login.html");
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        List<String> newLines = new ArrayList<>();

        for (int i=0; i<lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains("type=\"submit\"") && line.contains("Tra c")) {
                line = line.replace("<button", "<button id=\"loginBtn\"");
                newLines.add(line);
                // The next line is the span with text "Tra c..."
            } else if (line.contains("Tra c") && lines.get(i-1).contains("login</span")) {
                newLines.add("                    <span id=\"btnText\">Tra cứu</span>");
            } else if (line.contains("console.log('Form is being submitted...');")) {
                newLines.add(line);
                newLines.add("            const btn = document.getElementById('loginBtn');");
                newLines.add("            const btnText = document.getElementById('btnText');");
                newLines.add("            if (btn) {");
                newLines.add("                btn.disabled = true;");
                newLines.add("                btn.style.opacity = '0.7';");
                newLines.add("            }");
                newLines.add("            if (btnText) {");
                newLines.add("                btnText.innerText = 'Đang kiểm tra...';");
                newLines.add("            }");
            } else {
                newLines.add(line);
            }
        }
        Files.write(path, newLines, StandardCharsets.UTF_8);
        System.out.println("Updated login.html");
    }
}

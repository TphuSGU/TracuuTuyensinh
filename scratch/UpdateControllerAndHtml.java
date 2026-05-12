import java.nio.file.*;
import java.nio.charset.*;
import java.util.*;

public class UpdateControllerAndHtml {
    public static void main(String[] args) throws Exception {
        // Update TraCuuController.java
        Path ctrlPath = Paths.get("c:\\tphu\\PL_TRACUU\\tracuu\\src\\main\\java\\org\\example\\tracuu\\controller\\TraCuuController.java");
        List<String> lines = Files.readAllLines(ctrlPath, StandardCharsets.UTF_8);
        List<String> newLines = new ArrayList<>();
        
        for (String line : lines) {
            if (line.contains("model.addAttribute(\"danhSachNguyenVong\", danhSachNguyenVong);")) {
                newLines.add(line);
                newLines.add("                // Fetch map of tenNganh for all NV");
                newLines.add("                java.util.Map<String, String> tenNganhMap = new java.util.HashMap<>();");
                newLines.add("                for (org.example.tracuu.model.NguyenVong nv : danhSachNguyenVong) {");
                newLines.add("                    if (nv.getManganh() != null) {");
                newLines.add("                        admissionService.timNganhTheoMa(nv.getManganh().trim()).ifPresent(n -> ");
                newLines.add("                            tenNganhMap.put(nv.getManganh(), n.getTennganh())");
                newLines.add("                        );");
                newLines.add("                    }");
                newLines.add("                }");
                newLines.add("                model.addAttribute(\"tenNganhMap\", tenNganhMap);");
            } else if (line.contains("admissionService.timNganhTheoMa(nvTrungTuyen.getManganh())")) {
                newLines.add("                    admissionService.timNganhTheoMa(nvTrungTuyen.getManganh().trim()).ifPresent(nganh -> {");
            } else {
                newLines.add(line);
            }
        }
        Files.write(ctrlPath, newLines, StandardCharsets.UTF_8);
        System.out.println("Updated TraCuuController.java");

        // Update tracuu.html
        Path htmlPath = Paths.get("c:\\tphu\\PL_TRACUU\\tracuu\\src\\main\\resources\\templates\\tracuu.html");
        List<String> htmlLines = Files.readAllLines(htmlPath, StandardCharsets.UTF_8);
        List<String> newHtmlLines = new ArrayList<>();
        for (int i = 0; i < htmlLines.size(); i++) {
            String line = htmlLines.get(i);
            
            // In case 102 was the issue, we can also add fallback
            if (line.contains("th:text=\"${tenNganhTrungTuyen}\"")) {
                line = line.replace("th:text=\"${tenNganhTrungTuyen}\"", "th:text=\"${tenNganhTrungTuyen != null ? tenNganhTrungTuyen : 'Không xác định'}\"");
            }
            
            // Fix table headers
            if (line.contains("<th class=\"font-table-header text-table-header text-on-surface p-4 text-center\">Tổ hợp</th>")) {
                newHtmlLines.add("                            <th class=\"font-table-header text-table-header text-on-surface p-4\">Tên ngành</th>");
                newHtmlLines.add("                            <th class=\"font-table-header text-table-header text-on-surface p-4\">Phương thức</th>");
                newHtmlLines.add(line); // add To hop
            }
            else if (line.contains("<th class=\"font-table-header text-table-header text-on-surface p-4\">Tên ngành</th>")) {
                // skip the old one
                continue;
            }
            
            // Fix table body
            else if (line.contains("<td class=\"p-4 font-body-md text-on-surface font-semibold\" th:text=\"${nv.phuongThuc}\">Học bạ</td>")) {
                newHtmlLines.add("                            <td class=\"p-4 font-body-md text-on-surface font-semibold\" th:text=\"${tenNganhMap[nv.manganh] != null ? tenNganhMap[nv.manganh] : 'Đang cập nhật'}\">Khoa học Máy tính</td>");
                newHtmlLines.add("                            <td class=\"p-4 font-body-md text-on-surface-variant\" th:text=\"${nv.phuongThuc}\">Học bạ</td>");
            }
            else {
                newHtmlLines.add(line);
            }
        }
        Files.write(htmlPath, newHtmlLines, StandardCharsets.UTF_8);
        System.out.println("Updated tracuu.html");
    }
}

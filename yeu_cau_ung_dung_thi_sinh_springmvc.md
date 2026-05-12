# Yêu Cầu Ứng Dụng Thí Sinh – Spring MVC

## Tổng Quan

**Framework:** Spring MVC  
**Đối tượng sử dụng:** Thí sinh  
**Chức năng chính:**
1. Tra cứu kết quả xét tuyển
2. Tra cứu cách tính điểm xét tuyển (ĐGNL và VSAT/THPT)

---

## Chức Năng 1: Tra Cứu Kết Quả Xét Tuyển

### Đăng nhập

- **Username:** Số căn cước công dân (CCCD)
- **Password:** 8 chữ số gồm ngày sinh + tháng sinh + năm sinh  
  - Ví dụ: ngày 05/03/2001 → password: `05032001`

### Kết quả trả về

| Trường hợp | Hiển thị |
|---|---|
| Không tìm thấy thí sinh | Thông báo "Không tìm thấy" |
| Tìm thấy – Trúng tuyển | Tên ngành, điểm xét tuyển, tổ hợp môn, phương thức xét tuyển |
| Tìm thấy – Không trúng tuyển | Thông báo "Không trúng tuyển" |

---

## Chức Năng 2: Tra Cứu Cách Tính Điểm Xét Tuyển

Thí sinh chọn 1 trong 2 phương thức để tính điểm tham khảo.

---

### 2a. Phương Thức ĐGNL (Đánh Giá Năng Lực)

> Thang điểm thi: **1200 điểm**  
> Môn thi không có điểm: nhập **0**

#### Input (Thí sinh nhập/chọn)

| Trường | Mô tả |
|---|---|
| Điểm thi ĐGNL | Số điểm thí sinh đạt được (thang 1200) |
| Ngành xét tuyển | Dropdown chọn ngành |
| Đối tượng ưu tiên | Dropdown (VD: Không đối tượng, Đối tượng 01...) |
| Khu vực ưu tiên | Dropdown (VD: Không có, KV1, KV2, KV2NT, KV3) |
| Điểm cộng (≤ 3.0) | Tự nhập nếu có (mục tự bổ sung) |

#### Output (Hiển thị sau khi bấm "Tính điểm")

Bảng chi tiết kết quả gồm các dòng:

| Nội dung | Chi tiết điểm |
|---|---|
| Điểm thi ĐGNL | Điểm thí sinh đã nhập |
| Công thức quy đổi về thang 30 (THXT gốc: `mã tổ hợp`) | `d_a + (diem - d_diema) / (d_diemb - d_diema) * (d_b - d_a)` |
| Điểm thi quy đổi | Kết quả quy đổi về thang 30 |
| Điểm cộng | Điểm cộng thí sinh đã nhập |
| Điểm ưu tiên quy đổi | Tính theo khu vực + đối tượng ưu tiên |
| **Điểm xét tuyển** = Điểm thi quy đổi + Điểm cộng + Điểm ưu tiên quy đổi | Tổng điểm cuối |

#### Logic nghiệp vụ

- Tra bảng `xt_bangquydoi` theo `d_phuongthuc = 'DGNL'` và tổ hợp gốc của ngành để quy đổi điểm ĐGNL sang thang 30.
- So sánh điểm xét tuyển với **điểm ngưỡng** (`n_diemsan`) → Hiển thị **Đạt / Không đạt**.
- Nếu có dữ liệu điểm trúng tuyển (`n_diemtrungtuyen`): so sánh thêm → Hiển thị **Đạt / Không đạt** điểm trúng tuyển.

---

### 2b. Phương Thức VSAT / THPT

> VSAT: thang điểm **150**  
> THPT: thang điểm **10**  
> Môn thi không có điểm: nhập **0**

#### Input (Thí sinh nhập/chọn)

| Trường | Mô tả |
|---|---|
| Ngành xét tuyển | Dropdown chọn ngành |
| Phương thức | VSAT (thang 150) hoặc THPT (thang 10) |
| Điểm từng môn | Toán, Ngữ văn, Vật lý, Hóa học, Sinh học, Lịch sử, Địa lý, Tiếng Anh |
| Điểm môn Tiếng Anh / Quy đổi Tiếng Anh | Nhập nếu có chứng chỉ quy đổi |
| Đối tượng ưu tiên | Dropdown |
| Khu vực ưu tiên | Dropdown |
| Điểm cộng (≤ 3.0) | Chọn môn có điểm cộng và mức điểm cộng tương ứng (tự bổ sung) |

#### Output (Hiển thị sau khi bấm "Tính điểm")

Tiêu đề: **Tính điểm vào ngành xét tuyển: `Tên ngành` (`mã ngành`)**  
Hiển thị: Tổ hợp có điểm xét tuyển cao nhất, tổ hợp gốc, ngưỡng đầu vào.

**Bảng chi tiết — lặp lại cho từng tổ hợp môn (THM) của ngành:**

| Nội dung | Chi tiết điểm |
|---|---|
| Khu vực ưu tiên | Điểm ưu tiên khu vực (VD: KV2NT → 0.50) |
| Đối tượng ưu tiên | Điểm ưu tiên đối tượng (VD: ĐT06 → 1.00) |
| Điểm cộng (≤ 3) | Điểm cộng thí sinh nhập |
| **Điểm môn `X`** | Công thức quy đổi (nếu VSAT): `d_a + (diem - d_diema) / (d_diemb - d_diema) * (d_b - d_a)` |
| Điểm môn không thuộc tổ hợp | Ghi chú: "Lỗi, điểm nhập vào không nằm trong phân vị nào = 0" |
| Xét ngưỡng = Tổng 3 môn + Điểm ưu tiên | Tổng điểm xét ngưỡng |
| Điểm tổ hợp xét tuyển (ĐTHXT) | `(môn1 * hệ_số1 + môn2 * hệ_số2 + môn3 * hệ_số3) / 4 * 3` |
| Độ lệch với TH Gốc (`mã`) | Hiển thị nếu tổ hợp khác tổ hợp gốc |
| Điểm ưu tiên (ĐUT) | Tổng điểm ưu tiên (khu vực + đối tượng) |
| **Điểm xét tuyển (DXT)** = ĐTHXT + ĐC + ĐUT − Độ lệch (≤ 30) | Điểm xét tuyển cuối cùng (tô màu đỏ nếu không đạt ngưỡng) |

#### Logic nghiệp vụ

- Tra bảng `xt_nganh_tohop` để lấy danh sách tổ hợp môn xét tuyển của ngành.
- Nếu là VSAT: tra bảng `xt_bangquydoi` (`d_phuongthuc = 'VSAT'`) để quy đổi điểm từng môn về thang 10.
- Tính điểm từng tổ hợp theo công thức: `(môn1 × hsmon1 + môn2 × hsmon2 + môn3 × hsmon3) / 4 * 3`
- Cộng thêm điểm ưu tiên (khu vực, đối tượng) và điểm cộng.
- Trừ độ lệch (`dolech`) nếu tổ hợp khác tổ hợp gốc của ngành.
- Giới hạn điểm xét tuyển ≤ 30.
- So sánh với `n_diemsan` → Hiển thị **Đạt / Không đạt** ngưỡng (tô màu).
- Tổ hợp có điểm cao nhất được hiển thị nổi bật ở đầu trang kết quả.

---

## Ghi Chú Chung

- Cả 2 phương thức đều có nút **"Quay lại"** để nhập lại thông tin.
- Điểm cộng tối đa là **3.0**, thí sinh tự nhập — không tra từ hệ thống.
- Môn Tiếng Anh hỗ trợ nhập điểm quy đổi từ chứng chỉ ngoại ngữ (thay thế điểm thi).
- Tất cả dữ liệu ngành, tổ hợp, bảng quy đổi được đọc từ CSDL MySQL dùng chung với module Admin.

---

## Các Bảng CSDL Liên Quan

| Bảng | Mục đích sử dụng |
|---|---|
| `xt_thisinhxettuyen25` | Xác thực đăng nhập thí sinh (CCCD + ngày sinh) |
| `xt_nguyenvongxetuyen` | Lấy kết quả xét tuyển theo CCCD |
| `xt_nganh` | Dropdown chọn ngành, lấy điểm ngưỡng / trúng tuyển |
| `xt_nganh_tohop` | Lấy danh sách tổ hợp môn + hệ số + độ lệch của ngành |
| `xt_tohop_monthi` | Lấy tên và danh sách môn của từng tổ hợp |
| `xt_bangquydoi` | Quy đổi điểm ĐGNL / VSAT về thang 30 / thang 10 |

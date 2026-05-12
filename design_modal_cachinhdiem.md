# Design: Modal "Cách Tính Điểm" – Giải Thích Kết Quả Xét Tuyển

## Mục Tiêu

Khi thí sinh nhấn **ⓘ Cách tính điểm** trong bảng danh sách nguyện vọng, hệ thống mở một **modal giải thích chi tiết** lý do thí sinh **đậu hoặc trượt** ngành đó — bằng cách trình bày toàn bộ điểm đang có và từng bước tính toán theo phương thức xét tuyển của ngành đó.

---

## Trigger

```
Bảng "Danh sách nguyện vọng đã đăng ký"
│
├── Cột "Thao tác" → nút [ⓘ Cách tính điểm]
│
└── onClick → Mở Modal với context:
        - nn_cccd   (CCCD thí sinh)
        - nv_manganh (mã ngành)
        - tt_phuongthuc (phương thức: THPT / VSAT / DGNL)
        - matohop   (tổ hợp môn xét tuyển)
```

---

## Dữ Liệu Cần Truy Vấn (Server-side)

| Dữ liệu | Bảng | Điều kiện |
|---|---|---|
| Thông tin thí sinh | `xt_thisinhxettuyen25` | `cccd = nn_cccd` |
| Điểm thi tất cả phương thức | `xt_diemthixetuyen` | `cccd = nn_cccd` |
| Điểm cộng & ưu tiên | `xt_diemcongxetuyen` | `ts_cccd = nn_cccd` AND `manganh = nv_manganh` |
| Thông tin ngành | `xt_nganh` | `manganh = nv_manganh` |
| Tổ hợp môn của ngành | `xt_nganh_tohop` | `manganh = nv_manganh` AND `matohop = matohop` |
| Tên tổ hợp | `xt_tohop_monthi` | `matohop = matohop` |
| Bảng quy đổi | `xt_bangquydoi` | `d_phuongthuc = tt_phuongthuc`, `d_tohop / d_mon` |
| Kết quả nguyện vọng | `xt_nguyenvongxetuyen` | `nn_cccd = ?` AND `nv_manganh = ?` |

---

## Cấu Trúc Modal

### Layout Tổng Thể

```
┌──────────────────────────────────────────────────────────────────┐
│  HEADER                                                [✕ Đóng] │
├──────────────────────────────────────────────────────────────────┤
│  BLOCK A — Thông Tin Ngành & Kết Quả                            │
├──────────────────────────────────────────────────────────────────┤
│  BLOCK B — Toàn Bộ Điểm Thí Sinh                               │
│    ├── B1. Điểm THPT                                            │
│    ├── B2. Điểm VSAT                                            │
│    ├── B3. Điểm ĐGNL                                            │
│    └── B4. Điểm Cộng & Ưu Tiên                                 │
├──────────────────────────────────────────────────────────────────┤
│  BLOCK C — Tính Toán Theo Phương Thức Xét Tuyển Của Ngành Này  │
│    (chỉ tính phương thức đã xét cho ngành này)                  │
├──────────────────────────────────────────────────────────────────┤
│  BLOCK D — Kết Luận                                             │
└──────────────────────────────────────────────────────────────────┘
```

> Modal có thể scroll dọc. Header và footer cố định (sticky).  
> Width: 720px (desktop), full-screen (mobile).

---

### HEADER

```
┌──────────────────────────────────────────────────────────────────┐
│ 📋 Giải thích kết quả xét tuyển                      [✕ Đóng]  │
│ Họ tên: NGUYỄN VĂN A  |  CCCD: 012345678901                    │
└──────────────────────────────────────────────────────────────────┘
```

---

### BLOCK A — Thông Tin Ngành & Kết Quả

```
┌──────────────────────────────────────────────────────────────────┐
│                                                                  │
│  Ngành:     Công nghệ Thông tin                                 │
│  Mã ngành:  7480201          Thứ tự NV: 1                       │
│  Phương thức xét tuyển: VSAT                                    │
│  Tổ hợp:   A00 (Toán, Vật lý, Tiếng Anh)                      │
│                                                                  │
│  Điểm ngưỡng đầu vào:     [n_diemsan]                          │
│  Điểm trúng tuyển:        [n_diemtrungtuyen]                    │
│                                                                  │
│  ┌─────────────────────────────┐                                │
│  │  ✅  TRÚNG TUYỂN           │  ← màu xanh nếu đậu           │
│  │  ❌  KHÔNG TRÚNG TUYỂN     │  ← màu đỏ nếu trượt           │
│  └─────────────────────────────┘                                │
└──────────────────────────────────────────────────────────────────┘
```

---

### BLOCK B — Toàn Bộ Điểm Thí Sinh

> Hiển thị tất cả điểm đang có trong hệ thống, không phụ thuộc phương thức xét tuyển.  
> Mục đích: thí sinh thấy toàn cảnh điểm của mình.

#### B1. Điểm THPT (thang 10)

- Nguồn: `xt_diemthixetuyen`, các cột `TO, VA, LI, HO, SI, SU, DI, TI, KTPL`
- Chỉ hiển thị các môn có giá trị > 0; môn = 0 hiển thị mờ với dấu `—`

```
┌────────────────────────────────────────────┐
│  📗 Điểm THPT                             │
│  ─────────────────────────────────────── │
│  Toán          :  8.00                   │
│  Ngữ văn       :  7.50                   │
│  Vật lý        :  7.00                   │
│  Hóa học       :  —                      │  ← mờ, = 0
│  Sinh học      :  —                      │
│  Lịch sử       :  —                      │
│  Địa lý        :  —                      │
│  Tiếng Anh     :  8.50                   │
│  KTPL          :  —                      │
└────────────────────────────────────────────┘
```

Badge: 🟢 **Có điểm THPT** / ⚫ **Chưa có điểm THPT**

---

#### B2. Điểm VSAT (thang 150 → quy đổi thang 10)

- Nguồn: `xt_diemthixetuyen`, xác định môn nào là VSAT dựa theo `d_phuongthuc = 'VSAT'` trong `xt_bangquydoi`
- Hiển thị điểm thô và điểm đã quy đổi

```
┌────────────────────────────────────────────────────────┐
│  📘 Điểm VSAT                                         │
│  ─────────────────────────────────────────────────── │
│  Môn          │ Điểm thô (thang 150) │ Quy đổi (thang 10) │
│  Toán         │        120           │       7.52         │
│  Ngữ văn      │         90           │       6.75         │
│  Vật lý       │          0           │        —           │
│  Tiếng Anh    │         90           │       6.75         │
│  ...          │        ...           │       ...          │
└────────────────────────────────────────────────────────┘
```

Badge: 🟣 **Có điểm VSAT** / ⚫ **Chưa có điểm VSAT**

> Công thức quy đổi (hiển thị tooltip khi hover):  
> `Điểm QĐ = d_a + (điểm_thô − d_diema) / (d_diemb − d_diema) × (d_b − d_a)`

---

#### B3. Điểm ĐGNL (thang 1200)

- Nguồn: `xt_diemthixetuyen`, cột `N1_THI` (điểm thi) và `N1_CC` (chứng chỉ)

```
┌──────────────────────────────────────────┐
│  📙 Điểm ĐGNL                           │
│  ────────────────────────────────────── │
│  Điểm thi ĐGNL (N1_THI)   :  890.00    │
│  Điểm chứng chỉ (N1_CC)   :   —        │
│  → Sử dụng điểm cao nhất  :  890.00    │
└──────────────────────────────────────────┘
```

Badge: 🟠 **Có điểm ĐGNL** / ⚫ **Chưa có điểm ĐGNL**

---

#### B4. Điểm Cộng & Ưu Tiên

- Nguồn: `xt_diemcongxetuyen` (theo ngành này) + thông tin khu vực/đối tượng từ `xt_thisinhxettuyen25`

```
┌──────────────────────────────────────────────────────┐
│  🎖️ Điểm Cộng & Ưu Tiên                            │
│  ────────────────────────────────────────────────── │
│  Khu vực ưu tiên    :  KV2NT          → +0.25       │
│  Đối tượng ưu tiên  :  Đối tượng 06  → +0.50       │
│  Điểm cộng KK/TT   :  diemCC         → +1.50       │
│  ────────────────────────────────────────────────── │
│  Tổng điểm ưu tiên + cộng            → +2.25       │
└──────────────────────────────────────────────────────┘
```

> Bảng điểm ưu tiên khu vực chuẩn:
> - KV1 → +0.75 | KV2 → +0.50 | KV2NT → +0.25 | KV3/Không có → 0
>
> Bảng điểm ưu tiên đối tượng:
> - ĐT01, ĐT02 → +2.00 | ĐT03, ĐT04 → +1.00 | ĐT05, ĐT06 → +0.50 | Không có → 0

---

### BLOCK C — Tính Toán Theo Phương Thức Xét Tuyển Của Ngành Này

> Chỉ tính và hiển thị **phương thức đã áp dụng** cho nguyện vọng này (`tt_phuongthuc`).  
> Mỗi tổ hợp môn của ngành được tính riêng. Tổ hợp dùng để xét (`matohop`) được làm nổi bật.

---

#### Trường hợp 1: Phương thức VSAT hoặc THPT

Lặp qua từng tổ hợp trong `xt_nganh_tohop` của ngành:

```
┌──────────────────────────────────────────────────────────────────┐
│  🧮 Tính điểm xét tuyển – Phương thức: VSAT                    │
│  Tổ hợp gốc của ngành: D01                                      │
├──────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ⭐ Tổ hợp A00 – Toán, Vật lý, Tiếng Anh  [Tổ hợp đã xét]    │
│  ─────────────────────────────────────────────────────────────  │
│  Điểm Toán   (VSAT 120) → Quy đổi: 7.52                        │
│    Công thức: 7.00 + (120 − 114.50) / (122.50 − 114.50)        │
│               × (7.75 − 7.00) = 7.52                            │
│                                                                  │
│  Điểm Vật lý (VSAT 0)   → Lỗi, điểm không thuộc phân vị = 0   │
│                                                                  │
│  Điểm Tiếng Anh (VSAT 90) → Quy đổi: 6.75                      │
│    Công thức: 3.50 + (90 − 5.00) / (90.00 − 5.00)              │
│               × (6.75 − 3.50) = 6.75                            │
│                                                                  │
│  Khu vực ưu tiên (KV2NT)             :  +0.25                  │
│  Đối tượng ưu tiên (ĐT06)            :  +0.50                  │
│  Điểm cộng (≤ 3.0)                   :  +1.50                  │
│                                                                  │
│  Xét ngưỡng = Tổng 3 môn QĐ + ưu tiên                         │
│             = (7.52 + 0 + 6.75) + (0.25 + 0.50) = 15.02       │
│  → So ngưỡng [n_diemsan]: ✅ ĐẠT                               │
│                                                                  │
│  ĐTHXT = (7.52×1 + 0×1 + 6.75×1) / 4 × 3 = 10.706            │
│  Độ lệch với TH Gốc D01              :  −0.00                  │
│  Tổng ưu tiên + cộng (ĐUT + ĐC)     :  +2.25                  │
│  ─────────────────────────────────────────────────────────────  │
│  ĐIỂM XÉT TUYỂN = 10.706 + 2.25 − 0 = 12.956  (≤ 30) ✅       │
│  → So với điểm trúng tuyển [n_diemtrungtuyen]: ✅ TRÚNG TUYỂN  │
│                                                                  │
├──────────────────────────────────────────────────────────────────┤
│                                                                  │
│  Tổ hợp B00 – Toán, Hóa học, Sinh học                          │
│  [Tổ hợp khác – tính tham khảo]                                 │
│  ─────────────────────────────────────────────────────────────  │
│  ... (cấu trúc tương tự, hiển thị mờ hơn)                      │
│                                                                  │
└──────────────────────────────────────────────────────────────────┘
```

---

#### Trường hợp 2: Phương thức ĐGNL

```
┌──────────────────────────────────────────────────────────────────┐
│  🧮 Tính điểm xét tuyển – Phương thức: ĐGNL                    │
│  Tổ hợp gốc của ngành: D01   |   Điểm ĐGNL sử dụng: 890.00    │
│  ─────────────────────────────────────────────────────────────  │
│                                                                  │
│  Điểm thi ĐGNL                              : 890.00            │
│                                                                  │
│  Công thức quy đổi về thang 30 (TH gốc D01):                   │
│    22.25 + (890 − 889.00) / (895.00 − 889.00)                  │
│           × (22.25 − 22.25) = 22.25                             │
│  → Điểm thi quy đổi                        : 22.25             │
│                                                                  │
│  Điểm cộng                                 : +2.00             │
│  Điểm ưu tiên KV + ĐT (quy đổi)           :  0.00             │
│                                                                  │
│  ─────────────────────────────────────────────────────────────  │
│  ĐIỂM XÉT TUYỂN = 22.25 + 2.00 + 0.00 = 24.25                 │
│                                                                  │
│  → So với ngưỡng [n_diemsan]        : ✅ ĐẠT                   │
│  → So với điểm trúng tuyển          : ✅ TRÚNG TUYỂN           │
└──────────────────────────────────────────────────────────────────┘
```

---

### BLOCK D — Kết Luận

```
┌──────────────────────────────────────────────────────────────────┐
│  📌 KẾT LUẬN                                                    │
│  ─────────────────────────────────────────────────────────────  │
│                                                                  │
│  Ngành:  Công nghệ Thông tin (7480201)                          │
│  Phương thức: VSAT  |  Tổ hợp: A00                             │
│                                                                  │
│  Điểm xét tuyển của bạn      :  12.956                         │
│  Điểm trúng tuyển của ngành  :  12.50                          │
│  Chênh lệch                  :  +0.456                          │
│                                                                  │
│  ┌────────────────────────────────────────────────────────┐     │
│  │  ✅  TRÚNG TUYỂN – Bạn đủ điều kiện vào ngành này.   │     │
│  └────────────────────────────────────────────────────────┘     │
│                                                                  │
│  [🖨️ In kết quả]                            [✕ Đóng]          │
└──────────────────────────────────────────────────────────────────┘
```

---

## Quy Tắc Màu Sắc & Trạng Thái

| Trạng thái | Màu nền badge | Màu chữ | Icon |
|---|---|---|---|
| Trúng tuyển | `#dcfce7` (xanh nhạt) | `#16a34a` | ✅ |
| Đạt ngưỡng, chưa trúng | `#fef9c3` (vàng nhạt) | `#ca8a04` | ⚠️ |
| Không đạt ngưỡng | `#fee2e2` (đỏ nhạt) | `#dc2626` | ❌ |
| Không có dữ liệu | `#f3f4f6` (xám) | `#6b7280` | — |

---

## Ghi Chú Kỹ Thuật

- Modal mở qua `GET /xettuyen/cachinhdiem?cccd=...&manganh=...&phuongthuc=...&tohop=...`
- Spring MVC Controller tính toán toàn bộ, trả về model đầy đủ cho Thymeleaf render.
- **Không tính toán lại ở frontend** — chỉ render HTML từ model đã chuẩn bị.
- Tổ hợp được dùng để xét tuyển (`matohop` từ `xt_nguyenvongxetuyen`) hiển thị trước, đánh dấu ⭐.
- Các tổ hợp còn lại của ngành hiển thị phía dưới, mờ hơn, có nhãn "Tham khảo".
- Công thức quy đổi hiển thị đầy đủ số thực tế (không dùng ký hiệu trừu tượng).
- Nếu `n_diemtrungtuyen` = NULL hoặc 0: chỉ so ngưỡng `n_diemsan`, không hiển thị dòng trúng tuyển.

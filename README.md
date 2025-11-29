🚗 Ứng Dụng Ôn Thi Bằng Lái Xe – Driving Licence App

Ứng dụng hỗ trợ học và ôn thi sát hạch giấy phép lái xe hạng A1, A2, B1, B2…
Bao gồm học lý thuyết, biển báo, mẹo thi, câu hỏi điểm liệt và làm bài thi thử theo bộ đề chuẩn.

📌 Tính năng chính
✔️ Học lý thuyết

Tổng hợp đầy đủ các câu hỏi lý thuyết theo bộ đề chuẩn của Bộ GTVT.

Chia theo chương: luật giao thông, kỹ thuật lái xe, biển báo, sa hình…

✔️ Thi thử giống thật

Làm bài thi thử với thời gian đếm ngược.

Chấm điểm tự động sau khi kết thúc bài thi.

Lưu lịch sử thi để người dùng xem lại kết quả.

✔️ Ôn tập câu sai & câu liệt

Tự động thu thập câu trả lời sai.

Danh mục riêng câu hỏi điểm liệt để người dùng ôn tập.

✔️ Biển báo giao thông

Phân loại rõ ràng: biển báo cấm, cảnh báo, chỉ dẫn, hiệu lệnh…

✔️ Mẹo thi sát hạch

Mẹo làm bài lý thuyết.

Mẹo xử lý nhanh các câu dễ nhầm.

✔️ Tìm kiếm luật nhanh chóng

Tìm nội dung theo từ khóa.

Tra cứu luật giao thông đường bộ.

📂 Cấu trúc dự án
.
├── AndroidManifest.xml
├── assets/lottie/loading.json
├── java/com/example/drivinglicence
│   ├── app
│   │   ├── activites        → Các Activity của ứng dụng (học, thi, biển báo)
│   │   ├── adapter          → Adapter cho RecyclerView
│   │   ├── connection       → Xử lý dữ liệu/API (nếu có)
│   │   ├── entity           → Model câu hỏi, biển báo, đề thi
│   │   ├── fragment         → Các Fragment dùng trong các màn
│   │   └── viewmodel        → ViewModel theo chuẩn MVVM
│   ├── component
│   │   ├── activity         → BaseActivity
│   │   ├── adapter          → BaseAdapter
│   │   ├── dialog           → Dialog custom (loading, cảnh báo…)
│   │   ├── fragment         → BaseFragment
│   │   ├── navigator        → Xử lý điều hướng
│   │   ├── viewmodel        → BaseViewModel
│   │   └── widgets          → View custom
│   ├── pref                 → SharedPreferences & cache
│   │   ├── SpUtils.kt
│   │   ├── SpX.kt
│   │   ├── LocalCache.kt
│   │   ├── ToastUtils.kt
│   │   └── KeyBoardUtils.kt
│   └── utils
│       ├── AppContants.kt
│       └── DataUtils.kt
└── res
    ├── layout               → UI màn hình: thi thử, học bài, biển báo…
    ├── anim
    ├── menu
    ├── mipmap               → Icon launcher
    ├── values               → Colors, styles, themes, strings
    └── xml/network_security_config.xml

🧱 Kiến trúc & Công nghệ sử dụng

MVVM + LiveData + ViewModel

RecyclerView + Adapter

Navigation

Lottie Animation

SharedPreferences cho lưu dữ liệu người dùng

Material Design 3

🚀 Cài đặt & chạy ứng dụng
Yêu cầu:

Android Studio Flamingo trở lên

Min SDK: 21

Target SDK: 34

Build:
./gradlew assembleDebug

Chạy trên điện thoại thật:

Bật Developer Options → USB Debugging

Kết nối thiết bị

Bấm Run trong Android Studio

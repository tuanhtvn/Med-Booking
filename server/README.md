### QUÁ TRÌNH NHẬN VÀ TRẢ CỦA API

**1. request -> controller (DTO) -> service -> repositories**

**2. response <- controller (DTO) <- service <- repositories**

### NOTE:<br>

- Route: là định tuyến (endpoint)
- Controller = handler
- Service = business = biz = usecase
- Repository = storage
- Util = pkg

### PROJECT PLAYOUT FOR NON-MICROSERVICE

```
Chuyển thư mục API của MICROSERVICE vào trong internal và không gói các thư mục theo tên app

my-server/
    ├── cmd/                # Các ứng dụng chính của dự án
    │   └── _your_app_/     # Thư mục cho ứng dụng chính
    │       └── .keep
    ├── internal/           # Mã nguồn nội bộ không được phép sử dụng bởi các ứng dụng bên ngoài
    │   ├── app/            # Thư mục ứng dụng nội bộ
    │   │   └── _your_app_/ # Thư mục cho ứng dụng nội bộ
    │   │       └── .keep
    │   └── pkg/            # Thư viện nội bộ có thể tái sử dụng
    │       └── _your_private_lib_/
    │           └── .keep
    ├── pkg/                # Thư viện và gói có thể tái sử dụng công khai
    │   └── _your_public_lib_/
    │       └── .keep
    ├── api/                # Định nghĩa API và tài liệu liên quan
    │   └── README.md
    ├── assets/             # Tài nguyên tĩnh, ví dụ hình ảnh, biểu tượng
    │   └── README.md
    ├── build/              # Tài nguyên liên quan đến xây dựng và triển khai
    │   ├── ci/             # Cấu hình liên quan đến tích hợp liên tục (CI)
    │   │   └── .keep
    │   └── package/        # Tài nguyên liên quan đến đóng gói
    │       └── .keep
    ├── configs/            # Các tệp cấu hình cho ứng dụng
    │   └── README.md
    ├── deployments/        # Tài nguyên và tài liệu liên quan đến triển khai
    │   └── README.md
    ├── docs/               # Tài liệu dự án
    │   └── README.md
    ├── examples/           # Ví dụ về cách sử dụng ứng dụng hoặc thư viện
    │   └── README.md
    ├── githooks/           # Các hook Git tùy chỉnh
    │   └── README.md
    ├── init/               # Tài nguyên liên quan đến việc khởi tạo dự án
    │   └── README.md
    ├── scripts/            # Các script tiện ích cho phát triển hoặc triển khai
    │   └── README.md
    ├── test/               # Tài nguyên kiểm thử bổ sung, kiểm thử tích hợp
    │   └── README.md
    ├── third_party/        # Tài nguyên và mã nguồn của bên thứ ba
    │   └── README.md
    ├── tools/              # Công cụ hỗ trợ phát triển
    │   └── README.md
    ├── vendor/             # Các phụ thuộc của bên thứ ba được giữ lại bởi Go Modules
    │   └── README.md
    ├── web/                # Các tệp liên quan đến giao diện người dùng hoặc trang web
    │   ├── app/            # Ứng dụng web
    │   │   └── .keep
    │   ├── static/         # Tệp tĩnh như CSS, JavaScript, hình ảnh
    │   │   └── .keep
    │   └── template/       # Mẫu HTML hoặc template khác
```

### PROJECT PLAYOUT FOR MICROSERVICE

```
    1. api: chưa định tuyến của project (endpoint, route) để gọi controller phù hợp xử lý 'route => controller - handler'

    2. cmd: chứa file main và cấu hình ứng dụng

    3*. internal: chứa code chỉ sử dụng trong project này (middlewares, configs, constant, utils - pkg, datasources <caches, record - model, storage - repositories>, controller - handler, service - business - biz - usecase)

    4. pkg: chứa code có thể sử dụng (share) cho nhiều project khác

    5. scripts: chứa các file kịch bản chạy hệ thống

    6. init: khởi tạo database và environment (.env)
    -----------
    my-server/
    ├── cmd/                # Các ứng dụng chính của dự án
    │   └── _your_app_/     # Thư mục cho ứng dụng chính
    │       └── .keep
    ├── internal/           # Mã nguồn nội bộ không được phép sử dụng bởi các ứng dụng bên ngoài
    │   ├── app/            # Thư mục ứng dụng nội bộ
    │   │   └── _your_app_/ # Thư mục cho ứng dụng nội bộ
    │   │       └── .keep
    │   └── pkg/            # Thư viện nội bộ có thể tái sử dụng
    │       └── _your_private_lib_/
    │           └── .keep
    ├── pkg/                # Thư viện và gói có thể tái sử dụng công khai
    │   └── _your_public_lib_/
    │       └── .keep
    ├── api/                # Định nghĩa API và tài liệu liên quan
    │   └── README.md
    ├── assets/             # Tài nguyên tĩnh, ví dụ hình ảnh, biểu tượng
    │   └── README.md
    ├── build/              # Tài nguyên liên quan đến xây dựng và triển khai
    │   ├── ci/             # Cấu hình liên quan đến tích hợp liên tục (CI)
    │   │   └── .keep
    │   └── package/        # Tài nguyên liên quan đến đóng gói
    │       └── .keep
    ├── configs/            # Các tệp cấu hình cho ứng dụng
    │   └── README.md
    ├── deployments/        # Tài nguyên và tài liệu liên quan đến triển khai
    │   └── README.md
    ├── docs/               # Tài liệu dự án
    │   └── README.md
    ├── examples/           # Ví dụ về cách sử dụng ứng dụng hoặc thư viện
    │   └── README.md
    ├── githooks/           # Các hook Git tùy chỉnh
    │   └── README.md
    ├── init/               # Tài nguyên liên quan đến việc khởi tạo dự án
    │   └── README.md
    ├── scripts/            # Các script tiện ích cho phát triển hoặc triển khai
    │   └── README.md
    ├── test/               # Tài nguyên kiểm thử bổ sung, kiểm thử tích hợp
    │   └── README.md
    ├── third_party/        # Tài nguyên và mã nguồn của bên thứ ba
    │   └── README.md
    ├── tools/              # Công cụ hỗ trợ phát triển
    │   └── README.md
    ├── vendor/             # Các phụ thuộc của bên thứ ba được giữ lại bởi Go Modules
    │   └── README.md
    ├── web/                # Các tệp liên quan đến giao diện người dùng hoặc trang web
    │   ├── app/            # Ứng dụng web
    │   │   └── .keep
    │   ├── static/         # Tệp tĩnh như CSS, JavaScript, hình ảnh
    │   │   └── .keep
    │   └── template/       # Mẫu HTML hoặc template khác
    │       └── .keep
    ├── website/            # Tài nguyên liên quan đến website
    │   └── README.md
    ├── .editorconfig       # Cấu hình trình soạn thảo
    ├── .gitattributes      # Cấu hình thuộc tính Git
    ├── .gitignore          # Danh sách các tệp và thư mục bị loại trừ bởi Git
    ├── go.mod              # Tệp module quản lý các phụ thuộc Go
    ├── LICENSE.md          # Giấy phép sử dụng
    ├── Makefile            # Tập lệnh để tự động hóa các nhiệm vụ (xây dựng, thử nghiệm, v.v.)
    └── README.md           # Tài liệu hướng dẫn sử dụng và thông tin dự án
```

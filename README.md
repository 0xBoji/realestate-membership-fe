# Real Estate Frontend Application

Ứng dụng frontend cho hệ thống quản lý thành viên bất động sản, được xây dựng bằng Spring Boot MVC và Thymeleaf.

## Tính năng chính

### 🏠 Trang chủ
- Hiển thị bất động sản nổi bật
- Danh mục bất động sản
- Form tìm kiếm nhanh
- Thống kê tổng quan

### 🔐 Xác thực
- Đăng ký tài khoản mới
- Đăng nhập/đăng xuất
- Quản lý phiên đăng nhập

### 🏢 Quản lý bất động sản
- Danh sách bất động sản với phân trang
- Tìm kiếm nâng cao với bộ lọc
- Chi tiết bất động sản
- Sắp xếp và lọc kết quả

### 👑 Gói thành viên
- Danh sách gói thành viên
- Đăng ký gói thành viên
- Quản lý gói hiện tại
- Lịch sử thành viên

### 📊 Dashboard
- Tổng quan tài khoản
- Thống kê nhanh
- Hành động nhanh
- Hướng dẫn sử dụng

## Công nghệ sử dụng

- **Backend Framework**: Spring Boot 3.2.2
- **Template Engine**: Thymeleaf
- **Web Client**: Spring WebFlux WebClient
- **Frontend**: Bootstrap 5.3.2, jQuery 3.7.1, Font Awesome 6.0.0
- **Security**: Spring Security
- **Build Tool**: Maven

## Cấu trúc project

```
src/
├── main/
│   ├── java/com/realestate/frontend/
│   │   ├── config/          # Cấu hình WebClient, Security
│   │   ├── controller/      # Controllers MVC
│   │   ├── model/          # DTOs và Models
│   │   └── service/        # Services gọi API
│   └── resources/
│       ├── static/
│       │   ├── css/        # Custom CSS
│       │   └── js/         # Custom JavaScript
│       └── templates/      # Thymeleaf templates
│           ├── auth/       # Trang đăng nhập/đăng ký
│           ├── dashboard/  # Dashboard
│           ├── layout/     # Layout templates
│           └── properties/ # Trang bất động sản
```

## API Integration

Ứng dụng tích hợp với backend API tại:
- **Production URL**: `http://ec2-13-54-93-195.ap-southeast-2.compute.amazonaws.com:8080/api/v1`
- **Swagger UI**: `http://ec2-13-54-93-195.ap-southeast-2.compute.amazonaws.com:8080/api/v1/swagger-ui/index.html`

### Các endpoint được sử dụng:

#### Authentication
- `POST /auth/login` - Đăng nhập
- `POST /auth/register` - Đăng ký

#### Properties
- `GET /properties` - Danh sách bất động sản
- `GET /properties/{id}` - Chi tiết bất động sản
- `GET /properties/featured` - Bất động sản nổi bật
- `GET /properties/search` - Tìm kiếm bất động sản
- `GET /properties/my-properties` - Bất động sản của tôi

#### Categories
- `GET /categories` - Danh sách danh mục
- `GET /categories/{id}` - Chi tiết danh mục

#### Memberships
- `GET /memberships` - Danh sách gói thành viên
- `GET /memberships/{id}` - Chi tiết gói thành viên
- `POST /memberships/{id}/subscribe` - Đăng ký gói
- `GET /memberships/my-membership` - Gói hiện tại
- `GET /memberships/my-history` - Lịch sử thành viên

## Cài đặt và chạy

### Yêu cầu hệ thống
- Java 17+
- Maven 3.6+

### Hướng dẫn cài đặt

1. **Clone repository**
```bash
git clone <repository-url>
cd real-estate-frontend
```

2. **Cài đặt dependencies**
```bash
mvn clean install
```

3. **Chạy ứng dụng**
```bash
mvn spring-boot:run
```

4. **Truy cập ứng dụng**
- Frontend: http://localhost:8081
- API Documentation: Xem trong file API.md

### Cấu hình

Cấu hình trong `application.properties`:

```properties
# Server Configuration
server.port=8081
spring.application.name=real-estate-frontend

# API Configuration
api.base.url=http://ec2-13-54-93-195.ap-southeast-2.compute.amazonaws.com:8080/api/v1

# Thymeleaf Configuration
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
```

## Tính năng UI/UX

### 🎨 Thiết kế
- Responsive design với Bootstrap 5
- Dark/Light theme support
- Modern và clean interface
- Mobile-first approach

### 🔧 Tính năng nâng cao
- Lazy loading cho hình ảnh
- Infinite scroll (upcoming)
- Real-time notifications
- Advanced search filters
- Property comparison
- Favorites management

### 📱 Responsive
- Mobile responsive
- Tablet optimized
- Desktop friendly
- Touch-friendly interface

## Testing

### Unit Tests
```bash
mvn test
```

### Integration Tests
```bash
mvn integration-test
```

## Deployment

### Development
```bash
mvn spring-boot:run
```

### Production
```bash
mvn clean package
java -jar target/frontend-0.0.1-SNAPSHOT.jar
```

### Docker (Optional)
```bash
# Build image
docker build -t real-estate-frontend .

# Run container
docker run -p 8081:8081 real-estate-frontend
```

## Security

- CSRF protection enabled
- Session management
- XSS protection
- Input validation
- Secure headers

## Performance

- Static resource caching
- Compression enabled
- Lazy loading
- Optimized images
- Minified CSS/JS

## Browser Support

- Chrome 90+
- Firefox 88+
- Safari 14+
- Edge 90+

## Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

## Troubleshooting

### Common Issues

1. **API Connection Error**
   - Kiểm tra API base URL trong application.properties
   - Đảm bảo backend API đang chạy

2. **Session Timeout**
   - Cấu hình session timeout trong application.properties
   - Kiểm tra token expiration

3. **Static Resources Not Loading**
   - Kiểm tra đường dẫn static resources
   - Clear browser cache

### Debug Mode

Bật debug logging:
```properties
logging.level.com.realestate.frontend=DEBUG
logging.level.org.springframework.web=DEBUG
```

## Roadmap

- [ ] Chat với AI bot
- [ ] Property comparison
- [ ] Advanced filtering
- [ ] Map integration
- [ ] Virtual tour support
- [ ] Real-time notifications
- [ ] Mobile app

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact

- **Developer**: Real Estate Team
- **Email**: contact@realestate.com
- **Website**: https://realestate.com

---

**Lưu ý**: Đây là ứng dụng demo cho mục đích học tập và phát triển. Không sử dụng trong môi trường production mà không có đánh giá bảo mật đầy đủ.
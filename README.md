# Hospital Management System

A robust RESTful API for managing hospital information built with Spring Boot, featuring comprehensive CRUD operations, data validation, and PostgreSQL database integration.

## 🏥 Features

- **Complete CRUD Operations**: Create, Read, Update, and Delete hospital records
- **Data Validation**: Comprehensive input validation using Bean Validation
- **RESTful API**: Clean and intuitive REST endpoints
- **PostgreSQL Integration**: Reliable database storage with JPA/Hibernate
- **Error Handling**: Proper HTTP status codes and meaningful error messages
- **Lombok Integration**: Reduced boilerplate code
- **Comprehensive Testing**: Postman collection with automated tests

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.5.4
- **Language**: Java 17
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA with Hibernate
- **Build Tool**: Gradle
- **Validation**: Bean Validation (Jakarta)
- **Utilities**: Lombok
- **Testing**: JUnit 5, Postman Collection

## 📋 Prerequisites

Before running this application, make sure you have the following installed:

- **Java 17** or higher
- **Gradle** (or use the included Gradle wrapper)
- **PostgreSQL** database server
- **Postman** (for API testing - optional)

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone <repository-url>
cd Hospital-Management-Spring-Boot
```

### 2. Database Setup

1. **Install PostgreSQL** if not already installed
2. **Create a database**:
   ```sql
   CREATE DATABASE springb1;
   ```
3. **Update database configuration** in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5444/springb1
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

### 3. Run the Application

Using Gradle wrapper:
```bash
./gradlew bootRun
```

Or using Gradle directly:
```bash
gradle bootRun
```

The application will start on `http://localhost:8080`

## 📚 API Documentation

### Base URL
```
http://localhost:8080
```

### Endpoints

#### 1. Get All Hospitals
```http
GET /findall
```

**Response**: List of all hospitals
```json
[
  {
    "id": 1,
    "hospitalName": "City General Hospital",
    "hospitalAddress": "123 Main Street",
    "hospitalCity": "New York",
    "hospitalState": "NY"
  }
]
```

#### 2. Get Hospital by ID
```http
GET /getbyid?id={id}
```

**Parameters**:
- `id` (Long): Hospital ID

**Response**: Hospital object or 404 if not found

#### 3. Create Hospital
```http
POST /insert
Content-Type: application/json
```

**Request Body**:
```json
{
  "hospitalName": "City General Hospital",
  "hospitalAddress": "123 Main Street",
  "hospitalCity": "New York",
  "hospitalState": "NY"
}
```

**Response**: Created hospital object (201 Created)

#### 4. Update Hospital
```http
PUT /update
Content-Type: application/json
```

**Request Body**:
```json
{
  "id": 1,
  "hospitalName": "Updated Hospital Name",
  "hospitalAddress": "456 New Address",
  "hospitalCity": "Los Angeles",
  "hospitalState": "CA"
}
```

**Response**: 200 OK on success

#### 5. Delete Hospital
```http
DELETE /deletebyid?id={id}
```

**Parameters**:
- `id` (Long): Hospital ID to delete

**Response**: 200 OK on success

## 🏗️ Project Structure

```
Hospital-Management-Spring-Boot/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/hms/demo/
│   │   │   │   └── DemoApplication.java          # Main application class
│   │   │   ├── Driver/
│   │   │   │   ├── HospitalController.java       # REST controller
│   │   │   │   └── HospitalDao.java              # Data access layer
│   │   │   ├── Entity/
│   │   │   │   └── Hospital.java                 # JPA entity
│   │   │   └── Repository/
│   │   │       └── HospitalRepository.java       # Spring Data repository
│   │   └── resources/
│   │       ├── application.properties            # Configuration
│   │       ├── static/                           # Static resources
│   │       └── templates/                        # Template files
│   └── test/
│       └── java/
│           └── com/hms/demo/
│               └── DemoApplicationTests.java     # Test classes
├── build.gradle                                  # Gradle build file
├── gradlew                                       # Gradle wrapper script
├── gradlew.bat                                   # Gradle wrapper script (Windows)
├── settings.gradle                               # Gradle settings
├── Hospital_Management_System.postman_collection.json  # Postman collection
├── POSTMAN_SETUP_GUIDE.md                        # Postman setup guide
└── test_post_requests.md                         # Test documentation
```

## 🧪 Testing

### Using Postman Collection

1. **Import the collection**:
   - Open Postman
   - Click "Import" and select `Hospital_Management_System.postman_collection.json`

2. **Set up environment**:
   - The collection uses `{{base_url}}` variable (defaults to `http://localhost:8080`)
   - Update the base URL if needed

3. **Run tests**:
   - Start with "Get All Hospitals" to verify connectivity
   - Use "Create Hospital - Valid Data" to add test data
   - Test all CRUD operations and validation scenarios

### Manual Testing with curl

```bash
# Get all hospitals
curl -X GET http://localhost:8080/findall

# Get hospital by ID
curl -X GET "http://localhost:8080/getbyid?id=1"

# Create a hospital
curl -X POST http://localhost:8080/insert \
  -H "Content-Type: application/json" \
  -d '{
    "hospitalName": "Test Hospital",
    "hospitalAddress": "123 Test St",
    "hospitalCity": "Test City",
    "hospitalState": "TS"
  }'

# Update a hospital
curl -X PUT http://localhost:8080/update \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "hospitalName": "Updated Hospital",
    "hospitalAddress": "456 Updated St",
    "hospitalCity": "Updated City",
    "hospitalState": "US"
  }'

# Delete a hospital
curl -X DELETE "http://localhost:8080/deletebyid?id=1"
```

## 🔧 Configuration

### Database Configuration
Edit `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5444/springb1
spring.datasource.username=postgres
spring.datasource.password=root
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

### Available Properties
- `spring.jpa.hibernate.ddl-auto`: Database schema generation mode
- `spring.jpa.show-sql`: Enable SQL logging
- `spring.jpa.properties.hibernate.format_sql`: Format SQL output
- `spring.jpa.database-platform`: Database dialect

## 📊 Data Model

### Hospital Entity
```java
@Entity
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Hospital name is required")
    private String hospitalName;
    
    @NotBlank(message = "Hospital address is required")
    private String hospitalAddress;
    
    @NotBlank(message = "Hospital city is required")
    private String hospitalCity;
    
    @NotBlank(message = "Hospital state is required")
    private String hospitalState;
}
```

## 🚨 Error Handling

The application provides comprehensive error handling:

- **400 Bad Request**: Invalid input data or missing required fields
- **404 Not Found**: Hospital with specified ID not found
- **500 Internal Server Error**: Server-side errors

### Validation Errors
```json
{
  "hospitalName": "Hospital name is required",
  "hospitalAddress": "Hospital address is required",
  "hospitalCity": "Hospital city is required",
  "hospitalState": "Hospital state is required"
}
```

## 🛡️ Security Considerations

- Input validation using Bean Validation
- SQL injection protection through JPA
- Proper HTTP status codes
- Error message sanitization

## 🔄 Development

### Building the Project
```bash
./gradlew build
```

### Running Tests
```bash
./gradlew test
```

### Creating JAR File
```bash
./gradlew jar
```

### Running JAR File
```bash
java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
```

## 📝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🤝 Support

For support and questions:
- Check the [Postman Setup Guide](POSTMAN_SETUP_GUIDE.md)
- Review the test documentation in `test_post_requests.md`
- Open an issue in the repository

## 🔗 Related Documentation

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Reference](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Bean Validation Specification](https://beanvalidation.org/)

---

**Note**: Make sure to update the database configuration in `application.properties` with your actual PostgreSQL credentials before running the application.

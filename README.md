# Student Management System
A Spring Boot application for managing students with role-based authentication (Admin/User) and JWT-based security.

# Features


- **User Authentication & Authorization**
  -  Register & login with JWT
  - Role-based access:
    - ADMIN:full access to CRUD endpoints
    - USER: read-only endpoints
- **Student Management (CRUD)**
    - Create new student records
    - Fetch students with pagination
    - Update student details
    - Delete students safely
    - Custom exceptions for not found / duplicate records
### 1. Clone the repository
```bash
git clone git@github.com:mohamedIbnKhaled/Student_Management_Spectro_Systems.git
cd Student_Management_Spectro_Systems
```
### 2. Build & Run
```bash
mvn clean install
mvn spring-boot:run
```

### 3. Access Application
- API runs at: `http://localhost:8080`
##  API Endpoints

### Authentication
- `POST /register` → Register new user  
- `POST /login` → Login & get JWT token  

### Students
- `GET api/students` → Get all students (paginated)  
- `GET api/students/{id}` → Get student by ID  
- `POST api/students` → Create student (**ADMIN only**)  
- `PUT api/students/{id}` → Update student (**ADMIN only**)  
- `DELETE api/students/{id}` → Delete student (**ADMIN only**)  

##  Running Tests
Run all unit tests:
```bash
mvn test
```
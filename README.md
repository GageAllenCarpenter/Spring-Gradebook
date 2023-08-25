
# Spring Gradebook Application
[![License](https://img.shields.io/github/license/GageAllenCarpenter/Spring-Gradebook)](https://github.com/GageAllenCarpenter/Spring-Gradebook/blob/main/LICENSE) [![GitHub Issues](https://img.shields.io/github/issues/GageAllenCarpenter/Spring-Gradebook)](https://github.com/GageAllenCarpenter/Spring-Gradebook/issues)

The Spring Gradebook Application is a web-based system designed to manage student grades, courses, professors, and other educational information. It's built using the Spring Framework and Hibernate for data persistence. The view was made using Thymeleaf.
## Features
- Manage Professors: Add, view, update, and delete professor details including their name, email, and contact information. 
- Manage Students: Add, view, update, and delete student details such as name, email, and contact information. 
- Manage Courses: Create, view, update, and delete course information including course name, description, and credit hours. 
- Manage Assignments: Define assignments with details such as name, type, and points possible. 
- Record Grades: Record student grades for assignments, along with the corresponding division, professor, and course. 
- Manage Divisions: Organize courses into divisions with specific time slots, professors, and rooms. 
- Manage Rooms: Add, view, update, and delete room details within different buildings. 
- Manage Semesters: Define academic semesters with start and end dates. 
- Manage Enrollments: Enroll students in divisions and track their progress.
## Technologies Used
- Java 17 
- Spring Framework (Spring Boot, Spring MVC, Spring Data JPA) 
- Hibernate ORM - Thymeleaf (for server-side templating) 
- HTML/CSS - Project Lombok (for reducing boilerplate code) 
- H2 Database (for development) 
- Maven (for project management)
## Getting Started  
1. Clone the repository:
``git clone https://github.com/GageAllenCarpenter/Spring-Gradebook.git``
3. Navigate to the project directory:
``cd spring-gradebook``
4. Build the project using Maven:
``mvn clean install``
5. Run the application using Maven:
``mvn spring-boot:run``
6. Access the application in your web browser:
``[http://localhost:8080](http://localhost:8080/)``
## Configuration
- Database Configuration: The application uses H2 database for development. Update the `application.properties` file to configure your database settings. 
- Port Configuration: By default, the application runs on port 8080. You can change this in the `application.properties` file.
## Contributing
Contributions are welcome! If you'd like to contribute to the project, please follow these steps:
1. Fork the repository. 
2. Create a new branch for your feature/fix:
``git checkout -b feature-name``
3. Make your changes and commit them:
``git commit -m "Add your message here"``
4. Push your changes to your fork:
``git push origin feature-name``
5. Create  a  pull  request  to  the  main  repository.

## License  
This  project  is  licensed  under  the  MIT  License. See  the  [LICENSE](LICENSE) file  for  details.
## Contact  
For  questions  or  support, please  contact  [GageAllenCarpenter@gmail.com](mailto:GageAllenCarpenter@gmail.com).

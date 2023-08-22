USE Gradebook;

DROP TABLE IF EXISTS Grade;
DROP TABLE IF EXISTS Enrollment;
DROP TABLE IF EXISTS Division;
DROP TABLE IF EXISTS Semester;
DROP TABLE IF EXISTS Course;
DROP TABLE IF EXISTS Professor;
DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS Assignment;
DROP TABLE IF EXISTS Room;
DROP TABLE IF EXISTS Building;
DROP VIEW IF EXISTS HighestScore;
DROP VIEW IF EXISTS AverageScore;
DROP USER IF EXISTS Assistant;

CREATE TABLE Building (
	ID INT PRIMARY KEY IDENTITY(1,1),
	Name VARCHAR(60) NOT NULL,
	City VARCHAR(30) NOT NULL,
	State VARCHAR(30) NOT NULL,
	Zipcode VARCHAR(10) NOT NULL
);

CREATE TABLE Room (
	ID INT PRIMARY KEY IDENTITY(1,1),
	BuildingID INT Foreign Key References Building (ID)
);

CREATE TABLE Professor (
	ID INT PRIMARY KEY IDENTITY(1,1),
	FirstName NVARCHAR(60) NOT NULL,
	LastName NVARCHAR(60) NOT NULL,
	Email VARCHAR(60) NOT NULL,
	PhoneNumber VARCHAR(16) NOT NULL,
	Address VARCHAR(40) NOT NULL
);

CREATE TABLE Semester (
	ID INT PRIMARY KEY IDENTITY(1,1),
	StartDate DATE NOT NULL,
	EndDate DATE NOT NULL
);

CREATE TABLE Course (
	ID INT PRIMARY KEY IDENTITY(1,1),
	Name VARCHAR(20) NOT NULL,
	Description VARCHAR(60) NOT NULL,
	CreditHour SMALLINT NOT NULL,
	ProfessorID INT Foreign Key References Professor (ID)
);

CREATE TABLE Assignment (
	ID INT PRIMARY KEY IDENTITY(1,1),
	Name VARCHAR(60) NOT NULL,
	Type VARCHAR(15) NOT NULL,
	PointsPossible INT NOT NULL
);

CREATE TABLE Division (
	ID INT PRIMARY KEY IDENTITY(1,1),
	Time VARCHAR(60) NOT NULL,
	ProfessorID INT Foreign Key References Professor (ID),
	SemesterID INT Foreign Key References Semester (ID),
	CourseID INT Foreign Key References Course (ID),
	RoomID INT Foreign Key References Room (ID)
);

CREATE TABLE Student (
	ID INT PRIMARY KEY IDENTITY(1,1),
	FirstName NVARCHAR(60) NOT NULL,
	LastName NVARCHAR(60) NOT NULL,
	Email VARCHAR(60) NOT NULL,
	PhoneNumber VARCHAR(16) NOT NULL,
	Address VARCHAR(40) NOT NULL

);

CREATE TABLE Enrollment (
	PRIMARY KEY (StudentID, DivisionID),
	EnrollDate DATE NOT NULL,
	CurrentGrade INT NOT NULL,
	MidtermGrade INT,
	FinalGrade INT,
	DivisionID INT Foreign Key References Division (ID),
	StudentID INT Foreign Key References Student (ID)
);


CREATE TABLE Grade (
	ID INT PRIMARY KEY IDENTITY(1,1),
	Points INT NOT NULL,
	AssignmentID INT Foreign Key References Assignment(ID),
	DivisionID INT Foreign Key References Division (ID),
	StudentID INT Foreign Key References Student (ID),
	ProfessorID INT Foreign key References Student (ID)
);

GO 

 -- Highest Score
CREATE VIEW HighestScore AS
(SELECT MAX(Points) AS HighestScore
FROM Grade
WHERE (DivisionID = DivisionID) AND (StudentID = StudentID) AND (AssignmentID = AssignmentID));

GO

-- Average Score
CREATE VIEW AverageScore AS
(SELECT AVG(Points) AS AverageScore
FROM Grade
WHERE (DivisionID = DivisionID) AND (StudentID = StudentID) AND (AssignmentID = AssignmentID)); 

GO

-- Points Reveived
ALTER TABLE Grade
ADD CHECK (Points >= 0);

-- Student Index
CREATE UNIQUE INDEX Student ON Enrollment (StudentID, DivisionID);

-- AssistantProfessor 
CREATE USER Assistant WITHOUT LOGIN;
GRANT UPDATE (Points) ON Grade TO Assistant;

-- Buildings...
INSERT INTO Building (Name, City, State, Zipcode) VALUES
    ('Caperton', 'Parkersburg', 'West Virginia', 26101),
    ('MAIN', 'Parkersburg', 'West Virginia', 26101);

-- Semesters...
INSERT INTO Semester (StartDate, EndDate) VALUES
    ('2018-01-15', '2018-05-10'),
    ('2018-08-15', '2018-12-15'),
    ('2022-01-15', '2022-05-10');

-- Professors...
INSERT INTO Professor (FirstName, LastName, Email, PhoneNumber, Address) VALUES
    ('Charles', 'Almond', 'calmond@wvup.edu', '304-422-5232', '809 7th Street Parkersburg, WV 26101'),
    ('Doug', 'Rhodes', 'drhodes@wvup.edu', '304-917-4741', '820 Market St, Parkersburg, WV 26101');

-- Courses...
INSERT INTO Course (Name, Description, CreditHour, ProfessorID) VALUES
    ('CS122', 'Programming 2', 3, 1),
    ('CS201', 'Database Theory and Design', 3, 1);

-- Divisions...
INSERT INTO Division (Time) VALUES ('M T W TH F SA SU 8:00-8:00');
INSERT INTO Division (Time) VALUES ('M T W TH F SA SU 8:00-8:00');

-- Students...
INSERT INTO Student (FirstName, LastName, Email, PhoneNumber, Address) VALUES
    ('Gage', 'Carpenter', 'gcarpen2@wvup.edu', '304-588-9230', '3555 Farnam Street Omaha, NE 68131'),
    ('Thomas', 'Fullton', 'TFullton@gmail.com', '740-350-6696', 'narnia');

-- Assignments...
INSERT INTO Assignment (Name, Type, PointsPossible) VALUES
    ('Repo Design', 'Final', 100),
    ('Web Design Exam', 'Final', 100),
    ('Build A Website', 'Final', 100),
    ('Build A Network', 'Final', 100),
    ('Cisco Summary', 'Final', 100),
    ('Five Card Stud', 'Final', 100),
    ('Discrete Math Projects', 'Final', 100),
    ('Discrete Math Exam', 'Final', 100);

-- Grades...
INSERT INTO Grade (Points) VALUES (100), (100);
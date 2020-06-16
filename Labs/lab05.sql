-- Lab 06, CS 260, Spring 2019
-- Create and populate Employees and Departments tables

-- Section 1
-- If tables with these names already exist, drop them
DROP TABLE Employees;
DROP TABLE Departments;

-- Section 2
-- Create these tables, specifying all columns, primary keys, and appropriate foreign keys
-- Part 1. Create Departments table, with columns: 
--   department number, department name, department start date, department budget in US dollars
CREATE TABLE DEPARTMENTS(
DepartmentNumber INTEGER,
DepartmentName VARCHAR(30),
DepartmentStartDate DATE,
DepartmentBudget NUMBER(10),
PRIMARY KEY (DepartmentNumber)
);



-- Part 2. Create Employees table, with columns: 
--   employee id, employee first name, employee last name, employee birth date, employee salary, employee email address, employee department id
CREATE TABLE EMPLOYEES(
EmployeeID INTEGER,
EmployeeFirstName VARCHAR(16),
EmployeeLastName VARCHAR(16),
EmployeeBirthDate DATE,
EmployeeSalary NUMBER(8,2),
EmployeeEmail VARCHAR(16),
EmployeeDepartmentID INTEGER,
PRIMARY KEY (EmployeeID),
FOREIGN KEY(EmployeeDepartmentID) REFERENCES DEPARTMENTS(DepartmentNumber)
);


-- Section 3
-- Insert rows into these tables

-- Departments table, row 1:   1, Production, February 03, 2015, $2,500,000
INSERT INTO Departments
   VALUES (1, 'Production','03-FEB-2015' , 2500000);

-- Departments table, row 2:   2, Research, June 25, 2016,  $1,750,000
INSERT INTO Departments
   VALUES (2, 'Research','25-JUNE-2016' , 1750000);
   
-- Departments table, row 3:   3, Software Development, October 14, 2016, $3,125,000
INSERT INTO Departments
    VALUES (3, 'Software Development', '14-OCT-2016', 3125000);

-- Departments table, row 4:   4, Testing, January 2, 2016, $750,000
INSERT INTO Departments
    VALUES (4, 'Testing', '02-JAN-2016', 750000);

-- Employees table, row 1:     101, Marcela, Rodriguez, February 12, 1985, $60,000.00, "mr06@xcomp.com", 3
INSERT INTO Employees
    VALUES (101, 'Marcela', 'Rodriquez', '12-FEB-1985', 60000.00, 'mr06@xcomp.com',3);
    
-- Employees table, row 2:     102, Mingshen, Wu, September 30, 1990, $58,000.00, "mw03@xcomp.com", 2
INSERT INTO Employees
    VALUES(102, 'Mingshen', 'Wu', '30-SEP-1990', 58000.00, 'mw03@xcomp.com', 2);
    
-- Employees table, row 3:     103, Steve, Mitrovic, July 15, 1987, $62,000.00, "sm01@xcomp.com", 2
INSERT INTO Employees
    VALUES(103, 'Steve', 'Mitrovic', '15-JUL-1987', 62000.00, 'sm01@xcomp.com', 2);
    
-- Employees table, row 4:     104, Yalini, Sundralingam, February 22, 1993, $75,000.00, "ys02@xcomp.com", 1
INSERT INTO Employees
    VALUES(104, 'Yalini', 'Sundralingam', '22-FEB-1992', 75000.00, 'ys02@xcomp.com', 1);
    
-- Employees table, row 5:     105, Alexandro, Cariuna, May 1, 1995, $63,000.00 "ac10@xcomp.com", 1
INSERT INTO Employees
    VALUES(105, 'Alexandro','Cariuna', '01-MAY-1995', 63000.00, 'ac10@xcomp.com', 1);
    
-- Employees table, row 6:     106, Sara, Williams, December 05, 1991, $65,000.00, "sw05@xcomp.com", 2
INSERT INTO Employees
    VALUES(106, 'Sara', 'Williams', '05-DEC-1991', 65000.00, 'sw05@xcomp.com', 2);

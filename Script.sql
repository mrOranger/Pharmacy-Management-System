DROP TYPE Doctor FORCE;
DROP TYPE Patient FORCE;
DROP TYPE Prescription FORCE;
DROP TYPE PharmaceuticalCompany FORCE;
DROP TYPE Address FORCE;
DROP TYPE Report FORCE;
DROP TYPE Product FORCE;
DROP TYPE Medicine FORCE;
DROP TYPE BrandedMedicine FORCE;
DROP TYPE UnbrandedMedicine FORCE;
DROP TYPE PerfumeryProduct FORCE;
DROP TYPE SoldProducts FORCE;
DROP TYPE PresciptedMedicine FORCE;
DROP TYPE Sale FORCE;
DROP TYPE SoldProduct FORCE;
DROP TYPE PresciptedMedicines FORCE;
DROP VIEW GenerateList;
DROP VIEW PrintMedicine;
DROP VIEW RegisteredUser;
DROP VIEW ReportsStatistics;
DROP VIEW SalesRange;
DROP VIEW UnbrandeMedicineEquivalent;
DROP TABLE Doctors;
DROP TABLE Patients;
DROP TABLE Reports;
DROP TABLE BrandedMedicines;
DROP TABLE UnbrandedMedicines;
DROP TABLE PerfumeryProducts;
DROP TABLE Sales;
DROP TABLE Prescriptions;
DROP TABLE Companies;
DROP TABLE Users;
DROP PROCEDURE InsertCosmetic;
DROP PROCEDURE InsertNewMedicine;
DROP PROCEDURE InsertNewMedicineWithCompany;
DROP PROCEDURE InsertNewUser;
DROP PROCEDURE PopulateBrandedMedicine;
DROP PROCEDURE PopulateCompany;
DROP PROCEDURE PopulateDoctors;
DROP PROCEDURE PopulatePatient;
DROP PROCEDURE PopulatePerfumeryProducts;
DROP PROCEDURE PopulatePrescriptions;
DROP PROCEDURE PopulateReport;
DROP PROCEDURE PopulateUnbrandedMedicine;
DROP PROCEDURE PoupulateSale;
DROP PROCEDURE RegistareNewSale;
DROP PROCEDURE RegisterNewProduct;
DROP FUNCTION InsertNewPrescription;
DROP FUNCTION InsertNewSale;
DROP TRIGGER CheckReportYear;
DROP TRIGGER CheckPerfumeryNames;
DROP TRIGGER CheckLicences;
DROP TRIGGER CheckMedicinesPrescription;
DROP TRIGGER CheckUsers;
DROP TRIGGER CheckSaleDate;
DROP TRIGGER CheckSaleProductName;
DROP TRIGGER CheckNikname;
DROP TRIGGER UpdateReports;
DROP INDEX NickNameIndex;
/
CREATE OR REPLACE TYPE Doctor AS OBJECT(
    RegistrationNumber VARCHAR2(20),
    Name VARCHAR2(20),
    Surname VARCHAR2(20)
);
/
CREATE OR REPLACE TYPE Patient AS OBJECT(
    TaxCode CHAR(16),
    Name VARCHAR2(20),
    Surname VARCHAR2(20)
);
/
CREATE OR REPLACE TYPE Address AS OBJECT (
    Code INTEGER,
    Street VARCHAR2(20),
    StreetCode INTEGER,
    City VARCHAR2(20),
    Country VARCHAR2(20)
);
/
CREATE OR REPLACE TYPE PharmaceuticalCompany AS OBJECT (
    Name VARCHAR2(20),
    Located Address
);
/
CREATE OR REPLACE TYPE Report AS OBJECT(
    Code INTEGER,
    Semester INTEGER,
    Year INTEGER,
    Amount FLOAT
);
/
CREATE OR REPLACE TYPE Product AS OBJECT(
    Name VARCHAR2(20),
    Description VARCHAR2(300),
    Cost FLOAT
)NOT FINAL NOT INSTANTIABLE;
/
CREATE OR REPLACE TYPE PerfumeryProduct UNDER Product(
    Type VARCHAR2(20)
);
/
CREATE OR REPLACE TYPE Medicine UNDER Product(
    Prescript INTEGER
)NOT FINAL NOT INSTANTIABLE;
/
CREATE OR REPLACE TYPE BrandedMedicine UNDER Medicine(
    Years INTEGER,
    Distributed REF PharmaceuticalCompany
);
/
CREATE OR REPLACE TYPE UnbrandedMedicine UNDER Medicine(
    Refers REF BrandedMedicine
);
/
CREATE OR REPLACE TYPE SoldProduct AS OBJECT(
    Quantity INTEGER,
    Total FLOAT,
    ProductName VARCHAR2(20)
);
/
CREATE OR REPLACE TYPE SoldProducts AS TABLE OF SoldProduct;
/
CREATE OR REPLACE TYPE Sale AS OBJECT(
    Code VARCHAR2(20),
    Quantity INTEGER,
    Day DATE,
    Total FLOAT,
    PartOf REF Report,
    ProductSold SoldProducts
);
/
CREATE OR REPLACE TYPE PresciptedMedicine AS OBJECT(
    Name VARCHAR2(20)
);
/
CREATE OR REPLACE TYPE PresciptedMedicines AS TABLE OF PresciptedMedicine;
/
CREATE OR REPLACE TYPE Prescription AS OBJECT(
    Code INTEGER,
    NumberOfProducts INTEGER,
    Prescribe REF Doctor,
    Related REF Patient,
    Prescripted PresciptedMedicines,
    Linked REF Sale
);
/
CREATE TABLE Doctors OF Doctor(
    RegistrationNumber PRIMARY KEY,
    Name NOT NULL,
    Surname NOT NULL
);
/
CREATE TABLE Patients OF Patient(
    TaxCode PRIMARY KEY,
    Name NOT NULL,
    Surname NOT NULL
)
/
CREATE TABLE Companies OF PharmaceuticalCompany(
    Name PRIMARY KEY,
    Located NOT NULL
);
/
CREATE TABLE Reports OF Report(
    Code PRIMARY KEY,
    Semester NOT NULL,
    Year NOT NULL,
    Amount NOT NULL
)PARTITION BY RANGE (Year)( 
    PARTITION partition_2010 VALUES LESS THAN (2011),
    PARTITION partition_2011 VALUES LESS THAN (2012),
    PARTITION partition_2012 VALUES LESS THAN (2013),
    PARTITION partition_2013 VALUES LESS THAN (2014),
    PARTITION partition_2014 VALUES LESS THAN (2015),
    PARTITION partition_2015 VALUES LESS THAN (2016),
    PARTITION partition_2016 VALUES LESS THAN (2017),
    PARTITION partition_2017 VALUES LESS THAN (2018),
    PARTITION partition_2018 VALUES LESS THAN (2019),
    PARTITION partition_2019 VALUES LESS THAN (2020),
    PARTITION partition_2020 VALUES LESS THAN (2021)
);
/
ALTER TABLE Reports ENABLE ROW MOVEMENT;
/
CREATE TABLE BrandedMedicines OF BrandedMedicine(
    Name PRIMARY KEY,
    Cost NOT NULL,
    Prescript NOT NULL,
    Years NOT NULL
);
/
CREATE TABLE UnbrandedMedicines OF UnbrandedMedicine(
    Name PRIMARY KEY,
    Cost NOT NULL
);
/
CREATE TABLE PerfumeryProducts OF PerfumeryProduct(
    Name PRIMARY KEY,
    Cost NOT NULL,
    Type NOT NULL
);
/
CREATE TABLE Sales OF Sale(
    Code PRIMARY KEY,
    Quantity NOT NULL,
    Day NOT NULL,
    Total NOT NULL
)NESTED TABLE ProductSold STORE AS ProductSoldNT, PARTITION BY RANGE (Day)( 
    PARTITION partition_2010 VALUES LESS THAN (to_date('01-JAN-2011','DD-MON-YYYY')),
    PARTITION partition_2011 VALUES LESS THAN (to_date('01-JAN-2012', 'DD-MON-YYYY')),
    PARTITION partition_2012 VALUES LESS THAN (to_date('01-JAN-2013', 'DD-MON-YYYY')),
    PARTITION partition_2013 VALUES LESS THAN (to_date('01-JAN-2014', 'DD-MON-YYYY')),
    PARTITION partition_2014 VALUES LESS THAN (to_date('01-JAN-2015', 'DD-MON-YYYY')),
    PARTITION partition_2015 VALUES LESS THAN (to_date('01-JAN-2016', 'DD-MON-YYYY')),
    PARTITION partition_2016 VALUES LESS THAN (to_date('01-JAN-2017', 'DD-MON-YYYY')),
    PARTITION partition_2017 VALUES LESS THAN (to_date('01-JAN-2018', 'DD-MON-YYYY')),
    PARTITION partition_2018 VALUES LESS THAN (to_date('01-JAN-2019', 'DD-MON-YYYY')),
    PARTITION partition_2019 VALUES LESS THAN (to_date('01-JAN-2020', 'DD-MON-YYYY')),
    PARTITION partition_2020 VALUES LESS THAN (to_date('01-JAN-2021', 'DD-MON-YYYY'))
);
/
ALTER TABLE Sales ENABLE ROW MOVEMENT;
/
CREATE TABLE Prescriptions OF Prescription(
    Code PRIMARY KEY,
    NumberOfProducts NOT NULL
)NESTED TABLE Prescripted STORE AS PrescriptedNT;
/
CREATE TABLE Users(
    Code INTEGER,
    Name VARCHAR2(20),
    Surname VARCHAR2(20),
    DateOfBirth DATE,
    NickName VARCHAR2(30),
    Password VARCHAR2(50)
);
/
CREATE OR REPLACE PROCEDURE PopulateDoctors IS 
    TYPE namesVarray IS VARRAY(5) OF VARCHAR2(20);
    TYPE surnamesVarray IS VARRAY(5) OF VARCHAR2(20);
    names namesVarray := namesVarray('Mario', 'Giulio', 'Marco', 'Anna', 'Giovanna');
    surnames surnamesVarray := surnamesVarray('Rossi', 'Verdi', 'Bruni', 'Gialli', 'Neri');
BEGIN
    FOR currIndex IN 0..100 LOOP
        INSERT INTO Doctors VALUES ('Code_'||currIndex, names(TRUNC(DBMS_RANDOM.VALUE(1,5))), surnames(TRUNC(DBMS_RANDOM.VALUE(1,5))));
    END LOOP;
    COMMIT;
END;
/
CREATE OR REPLACE PROCEDURE PopulatePatient IS
    TYPE namesVarray IS VARRAY(5) OF VARCHAR2(20);
    TYPE surnamesVarray IS VARRAY(5) OF VARCHAR2(20);
    names namesVarray := namesVarray('Mario', 'Giulio', 'Marco', 'Anna', 'Giovanna');
    surnames surnamesVarray := surnamesVarray('Rossi', 'Verdi', 'Bruni', 'Gialli', 'Neri');
BEGIN
    FOR currIndex IN 0..500 LOOP
        INSERT INTO Patients VALUES ('TaxCode_'||currIndex, names(TRUNC(DBMS_RANDOM.VALUE(1,5))), surnames(TRUNC(DBMS_RANDOM.VALUE(1,5))));
    END LOOP;
    COMMIT;
END;
/
CREATE OR REPLACE PROCEDURE PopulateCompany IS
    TYPE streetsVarray IS VARRAY(5) OF VARCHAR2(30);
    TYPE countriesVarray IS VARRAY(5) OF VARCHAR2(20);
    TYPE citiesVarray IS VARRAY(5) OF VARCHAR2(30);
    streets streetsVarray := streetsVarray('Via delle Rose', 'Piazza Italia', 'Corso Unità', 'Viale Roma', 'Strada Libertà');
    countries countriesVarray := countriesVarray('Italia', 'USA', 'Spagna', 'Gernamia', 'Francia');
    cities citiesVarray := citiesVarray('Bari', 'New York', 'Madrid', 'Berlino', 'Parigi');
    currAddress Address;
BEGIN 
    FOR currIndex IN 0..10 LOOP
        currAddress := Address(currIndex, streets(TRUNC(DBMS_RANDOM.VALUE(1,5))), currIndex, cities(TRUNC(DBMS_RANDOM.VALUE(1,5))), countries(TRUNC(DBMS_RANDOM.VALUE(1,5))));
        INSERT INTO Companies VALUES ('Name_'||currIndex, currAddress);
    END LOOP;
    COMMIT;
END;
/
CREATE OR REPLACE PROCEDURE PopulateReport IS
BEGIN
    FOR currIndex IN 0..177 LOOP
        INSERT INTO Reports VALUES (currIndex, TRUNC(DBMS_RANDOM.VALUE(1,2)), TRUNC(DBMS_RANDOM.VALUE(2010, 2019)), DBMS_RANDOM.VALUE(0,10000));
    END LOOP;
    COMMIT;
END;
/
CREATE OR REPLACE PROCEDURE PopulatePerfumeryProducts IS
    TYPE productType IS VARRAY(3) OF VARCHAR2(20);
    typesP productType := productType('Cosmetic', 'Hygiene Product', 'Baby product');
BEGIN
    FOR currIndex IN 0..3000 LOOP
        INSERT INTO PerfumeryProducts VALUES ('Name_'||currIndex, 'Description_'||currIndex, DBMS_RANDOM.VALUE(1,100), typesP(TRUNC(DBMS_RANDOM.VALUE(1,3))));
    END LOOP;
    COMMIT;
END;
/
CREATE OR REPLACE PROCEDURE PoupulateSale IS
    currDate DATE;
    products SoldProducts; 
    randomValue INTEGER;
    productOneCost FLOAT;
    productTwoCost FLOAT;
    total FLOAT;
BEGIN
  FOR currIndex IN 1..10000 LOOP
    products := SoldProducts(); --Nested table Constructor
    products.EXTEND(2);
    productOneCost := DBMS_RANDOM.VALUE(1,50);
    productTwoCost := DBMS_RANDOM.VALUE(1,50);
    total := productOneCost + productTwoCost;
    randomValue := trunc(DBMS_RANDOM.VALUE(1,2));
    products(1):= SoldProduct(TRUNC(DBMS_RANDOM.VALUE(1,10)), productOneCost, 'Product_'||currIndex);
    products(2):= SoldProduct(TRUNC(DBMS_RANDOM.VALUE(1,10)), productTwoCost, 'Product_'||currIndex);
    currDate := to_date(trunc(dbms_random.value(to_char(DATE '2010-01-01','J'),to_char(DATE '2019-12-31','J'))),'J'); 
    INSERT INTO Sales VALUES (currIndex, TRUNC(DBMS_RANDOM.VALUE(1,10)), currDate, total, (SELECT * FROM (SELECT REF(R) FROM Reports R ORDER BY DBMS_RANDOM.VALUE) WHERE ROWNUM = 1), products);
  END LOOP;
  COMMIT;
END;
/
CREATE OR REPLACE PROCEDURE PopulateBrandedMedicine IS 
BEGIN 
    FOR currIndex IN 0..2500 LOOP
        INSERT INTO BrandedMedicines VALUES ('Name_'||currIndex, 'Description_'||currIndex, DBMS_RANDOM.VALUE(1,100), TRUNC(DBMS_RANDOM.VALUE(0,1)), TRUNC(DBMS_RANDOM.VALUE(1,5)), (SELECT * FROM (SELECT REF(C) FROM Companies C ORDER BY DBMS_RANDOM.VALUE) WHERE ROWNUM = 1));
    END LOOP;
    COMMIT;
END;
/
CREATE OR REPLACE PROCEDURE PopulateUnbrandedMedicine IS
BEGIN
    FOR currIndex IN 0..5 LOOP
        INSERT INTO UnbrandedMedicines VALUES ('Name_'||currIndex, 'Description_'||currIndex, DBMS_RANDOM.VALUE(1,100), TRUNC(DBMS_RANDOM.VALUE(0,1)),  (SELECT * FROM (SELECT REF(B) FROM BrandedMedicines B ORDER BY DBMS_RANDOM.VALUE) WHERE ROWNUM = 1));
    END LOOP;
    COMMIT;
END;
/
CREATE OR REPLACE PROCEDURE PopulatePrescriptions IS
    medicines PresciptedMedicines;
BEGIN
    FOR currIndex IN 0..1000 LOOP
        medicines := PresciptedMedicines();
        medicines.EXTEND(3);
        FOR medicinesIndex IN 1..3 LOOP
            medicines(medicinesIndex) := PresciptedMedicine('Name_'||medicinesIndex);
        END LOOP;
        INSERT INTO Prescriptions VALUES (currIndex, TRUNC(DBMS_RANDOM.VALUE(1,10)), (SELECT * FROM (SELECT REF(D) FROM Doctors D ORDER BY DBMS_RANDOM.VALUE) WHERE ROWNUM = 1), (SELECT * FROM (SELECT REF(P) FROM Patients P ORDER BY DBMS_RANDOM.VALUE) WHERE ROWNUM = 1), medicines, (SELECT * FROM (SELECT REF(S) FROM Sales S ORDER BY DBMS_RANDOM.VALUE) WHERE ROWNUM = 1));
    END LOOP;
    COMMIT;
END;
/
CREATE OR REPLACE PROCEDURE InsertNewUser (Name IN VARCHAR2, Surname IN VARCHAR2, DateOfBirth IN VARCHAR2, Username IN VARCHAR2, Password IN VARCHAR2) IS
BEGIN
  INSERT INTO Users VALUES ((SELECT COUNT(*) FROM Users), Name, Surname, DateOfBirth, Username, Password);
  COMMIT;
END;
/
CREATE OR REPLACE PROCEDURE InsertNewMedicine(Name IN VARCHAR2, Description IN VARCHAR2, Cost IN FLOAT, Licence IN NUMBER, Years IN NUMBER, Company IN VARCHAR2) IS
BEGIN
    INSERT INTO BrandedMedicines VALUES (Name, Description, Cost, Licence, Years, (SELECT REF(C) FROM Companies C WHERE C.Name = Company));
    COMMIT;
END;
/
CREATE OR REPLACE PROCEDURE InsertNewMedicineWithCompany(Name IN VARCHAR2, Description IN VARCHAR2, Cost IN FLOAT, Licence IN NUMBER, Years IN NUMBER, Company IN VARCHAR2, StreetName IN VARCHAR2, StreetCode IN NUMBER, CityName IN VARCHAR2, Country IN VARCHAR2) IS
BEGIN
    INSERT INTO Companies VALUES (Company, Address(1, StreetName, StreetCode, CityName, Country));
    COMMIT;
    InsertNewMedicine(Name, Description, Cost, Licence, Years, Company);
END;
/
CREATE OR REPLACE PROCEDURE InsertCosmetic(Name IN VARCHAR2, Description IN VARCHAR2, Cost IN FLOAT, Type IN VARCHAR2) IS
BEGIN
  INSERT INTO PerfumeryProducts VALUES (Name, description, Cost, Type);
  COMMIT;
END;
/
CREATE OR REPLACE FUNCTION InsertNewSale(Quantity IN NUMBER, Total IN NUMBER, ProductQuantity IN NUMBER, ProductTotal IN FLOAT, ProductName IN VARCHAR2) RETURN INTEGER IS
    currentSale INTEGER := 0;
    products SoldProducts; 
BEGIN
    SELECT COUNT(*) INTO currentSale FROM Sales;
    products := SoldProducts(); --Nested table Constructor
    products.EXTEND(1);
    products(1) := SoldProduct(ProductQuantity, ProductTotal, ProductName);
    INSERT INTO Sales VALUES (currentSale, Quantity, CURRENT_DATE, Total, NULL, products);
    COMMIT;
    RETURN currentSale;
END;
/
CREATE OR REPLACE PROCEDURE RegistareNewSale(CodeTable IN NUMBER, Quantity IN NUMBER, Total IN FLOAT, ProductName IN VARCHAR2) IS
BEGIN
  INSERT INTO TABLE(SELECT ProductSold FROM Sales WHERE Code = CodeTable) VALUES (quantity, total, productname);
  COMMIT;
END;
/
CREATE OR REPLACE FUNCTION InsertNewPrescription(NumberOfProducts IN NUMBER, Doctor IN VARCHAR2, Patient IN VARCHAR2, Medicine IN VARCHAR2) RETURN INTEGER IS
    currentPrescription INTEGER := 0;
    medicines PresciptedMedicines; --Nested table variable
BEGIN
    SELECT COUNT(*) INTO currentPrescription FROM Prescriptions;
    medicines := PresciptedMedicines();
    medicines.EXTEND(1);
    medicines(1) := PresciptedMedicine(Medicine);
    INSERT INTO Prescriptions VALUES (currentPrescription, NumberOfProducts, (SELECT REF(D) FROM Doctors D WHERE D.RegistrationNumber = Doctor), (SELECT REF(P) FROM Patients P WHERE P.TaxCode = Patient), medicines, NULL);
    COMMIT;
    RETURN currentPrescription;
END;
/
CREATE OR REPLACE PROCEDURE RegisterNewProduct(CodePrescription IN NUMBER, Name IN VARCHAR2) IS
BEGIN
    UPDATE Prescriptions SET Prescripted = COALESCE(Prescripted, PresciptedMedicines()) MULTISET UNION ALL PresciptedMedicines(PresciptedMedicine(Name)) WHERE Code = CodePrescription;
  COMMIT;
END;
/
CREATE INDEX NickNameIndex ON Users(NickName);
/
CREATE OR REPLACE VIEW UnbrandeMedicineEquivalent AS
SELECT U.Name AS Name, U.Description AS Description, U.Cost AS Cost, U.Prescript AS PrescriptionRequire, DEREF(U.Refers).Name AS BrandedMedicine 
FROM UnbrandedMedicines U ORDER BY BrandedMedicine;
/
CREATE OR REPLACE VIEW PrintMedicine AS
SELECT Bm.Name, Bm.Cost, Bm.Prescript, Bm.Years, DEREF(Bm.Distributed).Name AS CompanyName FROM BrandedMedicines Bm;
/
CREATE OR REPLACE VIEW GenerateList AS
SELECT P.Code, P.numberofproducts, M.* , DEREF(P.Prescribe).Name AS DoctorName, DEREF(P.Prescribe).Surname AS DoctorSurname, DEREF(P.Prescribe).RegistrationNumber AS DoctorNumber FROM Prescriptions P, TABLE(P.Prescripted) M;
/
CREATE OR REPLACE VIEW SalesRange AS
SELECT S.Code AS SaleCode, S.Quantity AS SoldQuantity, S.Day, S.Total AS SoldTotal, P.* 
FROM Sales S, TABLE(S.ProductSold) P ORDER BY S.Code;
/
CREATE OR REPLACE VIEW RegisteredUser AS
SELECT U.Code, U.Name, U.Surname, U.DateOfBirth FROM Users U;
/
CREATE OR REPLACE VIEW ReportsStatistics AS 
SELECT S.Code, S.Quantity, S.Day, S.Total, DEREF(S.PartOf).Semester AS ReportSemester, DEREF(S.PartOf).Year AS ReportYear, DEREF(S.PartOf).Amount AS ReportAmount 
FROM Sales S ORDER BY S.Day ASC;
/
EXECUTE PopulateDoctors;
EXECUTE PopulatePatient;
EXECUTE PopulateCompany;
EXECUTE PopulateReport;
EXECUTE PopulatePerfumeryProducts;
EXECUTE PopulateBrandedMedicine;
EXECUTE PopulateUnbrandedMedicine;
EXECUTE PoupulateSale;
EXECUTE PopulatePrescriptions;
/
CREATE OR REPLACE TRIGGER CheckReportYear
BEFORE INSERT OR UPDATE OF Year ON Reports
FOR EACH ROW
BEGIN
    dbms_output.put_line(to_char(CURRENT_DATE, 'yyyy'));
    IF (:new.Year < '2010' OR :new.Year > to_char(CURRENT_DATE, 'yyyy')) THEN
        RAISE_APPLICATION_ERROR(-20001, 'The given date is not valid!');
    END IF;
END;
/
CREATE OR REPLACE TRIGGER CheckPerfumeryNames
BEFORE INSERT OR UPDATE OF Type ON PerfumeryProducts
FOR EACH ROW
BEGIN
    IF (:new.Type <> 'Cosmetic' AND :new.Type <> 'Hygiene Product' AND :new.Type <> 'Baby Product') THEN
        RAISE_APPLICATION_ERROR(-20002, 'The given product doesnt exist!');
    END IF;
END;
/
CREATE OR REPLACE TRIGGER CheckLicences 
BEFORE INSERT OR UPDATE OF Years ON BrandedMedicines
FOR EACH ROW
BEGIN
    IF(:new.Years < 1 OR :new.Years > 5) THEN
        RAISE_APPLICATION_ERROR(-20003, 'The licence range must be between 1 and 5 years!');
    END IF;
END;
/
CREATE OR REPLACE TRIGGER CheckMedicinesPrescription
BEFORE INSERT OR UPDATE ON Prescriptions
FOR EACH ROW
DECLARE
    prescripted INTEGER := 0;
BEGIN 
    FOR currIndex IN :new.Prescripted.FIRST..:new.Prescripted.LAST LOOP
        SELECT M.Prescript INTO prescripted FROM BrandedMedicines M WHERE M.Name = :new.Prescripted(currIndex).Name;
        IF prescripted = 0 THEN
            RAISE_APPLICATION_ERROR(-20004, 'You cannot prescribe a medice that doesnt require it!');
        END IF;
    END LOOP;
END;
/
CREATE OR REPLACE TRIGGER CheckUsers
BEFORE INSERT ON Users
FOR EACH ROW
DECLARE 
  CURSOR userCursor IS SELECT * FROM Users;
BEGIN
    FOR currUser IN userCursor LOOP 
        IF (:new.Name = currUser.Name AND :new.Surname = currUser.Surname AND :new.DateOfBirth = currUser.DateOfBirth) THEN
            RAISE_APPLICATION_ERROR(-20005, 'The user is already registered to the platform!');
        END IF;
    END LOOP;
END;
/
CREATE OR REPLACE TRIGGER CheckSaleDate
BEFORE INSERT OR UPDATE OF Day ON Sales
FOR EACH ROW
BEGIN
    IF (:new.Day > CURRENT_DATE) THEN
        RAISE_APPLICATION_ERROR(-20006, 'The sales date cannot be greater than today!');
    END IF;
END;
/
CREATE OR REPLACE TRIGGER CheckSaleProductName
BEFORE INSERT OR UPDATE ON Sales
FOR EACH ROW
DECLARE
    CURSOR perfumeryCursor IS SELECT * FROM PerfumeryProducts;
    CURSOR brandedCursor IS SELECT * FROM BrandedMedicines;
    CURSOR unbrandedCursor IS SELECT * FROM UnbrandedMedicines;
    foundPerfumery INTEGER := 0;
    foundBranded INTEGER := 0;
    foundUnbranded INTEGER := 0;
BEGIN 
    IF :new.ProductSold IS NOT NULL THEN 
        FOR currIndex IN :new.ProductSold.FIRST..:new.ProductSold.LAST LOOP
            FOR currPerfumery IN perfumeryCursor LOOP
                IF currPerfumery.Name = :new.ProductSold(currIndex).ProductName THEN
                    foundPerfumery := 1;
                END IF;
            END LOOP;
            FOR currBranded IN brandedCursor LOOP
                IF currBranded.Name = :new.ProductSold(currIndex).ProductName THEN
                    foundBranded := 1;
                END IF;
            END LOOP;
            FOR currUnbranded IN unbrandedCursor LOOP
                IF currUnbranded.Name = :new.ProductSold(currIndex).ProductName THEN
                    foundUnbranded := 1;
                END IF;
            END LOOP;
        END LOOP;
        IF foundBranded = 0 AND foundPerfumery = 0 AND foundUnbranded = 0 THEN
            RAISE_APPLICATION_ERROR(-20007, 'The current medicine doesnt exist in the pharmacy!');
        END IF;
    END IF;
END;
/
CREATE OR REPLACE TRIGGER CheckNikname 
BEFORE INSERT OR UPDATE OF NickName ON Users
FOR EACH ROW
DECLARE
    CURSOR nickNameCursor IS SELECT * FROM Users;
BEGIN
    FOR currUser IN nickNameCursor LOOP
        IF currUser.Nickname = :new.NickName THEN
            RAISE_APPLICATION_ERROR(-20008, 'The following nickaname is already used!');
        END IF;
    END LOOP;
END;
/
CREATE OR REPLACE TRIGGER UpdateReports
BEFORE INSERT OR UPDATE OR DELETE OF Day ON Sales
FOR EACH ROW
DECLARE 
    numReports NUMBER := 0;
    oldReportTotal FLOAT := 0;
    newReportTotal FLOAT := 0;
    oldSaleSemester NUMBER := 0;
    newSaleSemester NUMBER := 0;
    oldSaleYear NUMBER := 0;
    newSaleYear NUMBER := 0;
BEGIN
    /* The trigger have been activated by an insert primitive */
    IF INSERTING THEN
        /* Case 1: first semester of the year */
        IF EXTRACT(Month FROM :new.Day) < 7 THEN
            SELECT COUNT(*) INTO numReports FROM Reports WHERE Semester = 1 AND Year = EXTRACT(Year FROM :new.Day); --Select Reports with these values
            /* If there are no reports in the Database, insert a new report and link it with the sale */
            IF numReports = 0 THEN
                INSERT INTO Reports VALUES ((SELECT COUNT(*) FROM Reports), 1, EXTRACT(Year FROM :new.Day), :new.Total);
            END IF;
            /* If there is one report in the database, update the total and insert the sale */
            IF numReports > 0 THEN
                SELECT R.Amount INTO oldReportTotal FROM Reports R WHERE R.Semester = 1 AND R.Year = EXTRACT(Year FROM :new.Day);
                UPDATE Reports SET Amount = oldReportTotal + :new.Total WHERE Semester = 1 AND Year = EXTRACT(Year FROM :new.Day);
            END IF;
            SELECT REF(R) INTO :new.PartOf FROM Reports R WHERE  Semester = 1 AND Year = EXTRACT(Year FROM :new.Day);
        END IF;
        IF EXTRACT(Month FROM :new.Day) > 6 THEN 
        /* Case 1: second semester of the year */
            SELECT COUNT(*) INTO numReports FROM Reports WHERE Semester = 2 AND Year = EXTRACT(Year FROM :new.Day); --Select Reports with these values
            /* If there are no reports in the Database, insert a new report and link it with the sale */
            IF numReports = 0 THEN
                INSERT INTO Reports VALUES ((SELECT COUNT(*) FROM Reports), 2, EXTRACT(Year FROM :new.Day), :new.Total);
            END IF;
            /* If there is one report in the database, update the total and insert the sale */
            IF numReports > 0 THEN
                SELECT R.Amount INTO oldReportTotal FROM Reports R WHERE R.Semester = 2 AND R.Year = EXTRACT(Year FROM :new.Day);
                UPDATE Reports SET Amount = oldReportTotal + :new.Total WHERE Semester = 2 AND Year = EXTRACT(Year FROM :new.Day);
            END IF;
            SELECT REF(R) INTO :new.PartOf FROM Reports R WHERE  Semester = 2 AND Year = EXTRACT(Year FROM :new.Day);
        END IF;
    END IF;
    /* The trigger have been activated by an update primitive */
    IF UPDATING THEN
        /* Case 1: first semester new date */
        IF EXTRACT(Month FROM :new.Day) < 7 THEN
            newSaleSemester := 1;
        END IF;
        /* Case 2: second semester new date */
        IF EXTRACT(Month FROM :new.Day) > 6 THEN
            newSaleSemester := 2;
        END IF;
        /* Case 3: first semester old date */
        IF EXTRACT(Month FROM :old.Day) < 7 THEN
            oldSaleSemester := 1;
        END IF;
        /* Case 4: second semester old date */
        IF EXTRACT(Month FROM :old.Day) > 6 THEN
            oldSaleSemester := 2;
        END IF;
        /* Updating of the Reports' amounts */
        newSaleYear := EXTRACT(Year FROM :new.Day);
        oldSaleYear := EXTRACT(Year FROM :old.Day);
        SELECT Amount INTO oldReportTotal FROM Reports WHERE Semester = oldSaleSemester AND Year = oldSaleYear;
        SELECT Amount INTO newReportTotal FROM Reports WHERE Semester = newSaleSemester AND Year = newSaleYear;
        UPDATE Reports SET Amount = oldReportTotal - :old.Total WHERE Semester = oldSaleSemester AND Year = oldSaleYear; 
        UPDATE Reports SET Amount = newReportTotal + :old.Total WHERE Semester = newSaleSemester AND Year = newSaleYear;
        SELECT REF(R) INTO :new.PartOf FROM Reports R WHERE  Semester = newSaleSemester AND Year = newSaleYear;
    END IF;
    IF DELETING THEN
        UPDATE Reports R SET R.Amount = R.Amount - :old.Total WHERE REF(R) = :old.PartOf;
    END IF;
END;
/
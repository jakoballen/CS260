DROP TABLE AuditLog;
DROP TRIGGER AuditTrigger;
DROP SEQUENCE AuditIDSeq;

CREATE TABLE AuditLog
(AuditLogID INTEGER,
AuditDateTime DATE,
AuditOperation VARCHAR(7),
AuditTableName VARCHAR(12),
AuditUser CHAR(12),
PRIMARY KEY (AuditLogID)
);


INSERT INTO Account VALUES(867545,'savings', 256.93,'111564812' ,'SB1','11-DEC-2016', null, 'Active');

UPDATE Account
SET ACCBalance = 10000.00
WHERE ACCNumber = 867545;

DELETE FROM Account WHERE ACCNumber = 867545;

CREATE SEQUENCE AuditIDSeq
start with 1
increment by 1;

CREATE OR REPLACE TRIGGER AuditTrigger 
    AFTER UPDATE OR DELETE OR INSERT
    ON Account
    FOR EACH ROW
DECLARE
    actiontype VARCHAR(7);
    newAuditID INTEGER;
    AuditUser CHAR(12);
BEGIN
    SELECT COUNT(*) INTO newAuditID FROM AuditLog;
    
    SELECT USER INTO AuditUser FROM DUAL;
        
    actiontype := CASE
        WHEN UPDATING THEN 'UPDATE'
        WHEN DELETING THEN 'DELETE'
        WHEN INSERTING THEN 'INSERT'
    END;
    
    INSERT INTO AuditLog VALUES (AuditIDSeq.nextval, SYSDATE, actiontype, 'Accounts', AuditUser);
        

END AuditTrigger;






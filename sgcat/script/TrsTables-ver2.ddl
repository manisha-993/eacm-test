--
-- TRS TABLES INTO GBLI SCHEMA FOR 0184
--

------------------------------------------------
-- DDL STATEMENTS FOR COLLECTION CATEGORY
------------------------------------------------
-- DDL STATEMENTS FOR TABLE "TRSNETTERPASS1"
------------------------------------------------
 CREATE SCHEMA "VECOLD" 
 CREATE TABLE "TRSNETTERPASS1"  (
                  "MARKER" INTEGER NOT NULL , 
                  "ENTERPRISE" CHAR(8) NOT NULL , 
                  "SESSIONID" INTEGER NOT NULL , 
                  "TRAN" CHAR(3) NOT NULL , 
                  "LEVEL" INTEGER NOT NULL , 
                  "ENTITY1TYPE" CHAR(32) NOT NULL , 
                  "ENTITY1ID" INTEGER NOT NULL , 
                  "ENTITYTYPE" CHAR(32) NOT NULL , 
                  "ENTITYID" INTEGER NOT NULL , 
                  "ENTITY2TYPE" CHAR(32) NOT NULL , 
                  "ENTITY2ID" INTEGER NOT NULL , 
                  "DIRECTION" CHAR(1) NOT NULL , 
                  "CORETYPE" CHAR(32) NOT NULL , 
                  "COREID" INTEGER NOT NULL , 
                  "CORELEVEL" INTEGER NOT NULL , 
                  "CORETRAN" CHAR(3) NOT NULL , 
                  "COREDIRECTION" CHAR(1) NOT NULL , 
                  "TYPE" CHAR(2) NOT NULL , 
                  "VALFROM" TIMESTAMP NOT NULL , 
                  "COREPATH" VARCHAR(254) NOT NULL ) 
                  IN "TSPACE08" INDEX IN "ISPACE08" 
                 
-- DDL STATEMENTS FOR INDEXES ON TABLE "TRSNETTERPASS1"
CREATE INDEX "TRSNETCATP1_IX1" ON "TRSNETTERPASS1" 
                ("SESSIONID" ASC,
                 "TYPE" ASC,
                 "MARKER" ASC,
                 "ENTERPRISE" ASC)

-- DDL STATEMENTS FOR INDEXES ON TABLE "TRSNETTERPASS1"
CREATE INDEX "TRSNETCATP1_IX2" ON "TRSNETTERPASS1" 
                ("SESSIONID" ASC,
                 "TRAN" ASC,
                 "MARKER" ASC,
                 "ENTERPRISE" ASC,
                 "ENTITYID" ASC,
                 "ENTITYTYPE" ASC,
                 "CORETYPE" ASC)

-- DDL STATEMENTS FOR INDEXES ON TABLE "TRSNETTERPASS1"
CREATE INDEX "TRSNETCATP1_IX3" ON "TRSNETTERPASS1" 
                ("SESSIONID" ASC,
                 "LEVEL" ASC,
                 "ENTITY2TYPE" ASC,
                 "ENTITY2ID" ASC)
                CLUSTER

-- DDL STATEMENTS FOR INDEXES ON TABLE "TRSNETTERPASS1"
CREATE INDEX "TRSNETCATP1_IX4" ON "TRSNETTERPASS1" 
                ("SESSIONID" ASC,
                 "LEVEL" ASC,
                 "ENTITYTYPE" ASC,
                 "ENTITYID" ASC)

-- DDL STATEMENTS FOR INDEXES ON TABLE "TRSNETTERPASS1"
CREATE INDEX "TRSNETCATP1_IX5" ON "TRSNETTERPASS1" 
                ("SESSIONID" ASC,
                 "LEVEL" ASC,
                 "ENTITY1TYPE" ASC,
                 "ENTITY1ID" ASC)

-- DDL STATEMENTS FOR INDEXES ON TABLE "TRSNETTERPASS1"
CREATE INDEX "TRSNETCATP1_IX6" ON "TRSNETTERPASS1" 
                ("SESSIONID" ASC,
                 "COREID" ASC,
                 "CORETYPE" ASC,
                 "MARKER" ASC,
                 "TYPE" ASC,
                 "ENTERPRISE" ASC)

-- DDL STATEMENTS FOR INDEXES ON TABLE "TRSNETTERPASS1"
CREATE INDEX "TRSNETCATP1_IX7" ON "TRSNETTERPASS1" 
                ("SESSIONID" ASC,
                 "TRAN" ASC,
                 "MARKER" ASC,
                 "VALFROM" ASC,
                 "ENTERPRISE" ASC,
                 "ENTITYID" ASC,
                 "ENTITYTYPE" ASC,
                 "CORETYPE" ASC)

------------------------------------------------
-- DDL STATEMENTS FOR TABLE "TRSNETTERPASS2"
------------------------------------------------
 
 CREATE TABLE "TRSNETTERPASS2"  (
                  "ENTERPRISE" CHAR(8) NOT NULL , 
                  "SESSIONID" INTEGER NOT NULL , 
                  "ACTIONTYPE" CHAR(32) NOT NULL , 
                  "LEVEL" INTEGER NOT NULL , 
                  "MYDIRECTION" CHAR(1) NOT NULL , 
                  "PARENTDIRECTION" CHAR(1) NOT NULL , 
                  "ENTITY1TYPE" CHAR(32) NOT NULL , 
                  "ENTITY1ID" INTEGER NOT NULL , 
                  "ENTITYTYPE" CHAR(32) NOT NULL , 
                  "ENTITYID" INTEGER NOT NULL , 
                  "ENTITY2TYPE" CHAR(32) NOT NULL , 
                  "ENTITY2ID" INTEGER NOT NULL , 
                  "TRAN" CHAR(3) NOT NULL , 
                  "VALFROM" TIMESTAMP NOT NULL , 
                  "ROOTTYPE" CHAR(32) NOT NULL , 
                  "ROOTID" INTEGER NOT NULL , 
                  "ROOTTRAN" CHAR(3) NOT NULL , 
                  "PATH" VARCHAR(254) NOT NULL )   
                 IN "TSPACE08" INDEX IN "ISPACE08"
                 
-- DDL STATEMENTS FOR INDEXES ON TABLE "TRSNETTERPASS2"
CREATE INDEX "TRSNETCATP2_IX1" ON "TRSNETTERPASS2" 
                ("SESSIONID" ASC,
                 "LEVEL" ASC,
                 "ENTITY1TYPE" ASC,
                 "ENTITY1ID" ASC)

-- DDL STATEMENTS FOR INDEXES ON TABLE "TRSNETTERPASS2"
CREATE INDEX "TRSNETCATP2_IX2" ON "TRSNETTERPASS2" 
                ("SESSIONID" ASC,
                 "LEVEL" ASC,
                 "ENTITY2TYPE" ASC,
                 "ENTITY2ID" ASC)

-- DDL STATEMENTS FOR INDEXES ON TABLE "TRSNETTERPASS2"
CREATE INDEX "TRSNETCATP2_IX3" ON "TRSNETTERPASS2" 
                ("SESSIONID" ASC,
                 "LEVEL" ASC,
                 "ENTITYTYPE" ASC,
                 "ENTITYID" ASC)
                CLUSTER 
------------------------------------------------
-- DDL STATEMENTS FOR TABLE "TRSNETTERATTS"
------------------------------------------------
 
 CREATE TABLE "TRSNETTERATTS"  (
                  "SESSIONID" INTEGER NOT NULL , 
                  "ENTITYID" INTEGER NOT NULL , 
                  "ENTITYTYPE" CHAR(32) NOT NULL , 
                  "ENTERPRISE" CHAR(16) NOT NULL )   
                 IN "TSPACE08" INDEX IN "ISPACE08" 
                 
-- DDL STATEMENTS FOR INDEXES ON TABLE "TRSNETTERATTS"
CREATE INDEX "TRSNETCATA_IX1" ON "TRSNETTERATTS" 
                ("ENTITYTYPE" ASC,
                 "SESSIONID" ASC,
                 "ENTERPRISE" ASC)

-- DDL STATEMENTS FOR INDEXES ON TABLE "TRSNETTERATTS"
CREATE UNIQUE INDEX "TRSNETCATA_PK" ON "TRSNETTERATTS" 
                ("ENTITYID" ASC,
                 "ENTITYTYPE" ASC,
                 "SESSIONID" ASC,
                 "ENTERPRISE" ASC)
                CLUSTER 

------------------------------------------------
-- DDL STATEMENTS FOR TABLE "TRSNETTERFINAL"
------------------------------------------------
 
 CREATE TABLE "TRSNETTERFINAL"  (
                  "ENTERPRISE" CHAR(8) NOT NULL , 
                  "SESSIONID" INTEGER NOT NULL , 
                  "ACTIONTYPE" CHAR(32) NOT NULL , 
                  "STARTDATE" TIMESTAMP NOT NULL , 
                  "ENDDATE" TIMESTAMP NOT NULL , 
                  "VALFROM" TIMESTAMP NOT NULL , 
                  "ROOTTYPE" CHAR(32) NOT NULL , 
                  "ROOTID" INTEGER NOT NULL , 
                  "ROOTTRAN" CHAR(3) NOT NULL , 
                  "CHILDTYPE" CHAR(32) NOT NULL , 
                  "CHILDID" INTEGER NOT NULL , 
                  "CHILDTRAN" CHAR(3) NOT NULL , 
                  "CHILDLEVEL" INTEGER NOT NULL , 
                  "CHILDCLASS" CHAR(1) NOT NULL , 
                  "CHILDPATH" VARCHAR(254) NOT NULL , 
                  "ENTITY1TYPE" CHAR(32) NOT NULL , 
                  "ENTITY1ID" INTEGER NOT NULL , 
                  "ENTITY2TYPE" CHAR(32) NOT NULL , 
                  "ENTITY2ID" INTEGER NOT NULL )   
                 IN "TSPACE08" INDEX IN "ISPACE08"
                 
-- DDL STATEMENTS FOR INDEXES ON TABLE "TRSNETTERFINAL"
CREATE INDEX "TRSNETCATF_IX1" ON "TRSNETTERFINAL" 
                ("SESSIONID" ASC,
                 "ROOTID" ASC,
                 "ROOTTYPE" ASC,
                 "ENTERPRISE" ASC)

-- DDL STATEMENTS FOR INDEXES ON TABLE "TRSNETTERFINAL"
CREATE INDEX "TRSNETCATF_IX2" ON "TRSNETTERFINAL" 
                ("SESSIONID" ASC,
                 "CHILDID" ASC,
                 "CHILDTYPE" ASC,
                 "ENTERPRISE" ASC);            
                 
COMMIT WORK;

CONNECT RESET;
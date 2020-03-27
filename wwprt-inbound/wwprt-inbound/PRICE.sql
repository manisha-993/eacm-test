CREATE TABLE "PRICE   "."PRICE"  (
                  "MACHTYPEATR" VARCHAR(4) ,
                  "MODELATR" VARCHAR(3) ,
                  "FEATURECODE" VARCHAR(6) ,
                  "PARTNUM" VARCHAR(7) ,
                  "FROM_MACHTYPEATR" VARCHAR(4) ,
                  "FROM_MODELATR" VARCHAR(3) ,
                  "FROM_FEATURECODE" VARCHAR(6) ,
                  "OFFERING_TYPE" VARCHAR(18) ,
                  "OFFERING" VARCHAR(20) NOT NULL ,
                  "PRICE_POINT_TYPE" VARCHAR(5) NOT NULL ,
                  "PRICE_POINT_VALUE" VARCHAR(20) NOT NULL ,
                  "PRICE_TYPE" VARCHAR(3) NOT NULL ,
                  "COUNTRY" VARCHAR(2) NOT NULL ,
                  "ONSHORE" VARCHAR(5) NOT NULL ,
                  "START_DATE" DATE ,
                  "END_DATE" DATE NOT NULL ,
                  "PRICE_VALUE" VARCHAR(24),
                  "PRICE_VALUE_USD" VARCHAR(24),
                  "CURRENCY" VARCHAR(3) ,
                  "FACTOR" VARCHAR(24) ,
                  "ID" VARCHAR(10) NOT NULL ,
                  "ACTION" CHAR(1) NOT NULL ,
                  "INSERT_TS" TIMESTAMP NOT NULL ,
                  "RELEASE_TS" TIMESTAMP ,
                  "CABLETYPE" VARCHAR(3) ,
                  "CABLEID" VARCHAR(31) ,
                  "PRICEXML" CLOB(2048) LOGGED NOT COMPACT NOT NULL WITH DEFAULT
 '' )
                 IN "TSPACE01" ;


-- DDL Statements for primary key on Table "PRICE   "."PRICE"

CREATE UNIQUE INDEX "PRICE"."PRC_IND1" ON "PRICE"."PRICE"
                ("OFFERING",
                 "PRICE_POINT_TYPE",
                 "PRICE_POINT_VALUE",
                 "PRICE_TYPE",
                 "COUNTRY",
                 "ONSHORE",
                 "END_DATE",
                 "ACTION")
                 IN ISPACE01 DISALLOW REVERSE SCANS;
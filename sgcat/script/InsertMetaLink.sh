db2 connect to PDHALIAS user OPICMADM using cat9tail
db2 "insert into opicm.metalinkattr values ('SG','Action/Entity','TMPS1','PRODSTRUCT','U','0','1980-01-01 00:00:00.0',timestamp('9999-12-31 00:00:00.0'),timestamp('1980-01-01 00:00:00.0'),timestamp('9999-12-31 00:00:00.0'),7,7)"
db2 "insert into opicm.metalinkattr values ('SG','Action/Entity','TMPS1','SWPRODSTRUCT','U','0','1980-01-01 00:00:00.0',timestamp('9999-12-31 00:00:00.0'),timestamp('1980-01-01 00:00:00.0'),timestamp('9999-12-31 00:00:00.0'),-9,-9)"
db2 "insert into opicm.metalinkattr values ('SG','Action/Entity','LSEOPS1','LSEOPRODSTRUCT','D','0','1980-01-01 00:00:00.0',timestamp('9999-12-31 00:00:00.0'),timestamp('1980-01-01 00:00:00.0'),timestamp('9999-12-31 00:00:00.0'),-9,-9)"
db2 "insert into opicm.metalinkattr values ('SG','Action/Entity','WWSEOPS1','WWSEOPRODSTRUCT','D','0','1980-01-01 00:00:00.0',timestamp('9999-12-31 00:00:00.0'),timestamp('1980-01-01 00:00:00.0'),timestamp('9999-12-31 00:00:00.0'),7,7)"
db2 "insert into opicm.metalinkattr values ('SG','Action/Entity','WWSEOPS1','WWSEOSWPRODSTRUCT','D','0','1980-01-01 00:00:00.0',timestamp('9999-12-31 00:00:00.0'),timestamp('1980-01-01 00:00:00.0'),timestamp('9999-12-31 00:00:00.0'),-9,-9)"
db2 "insert into opicm.metalinkattr values ('SG','Action/Entity','LSEOBPS1','LSEOBUNDLELSEO','D','0','1980-01-01 00:00:00.0',timestamp('9999-12-31 00:00:00.0'),timestamp('1980-01-01 00:00:00.0'),timestamp('9999-12-31 00:00:00.0'),-9,-9)"
db2 "insert into opicm.metalinkattr values ('SG','Action/Entity','LCTOPS1','PRODSTRUCT','U','0','1980-01-01 00:00:00.0',timestamp('9999-12-31 00:00:00.0'),timestamp('1980-01-01 00:00:00.0'),timestamp('9999-12-31 00:00:00.0'),-9,-9)"
db2 "insert into opicm.metalinkattr values ('SG','Action/Entity','LCTOPS1','SWPRODSTRUCT','U','0','1980-01-01 00:00:00.0',timestamp('9999-12-31 00:00:00.0'),timestamp('1980-01-01 00:00:00.0'),timestamp('9999-12-31 00:00:00.0'),-9,-9)"
db2 "insert into opicm.metalinkattr values ('SG','Action/Entity','LSEOPS1','LSEOSWPRODSTRUCT','D','0','1980-01-01 00:00:00.0',timestamp('9999-12-31 00:00:00.0'),timestamp('1980-01-01 00:00:00.0'),timestamp('9999-12-31 00:00:00.0'),-9,-9)"
db2 "insert into opicm.metalinkattr values ('SG','Action/Entity','LCTOPS1','OOFAVAIL','D','1','1980-01-01 00:00:00.0',timestamp('9999-12-31 00:00:00.0'),timestamp('1980-01-01 00:00:00.0'),timestamp('9999-12-31 00:00:00.0'),-9,-9)"
db2 "import from ./NewVE.txt of del insert into gbli.catscan"
db2 "import from ./MetaEntity.txt of del insert into opicm.METAENTITY"
db2 "import from ./MetaDesc.txt of del insert into opicm.METADESCRIPTION"
db2 commit
db2 terminate
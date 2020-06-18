##
## RUN SOFTWARE reorg runstats here
##
## $Log: eccm.software.sql,v $
## Revision 1.4  2004/02/05 19:27:59  dave
## more minor changes
##
## Revision 1.3  2003/12/15 19:20:43  dave
## more ECCM changes
##
## Revision 1.2  2003/12/11 06:37:48  dave
## more updates to the scripts
##
## Revision 1.1  2003/12/10 20:34:54  dave
## updating and making final scripts
##


db2 connect to eccmods user opicmadm using cat9tail;

db2 create index ECCM.software_ix1 on ECCM.software(category_fc,entitytype);
echo db2 reorg table ECCM.software index ECCM.software_PK;
db2 reorg table ECCM.software index ECCM.software_PK;
db2 runstats on table ECCM.software with distributeion and detailed indexes all;

db2 terminate all;

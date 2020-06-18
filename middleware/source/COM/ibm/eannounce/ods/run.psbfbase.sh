#
# $Log: run.psbfbase.sh,v $
# Revision 1.3  2005/03/17 19:11:14  dave
# new e-Announce catalog
#
# Revision 1.1.2.2  2004/03/24 00:57:25  dave
# final changes for the delete timing problem in the ECCMODS
#
# Revision 1.1.2.1  2004/03/12 00:09:45  dave
# script to implment the psbfbase.sh
#
#
#
WHO_AM_I=`whoami`

#
# this tells the DB2 client that we want NO codepage conversion from database (1208 = UTF8)
#
export DB2CODEPAGE=1208
export RPTNAME='psbfbase.out'

#
# here is the script that does the updating
#
db2batch -d $1 -a $2/$3 -f ./eccm.psbfbase.sql > $RPTNAME

# Now. lets finish it up with a permissions thing
#
db2batch -d $1 -a $2/$3 -f ./eccm.misc.sql >> $RPTNAME


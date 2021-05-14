db2 connect to pdhalias user opicmadm using cat9tail
for fix in CATCOL COLLCOL COMPCOL FEATCOL PRODCOL WWPRODCOL WWPCCOL PSCOL WWPSCOL
do 
   echo "fix is :"$fix
   sed "s/VECOLD/$fix/" TrsTables-ver2.ddl > TrsTables-VECOL$fix.ddl
   echo db2 -tvf TrsTables-VECOL$fix.ddl
   db2 -tvf TrsTables-VECOL$fix.ddl
   rm TrsTables-VECOL$fix.ddl
done
db2 terminate
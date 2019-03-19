@echo on

echo "started"

echo "param started loading"
echo %1
echo %2
echo %3

echo "param loaded"
 %3:
cd %2

mvn -pl . clean install

echo "completed"

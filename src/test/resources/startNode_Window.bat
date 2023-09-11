set ProjectPath=%~dp0
java -jar webdrivermanager-5.3.2-fat.jar resolveDriverFor chrome 
java -jar webdrivermanager-5.3.2-fat.jar resolveDriverFor firefox 
java -jar webdrivermanager-5.3.2-fat.jar resolveDriverFor edge 
java -jar webdrivermanager-5.3.2-fat.jar resolveDriverFor opera
java -jar -Dwebdriver.chrome.driver="%ProjectPath%\chromedriver.exe" -Dwebdriver.gecko.driver="%ProjectPath%\geckodriver.exe" selenium-server-standalone-3.141.59.jar -role webdriver -hub http://HUB_IP:HUB_PORT//grid/register -port NODE_PORT
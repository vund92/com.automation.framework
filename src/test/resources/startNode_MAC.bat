#!/bin/sh
project_path="/Volumes/Code/grid"
cd "$project_path"
java -jar -Dwebdriver.gecko.driver="$project_path/browserDrivers/geckodriver"
-Dwebdriver.chrome.driver="$project_path/browserDrivers/chromedriver" 
$project_path/libraries/selenium-server-standalone-3.141.59.jar -role webdriver -hub
http://192.168.1.15:4444/grid/register -port 5555
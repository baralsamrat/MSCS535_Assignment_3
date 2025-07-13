#!/bin/bash

# Set variables
TOMCAT_HOME="/c/Program Files/Apache Software Foundation/Tomcat 9.0"
WEBAPP_NAME="Assignment3"
SRC_DIR="/c/Users/samra/Documents/GitHub/MSCS535_Assignment_3/src/WEB-INF/classes"
WEB_XML_SRC="/c/Users/samra/Documents/GitHub/MSCS535_Assignment_3/src/WEB-INF/web.xml"
WEBAPP_DIR="$TOMCAT_HOME/webapps/$WEBAPP_NAME"
CLASSES_DIR="$WEBAPP_DIR/WEB-INF/classes"
WEB_XML_DEST="$WEBAPP_DIR/WEB-INF/web.xml"
SERVLET_API_JAR="$TOMCAT_HOME/lib/servlet-api.jar"

# 1. Compile Java files (fixed classpath separator)
javac -cp ".:$SERVLET_API_JAR" "$SRC_DIR/InputSanitizationFilter.java" "$SRC_DIR/TestServlet.java"
if [ $? -ne 0 ]; then
    echo "Compilation failed! Please check your Java files."
    exit 1
fi

# 2. Create destination directories if they don't exist
mkdir -p "$CLASSES_DIR"

# 3. Copy compiled classes (including inner classes)
cp "$SRC_DIR"/InputSanitizationFilter*.class "$CLASSES_DIR/"
cp "$SRC_DIR"/TestServlet*.class "$CLASSES_DIR/"

# 4. Copy web.xml
mkdir -p "$WEBAPP_DIR/WEB-INF"
cp "$WEB_XML_SRC" "$WEB_XML_DEST"

# 5. Restart Tomcat (Windows)
cd "$TOMCAT_HOME/bin"
./shutdown.bat
sleep 3
./startup.bat

echo "Deployment complete. Visit http://localhost:8080/$WEBAPP_NAME/test?input=%3Cscript%3Ealert(1)%3C/script%3E"

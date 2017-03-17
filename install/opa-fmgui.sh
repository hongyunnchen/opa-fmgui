#!/bin/sh

# Copyright (c) 2015, Intel Corporation
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are met:
#
#     * Redistributions of source code must retain the above copyright notice,
#       this list of conditions and the following disclaimer.
#     * Redistributions in binary form must reproduce the above copyright
#       notice, this list of conditions and the following disclaimer in the
#       documentation and/or other materials provided with the distribution.
#     * Neither the name of Intel Corporation nor the names of its contributors
#       may be used to endorse or promote products derived from this software
#       without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
# AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
# IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
# DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
# FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
# DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
# SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
# CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
# OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
# OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


#
# $Id$
#
# Runs the Intel Omni-Path Architecture Fabric Manager Graphical User Interface.
#

checkJava() {
if [ $DEBUG -eq 1 ]; then 
    echo Checking JVM $2
fi
JVER=`$2 -version 2>&1 | grep "version" | awk '{print substr($3, 2, 3);}' | sed -e 's;\.;0;g'`
if [ $JVER ]; then
    if [ $JVER -ge $JAVA_REQ ]; then
        if [ $DEBUG -eq 1 ]; then
           echo Found suitable Java: $2
        fi
        eval "$1=$2"
    fi
fi
}

DEBUG=0
JAVA_REQ=`echo 1.7 | sed -e 's;\.;0;g'`
INSTDIR=`dirname $0`
JAVA=nojava
JAR=/usr/share/java/opa-fmgui/opa-fmgui.jar
LIB=.:/usr/share/java/opa-fmgui/lib/*:/usr/share/java/opa-fmgui/gritty/*:/usr/share/java/opa-fmgui:/usr/share/java/opa-fmgui/help/*
MAIN_CLASS=com.intel.stl.main.FMGuiApp

if [ -n "$OPA_JAVA" ]; then
    # Check what's been provided in environment variable OPA_JAVA
    checkJava JAVA $OPA_JAVA/bin/java
fi
if [ "$JAVA" == "nojava" ]; then
    # No JVM found yet, try JAVA_HOME
    if [ -n "$JAVA_HOME" ]; then
        checkJava JAVA $JAVA_HOME/bin/java
    fi
fi
if [ "$JAVA" == "nojava" ]; then
    # Either JAVA_HOME was not defined or the specified JVM is not suitable. Try the PATH 
    checkJava JAVA "java"
fi
if [ "$JAVA" == "nojava" ]; then
    # JVM is not defined in the PATH or is not suitable. Attempt to find one in file system
    for JAVA_EXE in `locate bin/java | grep java$ | xargs echo`
    do
        checkJava JAVA $JAVA_EXE
        if [ "$JAVA" != "nojava" ]; then
           break 
        fi
    done
fi
if [ "$JAVA" == "nojava" ]; then
    echo "Java version 1.7 or higher is required to run application Fabric Manager GUI"
    echo "Please, download Java version 1.7 or higher from Java's download site:"
    echo ""
    echo "                http://www.java.com/en/download/manual.jsp"
    echo ""
    echo "and install it in your system. The installer adds Java 1.7 to your PATH.  If"
    echo "you do not want to override your default Java version, leave your PATH as it"
    echo "is and set the environment variable OPA_JAVA to the installation location of"
    echo "the Java 1.7 runtime. To do this, add the following line:"
    echo ""
    echo "                export OPA_JAVA=<jre7_install_location>"
    echo ""
    echo "to either /etc/bashrc for system-wide availability (root access required) or"
    echo "to ~/.bashrc for user scope availability. Then rerun this command."
    echo ""
    read -p 'Press [Enter] key to continue...'
else
    if [ -a $JAR ]; then
        # run the JVM and pass the jar name
        if [ $DEBUG -eq 1 ]; then 
            echo
            echo exec java -cp ${JAR}:${LIB} $MAIN_CLASS
        fi
        exec java -cp ${JAR}:${LIB} $MAIN_CLASS
    else
        echo "Cannot find the Fabric Manager GUI application jar file $JAR"
        echo ""
        read -p 'Press [Enter] key to continue...'
    fi
fi

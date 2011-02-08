#!/bin/bash
#
set -e

# home directory of resin application server
export RESIN_HOME=/home/ssolovyev/servers/resin-3.1.10

# home directory of JRE
export JAVA_HOME=/usr/lib/jvm/java-6-sun

# port listened by application
export APP_PORT=8080

# project root (directory where source and configuration files are located)
export APP_PROJECT_ROOT=/home/ssolovyev/projects/org.solovyev/study

# application home (directory where logs, out, compiled jsps, etc are located)
export APP_HOME_ROOT=/home/ssolovyev/applications/web/study

# application home (directory where logs, out, compiled jsps, etc are located)
export APP_HOME_ROOT=/home/ssolovyev/applications/web/study

# pid of resin
export $APP_RESIN_PID=resin_study_01

$APP_PROJECT_ROOT/study/study-misc/misc/conf/resin.sh $1

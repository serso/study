#!/bin/bash
#
set -e

# Next environment variables has to be set:
# 1. RESIN_HOME - home directory of resin application server
# 2. JAVA_HOME - home directory of JRE
# 3. APP_PORT - port listened by application
# 4. APP_PROJECT_ROOT - project root (directory where source files are located)
# 5. APP_HOME_ROOT - application home (directory where logs, out, compiled jsps, etc are located)
# 6. APP_RESIN_PID


start() {
        echo -n "Starting Resin: "
        $RESIN_HOME/bin/httpd.sh \
	-J-ea:org.solovyev... \
	-J-Dfile.encoding=UTF8 \
	-Xmn100M \
	-Xms200M \
	-Xmx500M \
	-XX:MaxPermSize=256m \
	-Xshare:off \
	-Xint \
	-Xdebug \
	-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 \
	-Dcom.sun.management.jmxremote=true \
	-Dcom.sun.management.jmxremote.port=1025 \
	-Dcom.sun.management.jmxremote.authenticate=false \
	-Dcom.sun.management.jmxremote.ssl=false \
	\
	-Dapp.server.port=$APP_PORT \
	-Dapp.project.root=$APP_PROJECT_ROOT \
	-Dresin.home=$RESIN_HOME \
	-Dapp.home.root=$APP_HOME_ROOT \
	-Dfile.prefix.for.log4j=file:// \
	\
	-pid $APP_RESIN_PID \
	\
	start -conf  $APP_PROJECT_ROOT/study/study-misc/misc/conf/resin-3.1.conf > /dev/null

    echo "[DONE]"
}

stop() {
        echo -n "Shutting down Resin: "
        $RESIN_HOME/bin/httpd.sh -pid $APP_RESIN_PID stop  > /dev/null
        echo "[DONE]"
}

# echo RESIN_HOME=$RESIN_HOME
# echo JAVA_HOME=$JAVA_HOME
# echo DB_USERNAME=$DB_USERNAME
# echo DB_PASSWORD=$DB_PASSWORD
# echo DB_URL=$DB_URL
# echo GLIBS_PORT=$GLIBS_PORT
# echo GLIBS_PROJECT_ROOT=$GLIBS_PROJECT_ROOT
# echo GLIBS_APP_HOME=$GLIBS_APP_HOME

# echo ACTION=$1

if [ -n "$1" ]; then
	case "$1" in
	  start)
		start
		;;
	  stop)
		stop
		;;
	  restart)
		stop
		sleep 5
		start
		;;
	  *)
		echo "Usage: $0 {start|stop|restart}"
		exit 1
		;;
	esac
else
		echo "Usage: $0 {start|stop|restart}"
		exit 1
fi

exit 0
@echo off
rem ======================================================================
rem windows startup script
rem
rem author: missionary
rem date: 2018-12-2
rem ======================================================================

rem Open in a browser
rem startup jar
echo Please check log file for more information
java -jar ../boot/aboot-system-0.0.4.jar --spring.config.location=../config/ &
pause
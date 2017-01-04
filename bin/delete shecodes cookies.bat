

@echo off
setlocal

for /f "tokens=2 delims=," %%I in (
    'wmic process where "name='chrome.exe'" get ExecutablePath^,Handle /format:csv ^| find /i "chrome.exe"'
) do set "exepath=%%~I"

@taskkill /f /t /im chrome.exe
@echo on
java -jar DeleteCromeCookies.jar shecodesconnect.com MoodleSession
start /b cmd /c "%exepath%"

pause
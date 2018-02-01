call runcrud.bat
if "%ERRORLEVEL%" == "0" goto start_browser
echo There were problems with deploying the application. Browser will not be started.
goto fail

:start_browser
start chrome http://localhost:8080/crud/v1/task
if "%ERRORLEVEL%" == "0" goto end
echo There were problems with starting chrome browser

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.

@echo off
for /f "delims=" %%f in ('dir/b/s/a-d *.jpg') do (if not "%%~nxf"=="%0" ren "%%f" "sample_%%~nxf")
pause
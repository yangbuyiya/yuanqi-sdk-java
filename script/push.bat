@echo off

cd ../

@REM echo Pull remote branch [master]
@REM call git checkout dev
@REM
@REM call git fetch github master:master
@REM
@REM call git fetch gitee master:master
@REM
@REM echo Pull remote branch [dev]
@REM call git checkout master
@REM
@REM call git fetch github dev:dev
@REM
@REM call git fetch gitee dev:dev


echo Push to gitee

call git push origin dev

call git push origin master

echo Push to github
call git push github dev

call git push github master

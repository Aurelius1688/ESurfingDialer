@echo off
title 校园网防检测程序
setlocal enabledelayedexpansion

:: 配置Java路径（如果环境变量有问题可手动指定）
set JAVA_CMD=java
:: set JAVA_CMD="D:\下载\microsoft-jdk-21.0.4-windows-x64\jdk-21.0.4+7\bin\java.exe"

:: 客户端参数配置（请输入校园网的账号、密码）
set USERNAME=改为校园网账户
set PASSWORD=改为校园网密码

:loop
echo [%time%] 防检测程序正在运行中...请勿点击关闭！
%JAVA_CMD% -jar network.jar -u %USERNAME% -p %PASSWORD% >> 检测日志.log 2>&1
echo [%time%] 客户端意外退出，10秒后自动重启...
timeout /t 10 /nobreak > nul
goto loop
@echo off
title У԰����������
setlocal enabledelayedexpansion

:: ����Java·�����������������������ֶ�ָ����
set JAVA_CMD=java
:: set JAVA_CMD="D:\����\microsoft-jdk-21.0.4-windows-x64\jdk-21.0.4+7\bin\java.exe"

:: �ͻ��˲������ã�������У԰�����˺š����룩
set USERNAME=��ΪУ԰���˻�
set PASSWORD=��ΪУ԰������

:loop
echo [%time%] ������������������...�������رգ�
%JAVA_CMD% -jar network.jar -u %USERNAME% -p %PASSWORD% >> �����־.log 2>&1
echo [%time%] �ͻ��������˳���10����Զ�����...
timeout /t 10 /nobreak > nul
goto loop
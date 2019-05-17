@echo off
REM Starts server, client 1 and client 2.
REM Oishik M | 17-18 May 2019
cd class
start cmd /k java Server 3033 3034
start cmd /k java Client 1 3033
start cmd /k java Client 2 3034


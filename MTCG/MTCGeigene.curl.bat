@echo off

REM --------------------------------------------------
REM Monster Trading Cards Game
REM --------------------------------------------------
title Monster Trading Cards Game
echo CURL Testing for Monster Trading Cards Game
echo.

REM --------------------------------------------------
echo 10) show unconfigured deck
curl -X GET http://localhost:10001/deck --header "Authorization: Bearer kienboec-mtcgToken"
echo f05f15b4-6cdb-4a24-853a-9cbf603cef4b
echo.
curl -X GET http://localhost:10001/deck --header "Authorization: Bearer altenhof-mtcgToken"
echo 26118bd7-40c2-4aa2-8e6d-2acb95341eb9
echo.

REM --------------------------------------------------
echo 11) configure deck
curl -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d "[\"4a2757d6-b1c3-47ac-b9a3-91deab093531\", \"65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5\", \"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"d04b736a-e874-4137-b191-638e0ff3b4e7\"]"
echo.
curl -X GET http://localhost:10001/deck --header "Authorization: Bearer kienboec-mtcgToken"
echo.
curl -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d "[\"02a9c76e-b17d-427f-9240-2dd49b0d3bfd\", \"2c98cd06-518b-464c-b911-8d787216cddd\", \"74635fae-8ad3-4295-9139-320ab89c2844\", \"84d276ee-21ec-4171-a509-c1b88162831c\"]"
echo.
curl -X GET http://localhost:10001/deck --header "Authorization: Bearer altenhof-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 12) show configured deck 
curl -X GET http://localhost:10001/deck --header "Authorization: Bearer kienboec-mtcgToken"
echo.
curl -X GET http://localhost:10001/deck --header "Authorization: Bearer altenhof-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 17) battle
start /b "kienboec battle" curl -X POST http://localhost:10001/battles --header "Authorization: Bearer kienboec-mtcgToken"
start /b "altenhof battle" curl -X POST http://localhost:10001/battles --header "Authorization: Bearer altenhof-mtcgToken"
ping localhost -n 10 >NUL 2>NUL

REM --------------------------------------------------
echo 18) Stats 
echo kienboec
curl -X GET http://localhost:10001/stats --header "Authorization: Bearer kienboec-mtcgToken"
echo.
echo altenhof
curl -X GET http://localhost:10001/stats --header "Authorization: Bearer altenhof-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo end...

REM this is approx a sleep 
ping localhost -n 100 >NUL 2>NUL
@echo on

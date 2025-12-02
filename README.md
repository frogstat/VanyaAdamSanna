# Vanya, Adam, och Sanna's tärningsspel.
Inlämning fär JAVA25

Detta projekt är vår försök att återskapa tärningspelet Farkle från ett datorspel som heter Kingdom Come Deliverence.
Vissa skillnader finns, men grundreglerna är samma.

Instruktioner:
-Klona detta repo
För Windows:
  kör package_and_run.bat
För Linux:
  kör package_and_run.sh
Det finns även package och run som separata skriptfiler.

OBS:
Spelet fungerar med WSL, men spelet har en ljudfunktion som fungerar endast på ett äkta Linuxsystem eller Windows.
Därför bör du använda .bat filerna om du är på Windows, istället för WSL eller MINGW64.

.bat filerna innehåller chcp 65001 vilket låter Windows terminalen att visa UTF-8 symboler som spelet använder.

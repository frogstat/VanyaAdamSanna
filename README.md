# Vanya, Adam, och Sanna's tärningsspel.

Detta projekt är vår försök att återskapa tärningspelet Farkle från ett datorspel som heter Kingdom Come: Deliverence.

Vissa skillnader finns, men grundreglerna är samma.

Använder MAVEN.

Instruktioner
---

Klona detta repo

För Windows:

&nbsp; kör package_and_run.bat

För Linux:

&nbsp; kör package_and_run.sh

Det finns även package och run som separata skriptfiler.

Obs
---
Spelet fungerar med WSL, men spelet har en ljudfunktion som inte går ihop bra med WSL.

Om du är på Windows och vill ha ljud, så bör du använda .bat filen.

.bat filen innehåller kommandot "chcp 65001" vilket låter Windows terminalen att visa UTF-8 symboler som spelet använder.

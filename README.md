# Weather-Simulation-Tool
Application to predict the fake weather data of a location.
Fetches location details from a locations.txt file and predict the output based on longitude,latitude and elevation.

## Algorithm
The application takes a location.txt file as input, wherein the latitude,longityde and elevation of a location is captured in json format. The format is :

### Temperature, Pressure and Relative Humidity Algorithm
For each location, temperature index values based on latitude & elevation is calculated, based on the below assumption:
1. The temperature is minimum in Polar (MAX_LAT) and maximum at the equator (MIN_LAT), and the index of same is calculated by normalisation.
2. Similar calaculation is done on the basis of elevation.
3. Average of the temperature index is multiplied with a standard maximum global temperature to fetch the temperature of a location.
4. For calculating pressure & humidity, the universal formula's are applied.

## Softwares Required

* [jdk1.7+]
* [Eclipse] - IDE 

## Steps to run the application and the testcases
Open the command prompt and change directory to the project folder and execute the below commands.
* gradlew run - To execute
* gradlew test - Execute testcases.

## Output
Output is based on below criteria:
- Location : City Name is fetched from the locations.txt file.
- Latitude,Longitude,Elevation : Longitude, Latitude & elevation are fetched from locations.txt file.
- Local time : currenttime in ISO860 format.
- Conditions is either Snow,Rain,Sunny
- Temperature is in °C 
- Pressure is in hPa
- Relative humidity is a%.


## Sample Output
Sydney|-33.86,151.21,39.0|2017-09-07T21:44:35Z|Sunny|26.0|1009.0|14.0<br/>
Melbourne|-37.81,144.96,31.0|2017-09-07T20:44:35Z|Sunny|26.0|1010.0|14.0<br/>
Canberra|-35.47,149.01,720.0|2017-09-07T20:44:35Z|Sunny|24.0|933.0|13.0<br/>
Victoria|-36.68,143.58,209.0|2017-09-07T20:44:35Z|Sunny|25.0|989.0|14.0<br/>
Hobart|42.88,147.32,19.0|2017-09-07T20:44:35Z|Sunny|25.0|1011.0|14.0<br/>
Perth|-31.95,115.85,15.0|2017-09-07T18:44:35Z|Sunny|26.0|1012.0|14.0<br/>
Adelaide|-34.66,138.68,59.0|2017-09-07T20:44:35Z|Sunny|26.0|1006.0|14.0<br/>
Brisbane|-27.47,153.02,28.0|2017-09-07T21:44:35Z|Sunny|27.0|1010.0|14.0<br/>
NewCastle|32.92,151.78,9.0|2017-09-07T21:44:35Z|Sunny|26.0|1012.0|14.0<br/>
GoldCoast|-28.01,153.4,12.0|2017-09-07T21:44:35Z|Sunny|27.0|1012.0|14.0<br/>
 


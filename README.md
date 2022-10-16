# Roman Emperors' Dynasties Scraper

Java application which crawls Wikipedia to scrape information about Roman emperors and their family ties and then uses the data to visualize their family tree graph.

## Running the application

### Requirements

If you want to run the app you will need to have [Java](https://www.java.com/) and a browser between [Google Chrome](https://www.google.com/chrome/), [Mozilla Firefox](https://www.mozilla.org/firefox/) or [Microsoft Edge](https://www.microsoft.com/edge) installed. Additionally, in order to compile the app you will need to have [Maven](https://maven.apache.org/) installed.

### Compiling the application

In order to compile the application run the following command:

```sh
mvn clean compile package
```
At this point you should have a ```roman-emperors-dynasties-scraper-1.0-jar-with-dependencies.jar``` file inside a folder named ```target``` in the project directory. This is the excutable JAR file you will want to run.

### Running the JAR file

In order to execute the JAR file run the following command:

```sh
java -jar roman-emperors-dynasties-scraper-1.0-jar-with-dependencies.jar
```
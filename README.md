# Simple Web Server

A Java-based web server that supports handling multiple concurrent requests and serves static files such as HTML, CSS, JavaScript, and images. It also includes asynchronous communication with REST services on the backend.

## Repository Contents
  - **Web Server:** Java implementation for handling GET and POST requests.
  - **Static Files:** index.html, styles.css, app.js, and an example image.
  - **REST Communication**: Endpoint for asynchronous data handling.

## Installation

  1. Clone the Repository
  ~~~ bash
  git clone https://github.com/yourusername/simple-web-server.git
  cd simple-web-server
  ~~~

  2. Build the Project

  Ensure you have [Maven](https://maven.apache.org/) installed. Build the project using:
    
  ~~~ bash
  mvn clean install
  ~~~
  
  This will compile the Java code and package it into a JAR file.
  

## Running the Application

  1. Start the Server

  You can run the server directly from Maven:

  ~~~ bash
  mvn exec:java -Dexec.mainClass="edu.escuelaing.arem.ASE.app.SimpleWebServer.SimpleWebServer"
  ~~~

  Alternatively, run the JAR file generated in the target directory:

  ~~~ bash
  java -jar target/taller-servidor-distribuido-1.0-SNAPSHOT.jar
  ~~~

  2. Access the Web Application

  Open your web browser and navigate to <http://localhost:8080/index.html> to see the application in action.

## Project Architecture

  ### Directory Structure

  The project's directory structure is organized as follows:

  ~~~ css
  src
  ├── main
  │   ├── java
  │   │   └── edu
  │   │       └── escuelaing
  │   │           └── arem
  │   │               └── ASE
  │   │                   └── app
  │   │                       └── SimpleWebServer
  │   │                           ├── ClientHandler.java
  │   │                           └── SimpleWebServer.java
  │   └── resources
  │       └── webroot
  │           ├── app.js
  │           ├── img
  │           │   └── example.png
  │           ├── index.html
  │           └── styles.css
  └── test
      └── java
          └── edu
              └── escuelaing
                  └── arem
                      └── ASE
                          └── app
                              └── AppTest.java
  ~~~

  ### Main Components

  - **`SimpleWebServer`**: This is the main class that initializes and starts the server. It listens for incoming connections on port 8080 and delegates request handling to the `ClientHandler` class.
  - **`ClientHandler`**: This class is responsible for processing client requests. It handles both `GET` and `POST` requests, serves static files, and interacts with REST endpoints.

  ### Flow of Data

  1. **Incoming Requests:**
        - When a request is received, `SimpleWebServer` accepts the connection and creates a new `ClientHandler` instance to process the request.

  2. **Request Processing:**
        - The `ClientHandler` class parses the request line to determine the HTTP method and requested resource.
        - For `GET` requests, it serves static files (HTML, CSS, JavaScript, images) or responds with data from the REST endpoint.
        - For `POST` requests, it processes form data and returns a JSON response.

  3. **Responses:**
        - **Static Files**: The server reads files from the `WEB_ROOT` directory and sends them to the client.
        - **REST Data**: The server responds with JSON data for specific endpoints (e.g., `/data`).

### Dependencies

  - **Java Standard Library**: Utilized for networking, file handling, and concurrent processing.
  - **Maven**: Used for dependency management and build automation. Configuration is specified in the `pom.xml` file.

### REST Endpoints

  - `/data`: Returns a JSON object with a sample message. This endpoint is used for demonstrating asynchronous communication.

### Static Files

  - `index.html`: The main HTML page for the web application. It includes a button to fetch data from the REST endpoint and a form to submit data via POST.
  - `styles.css`: Contains the CSS styles for the web application, providing a responsive and user-friendly interface.
  - `app.js`: JavaScript file that handles asynchronous requests and updates the DOM based on user interactions.
  - `img/example.png`: An example image used in the web application to demonstrate image serving capabilities.

## Testing

To verify the functionality of the web server and application, perform the following tests:

  - Serve Static Files
        - Navigate to http://localhost:8080/index.html and ensure that the page loads correctly.  ![image](https://github.com/user-attachments/assets/04abd08f-6791-4b68-8fe0-7142cc582d41)
        - Check if the CSS styles are applied properly.  ![image](https://github.com/user-attachments/assets/d237cc18-b2a6-4bf8-b836-d8f82eaef618)
        - Confirm that the JavaScript file is loaded and executed correctly.  ![image](https://github.com/user-attachments/assets/817eaefb-25cd-411e-870c-8dab7643cae4)
        - Verify that the image is displayed correctly on the page.  ![image](https://github.com/user-attachments/assets/7c3364c5-f037-4afc-9ab1-182035ec27f3)


  - Test GET Request
        - Click the "Obtain Data" button on the web page. Ensure that the data fetched from the /data endpoint is displayed correctly in the result section.
    ![image](https://github.com/user-attachments/assets/2fb84e9c-bf6d-4a0c-bf99-d434eb9178d9)
    
  - Test POST Request
        - Enter a name in the form and submit it. Verify that the server responds with a JSON message including the submitted name.
    ![image](https://github.com/user-attachments/assets/22bd8571-b68f-4e6c-80ac-efa0602d85cb)

## Author
  **Juliana Briceño** - Developer and Author of the Project

  

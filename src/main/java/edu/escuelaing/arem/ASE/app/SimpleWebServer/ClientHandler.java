package edu.escuelaing.arem.ASE.app.SimpleWebServer;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedOutputStream dataOut = new BufferedOutputStream(clientSocket.getOutputStream())) {

            String requestLine = in.readLine();
            if (requestLine == null)
                return;
            String[] tokens = requestLine.split(" ");
            String method = tokens[0];
            String fileRequested = tokens[1];

            if (method.equals("GET")) {
                handleGetRequest(fileRequested, out, dataOut);
            } else if (method.equals("POST")) {
                handlePostRequest(in, out);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handlePostRequest(BufferedReader in, PrintWriter out) throws IOException {
        // Lee los encabezados hasta una línea vacía
        String line;
        while (!(line = in.readLine()).isEmpty()) {
            // Puedes procesar los encabezados aquí si es necesario
        }

        // Lee el cuerpo de la solicitud
        StringBuilder payload = new StringBuilder();
        while (in.ready()) {
            payload.append((char) in.read());
        }

        String body = payload.toString();
        String name = null;
        if (body.startsWith("name=")) {
            name = body.substring("name=".length());
        }

        // Envía la respuesta
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: application/json");
        out.println("Connection: close"); // Asegúrate de cerrar la conexión
        out.println();
        if (name != null && !name.isEmpty()) {
            out.println("{ \"message\": \"Hola, " + name + "!\" }");
        } else {
            out.println("{ \"message\": \"Hola!\" }");
        }
        out.flush();
    }


    private void handleGetRequest(String fileRequested, PrintWriter out, BufferedOutputStream dataOut)
            throws IOException {
        // Verifica si la solicitud es al endpoint REST
        if (fileRequested.equals("/data")) {
            // Responde con un texto simple
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: application/json");
            out.println();
            out.println("{ \"message\": \"Este es un ejemplo de respuesta de un servicio REST.\" }");
            out.flush();
        } else {
            // Manejo de solicitudes para archivos
            File file = new File(SimpleWebServer.WEB_ROOT, fileRequested);
            String fileReadable = file.toString();
            int fileLength = (int) file.length();
            String content = getContentType(fileRequested);

            if (file.exists()) {
                if (fileRequested.endsWith(".png") || fileRequested.endsWith(".jpg")
                        || fileRequested.endsWith(".jpeg")) {
                    byte[] imageData = Files.readAllBytes(file.toPath());

                    // Enviar la respuesta HTTP para la imagen
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: " + content);
                    out.println("Content-Length: " + imageData.length);
                    out.println();
                    out.flush();

                    // Enviar los datos de la imagen
                    dataOut.write(imageData, 0, imageData.length);
                    dataOut.flush();
                } else {
                    byte[] fileData = readFileData(file, fileLength);
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: " + content);
                    out.println("Content-Length: " + fileLength);
                    out.println();
                    out.flush();
                    dataOut.write(fileData, 0, fileLength);
                    dataOut.flush();
                }
            } else {
                out.println("HTTP/1.1 404 Not Found");
                out.println("Content-Type: text/html");
                out.println();
                out.flush();
                out.println("<html><body><h1>File Not Found</h1></body></html>");
                out.flush();
            }
        }
    }

    private static byte[] getImageContent(String file) throws IOException {
        Path filePath = Paths.get(file);
        return Files.readAllBytes(filePath);
    }

    private String getContentType(String fileRequested) {
        if (fileRequested.endsWith(".html"))
            return "text/html";
        else if (fileRequested.endsWith(".css"))
            return "text/css";
        else if (fileRequested.endsWith(".js"))
            return "application/javascript";
        else if (fileRequested.endsWith(".png"))
            return "image/png";
        else if (fileRequested.endsWith(".jpg"))
            return "image/jpeg";
        return "text/plain";
    }

    private byte[] readFileData(File file, int fileLength) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];
        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null)
                fileIn.close();
        }
        return fileData;
    }

}
package client;

import client.ClientThings.ClientManager;
import client.ClientThings.OutputManager;
import coomon.Interaction.Request;
import coomon.Interaction.Response;
import coomon.exceptions.ConnectionErrorEX;
import coomon.exceptions.NotInDeclaredLimitsEX;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    private final String host;
    private final int port;
    private final int reconnectionTimeout;
    private int reconnectionAttempts;
    private final int maxReconnectionAttempts;
    private final OutputManager outputManager;
    private final ClientManager clientManager;
    private SocketChannel socketChannel;

    public Client(String host, int port, int reconnectionTimeout, int maxReconnectionAttempts, ClientManager clientManager, OutputManager outputManager) {
        this.host = host;
        this.port = port;
        this.reconnectionTimeout = reconnectionTimeout;
        this.maxReconnectionAttempts = maxReconnectionAttempts;
        this.clientManager = clientManager;
        this.outputManager = outputManager;
    }

    public void run() {
        try {
            boolean processingStatus = true;
            while (processingStatus) {
                try {
                    connectToServer();
                    processingStatus = processRequestToServer();
                } catch (ConnectionErrorEX exception) {
                    if (reconnectionAttempts >= maxReconnectionAttempts) {
                        outputManager.printlnError("Превышено количество попыток подключения к серверу!");
                        break;
                    }
                    try {
                        Thread.sleep(reconnectionTimeout);
                    } catch (IllegalArgumentException timeoutException) {
                        outputManager.printlnError("Время ожидания подключения '" + reconnectionTimeout +
                                "' выходит за пределы возможных значений!");
                        outputManager.println("Переподключение будет выполнено незамедлительно.");
                    } catch (Exception timeoutException) {
                        outputManager.printlnError("Ошибка во время подключения к серверу!");
                        outputManager.printlnError("Переподключение будет выполнено незамедлительно.");
                    }
                }
                reconnectionAttempts++;
            }
            if (socketChannel != null && socketChannel.isOpen()) socketChannel.close();
            System.out.println("Работа клиента была успешно завершена!");
            System.exit(0);
        } catch (NotInDeclaredLimitsEX exception) {
            outputManager.printlnError("Клиент не может быть запущен!");
        } catch (IOException exception) {
            outputManager.printlnError("Возникла ошибка во время соединения с сервером!");
        }
    }

    private void connectToServer() throws ConnectionErrorEX, NotInDeclaredLimitsEX {
        try {
            if (reconnectionAttempts >= 1) outputManager.println("Происходит соединение с сервером...");
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            socketChannel.configureBlocking(true);  // Включаем блокирующий режим
            outputManager.println("Соединение с сервером было успешно установлено!");
            outputManager.println("Жду разрешения на обмен данными...");
        } catch (IllegalArgumentException exception) {
            outputManager.printlnError("Адрес сервера был указан неправильно!");
            throw new NotInDeclaredLimitsEX();
        } catch (IOException exception) {
            outputManager.println("При подключении к серверу произошла ошибка!");
            throw new ConnectionErrorEX();
        }
    }

    private boolean processRequestToServer() {
        Request requestToServer = null;
        Response serverResponse = null;
        boolean exitCommandSent = false;

        do {
            try {
                requestToServer = serverResponse != null ? clientManager.handle(serverResponse.getResponseCode()) :
                        clientManager.handle(null);
                if (requestToServer.isEmpty()) continue;

                if ("exit".equalsIgnoreCase(requestToServer.getCommandName()) ||
                        "server_exit".equalsIgnoreCase(requestToServer.getCommandName())) {
                    exitCommandSent = true;
                }

                sendRequest(requestToServer);
                serverResponse = receiveResponse();
                System.out.println(serverResponse.getResponseBody());

                if ("Сервер завершает работу.".equalsIgnoreCase(serverResponse.getResponseBody())) {
                    outputManager.println("Сервер завершил работу. Клиент будет отключен.");
                    break;
                }

            } catch (InvalidClassException | NotSerializableException exception) {
                outputManager.printlnError("Произошла ошибка во время передачи данных с сервером! " + exception.getMessage());
                break;
            } catch (ClassNotFoundException exception) {
                outputManager.printlnError("При считывании полученных данных произошла ошибка! " + exception.getMessage());
                break;
            } catch (IOException exception) {
                outputManager.println("Соединение с сервером было разорвано!");
                outputManager.printlnError("Причина: " + exception.getMessage());

                if (exitCommandSent) {
                    outputManager.println("Команда выхода принята сервером. Клиент завершает работу.");
                    break;
                }

                try {
                    reconnectionAttempts++;
                    if (reconnectionAttempts > maxReconnectionAttempts) {
                        outputManager.printlnError("Превышено количество попыток подключения к серверу!");
                        break;
                    }
                    connectToServer();
                } catch (ConnectionErrorEX | NotInDeclaredLimitsEX reconnectionException) {
                    outputManager.printlnError("Невозможно установить соединение с сервером!");
                }
            }
        } while (requestToServer != null && !exitCommandSent);

        return !exitCommandSent;
    }

    private void sendRequest(Request request) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(request);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            while (buffer.hasRemaining()) {
                socketChannel.write(buffer);
            }
        }
    }

    private Response receiveResponse() throws IOException, ClassNotFoundException {
        ByteBuffer buffer = ByteBuffer.allocate(4096);  // Увеличен размер буфера
        int bytesRead = socketChannel.read(buffer);

        if (bytesRead == -1) {
            throw new IOException("Соединение с сервером было закрыто.");
        }

        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return (Response) objectInputStream.readObject();
        }
    }
}
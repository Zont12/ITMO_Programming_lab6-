package server;

import coomon.Interaction.Request;
import coomon.Interaction.Response;
import coomon.Interaction.ResponseCode;
import coomon.exceptions.ClosingSocketEX;
import coomon.exceptions.OpeningServerSocketEX;
import server.Managers.ServerManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {
    private int port;
    private ServerSocketChannel serverSocketChannel;
    private int soTimeout;
    private Selector selector;
    private ServerManager requestReaderModule;
    private boolean isRunning;

    public Server(int port, int soTimeout, ServerManager requestReaderModule) {
        this.port = port;
        this.soTimeout = soTimeout;
        this.requestReaderModule = requestReaderModule;
        this.isRunning = true;
    }

    public void run() {
        try {
            openServerSocket();
            while (isRunning) {
                try {
                    selector.select();
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();

                        if (!key.isValid()) {
                            continue;
                        }

                        if (key.isAcceptable()) {
                            accept(key);
                        } else if (key.isReadable()) {
                            read(key);
                        }

                        keyIterator.remove();
                    }
                } catch (IOException exception) {
                    System.err.println("При обработке соединения произошла ошибка: " + exception.getMessage());
                }
            }
            if (isRunning) {
                stop();
            }
        } catch (OpeningServerSocketEX exception) {
            System.err.println("Ошибка при открытии серверного сокета.");
        }
    }

    private void stop() {
        try {
            isRunning = false;
            System.out.println("Выключение сервера...");
            if (serverSocketChannel == null) {
                throw new ClosingSocketEX("Невозможно завершить работу сервера, который еще не был запущен!");
            }
            serverSocketChannel.close();
            System.out.println("Сервер успешно завершил работу.");
        } catch (ClosingSocketEX exception) {
            System.err.println("Возникли проблемы с сокетом!");
        } catch (IOException exception) {
            System.err.println("При выключении сервера произошла ошибка: " + exception.getMessage());
        } finally {
            System.exit(0);
        }
    }

    private void openServerSocket() throws OpeningServerSocketEX {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            serverSocketChannel.socket().setSoTimeout(soTimeout);
            System.out.println("Сервер запущен на порту: " + port);
        } catch (IllegalArgumentException exception) {
            System.err.println("Порт '" + port + "' выходит за пределы возможных значений!");
        } catch (IOException exception) {
            System.err.println("При попытке использовать порт '" + port + "' произошла ошибка!");
            throw new OpeningServerSocketEX();
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientSocketChannel = serverSocketChannel.accept();
        clientSocketChannel.configureBlocking(false);
        clientSocketChannel.register(selector, SelectionKey.OP_READ);
        System.out.println("Соединение с клиентом успешно установлено: " + clientSocketChannel.getRemoteAddress());
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel clientSocketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = clientSocketChannel.read(buffer);
        if (bytesRead > 0) {
            buffer.flip();
            byte[] bytes = new byte[bytesRead];
            buffer.get(bytes);
            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                 ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
                Request userRequest = (Request) objectInputStream.readObject();
                System.out.println("Получен запрос от клиента: " + userRequest.getCommandName());
                Response responseToUser;
                if ("exit".equalsIgnoreCase(userRequest.getCommandName())) {
                    responseToUser = new Response(ResponseCode.ERROR, "Клиент отключается.");
                    sendResponse(clientSocketChannel, responseToUser);
                    key.cancel();
                    clientSocketChannel.close();
                    System.out.println("Ответ на запрос: " + responseToUser.getResponseBody());
                    System.out.println("Соединение с клиентом закрыто: " + clientSocketChannel.getRemoteAddress());
                } else if ("server_exit".equalsIgnoreCase(userRequest.getCommandName())) {
                    responseToUser = new Response(ResponseCode.SERVER_EXIT, "Сервер завершает работу.");
                    sendResponse(clientSocketChannel, responseToUser);
                    stop();
                } else {
                    responseToUser = requestReaderModule.handle(userRequest);
                    sendResponse(clientSocketChannel, responseToUser);
                }
                System.out.println("Ответ на запрос: " + responseToUser.getResponseBody());
            } catch (ClassNotFoundException | IOException exception) {
                System.err.println("При считывании полученных данных произошла ошибка: " + exception.getMessage());
            }
        } else if (bytesRead == -1) {
            key.cancel();
            clientSocketChannel.close();
            System.out.println("Соединение с клиентом закрыто: " + clientSocketChannel.getRemoteAddress());
        }
    }

    private void sendResponse(SocketChannel clientSocketChannel, Response response) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(response);
            objectOutputStream.flush();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            while (buffer.hasRemaining()) {
                clientSocketChannel.write(buffer);
            }
        }
    }
}

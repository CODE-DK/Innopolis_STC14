package task_8.Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * поток исполнения на стороне клиента,
 * отвечает за задачу чтения сообщений со сторон сервера
 *
 * @author Komovskiy Dmitriy
 * @version v1.0
 */
class ClientListener extends Thread {

    /**
     * сокет для подключения к серверу
     */
    private Socket server;

    ClientListener(Socket server) {
        this.server = server;
    }

    /**
     * метод для запуска потока
     * пока сервер доступен - слушаем, пишем в консоль
     */
    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(server.getInputStream())){
            while (!server.isOutputShutdown()) {
                String text = in.readUTF();
                System.out.println(text);
            }
            server.close();
        } catch (IOException ex) {
            System.out.println("Error : " + ex);
        }
    }
}

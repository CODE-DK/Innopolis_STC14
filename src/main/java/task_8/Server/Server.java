package task_8.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * класс сервер, по сокету опрашивает новых подключившихся клиентов
 * создает список подключенных клиентов
 * добавляет на каждого клиента оператора {@code ServerThread}
 *
 * @author Komovskiy Dmitriy
 * @version v1.0
 */
public class Server {

    /**
     * порт на котором будет запущен сервер
     */
    private static final int PORT = 5001;

    /**
     * список подключенных клиентов и очередь
     * сообщений от клиентов
     */
    private ConcurrentMap<ServerListener, String> clientMap = new ConcurrentHashMap<>();
    private ConcurrentLinkedQueue<String> messages = new ConcurrentLinkedQueue<>();

    /**
     * флаг останова сервера
     * в данной версии не адействован
     */
    @Deprecated
    private static boolean stopFlag = false;

    /**
     * метод запуска сервера
     * по вызову метода сервер начинает опрос по сокету
     * на наличие новых клиентских подключений
     *
     * @throws IOException в случае проблеммы с соединением по сокету
     */
    private void start() {
        try (ServerSocket server = new ServerSocket(PORT)) {

            System.out.println("сервер запущен...");
            System.out.println("ожидаю клиентов...");

            while (!isStop()) {
                Socket client = server.accept();
                ServerListener listener = new ServerListener(client, clientMap, messages);
                listener.start();
            }
        } catch (IOException e) {
            System.out.println("e = " + e);
        }
    }


    private boolean isStop() {
        return stopFlag;
    }


    @Deprecated
    public static void stop() {
        stopFlag = true;
    }

    /**
     * запускает сервер вызывая start()
     *
     * @param args входные аргументы
     */
    public static void main(String[] args) {
        new Server().start();
    }
}
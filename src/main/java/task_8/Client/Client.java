package task_8.Client;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * класс - клиент
 * подключается по соке к серверу
 * можно писать сообщения и общаться с другими клиентами
 *
 * @author Komovskiy Dmitriy
 * @version v1.0
 */
public class Client {

    /**
     * порт и хост сервера
     */
    private static final int PORT = 5001;
    private static final String HOST = "localhost";

    /**
     * основной метод - поток клиента
     * создаем соединение, подключаемся,
     * читаем пишем
     *
     * @param args входные аргументы
     * @throws Exception при неудачном подключение к серверу
     */
    public static void main(String[] args) {

        try (Socket server = new Socket(HOST, PORT)) {

            ClientListener listener = new ClientListener(server);
            listener.start();

            manager(server);
        } catch (UnknownHostException e) {
            System.out.println("host is't exist");
        } catch (IOException e) {
            System.out.println("server is not available");
        }
    }

    /**
     * менеджер клиента, соединяет клиента с серверо
     * логирует, помогает в написании сообщений другим клиентам
     *
     * @param server сокет сервера
     * @throws IOException если потоки ввода вывода не открылись
     */
    private static void manager(Socket server) {

        try (DataOutputStream out = new DataOutputStream(server.getOutputStream())) {
            BufferedReader message = new BufferedReader(new InputStreamReader(System.in));

            String name = login(out, message);
            sayHallo(name);

            while (!server.isOutputShutdown()) {

                String text = message.readLine();
                if ("exit".equalsIgnoreCase(text)) {
                    break;
                }
                out.writeUTF(text);
                out.flush();
            }
            message.close();
        } catch (IOException e) {
            System.out.println("e = " + e);
        }
    }

    /**
     * приветствуем нового клиента
     * @param name имя клиента
     */
    private static void sayHallo(String name) {

        System.out.printf("Здравствуйте, %s! ", name);
        System.out.println("введите текст сообщения");
    }

    /**
     * логирует клиента,
     * отправляет результат на сервер
     * @param out выходной поток к серверу
     * @param message поле для ввода имени клиента
     * @return имя клиента
     * @throws IOException при ошибке чтения - записи
     */
    @NotNull
    private static String login(DataOutputStream out, BufferedReader message) throws IOException {

        System.out.print("Введите имя : ");
        String name = message.readLine();
        out.writeUTF(name);
        out.flush();
        return name;
    }
}

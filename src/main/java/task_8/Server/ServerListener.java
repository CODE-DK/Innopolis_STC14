package task_8.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * класс предназначен выполнять роль оператора
 * для работы с каждым клиентом в отдельности
 * при подключении создается отдельный поток на клиента
 *
 * @author Komovskiy Dmitriy
 * @version v1.0
 */
class ServerListener extends Thread {

    /**
     * сокет для подключения клиента
     */
    private Socket client;

    /**
     * список подключенных клиентов и
     * список сообщений клиентов
     */
    private ConcurrentMap<ServerListener, String> clientMap;
    private ConcurrentLinkedQueue<String> messages;

    /**
     * потоки для передачи информации
     * клиент - сервер - клиент
     */
    private DataInputStream in;
    private DataOutputStream out;

    /**
     * конструктор инициализации серверного потока
     *
     * @param client    новый клиент
     * @param clientMap список подключенных клиентов
     * @param messages  список сообщений клиентов
     * @throws IOException если не удало создать потоки ввода вывода
     */
    ServerListener(Socket client, ConcurrentMap<ServerListener, String> clientMap, ConcurrentLinkedQueue<String> messages) throws IOException {

        this.client = client;
        this.clientMap = clientMap;
        this.messages = messages;

        System.out.println("Подключился новый клиент...");

        in = new DataInputStream(client.getInputStream());
        out = new DataOutputStream(client.getOutputStream());
    }

    /**
     * вернуть поток для записи клиенту
     * {@link java.io.DataOutputStream}
     *
     * @return поток для записи клиенту
     * {@link java.io.DataOutputStream}
     */
    private DataOutputStream outputStream() {
        return out;
    }

    /**
     * метод запуска потока
     * читаем сообщение, ставим в очередь сообщений
     * рассылаем сообщение по всем клиентам в списке подключенных
     */
    @Override
    public void run() {
        try {

            clientMap.put(this, in.readUTF());

            sayToAll(String.format("Клиент %s присоединился к беседе.", clientMap.get(this)));

            while (!client.isClosed()) {
                String text = in.readUTF();
                messages.add(text);
                if (text.equalsIgnoreCase("exit")) {
                    return;
                }

                String s = messages.poll();
                sayToAll(clientMap.get(this) + " : " + s);
            }
            out.close();
            in.close();
            client.close();
        } catch (IOException ex) {
            System.out.println("Client disconnected");
        }
    }

    /**
     * рассылаем всем клиентам из {@code clientMap}
     * переданное сообщение
     *
     * @param message сообщение для передачи
     * @throws IOException при ошибке во время записи сообщения
     *                     в выходной поток
     */
    private void sayToAll(String message) throws IOException {
        for (ServerListener listener : clientMap.keySet()) {
            if (message != null && !listener.equals(this)) {
                listener.outputStream().writeUTF(message);
                listener.outputStream().flush();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerListener that = (ServerListener) o;
        return Objects.equals(client, that.client) &&
                Objects.equals(in, that.in) &&
                Objects.equals(out, that.out);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, messages, in, out);
    }
}

import commands.Invoker;
import commands.Receiver;
import connection.DatagramConnection;
import exceptions.InvalidArgsException;
import packages.Packet;
import packages.PacketManager;
import requests.RequestHandler;
import responces.ResponseBuilder;
import requests.Request;
import logic.serverLogic.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * A class containing methods for program and user interaction.
 */

public class Server {
    public String dataPath;

    public Receiver receiver;
    public DatagramConnection manager;
    public Invoker invoker;
    public ResponseBuilder builder;
    public CommandChecker checker = new CommandChecker();
    public boolean executing = false;

    private boolean running = true;
    String[] array;
    static byte[] buffer = new byte[32];

    private static final Logger logger = LogManager.getLogger();
    public Server(String[] args) throws IOException, InvalidArgsException {
        if(args.length == 0){
            logger.error("Не указан путь к файлу.");
            System.out.println("Не указан путь к файлу.");
            System.exit(1);
        } else {
            this.dataPath = args[0];
        }
    }

    public void start() throws IOException, InvalidArgsException, ClassNotFoundException {
        logger.info("Сервер запущен.");
        //System.out.println("𝘃𝗲𝗱𝘇𝗲𝘃𝗴𝗻\n𝘀𝗲𝗿𝘃𝗲𝗿 (𝗠𝘂𝘀𝗶𝗰𝗕𝗮𝗻𝗱 𝗰𝗼𝗹𝗹𝗲𝗰𝘁𝗶𝗼𝗻)\n");

        DatagramConnection connectionManager = new DatagramConnection();
        RequestHandler handler = new RequestHandler();
        builder = new ResponseBuilder();

        try {
            manager = new DatagramConnection(50689, true);
            receiver = new Receiver(dataPath, manager, builder);
            invoker = new Invoker(receiver, manager, builder);
            logger.info("Invoker и Receiver запущены.");
        } catch (SocketException e) {
            logger.error("Адрес уже используется.");
            running = false;
        } catch (UnknownHostException e) {
            running = false;
            logger.error("Неизвестный хост.");
        }

        //manager = new DatagramConnection();
        //receiver = new Receiver(dataPath, manager, builder);
        //invoker = new Invoker("", null, receiver, manager, builder);

        Request request = (Request) manager.receive();

        if(Objects.equals(request.getText(), "ready")){
            CommandsSender commandsSender = new CommandsSender(manager);
            commandsSender.sendCommands();
            logger.info("Список команд отправлен.");
        }

        //manager.sendObject(invoker.getCommands());
        //manager.sendObject(commands);
        String line;

        while(running){
            request = (Request) manager.receive();
            line = handler.getText(request);
            Object object = handler.getObject(request);
            if(!checker.isExecute(line) && !executing) {
                logger.info("Получена команда: " + line + ".");
                invoker.runCommand(line, dataPath, false, array, object);
            } else {
                request = (Request) manager.receive();
        line = handler.getText(request);
        String[] arguments = handler.getArgs(request);
        executing = true;
        invoker.runCommand(line, dataPath, true, arguments, object);
    }
}
    }

    public static int sizeof(Object obj) throws IOException {

        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);

        objectOutputStream.writeObject(obj);
        objectOutputStream.flush();
        objectOutputStream.close();

        return byteOutputStream.toByteArray().length;
    }

}



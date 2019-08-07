package cn.wode490390.mcbe.botclient;

import com.nukkitx.protocol.bedrock.BedrockClient;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ConsoleCommandManager {

    //private final Main main;

    private final Map<String, ConsoleCommand> commandMap;

    public ConsoleCommandManager(Main main) {
        //this.main = main;
        commandMap = new HashMap<String, ConsoleCommand>(){
            {
                put("stop", (args) -> {
                    log.info("The command has been executed successfully: {}", args[0]);
                    main.shutdown(); //TODO: fix
                });
                put("version", (args) -> {
                    log.info("The command has been executed successfully: {}", args[0]);
                    log.info("wodeFakeClient (MCBE) Version: " + Main.VERSION);
                });
                put("add", (args) -> {
                    int count = 1;
                    /*if (args.length > 0) {
                        try {
                            count = Integer.parseInt(args[1]);
                        } catch (Exception e) {
                            log.info("Unexpected parameter: {}", args[1]);
                        }
                    }*/
                    for (int i = 0; i < count; ++i) {
                        main.getClientManager().newClient();
                    }
                    log.info("The command has been executed successfully: {}", args[0]);
                });
                put("close", (args) -> {
                    int count = 1;
                    /*if (args.length > 0) {
                        try {
                            count = Integer.parseInt(args[1]);
                        } catch (Exception e) {
                            log.info("Unexpected parameter: {}", args[1]);
                        }
                    }*/
                    Iterator<BedrockClient> iterator = main.getClientManager().getClients().iterator();
                    for (int i = 0; i < count; ++i) {
                        BedrockClient client = iterator.next();
                        client.close();
                        main.getClientManager().getClients().remove(client);
                    }
                    log.info("The command has been executed successfully: {}", args[0]);
                });
            }
        };
    }

    public void dispatch(String command) {
        String[] commandLine = command.split(" ");
        ConsoleCommand cmd = getCommandMap().get(commandLine[0]);
        if (cmd != null) {
            cmd.dispatch(commandLine);
        } else {
            log.info("Unknown command: " + cmd);
        }
    }

    public Map<String, ConsoleCommand> getCommandMap() {
        return commandMap;
    }
}

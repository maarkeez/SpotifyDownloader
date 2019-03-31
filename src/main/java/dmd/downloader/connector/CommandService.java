package dmd.downloader.connector;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@Service
public class CommandService {

    public void execute(String... command) throws IOException {

        String inputStr;
        String commandStr = String.join(" ", command);

        try {
            log.debug("Executing command: '{}'", commandStr);
            Process p = Runtime.getRuntime().exec(commandStr);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // read the output from the command
            log.debug("Here is the standard output of the command:");
            while ((inputStr = stdInput.readLine()) != null) {
                log.debug(inputStr);
            }

            // read any errors from the attempted command
            log.debug("Here is the standard error of the command (if any):");
            while ((inputStr = stdError.readLine()) != null) {
                log.error(inputStr);
            }

        } catch (IOException e) {
            log.error("Could not execute command: '{}'. Error: {}", commandStr, e.getMessage());
            throw e;
        }
    }
}

package fr.csid.voilavoix.service;

import fr.csid.voilavoix.config.LoggingConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * Created by blozach on 22/06/17.
 * require : mpgtx linux package
 */
@Service
public class Mp3Cutter {

    private final Logger log = LoggerFactory.getLogger(LoggingConfiguration.class);

    public File cutMP3(File initialFile, String startTime, String endTime){
        Process process;
        int exitValue ;

        String originalFileName = initialFile.getParent()+"/"+initialFile.getName();
        String cuttedFileName = initialFile.getParent()+"/cutted"+startTime+"-"+endTime+initialFile.getName();
        String formatedStartTime = startTime; //hh:mm:ss
        String formatedEndTime = endTime;

        String command = "mpgtx -s "+originalFileName+" ["+formatedStartTime+"-"+formatedEndTime+"] -o "+cuttedFileName;
        log.error(command);
        try {
            process = Runtime.getRuntime().exec(command);
            exitValue = process.exitValue();
            log.error("exitValue"+exitValue);
            process.destroy();
        } catch (Exception e) {}

        File returnFile = new File(cuttedFileName);
        log.error("newFile? ="+returnFile.exists());
        if (returnFile.exists()){
            initialFile.delete();
            return returnFile ;
        }
        else {

            return initialFile;
        }
    }

}

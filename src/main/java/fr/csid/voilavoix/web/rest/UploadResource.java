package fr.csid.voilavoix.web.rest;

import fr.csid.voilavoix.config.LoggingConfiguration;
import fr.csid.voilavoix.repository.AudioRepository;
import fr.csid.voilavoix.service.ExternApiIntegration.APIInterface;
import fr.csid.voilavoix.service.ExternApiIntegration.BingService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by user on 21/02/2017.
 */


@RestController
@RequestMapping("/upload")
public class UploadResource {

    private final Logger log = LoggerFactory.getLogger(LoggingConfiguration.class);

    @Autowired
    private BingService bingService;

    @Autowired
    private APIInterface smService;


    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private AudioRepository audioRepository;

    @GetMapping("/json")
    public String getJson(){
        return "[ { \"_id\": \"58ac576f626d2ab54ed8b985\", \"index\": 0, \"age\": 38, \"name\": \"Harding Snow\" }, { \"_id\": \"58ac576ffdee2824c146fcdd\", \"index\": 1, \"age\": 38, \"name\": \"Dina Potts\" }, { \"_id\": \"58ac576f263c959f2d23c299\", \"index\": 2, \"age\": 40, \"name\": \"Cherie Petersen\" }, { \"_id\": \"58ac576f112e753d91cb88be\", \"index\": 3, \"age\": 34, \"name\": \"Gilliam Hampton\" }, { \"_id\": \"58ac576f008be59056062a91\", \"index\": 4, \"age\": 39, \"name\": \"Opal Weaver\" } ]";
    }

    @PostMapping("/job")
    public Object jobRequest(@RequestParam("file") MultipartFile file) throws IOException{
        String name = file.getOriginalFilename();
        File tempFile = new File(System.getProperty("java.io.temp") + "/" + name);
        FileUtils.writeByteArrayToFile(tempFile, file.getBytes());
//        file.transferTo(tempFile);
        Map<String, String> result = smService.sendRequest(tempFile);
        tempFile.delete();
        return result;
//        return smService.sendRequest(id);
    }

    @GetMapping("/job/{id}")
    public Object jobResponse(@PathVariable("id") int id){
        return this.smService.getResult(id);
    }

}

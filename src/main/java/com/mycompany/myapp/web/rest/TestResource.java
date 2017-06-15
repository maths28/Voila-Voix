package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.config.LoggingConfiguration;
import com.mycompany.myapp.domain.Audio;
import com.mycompany.myapp.repository.AudioRepository;
import com.mycompany.myapp.service.ExternApiIntegration.BingService;
import com.mycompany.myapp.service.ExternApiIntegration.SMService;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.AudioInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/testres")
public class TestResource {

    private final Logger log = LoggerFactory.getLogger(LoggingConfiguration.class);

    @Autowired
    private BingService bingService;

    @Autowired
    private SMService smService;


    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private AudioRepository audioRepository;

    @GetMapping("/json")
    public String getJson() {
        return "[ { \"_id\": \"58ac576f626d2ab54ed8b985\", \"index\": 0, \"age\": 38, \"name\": \"Harding Snow\" }, { \"_id\": \"58ac576ffdee2824c146fcdd\", \"index\": 1, \"age\": 38, \"name\": \"Dina Potts\" }, { \"_id\": \"58ac576f263c959f2d23c299\", \"index\": 2, \"age\": 40, \"name\": \"Cherie Petersen\" }, { \"_id\": \"58ac576f112e753d91cb88be\", \"index\": 3, \"age\": 34, \"name\": \"Gilliam Hampton\" }, { \"_id\": \"58ac576f008be59056062a91\", \"index\": 4, \"age\": 39, \"name\": \"Opal Weaver\" } ]";
    }

    @PostMapping("/demo")
    public Object demoRequest(@RequestParam("file") MultipartFile file) throws IOException, JSONException, URISyntaxException {
        AudioResource audioR = new AudioResource(audioRepository);
        List<Audio> listeAudio = audioR.getAllAudios();
        Audio audio = containNameOfAudioFile(listeAudio, file.getOriginalFilename());

        if (audio != null) {
            log.debug("Nom du fichier audio uploadé deja present dans la bdd");
            JSONObject jsonAsSend = new JSONObject(audio.getFile_content_type());
            Object respons = jsonAsSend.toString();
            return respons;
        } else {
            log.debug("Ajout de l'audio a la bdd");

            String name = file.getOriginalFilename();
            File tempFile = new File(System.getProperty("java.io.temp") + "/" + name);
            FileUtils.writeByteArrayToFile(tempFile, file.getBytes());
//        file.transferTo(tempFile);
            Map<String, String> result = smService.sendRequest(tempFile);
            tempFile.delete();
            JSONObject jsonResult = new JSONObject(result);
            try {
                log.error("ID : " + jsonResult.getString("id"));
            } catch (Exception e) {
                return e.getMessage();
            }
            log.error("RESULT : " + result.toString());
            audio = new Audio();
            audio.setName(file.getOriginalFilename());
            audio.setFile_content_type(result.toString());
            audioR.createAudio(audio);
            log.error("RESULT : " + result.toString());
            return result;
//        return smService.sendRequest(id);
        }


    }

    @GetMapping("/demo/{id}")
    public Object demoResponse(@PathVariable("id") int id) {
        return this.smService.getResult(id);
    }


    private Audio containNameOfAudioFile(List<Audio> theList, String audioName) {
        for (Audio a : theList) {
            log.debug("LIST : " + a.getName() + " : " + audioName);
            if (a.getName().equalsIgnoreCase(audioName)) {
                log.debug("TROUVE ! " + a.getName());
                return a;
            }
        }
        return null;
    }

}

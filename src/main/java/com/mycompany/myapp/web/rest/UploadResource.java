package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.config.LoggingConfiguration;
import com.mycompany.myapp.domain.Audio;
import com.mycompany.myapp.repository.AudioRepository;
import com.mycompany.myapp.service.ExternApiIntegration.APIInterface;
import com.mycompany.myapp.service.ExternApiIntegration.BingService;
import com.mycompany.myapp.service.Mp3Cutter;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;


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

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private Mp3Cutter mp3Cutter;

    @GetMapping("/json")
    public String getJson() {
        return "[ { \"_id\": \"58ac576f626d2ab54ed8b985\", \"index\": 0, \"age\": 38, \"name\": \"Harding Snow\" }, { \"_id\": \"58ac576ffdee2824c146fcdd\", \"index\": 1, \"age\": 38, \"name\": \"Dina Potts\" }, { \"_id\": \"58ac576f263c959f2d23c299\", \"index\": 2, \"age\": 40, \"name\": \"Cherie Petersen\" }, { \"_id\": \"58ac576f112e753d91cb88be\", \"index\": 3, \"age\": 34, \"name\": \"Gilliam Hampton\" }, { \"_id\": \"58ac576f008be59056062a91\", \"index\": 4, \"age\": 39, \"name\": \"Opal Weaver\" } ]";
    }

    @PostMapping("/job")
    public Object jobRequest(@RequestParam("file") MultipartFile file,
                             @RequestParam("startTime") String startTime,
                             @RequestParam("endTime") String endTime) throws IOException, URISyntaxException, JSONException {

        AudioResource audioR = new AudioResource(audioRepository);
        List<Audio> listeAudio = audioR.getAllAudios();

        log.debug("debut = "+startTime+" fin = "+endTime);
        String uploadsDir = "/upload";
        String realPathtoUploads =  request.getServletContext().getRealPath(uploadsDir);
        /*String realPathtoUploads = System.getProperty("java.io.temp") ;*/
        String name = file.getOriginalFilename();
        File tempFile = new File( realPathtoUploads+"/"+name);
        FileUtils.writeByteArrayToFile(tempFile, file.getBytes());

        //test cuttedMP3
        tempFile = mp3Cutter.cutMP3(tempFile, startTime, endTime);

        Audio audio = containNameOfAudioFile(listeAudio, tempFile.getName());
        if (audio != null && !audio.getFile_content_type().equals("")) {
            log.debug("Nom du fichier audio uploadé deja present dans la bdd");
            JSONObject jsonAsSend = new JSONObject(audio.getFile_content_type());
            Object respons = jsonAsSend.toString();
            return respons;
        } else {
            log.debug("Ajout de l'audio a la bdd");

            // *** the biggy ***
            Map<String, String> result = smService.sendRequest(tempFile);
//            String fileName = tempFile.getName();
//            String idJob = result.get("id").toString();


            JSONObject jsonResult = new JSONObject();
            jsonResult.append("name", tempFile.getName());
            jsonResult.append("id",  result.get("id"));
            log.debug(jsonResult.toString());
            try {
                log.error("ID : " + jsonResult.getString("id"));
            } catch (Exception e) {
                return e.getMessage();
            }
            log.error("RESULT : " + result.toString());
            audio = new Audio();
            audio.setName(tempFile.getName());
            audio.setFile_content_type(result.toString());
            audioR.createAudio(audio);
            tempFile.delete();
            log.error("RESULT : " + result.toString());
            return result;
//        return smService.sendRequest(id);
        }


    }


    @GetMapping("/job/{id}")
    public Object jobResponse(@PathVariable("id") int id) {
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

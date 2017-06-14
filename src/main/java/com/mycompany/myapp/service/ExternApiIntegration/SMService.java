package com.mycompany.myapp.service.ExternApiIntegration;

import com.mycompany.myapp.config.LoggingConfiguration;
import com.mycompany.myapp.repository.AudioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service("smervice")
public class SMService {

    private RestTemplate restTemplate;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private AudioRepository audioRepository;



    private final Logger log = LoggerFactory.getLogger(LoggingConfiguration.class);


    public SMService(){
        this.restTemplate = new RestTemplate();
    }


    public Map<String, String> sendRequest(File file){
//        Audio audio = audioRepository.findOne(Long.valueOf(id));
//        Resource resource = resourceLoader.sgetResource("classpath:camus1.mp3");
        //TODO : Requete token et id
        try {
            return this.sendFileRequest(file, "MzMxMDY2NTQtOWZlMC00OTNkLTllOGMtMDEwYjU0NzBmYmE2", "12066");
        } catch (Exception e){
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", e.toString());
            return errorMap;
        }
    }
    public Map<String, Object> getResult(int id){
        String authToken = "MzMxMDY2NTQtOWZlMC00OTNkLTllOGMtMDEwYjU0NzBmYmE2";
        String userId = "12066";
        try {
            Map<String, Object> status = this.getStatus(authToken, userId, id);
            if(!this.isJobFinished(status)){
                return status;
            } else {
                return this.getResult(authToken, userId, id);
            }
        } catch (Exception e){
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("error", e.toString());
            return errorMap;
        }
    }

    private Map<String, String> sendFileRequest(File file, String authToken, String userId){

        try {
            //Envoi avec content-type multipart/form-data pour dépôt de fichier
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);


            //Les parametres POST data_file (fichier) et model (langage)
            MultiValueMap<String, Object> parts =
                new LinkedMultiValueMap<>();
            parts.add("data_file", new FileSystemResource(file));
            parts.add("model", "fr");


            //Objet contenant l entete et le body de la requete http
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(parts, httpHeaders);


            //Construction de l'url
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.speechmatics.com/v1.0/user/"+userId+"/jobs/")
                .queryParam("auth_token", authToken);

            HashMap<String,String> dummy = new HashMap<>();

            log.debug("URL " + builder.build().encode().toUri());

            //Envoi requete

            ResponseEntity<HashMap<String,String>> conversion =
                restTemplate.exchange(builder.build().encode().toUri(),
                    HttpMethod.POST, entity, (Class<HashMap<String,String>>) dummy.getClass());

            return conversion.getBody();
        } catch (HttpClientErrorException e){
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", e.getResponseBodyAsString());
            return errorMap;
        } catch (Exception e){
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", e.getMessage());
            return errorMap;
        }

    }

    private Map<String, Object> getStatus(String authToken, String userId, int id){

        try {

            //Construction de l'url
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.speechmatics.com/v1.0/user/"+userId+"/jobs/"+id)
                .queryParam("auth_token", authToken);

            HashMap<String,Object> dummy = new HashMap<>();

            log.debug("URL " + builder.build().encode().toUri());

            //Envoi requete

            ResponseEntity<HashMap<String,Object>> conversion =
                restTemplate.getForEntity(builder.build().encode().toUri(), (Class<HashMap<String, Object>>)dummy.getClass());

            return conversion.getBody();
        } catch (HttpClientErrorException e){
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("error", e.getResponseBodyAsString());
            return errorMap;
        } catch (Exception e){
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("error", e.getMessage());
            return errorMap;
        }

    }

    private Map<String, Object> getResult(String authToken, String userId, int id){

        try {

            //Construction de l'url
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.speechmatics.com/v1.0/user/"+userId+"/jobs/"+id+"/transcript")
                .queryParam("auth_token", authToken);

            HashMap<String,Object> dummy = new HashMap<>();

            log.debug("URL " + builder.build().encode().toUri());

            //Envoi requete

            ResponseEntity<HashMap<String,Object>> conversion =
                restTemplate.getForEntity(builder.build().encode().toUri(), (Class<HashMap<String, Object>>)dummy.getClass());

            return conversion.getBody();
        } catch (HttpClientErrorException e){
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("error", e.getResponseBodyAsString());
            return errorMap;
        } catch (Exception e){
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("error", e.getMessage());
            return errorMap;
        }

    }

    private boolean isJobFinished(Map<String, Object> status){
        Object job = status.get("job");
        Map<String, Object> jobMap = (Map<String, Object>)job;
        return jobMap.get("check_wait") == null;
    }

}

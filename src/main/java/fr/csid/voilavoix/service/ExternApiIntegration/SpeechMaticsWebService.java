package fr.csid.voilavoix.service.ExternApiIntegration;

import fr.csid.voilavoix.config.LoggingConfiguration;
import fr.csid.voilavoix.domain.Audio;
import fr.csid.voilavoix.repository.AudioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.*;

/**
 * Created by mathieu on 02/03/17.
 */

@Service("smService")
public class SpeechMaticsWebService implements APIInterface{

    private RestTemplate restTemplate;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private AudioRepository audioRepository;

    @Value("${speechmatics.apikey}")
    private String smApiKey;

    @Value("${speechmatics.userId}")
    private String smUserId;

    @Value("${speechmatics.rootUrl}")
    private String rootUrl;



    private final Logger log = LoggerFactory.getLogger(LoggingConfiguration.class);


    public SpeechMaticsWebService(){
        this.restTemplate = new RestTemplate();
    }


    public Map<String, String> sendRequest(File file){
//        Audio audio = audioRepository.findOne(Long.valueOf(id));
//        Resource resource = resourceLoader.sgetResource("classpath:camus1.mp3");

        Map<String, String> exist = this.getExist(smApiKey, smUserId, file.getName());
        //TODO : Requete token et id
       if(exist != null){
           return exist;
       }

        try {
            return this.sendFileRequest(file, smApiKey, smUserId);
        } catch (Exception e){
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", e.toString());
            return errorMap;
        }
    }
    public Map<String, Object> getResult(int id){
        String authToken = smApiKey;
        String userId = smUserId;
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
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rootUrl+userId+"/jobs/")
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

    private Map<String, String> getExist(String authToken, String userId, String fileName){

        try {

            //Construction de l'url
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rootUrl+userId+"/jobs/")
                .queryParam("auth_token", authToken);

            HashMap<String,Object> dummy = new HashMap<>();

            log.debug("URL " + builder.build().encode().toUri());

            //Envoi requete

            ResponseEntity<HashMap<String,Object>> conversion =
                restTemplate.getForEntity(builder.build().encode().toUri(), (Class<HashMap<String, Object>>)dummy.getClass());

            List<Map<String, String>> jobs = (List<Map<String, String>>)conversion.getBody().get("jobs");
            for(Map<String, String> job : jobs){
                if(job.get("name").equals(fileName)){
                    return job;
                }
            }

            return null;
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
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rootUrl+userId+"/jobs/"+id)
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
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rootUrl+userId+"/jobs/"+id+"/transcript")
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

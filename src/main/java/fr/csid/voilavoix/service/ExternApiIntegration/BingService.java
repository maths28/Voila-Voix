package fr.csid.voilavoix.service.ExternApiIntegration;

import fr.csid.voilavoix.config.LoggingConfiguration;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * Created by mathieu on 02/03/17.
 */

@Service("bingService")
public class BingService {

    private RestTemplate restTemplate;

    @Autowired
    private ResourceLoader resourceLoader;

    private final Logger log = LoggerFactory.getLogger(LoggingConfiguration.class);


    public BingService(){
        this.restTemplate = new RestTemplate();
    }

    public Map<String, String> test(){

        String token =  this.getToken();
        Resource resource = resourceLoader.getResource("classpath:camus.wav");
        try {
            return this.getResponse(token, resource.getFile());
        } catch (Exception e){
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", e.toString());
            return errorMap;
        }
    }

    private String getToken() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
        httpHeaders.add(HttpHeaders.CONTENT_LENGTH, "0");
        //TODO: Recuperation clé en Requete HTTP
        httpHeaders.add("Ocp-Apim-Subscription-Key", "595166751fbb4ebbb71d5e1a95824c18");

        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        HttpEntity<String> response =
            restTemplate.postForEntity("https://api.cognitive.microsoft.com/sts/v1.0/issueToken", httpEntity, String.class);

        return response.getBody();
    }

    private Map<String, String> getResponse(String token, File file){
//        String auth = System.getenv("BING_AUTH");

//        log.info("Auth:\n" + auth);

        String authToken = "Bearer " + token;

        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);

        headers.setAccept(mediaTypes);

        headers.add(HttpHeaders.AUTHORIZATION, authToken);
        headers.add(HttpHeaders.HOST, "speech.platform.bing.com");
        headers.set(HttpHeaders.CONTENT_TYPE, "audio/wav;codec=\"audio/pcm\";");

        try {
            FileInputStream stream = new FileInputStream(file);
            HttpEntity<byte[]> entity = new HttpEntity<>(IOUtils.toByteArray(stream), headers);

            String requestId = UUID.randomUUID().toString();
            String testAppDeviceId = UUID.randomUUID().toString();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://speech.platform.bing.com/recognize")
                .queryParam("format", "json")
                .queryParam("appid", "D4D52672-91D7-4C74-8AD8-42B1D98141A5")
                .queryParam("locale", "fr-FR")
                .queryParam("device.os", "Linux")
                .queryParam("version", "3.0")
                .queryParam("scenarios", "ulm")
                .queryParam("instanceid", testAppDeviceId)
                .queryParam("requestid", requestId);

            HashMap<String,String> dummy = new HashMap<>();

            log.debug("URL " + builder.build().encode().toUri());

            ResponseEntity<HashMap<String,String>> conversion =
                restTemplate.exchange(builder.build().encode().toUri(),
                    HttpMethod.POST, entity, (Class<HashMap<String,String>>) dummy.getClass());

            return conversion.getBody();
        } catch (Exception e){
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", e.toString());
            return errorMap;
        }


    }

}

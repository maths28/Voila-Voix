package fr.csid.voilavoix.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by user on 21/02/2017.
 */


@RestController
@RequestMapping("/testres")
public class TestResource {

    @GetMapping("/json")
    public String getJson(){
        return "[ { \"_id\": \"58ac576f626d2ab54ed8b985\", \"index\": 0, \"age\": 38, \"name\": \"Harding Snow\" }, { \"_id\": \"58ac576ffdee2824c146fcdd\", \"index\": 1, \"age\": 38, \"name\": \"Dina Potts\" }, { \"_id\": \"58ac576f263c959f2d23c299\", \"index\": 2, \"age\": 40, \"name\": \"Cherie Petersen\" }, { \"_id\": \"58ac576f112e753d91cb88be\", \"index\": 3, \"age\": 34, \"name\": \"Gilliam Hampton\" }, { \"_id\": \"58ac576f008be59056062a91\", \"index\": 4, \"age\": 39, \"name\": \"Opal Weaver\" } ]";
    }


}

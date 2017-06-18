package fr.csid.voilavoix.service.ExternApiIntegration;

import java.io.File;
import java.util.Map;

/**
 * Created by mathieu on 09/06/17.
 */
public interface APIInterface {

    Map<String, String> sendRequest(File file);
    Map<String, Object> getResult(int id);

}

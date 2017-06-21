package com.mycompany.myapp.service.ExternApiIntegration;

import java.io.File;
import java.util.Map;


public interface APIInterface {

    Map<String, String> sendRequest(File file);

    Map<String, Object> getResult(int id);

}

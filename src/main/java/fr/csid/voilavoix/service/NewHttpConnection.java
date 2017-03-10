package fr.csid.voilavoix.service;

/**
 * Created by user on 20/02/2017.
 */
public class NewHttpConnection {
    private static NewHttpConnection ourInstance = new NewHttpConnection();

    public static NewHttpConnection getInstance() {
        return ourInstance;
    }

    private NewHttpConnection() {
    }

   public static void initConnection(){
       String url = "https://www.google.com/";


   }

}

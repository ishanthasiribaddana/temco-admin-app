/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco.tools;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;


/**
 *
 * @author USER
 */
public class GoogleCloudService {

    private Storage storage;

    public GoogleCloudService() {
        try {
            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
            storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Storage getStorage() {
        return storage;
    }

}

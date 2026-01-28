/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.primefaces.model.file.UploadedFile;
import java.util.ArrayList;
import java.util.List;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;

/**
 *
 * @author USER
 */
public class TemcoVerifications {

    public boolean nicVerification(UploadedFile file, String nicNo) {

        byte[] imageBytes = file.getContent();
        if (imageBytes != null && imageBytes.length > 0) {
            try {
                // Step 1: Convert image bytes to ByteString
                ByteString imgBytes = ByteString.copyFrom(imageBytes);

                // Step 2: Prepare the request for Google Vision API
                List<AnnotateImageRequest> requests = new ArrayList<>();
                Image img = Image.newBuilder().setContent(imgBytes).build();
                Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
                AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                        .addFeatures(feat)
                        .setImage(img)
                        .build();
                requests.add(request);

                // Step 3: Call Google Vision API
                try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
                    List<AnnotateImageResponse> responses = client.batchAnnotateImages(requests).getResponsesList();

                    for (AnnotateImageResponse res : responses) {
                        if (res.hasError()) {
                            System.out.printf("Error: %s%n", res.getError().getMessage());
                            return false;
                        }

                        // Step 4: Extract Text from Image and Compare with Input NIC
                        String detectedText = res.getTextAnnotationsList().get(0).getDescription();
                        System.out.println("Detected text: " + detectedText);

                        String extractedNic = extractSriLankanNic(detectedText);

                        if (extractedNic != null) {
                            System.out.println("Extracted NIC: " + extractedNic);

                            // Step 6: Compare Extracted NIC with Entered NIC
                            if (extractedNic.equals(nicNo)) {
                                System.out.println("NIC number matches the entered NIC number.");
                                return true;
                            } else {
                                System.out.println("NIC number does not match the entered NIC number.");
                                return false;
                            }
                        } else {
                            System.out.println("No valid Sri Lankan NIC number found in the image.");
                            return false;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("File is empty");
            return false;
        }
        return false;
    }

    private String extractSriLankanNic(String text) {
        // Pattern for Old NIC format (9 digits followed by 'V' or 'X')
        Pattern oldNicPattern = Pattern.compile("\\b\\d{9}[VX]\\b");
        // Pattern for New NIC format (12 digits)
        Pattern newNicPattern = Pattern.compile("\\b\\d{12}\\b");

        Matcher oldNicMatcher = oldNicPattern.matcher(text);
        Matcher newNicMatcher = newNicPattern.matcher(text);

        if (oldNicMatcher.find()) {
            return oldNicMatcher.group();
        } else if (newNicMatcher.find()) {
            return newNicMatcher.group();
        }
        return null; // No NIC found
    }

    
    
}

package lambda.service.impl;

import lambda.service.FileFilterService;

import java.io.File;

/**
 * Created by moriatry on 11/10/2015.
 *
 * Class that implements the fileFilterService interface and creates an
 */
public class FileFilterServiceImpl {

    public static void main(String[] args) {


        FileFilterService filterHtmlFile = new FileFilterService();

        //change location to your location where you have any HTML file
        File folder = new File("/Users/kanke/Desktop");
        File[] listOfFiles = folder.listFiles(filterHtmlFile);


        for (File file : listOfFiles) {
            System.out.println(file);
        }

    }


}

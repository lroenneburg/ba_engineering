package com.uhh.ba_roenneburg;

import com.uhh.ba_roenneburg.model.Decision;
import com.uhh.ba_roenneburg.model.DecisionSection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.io.IOException;
import java.util.ArrayList;

@SpringBootApplication
public class BaRoenneburgApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(BaRoenneburgApplication.class, args);

        DBController dbController = new DBController();
        dbController.addDecisionToDatabase(new Decision("testID2", "testECLI2", "TestCourtType2", "testformation2", "testdecdate",
                "testdocketnumber", "testdectype", new ArrayList<String>(), new ArrayList<String>(), "testtitle", "testgprinc",
                "testsonstosatz", new ArrayList<String>(), "testfact", new ArrayList<DecisionSection>(), "testdissopinion", "testurl.de", new ArrayList<String>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

        //DataDownloader dd = new DataDownloader();
        //WebScraper ws = new WebScraper();
        //DataMapper dm = new DataMapper();
    }

}

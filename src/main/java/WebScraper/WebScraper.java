package WebScraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;

public class WebScraper {


    public WebScraper() throws IOException {

        Document doc = Jsoup.connect("https://www.rechtsprechung-im-internet.de/jportal/portal/t/ylg/page/bsjrsprod.psml/media-type/html?action=controls.jw.MaxMinDocument&max=true").get();
        //https://www.rechtsprechung-im-internet.de/jportal/portal/t/nyc/page/bsjrsprod.psml/js_peid/Trefferliste/media-type/html?action=portlets.jw.ResultListFormAction&tl=true&IGNORE=true&currentNavigationPosition=26&numberofresults=3704&sortmethod=date&sortiern=OK&eventSubmit_doSkipback=1&forcemax=0001
        Elements elements = doc.select("a");
        System.out.println("hallo");
    }
}

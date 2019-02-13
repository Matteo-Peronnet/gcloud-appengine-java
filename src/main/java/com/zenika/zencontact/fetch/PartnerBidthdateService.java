package com.zenika.zencontact.fetch;


import com.google.appengine.api.urlfetch.*;

import java.net.URL;

public class PartnerBidthdateService {

    private static PartnerBidthdateService INSTANCE = new PartnerBidthdateService();

    public static PartnerBidthdateService getInstance() { return INSTANCE; }

    private static final String SERVICE_URL = "http://zenpartenaire.appspot.com/zenpartenaire";

    public String findBirthdate(String firstName, String lastName) {

        try {
            URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
            URL url = new URL(SERVICE_URL);
            HTTPRequest postRequest = new HTTPRequest(
                    url,
                    HTTPMethod.POST,
                    FetchOptions.Builder.withDeadline(30)
            );
            String payload = firstName + " " + lastName;
            postRequest.setPayload(payload.getBytes());

            HTTPResponse response = fetcher.fetch(postRequest);

            if(response.getResponseCode() != 200) {
                return null;
            }

            byte[] content = response.getContent();
            String birthdate = new String(content).trim();
            return birthdate;
        } catch (Exception e) {}
        return null;
    }
}

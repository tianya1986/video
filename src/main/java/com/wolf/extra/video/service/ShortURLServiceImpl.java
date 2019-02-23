package com.wolf.extra.video.service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.wolf.extra.video.VideoException;
import com.wolf.extra.video.entity.ShortEncodeRequest;
import com.wolf.extra.video.entity.ShortURL;

@Service("shortURLService")
public class ShortURLServiceImpl implements ShortURLService {

    @Override
    public ShortURL getShortURL(String url) throws VideoException {
        RestTemplate template = new RestTemplate();
        try {
            url = URLEncoder.encode(url, "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ShortEncodeRequest request = new ShortEncodeRequest();
        request.setUrlStr(url);
        request.setDomain("suo.im");
        request.setExpireType("1");

        String requstUrl = "http://create.suolink.cn/pageHome/createBySingle.htm";
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(requstUrl);
        UriComponents uriComponents = builder.build();
        URI uri = uriComponents.toUri();
        System.out.println("======================== requstUrl " + requstUrl);
        ShortURL reponseEntity = template.postForObject(uri, request, ShortURL.class);
        return reponseEntity;
    }

}

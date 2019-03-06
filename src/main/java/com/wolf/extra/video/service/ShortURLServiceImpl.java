package com.wolf.extra.video.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wolf.extra.video.VideoException;
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

		String requestUrl = "http://h5ip.cn/index/api??url=" + url;
		// ResponseEntity<String> reponseEntity =
		// template.getForEntity(requestUrl, String.class, new HashMap<>());
		ShortURL shortURL = new ShortURL();
		// shortURL.setData(reponseEntity.getBody());

		try {
			URL http = new URL(requestUrl);
			// 打开连接
			HttpURLConnection urlConnection = (HttpURLConnection) http
					.openConnection();

			if (200 == urlConnection.getResponseCode()) {
				// 得到输入流
				InputStream is = urlConnection.getInputStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while (-1 != (len = is.read(buffer))) {
					baos.write(buffer, 0, len);
					baos.flush();
				}
				String result = baos.toString("utf-8");
				shortURL.setData(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// String requestUrl = "http://h5ip.cn/index/api?url=" + url;
		// ResponseEntity<String> reponseEntity =
		// template.getForEntity(requestUrl, String.class, new HashMap<>());
		// ShortURL shortURL = new ShortURL();
		// shortURL.setData(reponseEntity.getBody());
		return shortURL;
	}

}

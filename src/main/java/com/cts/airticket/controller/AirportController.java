package com.cts.airticket.controller;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cts.airticket.vo.AirportVo;

@RestController
public class AirportController {


	@Value("${account.service.url}")
	String accountServiceUrl;

	@RequestMapping("/findAirportWithUserEntry/{userEntry}")
	List<AirportVo> findByAirportCodeContaining(@PathVariable String userEntry)
			throws ClientProtocolException, IOException {

		List<AirportVo> list = this.getApi(accountServiceUrl + "/findByCode/" + userEntry, HttpMethod.GET);

		if (CollectionUtils.isEmpty(list)) {
			list = this.getApi(accountServiceUrl + "/findByDescription/" + userEntry, HttpMethod.GET);
		}

		return list;
	}

	public <T> List<T> getApi(final String path, final HttpMethod method) {
		final RestTemplate restTemplate = new RestTemplate();
		final ResponseEntity<List<T>> response = restTemplate.exchange(path, method, null,
				new ParameterizedTypeReference<List<T>>() {
				});
		List<T> list = response.getBody();
		return list;
	}

}

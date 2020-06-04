package com.cts.airticket.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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

	@Value("${fare.per.kilometer:0.1}")
	float farePerKilometer;

	@Value("${authentication.token}")
	String authenticationToken;

	@RequestMapping("/findAirportWithUserEntry/{userEntry}")
	List<AirportVo> findByAirportCodeContaining(@PathVariable String userEntry)
			throws ClientProtocolException, IOException {

		List<AirportVo> list = this.getApi(accountServiceUrl + "/findByCode/" + userEntry, HttpMethod.GET);

		if (CollectionUtils.isEmpty(list)) {
			list = this.getApi(accountServiceUrl + "/findByDescription/" + userEntry, HttpMethod.GET);
		}

		return list;
	}

	@RequestMapping("/calculateOfferPrice/{origincode}/{destcode}")
	Double calculateOfferPrice(@PathVariable String origincode, @PathVariable String destcode) {

		List<Map<String, Object>> origins = this.getApi(accountServiceUrl + "/findByCode/" + origincode,
				HttpMethod.GET);
		List<Map<String, Object>> dests = this.getApi(accountServiceUrl + "/findByCode/" + destcode, HttpMethod.GET);

		Double originXCorordinate = Double.valueOf(origins.get(0).get("airportXCordinate").toString());
		Double originYCorordinate = Double.valueOf(origins.get(0).get("airportYCordinate").toString());
		Double destXCorordinate = Double.valueOf(dests.get(0).get("airportXCordinate").toString());
		Double destYCorordinate = Double.valueOf(dests.get(0).get("airportYCordinate").toString());

		double lon1 = Math.toRadians(originXCorordinate);
		double lon2 = Math.toRadians(destXCorordinate);
		double lat1 = Math.toRadians(originYCorordinate);
		double lat2 = Math.toRadians(destYCorordinate);

		// Haversine formula
		double dlon = lon2 - lon1;
		double dlat = lat2 - lat1;
		double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);

		double c = 2 * Math.asin(Math.sqrt(a));

		// Radius of earth in kilometers. Use 3956 // for miles
		double r = 6371;

		// calculate the result
		double offered = farePerKilometer * (c * r);
		return offered;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> getApi(final String path, final HttpMethod method) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", authenticationToken);
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		ResponseEntity<List> response = restTemplate.exchange(path, HttpMethod.GET, entity, List.class);
		List<T> list = response.getBody();
		return list;
	}

}

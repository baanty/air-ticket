package com.cts.airticket.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;



class AirportControllerTest {

	AirportController controller = new AirportController();
	
	@Test
	public void testAirFareCalculation() {
		    double x1 = 9834;
		    double x2 = 23;
		    double y1 = 1223;
		    double y2 = 35;
			double actualResult = controller.calulateFare(x1, x2, y1, y2);
			assertEquals(0, actualResult);
	}

}

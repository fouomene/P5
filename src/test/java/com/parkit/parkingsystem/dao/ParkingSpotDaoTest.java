package com.parkit.parkingsystem.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;


@ExtendWith(MockitoExtension.class)
public class ParkingSpotDaoTest {

	@Mock
	TicketDAO ticketDAO;

	@Mock
	ParkingSpotDAO parkingSpotDAO;

	@Mock
	InputReaderUtil inputReaderUtil;

	ParkingService parkingService;

	@BeforeEach
	public void setUp() throws Exception {
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		when(inputReaderUtil.readSelection()).thenReturn(1);
		ticketDAO = new TicketDAO();
		parkingSpotDAO = new ParkingSpotDAO();
	}

	@Test
	public void getNextAvailableSlotTest(){

		parkingService = new ParkingService(inputReaderUtil,parkingSpotDAO,ticketDAO);
		ParkingSpot parkingSpot = new ParkingSpot(1,ParkingType.CAR,false);
		parkingService.processIncomingVehicle();
		Ticket ticket = null;
		Boolean result = false;

		try {
			ticket = ticketDAO.getTicket(inputReaderUtil.readVehicleRegistrationNumber());
			result = parkingSpotDAO.updateParking(parkingSpot);
		}catch (Exception e){

		}
		assertEquals(true,result);
	}

}

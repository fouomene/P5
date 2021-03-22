package com.parkit.parkingsystem.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
public class TicketDaoTest {
	
	ParkingService parkingService;
	    
	@Mock
	TicketDAO ticketDAO;
	    
	@Mock
	InputReaderUtil inputReaderUtil;

	@Mock
	ParkingSpotDAO parkingSpotDAO;
	   
	@BeforeEach
	public void setUpPerTest() throws Exception {
	    when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		when(inputReaderUtil.readSelection()).thenReturn(1);  
		ticketDAO = new TicketDAO();
		ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();
	    }

	@Test
	public void checkOutIfSavedTicketExistsTest() {
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);
		when(parkingSpotDAO.updateParking(parkingSpot)).thenReturn(true);

		parkingService.processIncomingVehicle();
		Ticket ticket = new Ticket();
		try {
		ticket = ticketDAO.getTicket(inputReaderUtil.readVehicleRegistrationNumber());

		}catch (Exception e){

		}
		System.out.println(ticket.getVehicleRegNumber());
		assertEquals("ABCDEF",ticket.getVehicleRegNumber());
	   }
	 
	@Test
	public void updateTicketTest() {
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);
		parkingService.processIncomingVehicle();
		parkingService.processExitingVehicle();

		Ticket ticket = new Ticket();
			try {
				ticket = ticketDAO.getTicket(inputReaderUtil.readVehicleRegistrationNumber());
			}catch (Exception e){

		}
		assertNotNull(ticket.getOutTime());
	}
}

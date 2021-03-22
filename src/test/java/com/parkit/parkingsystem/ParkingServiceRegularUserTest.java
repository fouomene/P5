package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceRegularUserTest {

    private static ParkingService parkingService;

    @Mock
    private static InputReaderUtil inputReaderUtil;
    @Mock
    private static ParkingSpotDAO parkingSpotDAO;
    @Mock
    private static TicketDAO ticketDAO;

    @BeforeEach
    private void setUpPerTest() {
        try {
            when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");

            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
            Ticket ticket = new Ticket();
            ticket.setInTime(LocalDateTime.now().minusHours(1));
            ticket.setParkingSpot(parkingSpot);
            ticket.setVehicleRegNumber("ABCDEF");
            when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
            when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);

            when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);

            parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("Failed to set up test mock objects");
        }
    }
/*
    @Test
    public void processExitingRegularVehicleTest(){
        List<Ticket> tickets = new ArrayList<Ticket>();

        Ticket ticket = new Ticket();
        ticket.setInTime(LocalDateTime.now().minusHours(1));
        ticket.setVehicleRegNumber("ABCDEF");
        tickets.add(ticket);

        Ticket ticketBis = new Ticket();
        ticket.setInTime(LocalDateTime.now().minusHours(1));
        ticket.setVehicleRegNumber("ABCDEF");
        tickets.add(ticketBis);

        when(ticketDAO.getAllTickets(anyString())).thenReturn(tickets);

        parkingService.processExitingVehicle();
        verify(ticketDAO, Mockito.times(1)).getAllTickets(anyString());
        Ticket ticketRegular = ticketDAO.getTicket("ABCDEF");
        assertEquals(0.7125, ticketRegular.getPrice());
    }

    @Test
    public void processExitRegularVehicleTest1(){
        List<Ticket> tickets = new ArrayList<Ticket>();

        Ticket ticket = new Ticket();
        ticket.setInTime(LocalDateTime.now().minusMinutes(90));
        ticket.setVehicleRegNumber("ABCDEF");
        tickets.add(ticket);

        Ticket ticketBis = new Ticket();
        ticket.setInTime(LocalDateTime.now().minusMinutes(90));
        ticket.setVehicleRegNumber("ABCDEF");
        tickets.add(ticketBis);

        when(ticketDAO.getAllTickets(anyString())).thenReturn(tickets);

        parkingService.processExitingVehicle();//
        verify(ticketDAO, Mockito.times(1)).getAllTickets(anyString());
        Ticket ticketRegular = ticketDAO.getTicket("ABCDEF");
        assertEquals(1.425, ticketRegular.getPrice());
    }




    // Check out that a ticket with the correct fare is generated (5% discount)
    @Test
    public void processExitRegularVehicleTest2(){
        List<Ticket> tickets = new ArrayList<Ticket>();

        // First ticket 
        // Price for a bike = (1.5 hours - 30 min free) * 1 = 1
        Ticket ticket = new Ticket();
        ticket.setInTime(LocalDateTime.now().minusMinutes(90));
        ticket.setVehicleRegNumber("ABCDEF");
        tickets.add(ticket);

        // Second ticket
        // Price for a bike = (1.5 hours - 30 min free) * 1 * 5% discount = > 
        Ticket secondTicket = new Ticket();
        ticket.setInTime(LocalDateTime.now().minusMinutes(90));
       // ticket.setVehicleRegNumber("ABCDEF");
        tickets.add(secondTicket);

        // Get the list of all the tickets of the regular user
        when(ticketDAO.getAllTickets(anyString())).thenReturn(tickets);

        // Compare the price for 1H for a bike with 5% discount : 1.425, with the price on the ticket (which should be )
        parkingService.processExitingVehicle();
        verify(ticketDAO, Mockito.times(1)).getAllTickets(anyString());
        Ticket ticketRegularUser = ticketDAO.getTicket("ABCDEF");
        assertEquals(0.95, ticketRegularUser.getPrice());
    }


*/


}

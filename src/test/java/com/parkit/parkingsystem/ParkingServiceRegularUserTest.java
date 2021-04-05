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
            when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("TEST01");

            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
            Ticket ticket = new Ticket();
            ticket.setInTime(LocalDateTime.now().minusHours(1));
            ticket.setParkingSpot(parkingSpot);
            ticket.setVehicleRegNumber("TEST01");
            when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
            when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);

            when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);

            parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("Failed to set up test mock objects");
        }
    }

    // Check out if the system recognize a regular vehicle, and if the fare is correct : 30 min free + 5% discount
    // FARE : 1H - 30 min free = "30 min * BIKE.Fare - discount regular customer" => (0,5 * 1) - 5% = 0,475
    @Test
    public void processExitingRegularVehicleTest(){
        List<Ticket> tickets = new ArrayList<Ticket>();

        Ticket ticket = new Ticket();
        ticket.setInTime(LocalDateTime.now().minusHours(1));
        ticket.setVehicleRegNumber("TEST01");
        tickets.add(ticket);

        Ticket ticketBis = new Ticket();
        ticket.setInTime(LocalDateTime.now().minusHours(1));
        ticket.setVehicleRegNumber("TEST01");
        tickets.add(ticketBis);

        when(ticketDAO.getAllTickets(anyString())).thenReturn(tickets);

        parkingService.processExitingVehicle();
        verify(ticketDAO, Mockito.times(1)).getAllTickets(anyString());
        Ticket ticketRegular = ticketDAO.getTicket("TEST01");
        assertEquals(0.475, ticketRegular.getPrice());
    }

}

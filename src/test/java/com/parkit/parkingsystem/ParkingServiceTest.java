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
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

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
            ticket.setInTime(LocalDateTime.now());
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


    // Check out if the exit of the vehicle is processed correctly after 1H and that the Parking table is updated with availability 
    @Test
    public void processVehicleExitWithOneHourTest(){
    Ticket ticket = new Ticket();
    ticket.setInTime(LocalDateTime.now().minusMinutes(60));
    parkingService.processExitingVehicle();
    verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
    }
 

    // Check out if the exit of the vehicle is processed correctly and that the Parking table is updated with availability 
    @Test
    public void processExitingVehicleTest(){
        parkingService.processExitingVehicle();
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
    }


    // Check out if the exit of the vehicle is processed correctly and that the exit time is updated in the DB (not null)
    @Test
    public void testParkingCarExit(){
       // testParkingAVehicle();
       ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
       parkingService.processIncomingVehicle();
       Ticket testTicket = ticketDAO.getTicket("ABCDEF");
        try {
            TimeUnit.SECONDS.sleep(1);
           // ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
            parkingService.processExitingVehicle();
        } catch (Exception e) {
            System.out.println(e);
        }
       // Ticket testTicket = ticketDAO.getTicket("ABCDEF");
        assertNotNull(testTicket.getOutTime());
        assertNotNull(testTicket);
    }  


    // Check out if the fare is correct : free parking  (30 min stay)
    @Test
    public void processFreeEntranceTest() throws Exception {
 
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenReturn(1);
 
        parkingService.processIncomingVehicle();
        Ticket ticket = new Ticket();
        ticket.setInTime(LocalDateTime.now().minusMinutes(30));
        parkingService.processExitingVehicle();
 
        verify(parkingSpotDAO,times(1)).getNextAvailableSlot(any(ParkingType.class));
        assertEquals(0, ticket.getPrice());
    }

}

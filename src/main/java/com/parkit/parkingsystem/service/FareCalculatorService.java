package com.parkit.parkingsystem.service;

import java.time.LocalDateTime;
import java.time.Duration;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

  public static void calculateFare(Ticket ticket){
    if( (ticket.getOutTime() == null) || (ticket.getOutTime().isBefore(ticket.getInTime()))) { 
      throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
    }

    LocalDateTime inHour = ticket.getInTime();
    LocalDateTime outHour = ticket.getOutTime();

    // Get the difference between the two LocaleDateTime with Duration
    Duration duration = Duration.between(inHour, outHour);

    // Get the seconds from the duration and convert them in hours
    double durationInHours = (double) duration.toSeconds()/3600;

    switch (ticket.getParkingSpot().getParkingType()){
      case CAR: {
        // TERNARY IF
        // IF durationInHours < 0.5 THEN the price = 0 ELSE : durationInHours - 0.5 min free * FARE
        double price = (durationInHours < 0.5) ? 0: (durationInHours - 0.5) * Fare.CAR_RATE_PER_HOUR;
        ticket.setPrice(price);
        break;
      }
      case BIKE: {
        double price = (durationInHours < 0.5) ? 0: (durationInHours - 0.5) * Fare.BIKE_RATE_PER_HOUR;
        ticket.setPrice(price);
      break;
      }
    default: throw new IllegalArgumentException("Unkown Parking Type");
    }
  }

}
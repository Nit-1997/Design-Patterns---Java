package questions;

import java.sql.Time;
import java.util.*;

public class DesignParkingLot {
    /**
     * Requirement clarifications:
     *
     * Vehicle come In:  gets a parking spot assigned , and a ticket
     * Gets a  Ticket : contains information about the parking spot
     * Goes to the spot assigned : parks the car.
     * Takes the car to exit gate , makes the payment and leaves.
     *
     * Core entities:
     * ---------------------
     *
     * Vehicle : [S , M , L  ; vehicle number]
     *
     * Ticket  : [ entryTime, parking spot , rateHourly , rate24x7 ]
     *
     *                     {nearest}
     * Entrance Gate : [ find Parking Space , update parking space , generate ticket]
     *
     * Parking Spot : [id , isEmpty , vehicle , price , type ]
     *
     * Exit Gate : [ calculate cost , payment and update parking space]
     *
     *
     *
     * Bottom up :
     *
     *
     *
     *
     */

    enum VehicleType{
        BIKE,
        CAR
    }

    class EntryPoint {
        ParkingSpotManagerFactory parkingSpotFactory;
        ParkingSpotManager parkingSpotManager;
        Vehicle vehicle;

        EntryPoint(Vehicle v , VehicleType vehicleType , Set<ParkingSpot> parkingSpots) {
            parkingSpotFactory = new ParkingSpotManagerFactory();
            parkingSpotManager = parkingSpotFactory.getParkingSpotManager(vehicleType , parkingSpots);
            vehicle = v;
        }

        ParkingSpot findParkingSpot (ParkingStrategy parkingStrategy) {
            return parkingSpotManager.findSpot(parkingStrategy);
        }

        void bookSpot(ParkingSpot spot) {
            parkingSpotManager.parkVehicle(vehicle , spot);
        }

        Ticket generateTicket(ParkingSpot spot , Vehicle vehicle , long entryTimeInMillis) {
            return new Ticket(entryTimeInMillis ,vehicle ,  spot);
        }
    }

    class ExitPoint {
        ParkingSpotManagerFactory parkingSpotFactory;
        ParkingSpotManager parkingSpotManager;
        Vehicle vehicle;
        PricingStrategy pricingStrategy;

        ExitPoint(PricingStrategy pricingStrategy , Vehicle v , VehicleType vehicleType , Set<ParkingSpot> parkingSpots) {
            this.pricingStrategy = pricingStrategy;
            parkingSpotFactory = new ParkingSpotManagerFactory();
            parkingSpotManager = parkingSpotFactory.getParkingSpotManager(vehicleType , parkingSpots);
            vehicle = v;
        }

        int calculateCost(long exitTimeInMillis , Ticket ticket) {
            return pricingStrategy.calculateCost(exitTimeInMillis , ticket);
        }

        void makePayment(Ticket ticket) {
            int cost = this.calculateCost(System.currentTimeMillis() , ticket);
            parkingSpotManager.removeVehicle(vehicle , ticket.parkingSpot); //un park
            System.out.println("Payment Received of "+ cost + " $ successfully");
        }
    }


    interface PricingStrategy {
        int calculateCost(long exitTimeInMillis, Ticket ticket);
    }

    class HourlyPricingStrategy implements  PricingStrategy{
        int ratePerHr = 10;

        @Override
        public int calculateCost(long exitTimeInMillis, Ticket ticket) {
            long totalMillis = exitTimeInMillis - ticket.entryTimeInMillis;
            double hrs = Math.ceil((double) totalMillis / (1000*60*60));
            return (int) Math.ceil(hrs * this.ratePerHr);
        }
    }

    class FixedPricingStrategy implements  PricingStrategy{
        int ratePerDay = 20;

        @Override
        public int calculateCost(long exitTimeInMillis, Ticket ticket) {
            long totalMillis = exitTimeInMillis - ticket.entryTimeInMillis;
            double hrs = Math.ceil((double) totalMillis / (1000*60*60));
            if(hrs < 24) return ratePerDay;
            return (int) (Math.ceil(hrs/24) * ratePerDay);
        }
    }

    enum PricingStrategyType{
        FIXED,
        HOURLY
    }

    class pricingStrategyFactory {
        PricingStrategy getPricingStrategy(PricingStrategyType type) {
            PricingStrategy pricingStrategy;
            if(type.equals(PricingStrategyType.FIXED)) {
                pricingStrategy = new FixedPricingStrategy();
            }else if(type.equals(PricingStrategyType.HOURLY)) {
                pricingStrategy = new HourlyPricingStrategy();
            }else {
                throw new IllegalArgumentException("No such pricing strategy exists");
            }
            return pricingStrategy;
        }
    }


    class ParkingSpotManagerFactory {
        ParkingSpotManager getParkingSpotManager(VehicleType vehicleType , Set<ParkingSpot> parkingSpots) {
            ParkingSpotManager parkingSpotManager;
            if(vehicleType.equals(VehicleType.BIKE)) {
                 parkingSpotManager = new BikeParkingSpotManager(parkingSpots);
            }else if(vehicleType.equals(VehicleType.CAR)){
                 parkingSpotManager = new CarParkingSpotManager(parkingSpots);
            }else {
                throw new IllegalArgumentException("No such class exists !!!");
            }

            return parkingSpotManager;
        }
    }

    class ParkingSpotManager {
        Set<ParkingSpot> spots;
        ParkingSpotManager (Set<ParkingSpot> spots) {
            this.spots = spots;
        }

        ParkingSpot findSpot(ParkingStrategy strategy) {
            return strategy.findSpot(spots);
        }

        void addSpot(ParkingSpot spot) {
            spots.add(spot);
        }

        void removeSpot(ParkingSpot spot) {
            spots.remove(spot);
        }

        void parkVehicle(Vehicle v , ParkingSpot spot) {
            spot.park(v);
        }

        void removeVehicle(Vehicle v , ParkingSpot spot) {
            spot.unpark();
        }
    }


    class CarParkingSpotManager extends  ParkingSpotManager {
        CarParkingSpotManager(Set<ParkingSpot> spots) {
            super(spots);
        }
    }

    class BikeParkingSpotManager extends  ParkingSpotManager {
        BikeParkingSpotManager(Set<ParkingSpot> spots) {
            super(spots);
        }
    }

    interface ParkingStrategy {
        public ParkingSpot findSpot(Set<ParkingSpot> spots);
    }

    class nearToElevator implements  ParkingStrategy{
        @Override
        public ParkingSpot findSpot(Set<ParkingSpot> spots) {
            return null;
        }
    }

    class closeToGate implements  ParkingStrategy{
        @Override
        public ParkingSpot findSpot(Set<ParkingSpot> spots) {
            return null;
        }
    }

    class Ticket {
        long entryTimeInMillis;
        Vehicle vehicle;
        ParkingSpot parkingSpot;

        Ticket(long entryTime , Vehicle v , ParkingSpot spot) {
            entryTimeInMillis = entryTime;
            vehicle = v;
            parkingSpot = spot;
        }
    }


    class ParkingSpot {
        int id;
        boolean isEmpty;
        Vehicle vehicle;
        int price;

        void park(Vehicle v) {
            this.vehicle = v;
            isEmpty = false;
        }

        void unpark() {
            if(isEmpty) return;
            isEmpty = true;
            this.vehicle = null;
        }
    }

    interface Vehicle {
    }

    class Car implements Vehicle {
        String number;
    }

    class Bike implements Vehicle {
        String number;
    }

}

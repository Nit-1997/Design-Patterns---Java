package patterns;

import java.util.List;

/**
 * Used to avoid code duplication
 * If there are fixed strategies of a parent class method
 * child classes can just adopt one the strategies and instantiate the parent
 * This will avoid code duplication
 */
public class StrategyPattern {

    interface DriveStatergy {
        void drive();
    }

    class SportsDrive implements DriveStatergy{
        @Override
        public void drive() {
            System.out.println("Sports drive, max speed of 200mph , low gear ratio");
        }
    }

    class NormalDrive implements DriveStatergy{
        @Override
        public void drive() {
            System.out.println("Normal drive max speed of 100mph , high gear ratio");
        }
    }

    class Vehicle {
        DriveStatergy driveStatergy;
        Vehicle(DriveStatergy statergy) {
            this.driveStatergy = statergy;
        }
        // drive method of the strategy choosen by child class gets called.
        public void drive() {
            this.driveStatergy.drive();
        }
    }

    /**
     *  Following are 3 makes of the same car model
     */
    class Porche extends Vehicle {
        Porche() {
            super(new SportsDrive());
        }
    }

    class Bmw extends Vehicle {
        Bmw() {
            super(new SportsDrive());
        }
    }

    class Honda extends Vehicle {
        Honda() {
            super(new NormalDrive());
        }
    }

    // this will not work since rest are inner-classes to run this split the class this is just to give idea on the design pattern!
    public static void main(String[] args) {
        List<Vehicle> vehcles = List.of(
                new Porche(),
                new Bmw(),
                new Honda()
        );

        for(Vehicle v : vehcles) {
            v.drive(); // based on make the drive strategy is triggered
        }
    }

}

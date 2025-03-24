package patterns;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * Problem : When a temperature changes , fan out an alert to all weather stations to alert them
 *
 */
public class ObserverPatternExample {

    interface WSObservable {
        public void addSubscriber(WSObserver station);
        public void removeSubscriber(WSObserver station);
        public void notifySubscribers(int temp);
        public void updateTemp(int newTemp);
    }

    class WSObservableImpl implements  WSObservable{
        int temp;
        Set<WSObserver> weatherStations;

        WSObservableImpl(int initTemp){
            temp = initTemp;
            weatherStations = new HashSet<>();
        }

        @Override
        public void addSubscriber(WSObserver station){
            weatherStations.add(station);
        }

        @Override
        public void removeSubscriber(WSObserver station){
            weatherStations.remove(station);
        }

        @Override
        public void updateTemp(int newTemp) {
            if(newTemp == temp) return;
            temp = newTemp;
            notifySubscribers(temp);
        }

        @Override
        public void notifySubscribers(int temp){
            weatherStations.forEach(station -> station.email(temp));
        }
    }

    interface WSObserver {
        public void email(int latestTemp);
    }

    class WSObserverEmailImpl implements  WSObserver{
        String email;
        WSObservable tempObservable;

        WSObserverEmailImpl(String email , WSObservable tempObservable){
            this.email = email;
        }

        @Override
        public void email(int temp){
            System.out.println("temp changed to "+temp+" : "+email+" notified");
        }

    }

    class WSObserverMobileImpl implements  WSObserver{
        String phoneNumber;
        WSObservable tempObservable;

        WSObserverMobileImpl(String phoneNumber , WSObservable tempObservable){
            this.phoneNumber = phoneNumber;
            this.tempObservable = tempObservable;
        }

        @Override
        public void email(int temp){
            System.out.println("temp changed to "+temp+" : "+phoneNumber+" notified");
        }

    }

    public static void main(String[] args) {
        WSObservable temp = new WSObservableImpl(34);

        WSObserver nitinEmailSub = new WSObserverEmailImpl("ntnbhat9@gamil.com" , temp);
        WSObserver nitinMobileSub = new WSObserverMobileImpl("9891002934" , temp);

        temp.addSubscriber(nitinEmailSub);
        temp.addSubscriber(nitinMobileSub);

        temp.updateTemp(23);
        // notification is sent to all via their subscribed means

    }



}

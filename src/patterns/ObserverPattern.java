package patterns;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implement Notify me : Notification to all subscribed users
 *
 * Observable Interface : Generic interface to register watchers , fan-out notification on state change, remove subscription
 * Observer : Client subscribing to observable entities / products
 *
 *
 *
 */
public class ObserverPattern {

    interface IObservable {
        public void registerObserver(IObserver observer);
        public void removeObserver(IObserver observer);
        public void notification();
        public void changeDataState(boolean stateUpdate);
    }

    class Product implements IObservable{
        Set<IObserver> observersList;
        boolean isInStock;

        Product() {
            observersList = new HashSet<>();
            isInStock = true;
        }

        @Override
        public void registerObserver(IObserver observer){
            observersList.add(observer);
        }

        @Override
        public void removeObserver(IObserver observer){
            observersList.remove(observer);
        }

        @Override
        public void notification(){
            observersList.forEach(observer -> {
                observer.sendEmail();
            });
        }

        @Override
        public void changeDataState(boolean isInStock) {
            if(isInStock != this.isInStock) {
                this.isInStock = isInStock;
                this.notification();
            }
        }

    }

    interface IObserver {
        public void sendEmail(); //action
    }

    class User implements IObserver {
        String email;

        @Override
        public void sendEmail(){
            System.out.println("product is available notification sent to "+email+" successfully");
        }
    }




}

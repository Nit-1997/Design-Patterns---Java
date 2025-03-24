package patterns;

/**
 * Taking an example of Pizza builder
 *
 */
public class DecoratorPattern {

    abstract class BasePizza{
        public abstract int cost();
    }

    class Farmhouse extends BasePizza {

        @Override
        public int cost(){
            return 200;
        }
    }

    class ChickenOverloaded extends BasePizza {

        @Override
        public int cost(){
            return 300;
        }
    }

    abstract class ToppingDecorator extends BasePizza {
      // decorator
    }

    class ExtraCheese extends ToppingDecorator {
        BasePizza basePizza;

        public ExtraCheese(BasePizza pizza) {
            this.basePizza = pizza;
        }

        @Override
        public int cost() {
            return this.basePizza.cost() + 20;
        }
    }

    class Mushroom extends ToppingDecorator {
        BasePizza basePizza;

        public Mushroom(BasePizza pizza) {
            this.basePizza = pizza;
        }

        @Override
        public int cost() {
            return this.basePizza.cost() + 40;
        }
    }


    public static void main(String[] args) {
        BasePizza pizza = new Mushroom(new ExtraCheese(new ChickenOverloaded()));
        System.out.println(pizza.cost());
    }


}

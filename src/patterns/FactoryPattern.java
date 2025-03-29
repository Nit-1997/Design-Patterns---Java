package patterns;

public class FactoryPattern {

    interface Shape{
        public void draw();
    }

    class Circle implements Shape{
        @Override
        public void draw() {
            System.out.println("Draws circle");
        }
    }

    class Square implements Shape {
        @Override
        public void draw() {
            System.out.println("Draws Square");
        }
    }

    class Rectangle implements Shape {
        @Override
        public void draw() {
            System.out.println("Draws Rectangle");
        }
    }


    class ShapeFactory{
        Shape getShape(String shape){
            return switch (shape) {
                case "CIRCLE" -> new Circle();
                case "RECTANGLE" -> new Rectangle();
                default -> new Square();
            };
        }
    }


    public static void main(String[] args) {
         ShapeFactory shapeFactory = new ShapeFactory();
         Shape circle = shapeFactory.getShape("CIRCLE");
         circle.draw();
    }

}

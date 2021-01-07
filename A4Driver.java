import java.awt.*;
import java.lang.Object;
import java.util.*;
import java.io.File;

//given class
public class A4Driver {

	

	    public static void main(String[] args) throws Exception {

	        ShapeList shapeList = new ShapeList();
	        Scanner scanner = new Scanner(new File("TestData.txt"));

	        // skip file header
	        String line = scanner.nextLine();

	        while (scanner.hasNextLine()) {

	            boolean result = false;
	            Shape shape = null;

	            line = scanner.nextLine();

	            String[] lineParts = line.split(",");

	            String[] dimensions = lineParts[2].split("-");

	            try {

	                switch (lineParts[3].toLowerCase()) {

	                    case "rectangle":
	                        shape = new Quadrilateral(Integer.parseInt(lineParts[0]), lineParts[3], lineParts[4], getColor(lineParts[1]), Double.parseDouble(dimensions[0]), Double.parseDouble(dimensions[1]));
	                        break;
	                    case "square":
	                        shape = new Quadrilateral(Integer.parseInt(lineParts[0]), lineParts[3], lineParts[4], getColor(lineParts[1]), Double.parseDouble(dimensions[0]), Double.parseDouble(dimensions[1]));
	                        break;
	                    case "cuboid":
	                        shape = new Quadrilateral3D(Integer.parseInt(lineParts[0]), lineParts[3], lineParts[4], getColor(lineParts[1]), Double.parseDouble(dimensions[0]), Double.parseDouble(dimensions[1]), Double.parseDouble(dimensions[2]));
	                        break;
	                    case "cube":
	                        shape = new Quadrilateral3D(Integer.parseInt(lineParts[0]), lineParts[3], lineParts[4], getColor(lineParts[1]), Double.parseDouble(dimensions[0]), Double.parseDouble(dimensions[1]), Double.parseDouble(dimensions[2]));
	                        break;
	                    default:
	                        System.err.println("Unrecognized shape, skipping: " + line);
	                }

	                if (shape != null) {

	                    result = shapeList.add(shape);

	                    if (!result)
	                        System.err.println("Failed to add shape, skipping: " + line);
	                }
	            }
	            catch (Exception ex) {
	                System.err.println("   Duplicate shape, skipping: " + line);
	                continue;
	            }
	        }

	        TreeSet<Shape2D> set2D = shapeList.get2DShapes();
	        System.out.printf("There are %d 2-Dimentional shapes%n", set2D.size());

	        TreeSet<Shape3D> set3D = shapeList.get3DShapes();
	        System.out.printf("There are %d 3-Dimentional shapes%n", set3D.size());

	        shapeList.printFormatted();

	        System.out.printf("%d rows%n", shapeList.getSize());
	        scanner.close();
	    }

	    private static Color getColor(String strColor) {

	        switch (strColor.toLowerCase()) {
	            case "black":
	                return Color.BLACK;
	            case "blue":
	                return Color.BLUE;
	            case "green":
	                return Color.GREEN;
	            case "red":
	                return Color.RED;
	            case "white":
	                return Color.WHITE;
	        }
	        return null;
	    }
	}


//shape class that implements comparable for the compareto method in the subclasses
 abstract class Shape implements Comparable<Shape>{
	 //member variables
	 private final int id;
	 private final String name;
	 private final String description;
	 private Color color;
	 
	 
	 //nondefault constructor
	 public Shape(int id, String name, String description, Color color) {
		 this.id = id;
		 this.name = name;
		 this.description = description;
		 this.color = color;
	 }
	 
	 //abstract variables
	 public abstract double area();
	 
	 public abstract double perimeter();
	 
	 //toString method which prints out the member variables
	 public String toString() {

		 String variables = String.valueOf(id) + " " + name + " " + description + " " + getColor();
		 return variables;
	 }
	 
	 //getter member functions
	 public  int getId() {
		 return id;
	 }
	 
	 public String getName() {
		 return name;
	 }
	 
	 public String getColor() {
		 String colorstr = null;
		 if(Color.BLACK.equals(color))
		 {
			 colorstr = "Black";
		 }
		 else if(Color.WHITE.equals(color))
		 {
			 colorstr = "White";
		 }
		 else if(Color.BLUE.equals(color))
		 {
			 colorstr = "Blue";
		 }
		 else if(Color.GREEN.equals(color))
		 {
			 colorstr = "Green";
		 }
		 else if(Color.RED.equals(color))
		 {
			 colorstr = "Red";
		 }
		 return colorstr;
	 }
	 public String getDescription() {
		 return description;
	 }
	//abstract function to be implemented in child classes
	protected abstract Object getDimensions();
	 
	
 }
 //classes that inherits from shape
 abstract class Shape2D extends Shape{
	 //member variables
	 public final double height;
	 public final double width;
	 
	 //nondefault constructor that inherits from nondefault constructor in parent class
	 //also assigns two new variables
	 public Shape2D(int id, String name, String description, Color color, 
			 double height, double width){
		 super(id, name, description, color);
		 this.height = height;
		 this.width = width;
	 }
	 
	 //implementation of parent abstract clas
	 //returns height and width as a String 
	 public String getDimensions() {
		 return  "$" + height + ":" + width;
	 }
	 
	 //implementation of parent abstract method
	 //checks whether two shapes are the same
	 public int compareTo(Shape shape) {
		 if(shape instanceof Shape2D) {
			Shape2D shape2D = (Shape2D)shape;
			 if(this.getName().equals(shape2D.getName()))
			 {	if(this.height == shape2D.height ) {
				 if(this.width == shape2D.width) {
					 return 0;
				 }
					
				 
			 }
				
			 }
		 }
			 return -1;
	 }
	 //overriden to string method which inherits from the parent tostring method
	 //with inclusion of dimensions
	 @Override
	 public String toString(){
		 return super.toString() + " " + height + " " + width;
	 }
 }
 
 //Shape3D class that inherits from Shape2D
 abstract class Shape3D extends Shape2D{
	 
	 //member variable
	 public final double length;
	 
	 //non default constructor that uses parent non default constructor
	 //also asigns the length for the class
	 Shape3D(int id, String name, String description,
			 Color color, double height, double width, double length){
		 super(id, name, description, 
				 color, height, width);
		 this.length = length;
	 }
	 
	 //overriden compareTo method for the shape3D class
	 //checks whether other object is from the same class
	 @Override
	 public int compareTo(Shape shape) {
		 if(shape instanceof Shape3D) {
			 Shape3D shape3D = (Shape3D)shape;
			 if(shape3D.height == this.height) {
				 if(shape3D.width == this.width) {
					 if(shape3D.length == this.length) {
						 return 0;
					 }
						 
				 }
			 }
		 }
		 return -1;
	 }
	 
	 //overriden toString variable that returns all the variables as a space delimeted string
	 @Override
	 public String toString(){
		 return super.toString() + " " + length;
	 }
	 
	 //returns length width and height as a colon delimeted string
	 
	 @Override
	 public String getDimensions() {
		 return super.getDimensions() + ":" + length;
	 }
 }
 
 
 //
 class Quadrilateral extends Shape2D{
	 
	 
	 //non default constructor that uses parent non default constructor
	 Quadrilateral(int id, String name, String description, Color color, 
			 double height, double width){
		 super(id, name, description, color, height, width);
	 }
	 
	 //overriden area returns area of a quadtrilateral
	 @Override
	 public double area() {
		 return width * height;
	 }
	 
	 //overriden perimeter 
	 @Override
	 public double perimeter() {
		 return 2 * (width + height);
	 }
 }
 
 class Quadrilateral3D extends Shape3D {
	 
	 //non default constructor for quadrilateral that inherits from Shape3D
	 //uses Shape3D non default constructor
	 Quadrilateral3D(int id, String name, String description,
			 Color color, double height, double width, double length){
		 super(id, name, description, 
				 color, height, width, length);
	 }
	 
	 //overrides area and now also multiplies the width
	 @Override
	public double area() {
		 return length * width * height;
	 }
	 
	 //overrides perimeter and adapts the method for a 3D quadrilateral
	 @Override
	 public double perimeter() {
		 return 2 * (width * height + width * length + length * height);
	 }
	 
	
 }
 
 class ShapeList {
	
	//member variable
	 TreeSet<Shape> setShapes;
 
 	//non default constructor
 	ShapeList() {
 		 
 		setShapes = new TreeSet<Shape>();
 	}		 
 	
 	//add method that checks whether there is duplicate of shape
 	//if there is it throws an exception and returns false
 	//if not it adds the object into setShapes and returns true
	 public boolean add(Shape shape) throws Exception{
		 boolean bool = false; 
		 if(setShapes.contains(shape))
			 throw new Exception("Duplicate object");
		 else {
		 setShapes.add(shape);
		 bool = true;
		 }
		 return bool;
	 }
	 
	 //returns a new treeset with elements of objects from Shape2D
	 //iterates through setShapes and adds all Shape2D objects into the new treeset object
	 //before it is returned
	 public TreeSet<Shape2D> get2DShapes() {
		 
		 TreeSet<Shape2D> ReturnTree = new TreeSet<Shape2D>();
		 for(Shape value :setShapes){
			 if(value instanceof Shape2D) {
				 if(value instanceof Shape3D) {
					 
				 }
				 else {
				 ReturnTree.add((Shape2D)value);
				 }
			 }
		 }
		 return ReturnTree;
	 }
	 
	 //returns a new treeset with elements of objects from Shape3D
	 //iterates through setShapes and adds all Shape3D objects into the new treeset object
	 //before it is returned
	 public TreeSet<Shape3D> get3DShapes() {
		 
		 TreeSet<Shape3D> ReturnTree = new TreeSet<Shape3D>();
		 for(Shape value :setShapes) {
			 if(value instanceof Shape3D) {
				 ReturnTree.add((Shape3D)value);
			 }
		 }
		 return ReturnTree;
	 }
	 
	
	 //prints a formatted chart containing member variables from the shapes in the setShape treeset
	 public void printFormatted() {
		 System.out.println("+------+-------------+-------+-----------------------+-------------------+ ");
		 System.out.printf("| %-5s| %-12s| %-6s| %-22s| %-18s|"
				 , "ID", "Name", "Color", "Dimensions", "Description");
		 
		 for(Shape value :setShapes) {
			 System.out.println("\n+------+-------------+-------+-----------------------+-------------------+ ");
			 System.out.printf("| %-5s| %-12s| %-6s| %-22s| %-18s|", value.getId(), value.getName(), value.getColor(), value.getDimensions(), value.getDescription());
		 }
		 System.out.print("\n+------+-------------+-------+-----------------------+-------------------+ ");
	 }
	 //returns the size of treeset setShapes
	 public int getSize() {
		 return setShapes.size();
	 }
	 
	
	 
	 
 }
 
 
//import java.io.*;
//import java.util.*;
//
//public class copySnapp{
//    static int totalSnappIncome = 0;
//    public static void main(String[] args) throws FileNotFoundException {
//        getInput();
//    }
//
//    static void getInput() throws FileNotFoundException {
//        // getting input in input.txt
//        // setting out put in output.txt
//
//        Reader inputReader = new FileReader("input.txt");
//        Scanner input = new Scanner(inputReader);
//
//        System.setOut(new PrintStream(new FileOutputStream("output.txt")));
//
//        while (true){
//            String command = input.nextLine();
//            // fixing the end situation
//            if (command.equals("END")){
//                break;
//            }
//            // splitting the command into parts to recognize the first part
//            String[] parts = command.split(" ");
//
//            // analyze and directing the command type
//            switch (parts[0]){
//                case "ADD-DRIVER":{
//                    addDriver(parts);
//                    break;
//                }
//                case "CREATE-ORDER":{
//                    createOrder(parts);
//                    break;
//                }
//                case "ASSIGN-NEXT-ORDER":{
//                    assignNextOrder(parts);
//                    break;
//                }
//                case "GET-DRIVER":{
//                    getDriver(parts);
//                    break;
//                }
//                case "ORDER-UPDATE":{
//                    orderUpdate(parts);
//                    break;
//                }
//                case "GET-ORDER":{
//                    getOrder(parts);
//                    break;
//                }
//                case "GET-ORDER-LIST":{
//                    getOrderList(parts);
//                    break;
//                }
//                case "GET-DRIVER-LIST":{
//                    getDriverList(parts);
//                    break;
//                }
//                case "GET-NEAR-DRIVER":{
//                    getNearDriver(parts);
//                    break;
//                }
//                case "GET-CNT-ORDER":{
//                    getCntOrder(parts);
//                    break;
//                }
//                case "GET-NEAREST-PENDING-ORDER":{
//                    getNearestPendingOrder(parts);
//                    break;
//                }
//                case "GET-COMPANY":{
//                    System.out.println(totalSnappIncome);
//                }
//            }
//        }
//    }
//
//    private static void createOrder(String[] parts) {
//        int startPositionX = Integer.parseInt(parts[2].substring(1, parts[2].indexOf(",")));
//        int startPositionY = Integer.parseInt(parts[3].substring(0, parts[3].indexOf(")")));
//        int finishPositionX = Integer.parseInt(parts[4].substring(1, parts[4].indexOf(",")));
//        int finishPositionY = Integer.parseInt(parts[5].substring(0, parts[5].indexOf(")")));
//        if (startPositionX == finishPositionX && startPositionY == finishPositionY){
//            System.out.println("invalid order");
//            return;
//        }
//        Order order = new Order(
//                parts[1],
//                startPositionX,
//                startPositionY,
//                finishPositionX,
//                finishPositionY
//        );
//        System.out.println(order.orderID);
//    }
//
//    private static void addDriver(String[] parts){
//        if (Driver.checkIfDriverExists(parts[1])){
//            System.out.println("user previously added");
//        }
//        else {
//            Driver driver = new Driver(
//                    parts[1],
//                    Integer.parseInt(parts[2].substring(1, parts[2].indexOf(","))),
//                    Integer.parseInt(parts[3].substring(0, parts[3].indexOf(")"))),
//                    parts[4]
//            );
//            System.out.println("user added successfully");
//        }
//    }
//
//    private static void assignNextOrder(String[] parts) {
//        Driver driver = Driver.findDriver(parts[1]);
//        if (driver == null){ // if driver does not exist
//            System.out.println("invalid driver name");
//            return;
//        }
//        if (!driver.isFree){ // if driver is busy
//            System.out.println("driver is already busy");
//            return;
//        }
//        if (Order.pendingList.size() == 0 || !Order.checkTypeInPending(driver.serviceCategory)){ // if there is no pending order
//            System.out.println("there is no order right now");
//            return;
//        }
//
//        Order order = Order.findBestOrder(driver);
//        System.out.println(order.orderID + " assigned to " + driver.username);
//        driver.isFree = false;
//        driver.order = order;
//        order.driver = driver;
//        order.status = "ARRIVED";
//        Order.pendingList.remove(order);
//    }
//
//    private static void getDriver(String[] parts) {
//        Driver driver = Driver.findDriver(parts[1]);
//        if (driver == null){ // if driver does not exist
//            System.out.println("invalid driver name");
//            return;
//        }
//        String status = driver.isFree ? "FREE" : "BUSY";
//        System.out.println(status + " (" + driver.position[0] + ", " + driver.position[1] + ") " + driver.credit);
//    }
//
//    private static void orderUpdate(String[] parts) {
//        Driver driver = Driver.findDriver(parts[2]);
//        if (driver == null){ // if driver does not exist
//            System.out.println("invalid driver name");
//            return;
//        }
//        int orderID = Integer.parseInt(parts[3]);
//        if (driver.order.orderID != orderID){ // if order id is not correct
//            System.out.println("wrong order-id");
//            return;
//        }
//        String status = parts[1];
//        if (checkStatusChange(driver.order.status, status)){
//            System.out.println("status changed successfully");
//            driver.order.status = status; //todo: check if the order in orders list is changed or not
//            if (status.equals("DELIVERED")){
//                driver.credit += driver.order.cost * 0.8;
//                totalSnappIncome += driver.order.cost * 0.2;
//                driver.position[0] = driver.order.finishingPosition[0];
//                driver.position[1] = driver.order.finishingPosition[1];
//                driver.isFree = true;
//                driver.order = null;
//            }
//            else if (status.equals("PICKUP")){
//                driver.position[0] = driver.order.finishingPosition[0];
//                driver.position[1] = driver.order.finishingPosition[1];
//            }
//        }
//        else {
//            System.out.println("invalid status");
//        }
//    }
//
//    private static void getOrder(String[] parts) {
//        Order order = Order.findOrder(Integer.parseInt(parts[1]));
//        if (order == null){
//            System.out.println("invalid order");
//            return;
//        }
//        String status = order.status;
//        String driverName = order.driver == null ? "None" : order.driver.username;
//        String cost = String.valueOf(order.cost);
//
//        System.out.println(status + " " + driverName + " " + cost);
//    }
//
//    private static void getOrderList(String[] parts) {
//        Order.printOrdersWithStatus(parts[1]);
//    }
//
//    private static void getDriverList(String[] parts) {
//        Driver.printDriversWithServiceCategory(parts[1]);
//    }
//
//    private static void getNearDriver(String[] parts) {
//        int[] targetPosition = new int[2];
//        targetPosition[0] = Integer.parseInt(parts[1].substring(1, parts[1].indexOf(',')));
//        targetPosition[1] = Integer.parseInt(parts[2].substring(0, parts[2].indexOf(')')));
//        int count = Integer.parseInt(parts[3]);
//        Driver.getNearDrivers(targetPosition, count);
//    }
//
//    private static void getCntOrder(String[] parts) {
//        int x = Integer.parseInt(parts[1].substring(1, parts[1].indexOf(',')));
//        int y = Integer.parseInt(parts[2].substring(0, parts[2].indexOf(')')));
//        int radius = Integer.parseInt(parts[3]);
//        boolean isStartPosition = parts[4].equals("START");
//
//        int count = Order.getCountOfOrders(x, y, radius, isStartPosition);
//        System.out.println(count);
//    }
//
//    private static void getNearestPendingOrder(String[] parts) {
//        int x = Integer.parseInt(parts[1].substring(1, parts[1].indexOf(',')));
//        int y = Integer.parseInt(parts[2].substring(0, parts[2].indexOf(')')));
//
//        Order order = Order.getNearestPendingOrder(x, y);
//        if (order == null){
//            System.out.println("None");
//        }
//        else {
//            System.out.println(order.orderID);
//        }
//    }
//
//    private static boolean checkStatusChange(String firstStatus, String destStatus) {
//        switch (firstStatus){
//            case "ARRIVED":{
//                return destStatus.equals("PICKUP");
//            }
//            case "PICKUP":{
//                return destStatus.equals("DELIVERED");
//            }
//
//        }
//        return false; // todo : maybe first status be something else
//
//    }
//}
//
//
//
//class Driver {
//    static List<Driver> drivers = new ArrayList<>();
//    String username;
//    String serviceCategory;
//    int credit;
//    int[] position = new int[2];
//    boolean isFree;
//    Order order;
//
//    Driver(String username, int x, int y, String serviceCategory){
//        this.username = username;
//        this.position[0] = x;
//        this.position[1] = y;
//        this.serviceCategory = serviceCategory;
//        this.credit = 0;
//        this.isFree = true;
//        drivers.add(this);
//    }
//
//    static boolean checkIfDriverExists(String username){
//        for (Driver driver : drivers) {
//            if (driver.username.equals(username)){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public static Driver findDriver(String username) {
//        for (Driver driver : drivers) {
//            if (driver.username.equals(username)){
//                return driver;
//            }
//        }
//        return null;
//    }
//
//    public static void printDriversWithServiceCategory(String status) {
//        boolean free = status.equals("FREE");
//        boolean isNone = true;
//        for (Driver driver : drivers) {
//            if (driver.isFree == free){
//                isNone = false;
//                System.out.print(driver.username + " ");
//            }
//        }
//        if (isNone){
//            System.out.println("None");
//        }
//        else {
//            System.out.println();
//        }
//    }
//
//    public static void getNearDrivers(int[] targetPosition, int count) {
//        // todo : make this method more efficient if needed
//        Map<Driver, Integer> driverDistanceMap = new HashMap<>();
//        for (Driver driver : drivers) {
//            if (driver.isFree){
//                int distance = Order.calculateDistance(
//                        targetPosition[0],
//                        targetPosition[1],
//                        driver.position[0],
//                        driver.position[1]
//                );
//                driverDistanceMap.put(driver, distance);
//            }
//        }
//        // sorting the map by value form low to high
//        List<Map.Entry<Driver, Integer>> list = new LinkedList<>(driverDistanceMap.entrySet());
//        list.sort(Map.Entry.comparingByValue());
//        List<Driver> nearDrivers = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            if (i > list.size() - 1){
//                break;
//            }
//            System.out.print(list.get(i).getKey().username + " ");
//        }
//        System.out.println();
//    }
//
//}
//
//class Order {
//    static List<Order> orders = new ArrayList<>();
//    static List<Order> pendingList = new ArrayList<>();
//    String serviceCategory;
//    int[] startingPosition = new int[2];
//    int[] finishingPosition = new int[2];
//    int cost;
//    int orderID;
//    String status;
//    Driver driver;
//
//    Order(String serviceCategory, int startX, int startY, int finishX, int finishY){
//        this.serviceCategory = serviceCategory;
//        this.startingPosition[0] = startX;
//        this.startingPosition[1] = startY;
//        this.finishingPosition[0] = finishX;
//        this.finishingPosition[1] = finishY;
//        this.cost = calculateCost(startX, startY, finishX, finishY, serviceCategory);
//        this.status = "PENDING";
//        orders.add(this);
//        pendingList.add(this);
//        this.orderID = orders.size();
//    }
//
//    public static boolean checkTypeInPending(String serviceCategory) {
//        for (Order order : pendingList) {
//            if (order.serviceCategory.equals(serviceCategory)){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public static Order findBestOrder(Driver driver) {
//        int[] position = driver.position;
//        // finding the nearest order
//        int minDistance = Integer.MAX_VALUE;
//        Order nearestOrder = null;
//        for (Order order : pendingList) {
//            if (order.serviceCategory.equals(driver.serviceCategory)){
//                int distance = calculateDistance(position[0], position[1], order.startingPosition[0], order.startingPosition[1]);
//                if (distance < minDistance){
//                    minDistance = distance;
//                    nearestOrder = order;
//                }
//            }
//        }
//        return nearestOrder;
//    }
//
//    private static int calculateCost(int startX, int startY, int finishX, int finishY, String serviceCategory){
//        // calculating the k
//        int k = kCalculator(serviceCategory);
//        int cost = k + calculateDistance(startX, startY, finishX, finishY);
//        return cost * 100;
//    }
//
//    private static int kCalculator(String serviceCategory) {
//        int counter = 0;
//        for (Order order : pendingList) {
//            if (order.serviceCategory.equals(serviceCategory)){
//                counter++;
//            }
//        }
//        return ++counter;
//    }
//
//
//    public static int calculateDistance(int startX, int startY, int finishX, int finishY){
//        return Math.abs(startX - finishX) + Math.abs(startY - finishY);
//    }
//
//    public static Order findOrder(int orderID) {
//        for (Order order : orders) {
//            if (order.orderID == orderID){
//                return order;
//            }
//        }
//        return null;
//    }
//
//    public static void printOrdersWithStatus(String status) {
//        boolean isNone = true;
//        for (Order order : orders) {
//            if (order.status.equals(status)){
//                isNone = false;
//                System.out.print(order.orderID + " ");
//            }
//        }
//        if (isNone){
//            System.out.println("None");
//        }
//        else {
//            System.out.println();
//        }
//    }
//
//    public static int getCountOfOrders(int x, int y, int radius, boolean isStartPosition) {
//        int count = 0;
//        for (Order order : orders) {
//            int[] position = isStartPosition ? order.startingPosition : order.finishingPosition;
//            int distance = calculateDistance(x, y, position[0], position[1]);
//            if (distance <= radius){
//                count++;
//            }
//        }
//        return count;
//    }
//
//    public static Order getNearestPendingOrder(int x, int y) {
//        int minDistance = Integer.MAX_VALUE;
//        Order nearestOrder = null;
//        for (Order order : pendingList) {
//            int distance = calculateDistance(x, y, order.startingPosition[0], order.startingPosition[1]);
//            if (distance < minDistance){
//                minDistance = distance;
//                nearestOrder = order;
//            }
//        }
//        return nearestOrder;
//    }
//}
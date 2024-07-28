import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ShortestPathFinder {
    static Map<String, Map<String, Integer>> graph = new HashMap<>();
    static JTextArea outputTextArea; // JTextArea to display output

    public static void main(String[] args) {
        initializeGraph();

        SwingUtilities.invokeLater(ShortestPathFinder::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Shortest Path Finder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); // Using absolute layout



        // Image label
        ImageIcon image = new ImageIcon("Graph.jpg");
        JLabel imageLabel = new JLabel(image);
        imageLabel.setBounds(400, -50, 1200, 850);
        frame.add(imageLabel);




        // Input fields and labels
        JLabel panel = new JLabel("Start City:");
        JLabel startLabel = new JLabel("Start City:");
        startLabel.setBounds(80, 170, 80, 30);
        JTextField startField = new JTextField();
        startField.setBounds(200, 170, 180, 30);
        JLabel destLabel = new JLabel("Destination City:");
        destLabel.setBounds(80, 230, 120, 30);
        JTextField destField = new JTextField();
        destField.setBounds(200, 230, 180, 30);
        frame.add(startLabel);
        frame.add(startField);
        frame.add(destLabel);
        frame.add(destField);


        
        JButton findButton = new JButton("Find Shortest Path");
        findButton.setBounds(80, 340, 200, 30);
        frame.add(findButton);

        
        outputTextArea = new JTextArea(); 
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        scrollPane.setBounds(80, 390, 300, 100);
        frame.add(scrollPane);

        findButton.addActionListener(e -> {
            try {
                String startCity = startField.getText();
                String destCity = destField.getText();
                if (!startCity.isEmpty() && !destCity.isEmpty()) {
                    findShortestPath(startCity, destCity);
                } else {
                    outputTextArea.setText("Please enter both start and destination cities.");
                }
            } catch (Exception ex) {
                outputTextArea.setText("An error occurred: " + ex.getMessage());
            }
        });

        frame.setSize(350, 450);
        frame.setVisible(true);
    }

    static void initializeGraph() {
        try {
            addEdge("Mumbai", "Delhi", 1400);
            addEdge("Delhi", "Patna", 1055);
            addEdge("Patna", "Kolkata", 581);
            addEdge("Kolkata", "Hyderabad", 1474);
            addEdge("Hyderabad", "Chennai ", 631);
            addEdge("Chennai ", "Panaji", 932);
            addEdge("Panaji", "Mumbai", 553);
            addEdge("Mumbai", "Surat", 283);
            addEdge("Surat", "Agra", 1069);
            addEdge("Agra", "Kanpur", 300);
            addEdge("Kanpur", "Bhopal", 559);
            addEdge("Bhopal", "Jaipur", 611);
            addEdge("Jaipur", "Surat", 899);
            addEdge("Jaipur", "Panaji", 1623);
            addEdge("Jaipur", "Chennai", 2074);
            addEdge("Bhopal", "Chennai", 1477);
            addEdge("Bhopal", "Hyderabad", 847);
            addEdge("Bhopal", "Kolkata", 1415);
            addEdge("Kolkata", "Kanpur", 1003);
            addEdge("Kanpur", "Patna", 584);
            addEdge("Patna", "Agra", 836);
            addEdge("Agra", "Delhi", 240);

        } catch (Exception e) {

            
            outputTextArea.setText("An error occurred while initializing the graph: " + e.getMessage());
        }
    }

    static void addEdge(String source, String destination, int weight) {
        try {
            if (!graph.containsKey(source)) {
                graph.put(source, new HashMap<>());
            }

            if (!graph.containsKey(destination)) {
                graph.put(destination, new HashMap<>());
            }

            graph.get(source).put(destination, weight);
            graph.get(destination).put(source, weight);
        } catch (Exception e) {


            
            throw new RuntimeException("Error adding edge: " + e.getMessage());
        }
    }

    static void findShortestPath(String start, String destination) {
        try {
            Map<String, Integer> distances = new HashMap<>();
            Map<String, String> previous = new HashMap<>();
            PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

            for (String vertex : graph.keySet()) {
                distances.put(vertex, Integer.MAX_VALUE);
            }
            distances.put(start, 0);
            queue.offer(start);

            while (!queue.isEmpty()) {
                String current = queue.poll();
                if (current.equals(destination)) {
                    java.util.List<String> path = new java.util.ArrayList<>();

                    while (previous.containsKey(current)) {
                        path.add(current);
                        current = previous.get(current);
                    }
                    path.add(start);
                    Collections.reverse(path);
                    outputTextArea.setText("Shortest distance: " + distances.get(destination) + "km\n");
                    outputTextArea.append("Path: " + String.join(" -> ", path));
                    return;
                }

                for (String neighbor : graph.get(current).keySet()) {
                    int newDistance = distances.get(current) + graph.get(current).get(neighbor);
                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        previous.put(neighbor, current);
                        queue.offer(neighbor);
                    }
                }
            }

            outputTextArea.setText("No path found between " + start + " and " + destination);
        } catch (Exception e) {



            throw new RuntimeException("Error finding shortest path: " + e.getMessage());
        }
    }
}
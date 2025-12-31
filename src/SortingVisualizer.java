import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class SortingVisualizer extends JFrame {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;
    private static final int ARRAY_SIZE = 100;
    private static final int DELAY = 10;

    private int[] array;
    private SortPanel sortPanel;
    private JComboBox<String> algorithmSelector;
    private JButton startButton, resetButton, pauseButton;
    private JSlider speedSlider, sizeSlider;
    private JLabel statusLabel, comparisonsLabel, swapsLabel;

    private AtomicBoolean sorting = new AtomicBoolean(false);
    private AtomicBoolean paused = new AtomicBoolean(false);
    private int comparisons = 0;
    private int swaps = 0;

    public SortingVisualizer() {
        setTitle("Sorting Algorithm Visualizer");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        array = new int[ARRAY_SIZE];
        generateRandomArray();

        sortPanel = new SortPanel();
        add(sortPanel, BorderLayout.CENTER);

        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Row 1: Algorithm selection and buttons
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row1.add(new JLabel("Algorithm:"));

        String[] algorithms = {"Bubble Sort", "Selection Sort", "Insertion Sort",
                "Merge Sort", "Quick Sort", "Heap Sort"};
        algorithmSelector = new JComboBox<>(algorithms);
        row1.add(algorithmSelector);

        startButton = new JButton("Start");
        startButton.addActionListener(e -> startSorting());
        row1.add(startButton);

        pauseButton = new JButton("Pause");
        pauseButton.setEnabled(false);
        pauseButton.addActionListener(e -> togglePause());
        row1.add(pauseButton);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> reset());
        row1.add(resetButton);

        panel.add(row1);

        // Row 2: Speed and size controls
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row2.add(new JLabel("Speed:"));
        speedSlider = new JSlider(1, 100, 50);
        speedSlider.setPreferredSize(new Dimension(150, 30));
        row2.add(speedSlider);

        row2.add(new JLabel("Array Size:"));
        sizeSlider = new JSlider(10, 200, ARRAY_SIZE);
        sizeSlider.setPreferredSize(new Dimension(150, 30));
        sizeSlider.addChangeListener(e -> {
            if (!sorting.get()) {
                array = new int[sizeSlider.getValue()];
                generateRandomArray();
                sortPanel.repaint();
            }
        });
        row2.add(sizeSlider);

        panel.add(row2);

        // Row 3: Status labels
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        statusLabel = new JLabel("Status: Ready");
        comparisonsLabel = new JLabel("Comparisons: 0");
        swapsLabel = new JLabel("Swaps: 0");

        row3.add(statusLabel);
        row3.add(comparisonsLabel);
        row3.add(swapsLabel);

        panel.add(row3);

        return panel;
    }

    private void generateRandomArray() {
        Random rand = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = rand.nextInt(500) + 10;
        }
        comparisons = 0;
        swaps = 0;
        updateStats();
    }

    private void startSorting() {
        if (sorting.get()) return;

        sorting.set(true);
        paused.set(false);
        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
        algorithmSelector.setEnabled(false);
        sizeSlider.setEnabled(false);
        statusLabel.setText("Status: Sorting...");

        new Thread(() -> {
            String selected = (String) algorithmSelector.getSelectedItem();

            try {
                switch (selected) {
                    case "Bubble Sort": bubbleSort(); break;
                    case "Selection Sort": selectionSort(); break;
                    case "Insertion Sort": insertionSort(); break;
                    case "Merge Sort": mergeSort(0, array.length - 1); break;
                    case "Quick Sort": quickSort(0, array.length - 1); break;
                    case "Heap Sort": heapSort(); break;
                }
                statusLabel.setText("Status: Completed!");
            } catch (Exception e) {
                statusLabel.setText("Status: Stopped");
            }

            sorting.set(false);
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);
            algorithmSelector.setEnabled(true);
            sizeSlider.setEnabled(true);
        }).start();
    }

    private void togglePause() {
        paused.set(!paused.get());
        pauseButton.setText(paused.get() ? "Resume" : "Pause");
    }

    private void reset() {
        sorting.set(false);
        paused.set(false);
        generateRandomArray();
        sortPanel.clearHighlights();
        sortPanel.repaint();
        statusLabel.setText("Status: Ready");
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        pauseButton.setText("Pause");
        algorithmSelector.setEnabled(true);
        sizeSlider.setEnabled(true);
    }

    private void delay() throws InterruptedException {
        while (paused.get() && sorting.get()) {
            Thread.sleep(50);
        }
        if (!sorting.get()) throw new InterruptedException();

        int speed = speedSlider.getValue();
        Thread.sleep(101 - speed);
    }

    private void updateStats() {
        SwingUtilities.invokeLater(() -> {
            comparisonsLabel.setText("Comparisons: " + comparisons);
            swapsLabel.setText("Swaps: " + swaps);
        });
    }

    // Bubble Sort
    private void bubbleSort() throws InterruptedException {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                sortPanel.setHighlighted(j, j + 1);
                comparisons++;
                updateStats();

                if (array[j] > array[j + 1]) {
                    swap(j, j + 1);
                    swaps++;
                    updateStats();
                }
                delay();
            }
        }
        sortPanel.clearHighlights();
    }

    // Selection Sort
    private void selectionSort() throws InterruptedException {
        for (int i = 0; i < array.length - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < array.length; j++) {
                sortPanel.setHighlighted(minIdx, j);
                comparisons++;
                updateStats();

                if (array[j] < array[minIdx]) {
                    minIdx = j;
                }
                delay();
            }
            if (minIdx != i) {
                swap(i, minIdx);
                swaps++;
                updateStats();
            }
        }
        sortPanel.clearHighlights();
    }

    // Insertion Sort
    private void insertionSort() throws InterruptedException {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > key) {
                sortPanel.setHighlighted(j, j + 1);
                comparisons++;
                updateStats();

                array[j + 1] = array[j];
                j--;
                swaps++;
                updateStats();
                delay();
            }
            array[j + 1] = key;
            sortPanel.repaint();
        }
        sortPanel.clearHighlights();
    }

    // Merge Sort
    private void mergeSort(int l, int r) throws InterruptedException {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(l, m);
            mergeSort(m + 1, r);
            merge(l, m, r);
        }
    }

    private void merge(int l, int m, int r) throws InterruptedException {
        int n1 = m - l + 1;
        int n2 = r - m;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(array, l, L, 0, n1);
        System.arraycopy(array, m + 1, R, 0, n2);

        int i = 0, j = 0, k = l;

        while (i < n1 && j < n2) {
            sortPanel.setHighlighted(k, k);
            comparisons++;
            updateStats();

            if (L[i] <= R[j]) {
                array[k] = L[i++];
            } else {
                array[k] = R[j++];
            }
            k++;
            swaps++;
            updateStats();
            delay();
        }

        while (i < n1) {
            array[k++] = L[i++];
            swaps++;
            updateStats();
            delay();
        }

        while (j < n2) {
            array[k++] = R[j++];
            swaps++;
            updateStats();
            delay();
        }
    }

    // Quick Sort
    private void quickSort(int low, int high) throws InterruptedException {
        if (low < high) {
            int pi = partition(low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    private int partition(int low, int high) throws InterruptedException {
        int pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            sortPanel.setHighlighted(j, high);
            comparisons++;
            updateStats();

            if (array[j] < pivot) {
                i++;
                swap(i, j);
                swaps++;
                updateStats();
            }
            delay();
        }

        swap(i + 1, high);
        swaps++;
        updateStats();
        return i + 1;
    }

    // Heap Sort
    private void heapSort() throws InterruptedException {
        int n = array.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            swap(0, i);
            swaps++;
            updateStats();
            delay();
            heapify(i, 0);
        }
        sortPanel.clearHighlights();
    }

    private void heapify(int n, int i) throws InterruptedException {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n) {
            sortPanel.setHighlighted(l, largest);
            comparisons++;
            updateStats();
            delay();
            if (array[l] > array[largest]) {
                largest = l;
            }
        }

        if (r < n) {
            sortPanel.setHighlighted(r, largest);
            comparisons++;
            updateStats();
            delay();
            if (array[r] > array[largest]) {
                largest = r;
            }
        }

        if (largest != i) {
            swap(i, largest);
            swaps++;
            updateStats();
            delay();
            heapify(n, largest);
        }
    }

    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        sortPanel.repaint();
    }

    // Panel for drawing
    class SortPanel extends JPanel {
        private int highlighted1 = -1;
        private int highlighted2 = -1;

        public void setHighlighted(int i, int j) {
            highlighted1 = i;
            highlighted2 = j;
            repaint();
        }

        public void clearHighlights() {
            highlighted1 = -1;
            highlighted2 = -1;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int barWidth = Math.max(1, width / array.length);

            for (int i = 0; i < array.length; i++) {
                int barHeight = (int) ((array[i] / 510.0) * height);
                int x = i * barWidth;
                int y = height - barHeight;

                if (i == highlighted1 || i == highlighted2) {
                    g2d.setColor(Color.RED);
                } else {
                    g2d.setColor(new Color(100, 149, 237));
                }

                g2d.fillRect(x, y, barWidth - 1, barHeight);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SortingVisualizer());
    }
}
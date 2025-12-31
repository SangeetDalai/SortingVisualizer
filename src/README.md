# Sorting Algorithm Visualizer

A comprehensive Java-based sorting algorithm visualizer with an interactive GUI that demonstrates how various sorting algorithms work in real-time.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-GUI-blue?style=for-the-badge)

## 🎯 Features

- **6 Sorting Algorithms Implemented**
    - Bubble Sort
    - Selection Sort
    - Insertion Sort
    - Merge Sort
    - Quick Sort
    - Heap Sort

- **Interactive Controls**
    - Real-time visualization with animated bars
    - Adjustable sorting speed (1-100)
    - Dynamic array size (10-200 elements)
    - Pause/Resume functionality
    - Reset and regenerate array

- **Performance Metrics**
    - Live comparison counter
    - Live swap counter
    - Algorithm status indicator

- **User-Friendly Interface**
    - Clean, intuitive GUI built with Java Swing
    - Color-coded visualization (blue bars, red highlights for comparisons)
    - Responsive design

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code) or command line

### Installation

1. Clone the repository
```bash
git clone https://github.com/yourusername/sorting-visualizer.git
cd sorting-visualizer
```

2. Compile the program
```bash
javac SortingVisualizer.java
```

3. Run the application
```bash
java SortingVisualizer
```

## 💻 Usage

1. **Select Algorithm**: Choose from the dropdown menu
2. **Adjust Settings**:
    - Use the speed slider to control animation speed
    - Use the size slider to change array size
3. **Start Sorting**: Click the "Start" button to begin visualization
4. **Pause/Resume**: Control the sorting process mid-execution
5. **Reset**: Generate a new random array

## 🎓 Algorithm Complexity

| Algorithm | Best Case | Average Case | Worst Case | Space Complexity |
|-----------|-----------|--------------|------------|------------------|
| Bubble Sort | O(n) | O(n²) | O(n²) | O(1) |
| Selection Sort | O(n²) | O(n²) | O(n²) | O(1) |
| Insertion Sort | O(n) | O(n²) | O(n²) | O(1) |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) |
| Quick Sort | O(n log n) | O(n log n) | O(n²) | O(log n) |
| Heap Sort | O(n log n) | O(n log n) | O(n log n) | O(1) |

## 🛠️ Technical Details

### Built With
- **Java Swing** - GUI framework
- **Java AWT** - Graphics rendering
- **Multithreading** - For smooth animations without blocking the UI
- **AtomicBoolean** - Thread-safe state management

### Key Components
- `SortingVisualizer` - Main class managing the application
- `SortPanel` - Custom JPanel for rendering the visualization
- Algorithm implementations with visualization hooks
- Event-driven architecture for user interactions

## 🎯 Learning Outcomes

This project demonstrates:
- Understanding of fundamental sorting algorithms
- Proficiency in Java GUI development
- Knowledge of multithreading and concurrency
- Algorithm complexity analysis
- Clean code practices and OOP principles

## 🔮 Future Enhancements

- [ ] Add more sorting algorithms (Radix Sort, Counting Sort, Shell Sort)
- [ ] Sound effects for comparisons and swaps
- [ ] Save visualization as GIF/video
- [ ] Custom array input
- [ ] Dark mode theme
- [ ] Algorithm comparison mode (side-by-side)
- [ ] Step-by-step mode with explanation

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 👤 Author
- GitHub: [SangeetDalai](https://github.com/SangeetDalai)
- LinkedIn: [Sangeet Dalai](https://www.linkedin.com/in/sangeet-dalai-b1155b395)

## 🙏 Acknowledgments

- Inspired by various sorting visualization tools
- Thanks to the Java Swing documentation and community

---

⭐️ If you found this project helpful, please consider giving it a star!
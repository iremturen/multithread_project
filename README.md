# Java File Analysis and Compression Project

This project is a Java application that analyzes text files in parallel and compresses them afterward. It utilizes multithreading to accelerate file operations and archives analysis results in ZIP format.

## 📋 Features

- **Multi-threading Support**: Can process up to 10 files simultaneously
- **File Analysis**: Calculates line count and character count for each .txt file
- **Automatic Compression**: Archives analyzed files in ZIP format
- **Thread-Safe Operations**: Secure data sharing using ConcurrentHashMap
- **Performance Monitoring**: Measures and displays execution time for each phase using `System.nanoTime()`
- **Error Handling**: Catches file not found and reading errors

## 🏗️ Project Structure

```
project-directory/
├── src/
│   ├── Main.java          # Main class and program entry point
│   └── fileOperations/    # Package containing file operation classes
│       ├── FileAnalyzer.java  # File analysis thread class
│       ├── FileStats.java     # File statistics data class
│       └── FileZipper.java    # ZIP creation thread class
├── input/                 # .txt files to be analyzed
│   ├── file1.txt
│   ├── file2.txt
│   └── ...
├── output/                # Output ZIP file (created automatically)
│   └── files.zip
└── README.md
```

## 🛠️ Requirements
- **Operating System**: Windows, Linux, macOS

## 🚀 Execution

**Terminal/Command Prompt:**
```bash
# Compile from project root directory (MacOS)
 javac -d out $(find src -name "*.java")

# Run
java -cp out Main   
```

**If Using an IDE:**
- Import the project into IDEs like Eclipse, IntelliJ IDEA, VS Code
- Set `src` as the source directory
- The IDE will automatically handle package compilation

## 📊 Program Output

When the program runs, you will see output like this:

```

✓ file3.txt processed. Duration: 1,187,250 ns (1.187 ms)
✓ file2.txt processed. Duration: 1,228,416 ns (1.228 ms)
✓ file1.txt processed. Duration: 1,137,208 ns (1.137 ms)
Total analysis process duration: 15,136,875 ns (15.137 ms)
*** Starting the file analysis. ***
file1.txt - 28 lines / 1944 characters
file3.txt - 3 lines / 56 characters
file2.txt - 11 lines / 893 characters
Total: 42 line / 2893 characters
*** Creating zip ***
file2.txt added to the zip file.
file3.txt added to the zip file.
file1.txt added to the zip file.
✅ Zip creation completed: output/files.zip
Zip process duration: 15,178,750 ns (15.179 ms)
Total program duration: 45,161,292 ns (45.161 ms)

```

## ⚙️ Configuration

You can modify the following constants in `Main.java`:

```java
private static final int MAX_THREADS = 10;              // Maximum number of threads
private static final String INPUT_FOLDER_PATH = "input"; // Input folder path
private static final String OUTPUT_ZIP_PATH = "output/files.zip"; // Output ZIP path
```

## 🔧 Class Descriptions

### Main.java
- Program entry point
- Creates and manages threads
- Scans input folder and finds .txt files
- **Performance monitoring**: Measures execution time for analysis and ZIP creation phases using `System.nanoTime()`

### FileAnalyzer.java
- Analyzes each file in a separate thread
- Calculates line and character counts

### FileStats.java
- Holds file statistics (line count, character count)
- Immutable data class

### FileZipper.java
- Compresses analyzed files in ZIP format
- Provides detailed console output during ZIP creation
- Handles invalid files gracefully

## 👨‍💻 Developers

- **İrem Türen** - Main class and Threads
- **Sefa Bulut** - FileAnalyzer implementation
- **Meryem Özevren** - FileZipper implementation
- **Mert Ünal** - Time measurement implementation
- **Emirkan Kurt** - Project architecture

## 📄 Documentation 
[Document](MultiThread_Project_Document.pdf)

---

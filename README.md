# Word Search Solver

## Overview

Welcome to the Word Search Solver project! This Java application is designed to generate a word search grid and then search for specific words within that grid. The project is divided into two main stages: the word search grid generating stage and the word searching stage.

## Features

- **Grid Generation:** Dynamically generates a word search grid of customizable siz with words of your choosing.
- **Word Search:** Searches for target words within the generated grid.
- **Customizable:** Allows users to customize the list of words to be included in the grid.

## Getting Started

### Prerequisites

To run this project, you need:

- Java Development Kit (JDK) 8 or higher
- A Java Integrated Development Environment (IDE) such as IntelliJ IDEA, Eclipse, or NetBeans

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/andrewt1018/WordSearchSolver.git
   cd WordSearchSolver
   ```

2. Open the project in your preferred IDE.

3. Build the project to resolve any dependencies.

## Usage

### Grid Generation

1. Navigate to the `Main.java` file.
2. Add a list of words of your choosing into an ArrayList 
3. Create a new `Gridmaker` object with your ArrayList of words.
4. Print out the grid using the println function.

### Word Searching

1. Create a new `WordSearcher` object using the Gridmaker object as a parameter.
2. Call `searcher.searchWords()` and print out the results.

### Example

Here is a simple example of how to generate a grid and search for words:

```java
public class Main {
        public static void main(String[] args) {
            // Create a list of words to generate in your grid.
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add("funny");
            inputs.add("cow");
            inputs.add("factual");
            inputs.add("focus");
            inputs.add("fertile");
            inputs.add("fruition");
            inputs.add("flamingo");

            // Create and print out the grid
            Gridmaker grid = new Gridmaker(inputs);
            System.out.println(grid);

            // Create the searcher object
            WordSearcher searcher = new WordSearcher(grid);

            // Search for and print out the location of the words.
            System.out.println(searcher.searchWords());
    }
}
```


## Contact

For any questions, concerns or suggestions, please contact me [here](andrewt8101@gmail.com).

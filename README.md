The purpose of this tool is to detect java files that contain unit test methods. The results of the tools execution will
saved in a CSV file for further processing (by other tools) or to imported into a database.

## Usage

To run the jar file, pass the project directory. The tool will iterate through all subdirectories to detect JUnit-based
test files

`C:\Projects\TestFileDetector>java -jar TestFileDetector.jar C:\MyProject`

## Output

The output csv file will be saved in the same directory as the jar. The file will start with the name 'Output_Class_'.
This file will contain details such as the number of test methods in the file and the use of JUnit specific import
statements.

# Flight Fare Optimizer

A simple yet functional JavaFX application to search, filter, and find the cheapest flights — built as a practice project to consolidate Java development, data handling, and UI design skills.

<img width="945" height="624" alt="image" src="https://github.com/user-attachments/assets/a9b24b27-3d96-4c0b-ae71-a501d66a9a7f" />

<img width="796" height="826" alt="image" src="https://github.com/user-attachments/assets/5441ebfb-c24d-4c0a-9d7e-c7e7065a2d9f" />


---

## Overview

This desktop app loads flight data from a local JSON file, allows filtering by origin, destination, and date, and highlights the cheapest available option.  
It was developed using **Java 17**, **JavaFX**, **Jackson**, and **iTextPDF** to generate PDF reports.

It’s a lightweight project that demonstrates:
- Clean separation between **model**, **service**, and **UI** layers.  
- Basic use of **FXML** for interfaces.  
- **JSON parsing** and **PDF export** integration.

---

## Tech Stack: 
-Java 17
-JavaFX (FXML)
-Jackson (JSON parsing) 
-iTextPDF
-Maven

---

##  Features

- Load local flight data from `sample-flights.json`.
- Filter by origin, destination, and travel date.
- Automatically sort results by price.
- Highlight the cheapest flight found.
- Export the current list to a styled PDF report.

---

## Lessons learned

Working on this project helped me practice:
- Structuring a small-scale Java project with multiple layers.
- Handling JSON data using Jackson’s modern modules.
- Building a responsive JavaFX interface with FXML.
- Managing Maven dependencies and packaging a runnable app.

---

##  Run it locally

```bash
# Clone this repository
git clone https://github.com/KevPrieto/Flight-Fare-Optimizer.git

# Navigate to the project folder
cd Flight-Fare-Optimizer

# Compile and run
mvn javafx:run

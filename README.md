# 🛫 Flight Fare Optimizer

**Flight Fare Optimizer** is a modular JavaFX desktop application that allows users to search, filter, and export flight fares to PDF.  
It was built as a self-contained project to strengthen practical experience in **Java 17**, **object-oriented design**, and **UI/UX engineering** using **JavaFX** and **iText**.

<p align="center">
  <img width="900" alt="UI Screenshot" src="https://github.com/user-attachments/assets/a9b24b27-3d96-4c0b-ae71-a501d66a9a7f" />
  <br/>
  <em>Flight Fare Optimizer interface – built with JavaFX and FXML</em>
</p>

---

## 🚀 Overview

This app loads and filters flight data stored in JSON, dynamically updates the results table, and highlights the cheapest option available.  
Results can be exported to a **styled, embedded-font PDF report** — all within a clean, cross-platform UI.

It simulates real-world scenarios where Java developers handle **data ingestion**, **logic separation**, and **frontend presentation** layers cohesively.

---

## 🧠 Key Features

- Load local or remote JSON flight data  
- Filter by **Origin**, **Destination**, and **Date**  
- Automatically sort flights by **lowest fare**  
- Export search results as **PDF** with custom fonts  
- Graceful error handling and user feedback  
- Fully packaged as a **Windows `.exe` installer**

---

## 🧩 Tech Stack

| Layer | Technology |
|--------|-------------|
| Language | Java 17 |
| UI | JavaFX (FXML) |
| Data | Jackson Databind / JSON |
| Reporting | iText PDF |
| Build Tool | Maven |
| Logging | SLF4J |
| Packaging | jpackage |

---

## 🧱 Architecture

Clean separation between layers ensures modularity and maintainability:
src/
├─ main/
│ ├─ java/com/edo/fares/
│ │ ├─ api/ → Data clients (local/remote JSON)
│ │ ├─ gui/ → JavaFX controllers and UI logic
│ │ ├─ model/ → Domain models (Flight, SearchCriteria)
│ │ ├─ report/ → PDF generation (ReportGenerator)
│ │ └─ service/ → Core business logic (FareService)
│ └─ resources/
│ ├─ gui/view/ → FXML and CSS assets
│ ├─ fonts/ → Embedded PDF fonts (DejaVuSans)
│ └─ sample-flights.json

## 💾 Installation & Run

### Run locally (development mode)
```bash
# Clone the repository
git clone https://github.com/KevPrieto/Flight-Fare-Optimizer.git
cd Flight-Fare-Optimizer

# Compile and package
mvn clean package

# Run from JAR
java -jar target/flight-fare-optimizer-1.0.0-jar-with-dependencies.jar

# Install as native Windows app: 
After building, run the generated installer found in: 
target/installer/Flight Fare Optimizer-1.0.exe
The installer creates a shortcut and installs the app in:
C:\Program Files\Flight Fare Optimizer

📘 Lessons Learned

This project was designed as a bridge between academic learning and real-world engineering.
Key takeaways include:

Building modular, scalable applications using JavaFX + OOP principles

Implementing data handling via Jackson and JSON mapping

Creating export pipelines using iText PDF

Packaging Java applications for Windows distribution

Managing dependencies and reproducible builds with Maven

🧩 Author

Kevin Prieto Serrano
Java Developer | Data & AI Background | Cybersecurity Enthusiast
📍 Madrid, Spain
📧 kgps1003@gmail.com

🔗 LinkedIn: linkedin.com/in/kevin-prieto-developer
 · GitHub: Github.com/KevPrieto


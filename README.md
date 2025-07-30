# ☁️ Cloud-of-Clouds Storage System

This project implements a secure, distributed, and user-friendly cloud storage web application based on a **Cloud-of-Clouds architecture**. Users can upload, download, list, and share files while managing file storage across multiple cloud providers.

## 📌 Features~~~~

- ✅ File Upload with client ID tagging
- 📥 File Download by name and client ID
- 📄 List all files associated with a client
- 🔁 Share files between users securely
- 📊 System Status Monitor (GCP, AWS, Azure, leases, etc.)
- 🧹 Maintenance Tools (clear leases, clean temp files, verify data)
- 🎨 Modern Web Interface (HTML, CSS, JS)

## ⚙️ Technologies Used

- **Backend**: Java, Servlets, Jetty Server (Embedded)
- **Frontend**: HTML5, CSS3, JavaScript
- **Database**: MySQL (for metadata)
- **Cloud Integration**: (Simulated AWS, GCP, Azure in local setup)
- **Tools**: IntelliJ IDEA / Eclipse, JDK 21, Jetty 9.4

## 📁 Folder Structure
CloudOfCloudsStorageSystem/
├── src/
│ └── main/
│ ├── java/
│ │ └── com/cloudstorage/servlets/
│ │ ├── UploadServlet.java
│ │ ├── DownloadServlet.java
│ │ ├── ListFilesServlet.java
│ │ ├── ShareFileServlet.java
│ │ ├── StatusServlet.java
│ │ ├── CleanupServlet.java
│ │ ├── ClearLeaseServlet.java
│ │ ├── VerifyDataServlet.java
│ │ └── JettyLauncher.java
│ └── resources/
├── web/
│ ├── index.html
│ ├── styles.css
│ ├── script.js
│ └── assets/ (optional for icons/images)
└── README.md


## 🚀 How to Run

1. **Clone or unzip the project folder.**
2. Open the project in **IntelliJ IDEA** or **Eclipse**.
3. Make sure you have JDK 21+ installed and Jetty libraries added.
4. Run the `JettyLauncher.java` class.
5. Open your browser and go to: [http://localhost:8080](http://localhost:8080)
6. Start uploading and managing your files!

## ✍️ Author
- **Sai Dhanashri**  
  Java Full Stack Development Intern  
  Vcodez Technologies  
  [LinkedIn](#) | [GitHub](#)

## 📃 License
This project is for educational and demonstration purposes only.
function showSection(sectionId) {
  const sections = document.querySelectorAll('.section');
  sections.forEach(sec => sec.style.display = 'none');
  document.getElementById(sectionId).style.display = 'block';
}

function uploadFile() {
  const fileInput = document.getElementById('uploadFile');
  const clientId = document.getElementById('uploadClientId').value;
  const file = fileInput.files[0];
  const status = document.getElementById('uploadStatus');

  if (!file || !clientId) {
    alert("Please select a file and enter Client ID.");
    return;
  }

  const formData = new FormData();
  formData.append("file", file);
  formData.append("clientId", clientId);

  fetch("upload", {
    method: "POST",
    body: formData
  })
    .then(response => response.text())
    .then(data => {
      const clean = data.replace(/\?/g, '').trim();
      status.innerText = clean;
      if (clean.toLowerCase().includes("successfully")) {
        alert("✅ File uploaded successfully!");
      } else {
        alert("⚠️ " + clean);
      }
    })
    .catch(error => {
      console.error("Upload error:", error);
      status.innerText = "❌ Upload failed.";
    });
}

function downloadFile() {
  const fileName = document.getElementById('downloadFileName').value;
  const clientId = document.getElementById('downloadClientId').value;

  if (!fileName || !clientId) {
    alert("Please enter both file name and client ID.");
    return;
  }

  fetch(`download?fileName=${encodeURIComponent(fileName)}&clientId=${encodeURIComponent(clientId)}`)
    .then(response => {
      if (response.ok) return response.blob();
      else throw new Error("File not found.");
    })
    .then(blob => {
      const link = document.createElement("a");
      link.href = URL.createObjectURL(blob);
      link.download = fileName;
      link.click();
    })
    .catch(error => {
      alert("❌ " + error.message);
    });
}

function listFiles() {
  const clientId = document.getElementById('listClientId').value;
  const output = document.getElementById('fileListOutput');

  if (!clientId) {
    alert("Please enter Client ID.");
    return;
  }

  fetch(`list?clientId=${encodeURIComponent(clientId)}`)
    .then(response => response.text())
    .then(data => {
      const clean = data.replace(/\?/g, '').trim();
      output.innerText = clean;
    })
    .catch(error => {
      output.innerText = "❌ Failed to list files.";
    });
}

function shareFile() {
  const fileName = document.getElementById('shareFileName').value;
  const yourId = document.getElementById('yourClientId').value;
  const targetId = document.getElementById('targetClientId').value;

  if (!fileName || !yourId || !targetId) {
    alert("Please fill in all share fields.");
    return;
  }

  fetch("share", {
    method: "POST",
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      fileName,
      ownerClientId: yourId,
      targetClientId: targetId
    })
  })
    .then(res => res.text())
    .then(msg => alert("✅ " + msg))
    .catch(err => alert("❌ Failed to share file."));
}

function checkSystemStatus() {
  const output = document.getElementById('statusOutput');
  fetch("status")
    .then(res => res.text())
    .then(data => {
      const clean = data.replace(/\?/g, '').trim();
      output.innerText = clean;
    })
    .catch(() => output.innerText = "❌ Could not fetch status.");
}

function clearLeases() {
  fetch("clear-leases")
    .then(res => res.text())
    .then(msg => alert(msg))
    .catch(() => alert("❌ Failed to clear leases."));
}

function cleanupTemp() {
  fetch("cleanup-temp")
    .then(res => res.text())
    .then(msg => alert(msg))
    .catch(() => alert("❌ Failed to cleanup temporary files."));
}

function verifyData() {
  fetch("verify-data")
    .then(res => res.text())
    .then(msg => alert(msg))
    .catch(() => alert("❌ Data verification failed."));
}

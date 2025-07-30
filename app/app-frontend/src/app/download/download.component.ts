import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // ✅ This is required for ngModel

@Component({
  standalone: true,
  selector: 'app-download',
  templateUrl: './download.component.html',
  styleUrls: ['./download.component.css'],
  imports: [CommonModule, FormsModule], // ✅ Add FormsModule here
})
export class DownloadComponent {
  fileId: string = '';

  constructor(private http: HttpClient) {}

  downloadFile() {
    this.http.get(`/api/download/${this.fileId}`, { responseType: 'blob' }).subscribe(blob => {
      const a = document.createElement('a');
      const objectUrl = URL.createObjectURL(blob);
      a.href = objectUrl;
      a.download = this.fileId;
      a.click();
      URL.revokeObjectURL(objectUrl);
    }, error => {
      alert('File download failed.');
    });
  }
}


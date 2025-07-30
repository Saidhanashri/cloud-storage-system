import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-upload',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent {
  fileToUpload: File | null = null;
  clientId: string = '';

  constructor(private http: HttpClient) {}

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.fileToUpload = input.files[0];
    }
  }

  onUpload(): void {
    if (!this.fileToUpload || !this.clientId) {
      alert('Please select a file and enter a Client ID.');
      return;
    }

    const formData = new FormData();
    formData.append('file', this.fileToUpload);
    formData.append('clientId', this.clientId);

    this.http.post('http://localhost:8080/upload', formData).subscribe({
      next: (response) => {
        console.log('Upload success:', response);
        alert('File uploaded successfully!');
      },
      error: (error) => {
        console.error('Upload error:', error);
        alert('Upload failed. Ensure the backend is running at http://localhost:8080/upload');
      }
    });
  }
}

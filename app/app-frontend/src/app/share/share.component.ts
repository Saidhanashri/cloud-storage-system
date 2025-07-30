import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-share',
  templateUrl: './share.component.html',
  styleUrls: ['./share.component.css'],
  imports: [CommonModule, FormsModule]
})
export class ShareComponent {
  fileId: string = '';
  recipientEmail: string = '';

  constructor(private http: HttpClient) {}

  shareFile() {
    if (!this.fileId || !this.recipientEmail) {
      alert('Please enter both file ID and recipient email.');
      return;
    }

    const body = {
      fileId: this.fileId,
      recipientEmail: this.recipientEmail
    };

    this.http.post('/api/share', body).subscribe(
      (response) => {
        alert('File shared successfully!');
        console.log('Response:', response);
      },
      (error) => {
        alert('Error sharing file.');
        console.error(error);
      }
    );
  }
}


import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-file-list',
  templateUrl: './file-list.component.html',
  styleUrls: ['./file-list.component.css'],
  imports: [CommonModule],
})
export class FileListComponent implements OnInit {
  files: string[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get<string[]>('http://localhost:8080/api/files').subscribe(
      (data: string[]) => this.files = data,
      (error: any) => console.error('Error fetching files', error)
    );
  }
}



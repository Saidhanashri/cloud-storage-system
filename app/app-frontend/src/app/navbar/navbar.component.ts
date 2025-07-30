import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, RouterLink } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <nav>
      <button routerLink="/upload">Upload</button>
      <button routerLink="/download">Download</button>
      <button routerLink="/file-list">File List</button>
      <button routerLink="/share">Share</button>
      <button routerLink="/status">Status</button>
    </nav>
  `,
  styles: [`
    nav {
      display: flex;
      gap: 10px;
      background-color: #e0e0e0;
      padding: 10px;
      margin-bottom: 20px;
    }
    button {
      padding: 8px 16px;
      border: none;
      background-color: #1976d2;
      color: white;
      border-radius: 4px;
      cursor: pointer;
    }
    button:hover {
      background-color: #115293;
    }
  `]
})
export class NavbarComponent {}


import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-system-status',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="card">
      <h2>System Status</h2>
      <p>All cloud services are operational ✅</p>
    </div>
  `,
  styleUrls: ['./system-status.component.css']
})
export class SystemStatusComponent {}

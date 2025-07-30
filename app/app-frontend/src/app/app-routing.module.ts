// src/app/app-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UploadComponent } from './upload/upload.component'; // ✅ Only import used component

const routes: Routes = [
  { path: '', component: UploadComponent },
  { path: 'upload', component: UploadComponent },
  // Remove download, file-list, share, status routes if not used
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

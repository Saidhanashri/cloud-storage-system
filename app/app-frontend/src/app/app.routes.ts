import { Routes } from '@angular/router';
import { UploadComponent } from './upload/upload.component';
import { DownloadComponent } from './download/download.component';
import { FileListComponent } from './file-list/file-list.component';
import { ShareComponent } from './share/share.component';
import { SystemStatusComponent } from './system-status/system-status.component';

export const routes: Routes = [
  { path: 'upload', component: UploadComponent },
  { path: 'download', component: DownloadComponent },
  { path: 'file-list', component: FileListComponent },
  { path: 'share', component: ShareComponent },
  { path: 'status', component: SystemStatusComponent },
  { path: '', redirectTo: '/upload', pathMatch: 'full' }
];





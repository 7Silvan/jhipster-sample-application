import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProjectSupportedCosmosComponent } from './list/project-supported-cosmos.component';
import { ProjectSupportedCosmosDetailComponent } from './detail/project-supported-cosmos-detail.component';
import { ProjectSupportedCosmosUpdateComponent } from './update/project-supported-cosmos-update.component';
import { ProjectSupportedCosmosDeleteDialogComponent } from './delete/project-supported-cosmos-delete-dialog.component';
import { ProjectSupportedCosmosRoutingModule } from './route/project-supported-cosmos-routing.module';

@NgModule({
  imports: [SharedModule, ProjectSupportedCosmosRoutingModule],
  declarations: [
    ProjectSupportedCosmosComponent,
    ProjectSupportedCosmosDetailComponent,
    ProjectSupportedCosmosUpdateComponent,
    ProjectSupportedCosmosDeleteDialogComponent,
  ],
})
export class ProjectSupportedCosmosModule {}

import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PublicationCosmosComponent } from './list/publication-cosmos.component';
import { PublicationCosmosDetailComponent } from './detail/publication-cosmos-detail.component';
import { PublicationCosmosUpdateComponent } from './update/publication-cosmos-update.component';
import { PublicationCosmosDeleteDialogComponent } from './delete/publication-cosmos-delete-dialog.component';
import { PublicationCosmosRoutingModule } from './route/publication-cosmos-routing.module';

@NgModule({
  imports: [SharedModule, PublicationCosmosRoutingModule],
  declarations: [
    PublicationCosmosComponent,
    PublicationCosmosDetailComponent,
    PublicationCosmosUpdateComponent,
    PublicationCosmosDeleteDialogComponent,
  ],
})
export class PublicationCosmosModule {}

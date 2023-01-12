import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CharacterizationDataCosmosComponent } from './list/characterization-data-cosmos.component';
import { CharacterizationDataCosmosDetailComponent } from './detail/characterization-data-cosmos-detail.component';
import { CharacterizationDataCosmosUpdateComponent } from './update/characterization-data-cosmos-update.component';
import { CharacterizationDataCosmosDeleteDialogComponent } from './delete/characterization-data-cosmos-delete-dialog.component';
import { CharacterizationDataCosmosRoutingModule } from './route/characterization-data-cosmos-routing.module';

@NgModule({
  imports: [SharedModule, CharacterizationDataCosmosRoutingModule],
  declarations: [
    CharacterizationDataCosmosComponent,
    CharacterizationDataCosmosDetailComponent,
    CharacterizationDataCosmosUpdateComponent,
    CharacterizationDataCosmosDeleteDialogComponent,
  ],
})
export class CharacterizationDataCosmosModule {}

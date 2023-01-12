import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CellModelCosmosComponent } from './list/cell-model-cosmos.component';
import { CellModelCosmosDetailComponent } from './detail/cell-model-cosmos-detail.component';
import { CellModelCosmosUpdateComponent } from './update/cell-model-cosmos-update.component';
import { CellModelCosmosDeleteDialogComponent } from './delete/cell-model-cosmos-delete-dialog.component';
import { CellModelCosmosRoutingModule } from './route/cell-model-cosmos-routing.module';

@NgModule({
  imports: [SharedModule, CellModelCosmosRoutingModule],
  declarations: [
    CellModelCosmosComponent,
    CellModelCosmosDetailComponent,
    CellModelCosmosUpdateComponent,
    CellModelCosmosDeleteDialogComponent,
  ],
})
export class CellModelCosmosModule {}

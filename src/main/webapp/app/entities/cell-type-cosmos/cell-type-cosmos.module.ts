import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CellTypeCosmosComponent } from './list/cell-type-cosmos.component';
import { CellTypeCosmosDetailComponent } from './detail/cell-type-cosmos-detail.component';
import { CellTypeCosmosUpdateComponent } from './update/cell-type-cosmos-update.component';
import { CellTypeCosmosDeleteDialogComponent } from './delete/cell-type-cosmos-delete-dialog.component';
import { CellTypeCosmosRoutingModule } from './route/cell-type-cosmos-routing.module';

@NgModule({
  imports: [SharedModule, CellTypeCosmosRoutingModule],
  declarations: [
    CellTypeCosmosComponent,
    CellTypeCosmosDetailComponent,
    CellTypeCosmosUpdateComponent,
    CellTypeCosmosDeleteDialogComponent,
  ],
})
export class CellTypeCosmosModule {}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CellModelCosmosComponent } from '../list/cell-model-cosmos.component';
import { CellModelCosmosDetailComponent } from '../detail/cell-model-cosmos-detail.component';
import { CellModelCosmosUpdateComponent } from '../update/cell-model-cosmos-update.component';
import { CellModelCosmosRoutingResolveService } from './cell-model-cosmos-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const cellModelRoute: Routes = [
  {
    path: '',
    component: CellModelCosmosComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CellModelCosmosDetailComponent,
    resolve: {
      cellModel: CellModelCosmosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CellModelCosmosUpdateComponent,
    resolve: {
      cellModel: CellModelCosmosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CellModelCosmosUpdateComponent,
    resolve: {
      cellModel: CellModelCosmosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cellModelRoute)],
  exports: [RouterModule],
})
export class CellModelCosmosRoutingModule {}

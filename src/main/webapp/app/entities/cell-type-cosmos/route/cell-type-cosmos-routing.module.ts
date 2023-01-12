import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CellTypeCosmosComponent } from '../list/cell-type-cosmos.component';
import { CellTypeCosmosDetailComponent } from '../detail/cell-type-cosmos-detail.component';
import { CellTypeCosmosUpdateComponent } from '../update/cell-type-cosmos-update.component';
import { CellTypeCosmosRoutingResolveService } from './cell-type-cosmos-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const cellTypeRoute: Routes = [
  {
    path: '',
    component: CellTypeCosmosComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CellTypeCosmosDetailComponent,
    resolve: {
      cellType: CellTypeCosmosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CellTypeCosmosUpdateComponent,
    resolve: {
      cellType: CellTypeCosmosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CellTypeCosmosUpdateComponent,
    resolve: {
      cellType: CellTypeCosmosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cellTypeRoute)],
  exports: [RouterModule],
})
export class CellTypeCosmosRoutingModule {}

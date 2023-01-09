import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CharacterizationDataCosmosComponent } from '../list/characterization-data-cosmos.component';
import { CharacterizationDataCosmosDetailComponent } from '../detail/characterization-data-cosmos-detail.component';
import { CharacterizationDataCosmosUpdateComponent } from '../update/characterization-data-cosmos-update.component';
import { CharacterizationDataCosmosRoutingResolveService } from './characterization-data-cosmos-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const characterizationDataRoute: Routes = [
  {
    path: '',
    component: CharacterizationDataCosmosComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CharacterizationDataCosmosDetailComponent,
    resolve: {
      characterizationData: CharacterizationDataCosmosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CharacterizationDataCosmosUpdateComponent,
    resolve: {
      characterizationData: CharacterizationDataCosmosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CharacterizationDataCosmosUpdateComponent,
    resolve: {
      characterizationData: CharacterizationDataCosmosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(characterizationDataRoute)],
  exports: [RouterModule],
})
export class CharacterizationDataCosmosRoutingModule {}

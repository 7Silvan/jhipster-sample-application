import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PublicationCosmosComponent } from '../list/publication-cosmos.component';
import { PublicationCosmosDetailComponent } from '../detail/publication-cosmos-detail.component';
import { PublicationCosmosUpdateComponent } from '../update/publication-cosmos-update.component';
import { PublicationCosmosRoutingResolveService } from './publication-cosmos-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const publicationRoute: Routes = [
  {
    path: '',
    component: PublicationCosmosComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PublicationCosmosDetailComponent,
    resolve: {
      publication: PublicationCosmosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PublicationCosmosUpdateComponent,
    resolve: {
      publication: PublicationCosmosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PublicationCosmosUpdateComponent,
    resolve: {
      publication: PublicationCosmosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(publicationRoute)],
  exports: [RouterModule],
})
export class PublicationCosmosRoutingModule {}

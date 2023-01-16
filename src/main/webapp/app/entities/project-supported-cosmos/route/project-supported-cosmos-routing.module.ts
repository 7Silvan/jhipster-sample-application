import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProjectSupportedCosmosComponent } from '../list/project-supported-cosmos.component';
import { ProjectSupportedCosmosDetailComponent } from '../detail/project-supported-cosmos-detail.component';
import { ProjectSupportedCosmosUpdateComponent } from '../update/project-supported-cosmos-update.component';
import { ProjectSupportedCosmosRoutingResolveService } from './project-supported-cosmos-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const projectSupportedRoute: Routes = [
  {
    path: '',
    component: ProjectSupportedCosmosComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProjectSupportedCosmosDetailComponent,
    resolve: {
      projectSupported: ProjectSupportedCosmosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProjectSupportedCosmosUpdateComponent,
    resolve: {
      projectSupported: ProjectSupportedCosmosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProjectSupportedCosmosUpdateComponent,
    resolve: {
      projectSupported: ProjectSupportedCosmosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(projectSupportedRoute)],
  exports: [RouterModule],
})
export class ProjectSupportedCosmosRoutingModule {}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CommentCosmosComponent } from '../list/comment-cosmos.component';
import { CommentCosmosDetailComponent } from '../detail/comment-cosmos-detail.component';
import { CommentCosmosUpdateComponent } from '../update/comment-cosmos-update.component';
import { CommentCosmosRoutingResolveService } from './comment-cosmos-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const commentRoute: Routes = [
  {
    path: '',
    component: CommentCosmosComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CommentCosmosDetailComponent,
    resolve: {
      comment: CommentCosmosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CommentCosmosUpdateComponent,
    resolve: {
      comment: CommentCosmosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CommentCosmosUpdateComponent,
    resolve: {
      comment: CommentCosmosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(commentRoute)],
  exports: [RouterModule],
})
export class CommentCosmosRoutingModule {}

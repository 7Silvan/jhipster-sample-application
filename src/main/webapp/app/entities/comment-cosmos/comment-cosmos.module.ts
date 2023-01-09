import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CommentCosmosComponent } from './list/comment-cosmos.component';
import { CommentCosmosDetailComponent } from './detail/comment-cosmos-detail.component';
import { CommentCosmosUpdateComponent } from './update/comment-cosmos-update.component';
import { CommentCosmosDeleteDialogComponent } from './delete/comment-cosmos-delete-dialog.component';
import { CommentCosmosRoutingModule } from './route/comment-cosmos-routing.module';

@NgModule({
  imports: [SharedModule, CommentCosmosRoutingModule],
  declarations: [CommentCosmosComponent, CommentCosmosDetailComponent, CommentCosmosUpdateComponent, CommentCosmosDeleteDialogComponent],
})
export class CommentCosmosModule {}

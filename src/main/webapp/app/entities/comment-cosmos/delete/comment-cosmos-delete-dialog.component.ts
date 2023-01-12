import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommentCosmos } from '../comment-cosmos.model';
import { CommentCosmosService } from '../service/comment-cosmos.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './comment-cosmos-delete-dialog.component.html',
})
export class CommentCosmosDeleteDialogComponent {
  comment?: ICommentCosmos;

  constructor(protected commentService: CommentCosmosService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commentService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

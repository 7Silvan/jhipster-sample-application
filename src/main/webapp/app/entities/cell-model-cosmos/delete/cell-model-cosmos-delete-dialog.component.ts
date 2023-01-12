import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICellModelCosmos } from '../cell-model-cosmos.model';
import { CellModelCosmosService } from '../service/cell-model-cosmos.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './cell-model-cosmos-delete-dialog.component.html',
})
export class CellModelCosmosDeleteDialogComponent {
  cellModel?: ICellModelCosmos;

  constructor(protected cellModelService: CellModelCosmosService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.cellModelService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

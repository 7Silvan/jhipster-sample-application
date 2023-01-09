import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICellTypeCosmos } from '../cell-type-cosmos.model';
import { CellTypeCosmosService } from '../service/cell-type-cosmos.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './cell-type-cosmos-delete-dialog.component.html',
})
export class CellTypeCosmosDeleteDialogComponent {
  cellType?: ICellTypeCosmos;

  constructor(protected cellTypeService: CellTypeCosmosService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cellTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

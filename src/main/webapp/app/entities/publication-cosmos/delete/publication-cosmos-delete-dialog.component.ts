import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPublicationCosmos } from '../publication-cosmos.model';
import { PublicationCosmosService } from '../service/publication-cosmos.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './publication-cosmos-delete-dialog.component.html',
})
export class PublicationCosmosDeleteDialogComponent {
  publication?: IPublicationCosmos;

  constructor(protected publicationService: PublicationCosmosService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.publicationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICharacterizationDataCosmos } from '../characterization-data-cosmos.model';
import { CharacterizationDataCosmosService } from '../service/characterization-data-cosmos.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './characterization-data-cosmos-delete-dialog.component.html',
})
export class CharacterizationDataCosmosDeleteDialogComponent {
  characterizationData?: ICharacterizationDataCosmos;

  constructor(protected characterizationDataService: CharacterizationDataCosmosService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.characterizationDataService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

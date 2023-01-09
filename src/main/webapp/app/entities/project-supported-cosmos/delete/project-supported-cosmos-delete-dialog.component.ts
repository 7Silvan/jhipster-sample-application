import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProjectSupportedCosmos } from '../project-supported-cosmos.model';
import { ProjectSupportedCosmosService } from '../service/project-supported-cosmos.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './project-supported-cosmos-delete-dialog.component.html',
})
export class ProjectSupportedCosmosDeleteDialogComponent {
  projectSupported?: IProjectSupportedCosmos;

  constructor(protected projectSupportedService: ProjectSupportedCosmosService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.projectSupportedService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

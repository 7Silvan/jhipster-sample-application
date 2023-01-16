import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectSupportedCosmos } from '../project-supported-cosmos.model';

@Component({
  selector: 'jhi-project-supported-cosmos-detail',
  templateUrl: './project-supported-cosmos-detail.component.html',
})
export class ProjectSupportedCosmosDetailComponent implements OnInit {
  projectSupported: IProjectSupportedCosmos | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectSupported }) => {
      this.projectSupported = projectSupported;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

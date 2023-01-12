import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPublicationCosmos } from '../publication-cosmos.model';

@Component({
  selector: 'jhi-publication-cosmos-detail',
  templateUrl: './publication-cosmos-detail.component.html',
})
export class PublicationCosmosDetailComponent implements OnInit {
  publication: IPublicationCosmos | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ publication }) => {
      this.publication = publication;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

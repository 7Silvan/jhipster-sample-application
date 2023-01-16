import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICharacterizationDataCosmos } from '../characterization-data-cosmos.model';

@Component({
  selector: 'jhi-characterization-data-cosmos-detail',
  templateUrl: './characterization-data-cosmos-detail.component.html',
})
export class CharacterizationDataCosmosDetailComponent implements OnInit {
  characterizationData: ICharacterizationDataCosmos | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ characterizationData }) => {
      this.characterizationData = characterizationData;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

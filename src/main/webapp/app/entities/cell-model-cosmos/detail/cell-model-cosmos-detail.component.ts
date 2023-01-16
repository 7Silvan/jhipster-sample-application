import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICellModelCosmos } from '../cell-model-cosmos.model';

@Component({
  selector: 'jhi-cell-model-cosmos-detail',
  templateUrl: './cell-model-cosmos-detail.component.html',
})
export class CellModelCosmosDetailComponent implements OnInit {
  cellModel: ICellModelCosmos | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cellModel }) => {
      this.cellModel = cellModel;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

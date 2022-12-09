import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICellTypeCosmos } from '../cell-type-cosmos.model';

@Component({
  selector: 'jhi-cell-type-cosmos-detail',
  templateUrl: './cell-type-cosmos-detail.component.html',
})
export class CellTypeCosmosDetailComponent implements OnInit {
  cellType: ICellTypeCosmos | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cellType }) => {
      this.cellType = cellType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

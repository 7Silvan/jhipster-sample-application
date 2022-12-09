import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICellModelCosmos } from '../cell-model-cosmos.model';
import { CellModelCosmosService } from '../service/cell-model-cosmos.service';

@Injectable({ providedIn: 'root' })
export class CellModelCosmosRoutingResolveService implements Resolve<ICellModelCosmos | null> {
  constructor(protected service: CellModelCosmosService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICellModelCosmos | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((cellModel: HttpResponse<ICellModelCosmos>) => {
          if (cellModel.body) {
            return of(cellModel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}

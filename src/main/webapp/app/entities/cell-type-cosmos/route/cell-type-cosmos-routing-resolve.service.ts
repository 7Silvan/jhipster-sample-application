import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICellTypeCosmos } from '../cell-type-cosmos.model';
import { CellTypeCosmosService } from '../service/cell-type-cosmos.service';

@Injectable({ providedIn: 'root' })
export class CellTypeCosmosRoutingResolveService implements Resolve<ICellTypeCosmos | null> {
  constructor(protected service: CellTypeCosmosService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICellTypeCosmos | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((cellType: HttpResponse<ICellTypeCosmos>) => {
          if (cellType.body) {
            return of(cellType.body);
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

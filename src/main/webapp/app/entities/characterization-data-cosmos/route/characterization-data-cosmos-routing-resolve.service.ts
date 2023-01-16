import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICharacterizationDataCosmos } from '../characterization-data-cosmos.model';
import { CharacterizationDataCosmosService } from '../service/characterization-data-cosmos.service';

@Injectable({ providedIn: 'root' })
export class CharacterizationDataCosmosRoutingResolveService implements Resolve<ICharacterizationDataCosmos | null> {
  constructor(protected service: CharacterizationDataCosmosService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICharacterizationDataCosmos | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((characterizationData: HttpResponse<ICharacterizationDataCosmos>) => {
          if (characterizationData.body) {
            return of(characterizationData.body);
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

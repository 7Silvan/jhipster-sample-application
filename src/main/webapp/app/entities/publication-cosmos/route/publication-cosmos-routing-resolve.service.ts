import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPublicationCosmos } from '../publication-cosmos.model';
import { PublicationCosmosService } from '../service/publication-cosmos.service';

@Injectable({ providedIn: 'root' })
export class PublicationCosmosRoutingResolveService implements Resolve<IPublicationCosmos | null> {
  constructor(protected service: PublicationCosmosService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPublicationCosmos | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((publication: HttpResponse<IPublicationCosmos>) => {
          if (publication.body) {
            return of(publication.body);
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

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProjectSupportedCosmos } from '../project-supported-cosmos.model';
import { ProjectSupportedCosmosService } from '../service/project-supported-cosmos.service';

@Injectable({ providedIn: 'root' })
export class ProjectSupportedCosmosRoutingResolveService implements Resolve<IProjectSupportedCosmos | null> {
  constructor(protected service: ProjectSupportedCosmosService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProjectSupportedCosmos | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((projectSupported: HttpResponse<IProjectSupportedCosmos>) => {
          if (projectSupported.body) {
            return of(projectSupported.body);
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

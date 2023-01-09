import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICommentCosmos } from '../comment-cosmos.model';
import { CommentCosmosService } from '../service/comment-cosmos.service';

@Injectable({ providedIn: 'root' })
export class CommentCosmosRoutingResolveService implements Resolve<ICommentCosmos | null> {
  constructor(protected service: CommentCosmosService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommentCosmos | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((comment: HttpResponse<ICommentCosmos>) => {
          if (comment.body) {
            return of(comment.body);
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

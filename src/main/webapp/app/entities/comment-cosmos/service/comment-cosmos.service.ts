import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICommentCosmos, NewCommentCosmos } from '../comment-cosmos.model';

export type PartialUpdateCommentCosmos = Partial<ICommentCosmos> & Pick<ICommentCosmos, 'id'>;

type RestOf<T extends ICommentCosmos | NewCommentCosmos> = Omit<T, 'timeCreated'> & {
  timeCreated?: string | null;
};

export type RestCommentCosmos = RestOf<ICommentCosmos>;

export type NewRestCommentCosmos = RestOf<NewCommentCosmos>;

export type PartialUpdateRestCommentCosmos = RestOf<PartialUpdateCommentCosmos>;

export type EntityResponseType = HttpResponse<ICommentCosmos>;
export type EntityArrayResponseType = HttpResponse<ICommentCosmos[]>;

@Injectable({ providedIn: 'root' })
export class CommentCosmosService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/comments');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(comment: NewCommentCosmos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comment);
    return this.http
      .post<RestCommentCosmos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(comment: ICommentCosmos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comment);
    return this.http
      .put<RestCommentCosmos>(`${this.resourceUrl}/${this.getCommentCosmosIdentifier(comment)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(comment: PartialUpdateCommentCosmos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comment);
    return this.http
      .patch<RestCommentCosmos>(`${this.resourceUrl}/${this.getCommentCosmosIdentifier(comment)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCommentCosmos>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCommentCosmos[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCommentCosmosIdentifier(comment: Pick<ICommentCosmos, 'id'>): number {
    return comment.id;
  }

  compareCommentCosmos(o1: Pick<ICommentCosmos, 'id'> | null, o2: Pick<ICommentCosmos, 'id'> | null): boolean {
    return o1 && o2 ? this.getCommentCosmosIdentifier(o1) === this.getCommentCosmosIdentifier(o2) : o1 === o2;
  }

  addCommentCosmosToCollectionIfMissing<Type extends Pick<ICommentCosmos, 'id'>>(
    commentCollection: Type[],
    ...commentsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const comments: Type[] = commentsToCheck.filter(isPresent);
    if (comments.length > 0) {
      const commentCollectionIdentifiers = commentCollection.map(commentItem => this.getCommentCosmosIdentifier(commentItem)!);
      const commentsToAdd = comments.filter(commentItem => {
        const commentIdentifier = this.getCommentCosmosIdentifier(commentItem);
        if (commentCollectionIdentifiers.includes(commentIdentifier)) {
          return false;
        }
        commentCollectionIdentifiers.push(commentIdentifier);
        return true;
      });
      return [...commentsToAdd, ...commentCollection];
    }
    return commentCollection;
  }

  protected convertDateFromClient<T extends ICommentCosmos | NewCommentCosmos | PartialUpdateCommentCosmos>(comment: T): RestOf<T> {
    return {
      ...comment,
      timeCreated: comment.timeCreated?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCommentCosmos: RestCommentCosmos): ICommentCosmos {
    return {
      ...restCommentCosmos,
      timeCreated: restCommentCosmos.timeCreated ? dayjs(restCommentCosmos.timeCreated) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCommentCosmos>): HttpResponse<ICommentCosmos> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCommentCosmos[]>): HttpResponse<ICommentCosmos[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

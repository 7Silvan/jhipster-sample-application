import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPublicationCosmos, NewPublicationCosmos } from '../publication-cosmos.model';

export type PartialUpdatePublicationCosmos = Partial<IPublicationCosmos> & Pick<IPublicationCosmos, 'id'>;

export type EntityResponseType = HttpResponse<IPublicationCosmos>;
export type EntityArrayResponseType = HttpResponse<IPublicationCosmos[]>;

@Injectable({ providedIn: 'root' })
export class PublicationCosmosService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/publications');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(publication: NewPublicationCosmos): Observable<EntityResponseType> {
    return this.http.post<IPublicationCosmos>(this.resourceUrl, publication, { observe: 'response' });
  }

  update(publication: IPublicationCosmos): Observable<EntityResponseType> {
    return this.http.put<IPublicationCosmos>(`${this.resourceUrl}/${this.getPublicationCosmosIdentifier(publication)}`, publication, {
      observe: 'response',
    });
  }

  partialUpdate(publication: PartialUpdatePublicationCosmos): Observable<EntityResponseType> {
    return this.http.patch<IPublicationCosmos>(`${this.resourceUrl}/${this.getPublicationCosmosIdentifier(publication)}`, publication, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPublicationCosmos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPublicationCosmos[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPublicationCosmosIdentifier(publication: Pick<IPublicationCosmos, 'id'>): number {
    return publication.id;
  }

  comparePublicationCosmos(o1: Pick<IPublicationCosmos, 'id'> | null, o2: Pick<IPublicationCosmos, 'id'> | null): boolean {
    return o1 && o2 ? this.getPublicationCosmosIdentifier(o1) === this.getPublicationCosmosIdentifier(o2) : o1 === o2;
  }

  addPublicationCosmosToCollectionIfMissing<Type extends Pick<IPublicationCosmos, 'id'>>(
    publicationCollection: Type[],
    ...publicationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const publications: Type[] = publicationsToCheck.filter(isPresent);
    if (publications.length > 0) {
      const publicationCollectionIdentifiers = publicationCollection.map(
        publicationItem => this.getPublicationCosmosIdentifier(publicationItem)!
      );
      const publicationsToAdd = publications.filter(publicationItem => {
        const publicationIdentifier = this.getPublicationCosmosIdentifier(publicationItem);
        if (publicationCollectionIdentifiers.includes(publicationIdentifier)) {
          return false;
        }
        publicationCollectionIdentifiers.push(publicationIdentifier);
        return true;
      });
      return [...publicationsToAdd, ...publicationCollection];
    }
    return publicationCollection;
  }
}

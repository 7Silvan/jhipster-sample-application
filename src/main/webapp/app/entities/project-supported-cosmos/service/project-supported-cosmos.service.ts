import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProjectSupportedCosmos, NewProjectSupportedCosmos } from '../project-supported-cosmos.model';

export type PartialUpdateProjectSupportedCosmos = Partial<IProjectSupportedCosmos> & Pick<IProjectSupportedCosmos, 'id'>;

export type EntityResponseType = HttpResponse<IProjectSupportedCosmos>;
export type EntityArrayResponseType = HttpResponse<IProjectSupportedCosmos[]>;

@Injectable({ providedIn: 'root' })
export class ProjectSupportedCosmosService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/project-supporteds');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(projectSupported: NewProjectSupportedCosmos): Observable<EntityResponseType> {
    return this.http.post<IProjectSupportedCosmos>(this.resourceUrl, projectSupported, { observe: 'response' });
  }

  update(projectSupported: IProjectSupportedCosmos): Observable<EntityResponseType> {
    return this.http.put<IProjectSupportedCosmos>(
      `${this.resourceUrl}/${this.getProjectSupportedCosmosIdentifier(projectSupported)}`,
      projectSupported,
      { observe: 'response' }
    );
  }

  partialUpdate(projectSupported: PartialUpdateProjectSupportedCosmos): Observable<EntityResponseType> {
    return this.http.patch<IProjectSupportedCosmos>(
      `${this.resourceUrl}/${this.getProjectSupportedCosmosIdentifier(projectSupported)}`,
      projectSupported,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectSupportedCosmos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectSupportedCosmos[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProjectSupportedCosmosIdentifier(projectSupported: Pick<IProjectSupportedCosmos, 'id'>): number {
    return projectSupported.id;
  }

  compareProjectSupportedCosmos(o1: Pick<IProjectSupportedCosmos, 'id'> | null, o2: Pick<IProjectSupportedCosmos, 'id'> | null): boolean {
    return o1 && o2 ? this.getProjectSupportedCosmosIdentifier(o1) === this.getProjectSupportedCosmosIdentifier(o2) : o1 === o2;
  }

  addProjectSupportedCosmosToCollectionIfMissing<Type extends Pick<IProjectSupportedCosmos, 'id'>>(
    projectSupportedCollection: Type[],
    ...projectSupportedsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const projectSupporteds: Type[] = projectSupportedsToCheck.filter(isPresent);
    if (projectSupporteds.length > 0) {
      const projectSupportedCollectionIdentifiers = projectSupportedCollection.map(
        projectSupportedItem => this.getProjectSupportedCosmosIdentifier(projectSupportedItem)!
      );
      const projectSupportedsToAdd = projectSupporteds.filter(projectSupportedItem => {
        const projectSupportedIdentifier = this.getProjectSupportedCosmosIdentifier(projectSupportedItem);
        if (projectSupportedCollectionIdentifiers.includes(projectSupportedIdentifier)) {
          return false;
        }
        projectSupportedCollectionIdentifiers.push(projectSupportedIdentifier);
        return true;
      });
      return [...projectSupportedsToAdd, ...projectSupportedCollection];
    }
    return projectSupportedCollection;
  }
}

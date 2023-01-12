import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICharacterizationDataCosmos, NewCharacterizationDataCosmos } from '../characterization-data-cosmos.model';

export type PartialUpdateCharacterizationDataCosmos = Partial<ICharacterizationDataCosmos> & Pick<ICharacterizationDataCosmos, 'id'>;

export type EntityResponseType = HttpResponse<ICharacterizationDataCosmos>;
export type EntityArrayResponseType = HttpResponse<ICharacterizationDataCosmos[]>;

@Injectable({ providedIn: 'root' })
export class CharacterizationDataCosmosService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/characterization-data');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(characterizationData: NewCharacterizationDataCosmos): Observable<EntityResponseType> {
    return this.http.post<ICharacterizationDataCosmos>(this.resourceUrl, characterizationData, { observe: 'response' });
  }

  update(characterizationData: ICharacterizationDataCosmos): Observable<EntityResponseType> {
    return this.http.put<ICharacterizationDataCosmos>(
      `${this.resourceUrl}/${this.getCharacterizationDataCosmosIdentifier(characterizationData)}`,
      characterizationData,
      { observe: 'response' }
    );
  }

  partialUpdate(characterizationData: PartialUpdateCharacterizationDataCosmos): Observable<EntityResponseType> {
    return this.http.patch<ICharacterizationDataCosmos>(
      `${this.resourceUrl}/${this.getCharacterizationDataCosmosIdentifier(characterizationData)}`,
      characterizationData,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICharacterizationDataCosmos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICharacterizationDataCosmos[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCharacterizationDataCosmosIdentifier(characterizationData: Pick<ICharacterizationDataCosmos, 'id'>): number {
    return characterizationData.id;
  }

  compareCharacterizationDataCosmos(
    o1: Pick<ICharacterizationDataCosmos, 'id'> | null,
    o2: Pick<ICharacterizationDataCosmos, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getCharacterizationDataCosmosIdentifier(o1) === this.getCharacterizationDataCosmosIdentifier(o2) : o1 === o2;
  }

  addCharacterizationDataCosmosToCollectionIfMissing<Type extends Pick<ICharacterizationDataCosmos, 'id'>>(
    characterizationDataCollection: Type[],
    ...characterizationDataToCheck: (Type | null | undefined)[]
  ): Type[] {
    const characterizationData: Type[] = characterizationDataToCheck.filter(isPresent);
    if (characterizationData.length > 0) {
      const characterizationDataCollectionIdentifiers = characterizationDataCollection.map(
        characterizationDataItem => this.getCharacterizationDataCosmosIdentifier(characterizationDataItem)!
      );
      const characterizationDataToAdd = characterizationData.filter(characterizationDataItem => {
        const characterizationDataIdentifier = this.getCharacterizationDataCosmosIdentifier(characterizationDataItem);
        if (characterizationDataCollectionIdentifiers.includes(characterizationDataIdentifier)) {
          return false;
        }
        characterizationDataCollectionIdentifiers.push(characterizationDataIdentifier);
        return true;
      });
      return [...characterizationDataToAdd, ...characterizationDataCollection];
    }
    return characterizationDataCollection;
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICellTypeCosmos, NewCellTypeCosmos } from '../cell-type-cosmos.model';

export type PartialUpdateCellTypeCosmos = Partial<ICellTypeCosmos> & Pick<ICellTypeCosmos, 'id'>;

export type EntityResponseType = HttpResponse<ICellTypeCosmos>;
export type EntityArrayResponseType = HttpResponse<ICellTypeCosmos[]>;

@Injectable({ providedIn: 'root' })
export class CellTypeCosmosService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cell-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cellType: NewCellTypeCosmos): Observable<EntityResponseType> {
    return this.http.post<ICellTypeCosmos>(this.resourceUrl, cellType, { observe: 'response' });
  }

  update(cellType: ICellTypeCosmos): Observable<EntityResponseType> {
    return this.http.put<ICellTypeCosmos>(`${this.resourceUrl}/${this.getCellTypeCosmosIdentifier(cellType)}`, cellType, {
      observe: 'response',
    });
  }

  partialUpdate(cellType: PartialUpdateCellTypeCosmos): Observable<EntityResponseType> {
    return this.http.patch<ICellTypeCosmos>(`${this.resourceUrl}/${this.getCellTypeCosmosIdentifier(cellType)}`, cellType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICellTypeCosmos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICellTypeCosmos[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCellTypeCosmosIdentifier(cellType: Pick<ICellTypeCosmos, 'id'>): number {
    return cellType.id;
  }

  compareCellTypeCosmos(o1: Pick<ICellTypeCosmos, 'id'> | null, o2: Pick<ICellTypeCosmos, 'id'> | null): boolean {
    return o1 && o2 ? this.getCellTypeCosmosIdentifier(o1) === this.getCellTypeCosmosIdentifier(o2) : o1 === o2;
  }

  addCellTypeCosmosToCollectionIfMissing<Type extends Pick<ICellTypeCosmos, 'id'>>(
    cellTypeCollection: Type[],
    ...cellTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cellTypes: Type[] = cellTypesToCheck.filter(isPresent);
    if (cellTypes.length > 0) {
      const cellTypeCollectionIdentifiers = cellTypeCollection.map(cellTypeItem => this.getCellTypeCosmosIdentifier(cellTypeItem)!);
      const cellTypesToAdd = cellTypes.filter(cellTypeItem => {
        const cellTypeIdentifier = this.getCellTypeCosmosIdentifier(cellTypeItem);
        if (cellTypeCollectionIdentifiers.includes(cellTypeIdentifier)) {
          return false;
        }
        cellTypeCollectionIdentifiers.push(cellTypeIdentifier);
        return true;
      });
      return [...cellTypesToAdd, ...cellTypeCollection];
    }
    return cellTypeCollection;
  }
}

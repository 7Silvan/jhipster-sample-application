import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICellModelCosmos, NewCellModelCosmos } from '../cell-model-cosmos.model';

export type PartialUpdateCellModelCosmos = Partial<ICellModelCosmos> & Pick<ICellModelCosmos, 'id'>;

type RestOf<T extends ICellModelCosmos | NewCellModelCosmos> = Omit<T, 'dateAdded' | 'dateUpdated'> & {
  dateAdded?: string | null;
  dateUpdated?: string | null;
};

export type RestCellModelCosmos = RestOf<ICellModelCosmos>;

export type NewRestCellModelCosmos = RestOf<NewCellModelCosmos>;

export type PartialUpdateRestCellModelCosmos = RestOf<PartialUpdateCellModelCosmos>;

export type EntityResponseType = HttpResponse<ICellModelCosmos>;
export type EntityArrayResponseType = HttpResponse<ICellModelCosmos[]>;

@Injectable({ providedIn: 'root' })
export class CellModelCosmosService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cell-models');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cellModel: NewCellModelCosmos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cellModel);
    return this.http
      .post<RestCellModelCosmos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(cellModel: ICellModelCosmos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cellModel);
    return this.http
      .put<RestCellModelCosmos>(`${this.resourceUrl}/${this.getCellModelCosmosIdentifier(cellModel)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(cellModel: PartialUpdateCellModelCosmos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cellModel);
    return this.http
      .patch<RestCellModelCosmos>(`${this.resourceUrl}/${this.getCellModelCosmosIdentifier(cellModel)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestCellModelCosmos>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCellModelCosmos[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCellModelCosmosIdentifier(cellModel: Pick<ICellModelCosmos, 'id'>): string {
    return cellModel.id;
  }

  compareCellModelCosmos(o1: Pick<ICellModelCosmos, 'id'> | null, o2: Pick<ICellModelCosmos, 'id'> | null): boolean {
    return o1 && o2 ? this.getCellModelCosmosIdentifier(o1) === this.getCellModelCosmosIdentifier(o2) : o1 === o2;
  }

  addCellModelCosmosToCollectionIfMissing<Type extends Pick<ICellModelCosmos, 'id'>>(
    cellModelCollection: Type[],
    ...cellModelsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cellModels: Type[] = cellModelsToCheck.filter(isPresent);
    if (cellModels.length > 0) {
      const cellModelCollectionIdentifiers = cellModelCollection.map(cellModelItem => this.getCellModelCosmosIdentifier(cellModelItem)!);
      const cellModelsToAdd = cellModels.filter(cellModelItem => {
        const cellModelIdentifier = this.getCellModelCosmosIdentifier(cellModelItem);
        if (cellModelCollectionIdentifiers.includes(cellModelIdentifier)) {
          return false;
        }
        cellModelCollectionIdentifiers.push(cellModelIdentifier);
        return true;
      });
      return [...cellModelsToAdd, ...cellModelCollection];
    }
    return cellModelCollection;
  }

  protected convertDateFromClient<T extends ICellModelCosmos | NewCellModelCosmos | PartialUpdateCellModelCosmos>(cellModel: T): RestOf<T> {
    return {
      ...cellModel,
      dateAdded: cellModel.dateAdded?.toJSON() ?? null,
      dateUpdated: cellModel.dateUpdated?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCellModelCosmos: RestCellModelCosmos): ICellModelCosmos {
    return {
      ...restCellModelCosmos,
      dateAdded: restCellModelCosmos.dateAdded ? dayjs(restCellModelCosmos.dateAdded) : undefined,
      dateUpdated: restCellModelCosmos.dateUpdated ? dayjs(restCellModelCosmos.dateUpdated) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCellModelCosmos>): HttpResponse<ICellModelCosmos> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCellModelCosmos[]>): HttpResponse<ICellModelCosmos[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

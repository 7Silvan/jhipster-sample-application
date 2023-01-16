import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICellModelCosmos } from '../cell-model-cosmos.model';
import { CellModelCosmosService } from '../service/cell-model-cosmos.service';

import { CellModelCosmosRoutingResolveService } from './cell-model-cosmos-routing-resolve.service';

describe('CellModelCosmos routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CellModelCosmosRoutingResolveService;
  let service: CellModelCosmosService;
  let resultCellModelCosmos: ICellModelCosmos | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(CellModelCosmosRoutingResolveService);
    service = TestBed.inject(CellModelCosmosService);
    resultCellModelCosmos = undefined;
  });

  describe('resolve', () => {
    it('should return ICellModelCosmos returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCellModelCosmos = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultCellModelCosmos).toEqual({ id: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCellModelCosmos = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCellModelCosmos).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICellModelCosmos>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCellModelCosmos = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultCellModelCosmos).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

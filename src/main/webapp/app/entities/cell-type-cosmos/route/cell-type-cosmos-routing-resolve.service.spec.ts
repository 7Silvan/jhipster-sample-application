import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICellTypeCosmos } from '../cell-type-cosmos.model';
import { CellTypeCosmosService } from '../service/cell-type-cosmos.service';

import { CellTypeCosmosRoutingResolveService } from './cell-type-cosmos-routing-resolve.service';

describe('CellTypeCosmos routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CellTypeCosmosRoutingResolveService;
  let service: CellTypeCosmosService;
  let resultCellTypeCosmos: ICellTypeCosmos | null | undefined;

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
    routingResolveService = TestBed.inject(CellTypeCosmosRoutingResolveService);
    service = TestBed.inject(CellTypeCosmosService);
    resultCellTypeCosmos = undefined;
  });

  describe('resolve', () => {
    it('should return ICellTypeCosmos returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCellTypeCosmos = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCellTypeCosmos).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCellTypeCosmos = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCellTypeCosmos).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICellTypeCosmos>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCellTypeCosmos = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCellTypeCosmos).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

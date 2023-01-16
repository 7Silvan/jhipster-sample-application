import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICharacterizationDataCosmos } from '../characterization-data-cosmos.model';
import { CharacterizationDataCosmosService } from '../service/characterization-data-cosmos.service';

import { CharacterizationDataCosmosRoutingResolveService } from './characterization-data-cosmos-routing-resolve.service';

describe('CharacterizationDataCosmos routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CharacterizationDataCosmosRoutingResolveService;
  let service: CharacterizationDataCosmosService;
  let resultCharacterizationDataCosmos: ICharacterizationDataCosmos | null | undefined;

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
    routingResolveService = TestBed.inject(CharacterizationDataCosmosRoutingResolveService);
    service = TestBed.inject(CharacterizationDataCosmosService);
    resultCharacterizationDataCosmos = undefined;
  });

  describe('resolve', () => {
    it('should return ICharacterizationDataCosmos returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCharacterizationDataCosmos = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCharacterizationDataCosmos).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCharacterizationDataCosmos = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCharacterizationDataCosmos).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICharacterizationDataCosmos>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCharacterizationDataCosmos = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCharacterizationDataCosmos).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

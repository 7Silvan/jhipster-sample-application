import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CellTypeCosmosDetailComponent } from './cell-type-cosmos-detail.component';

describe('CellTypeCosmos Management Detail Component', () => {
  let comp: CellTypeCosmosDetailComponent;
  let fixture: ComponentFixture<CellTypeCosmosDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CellTypeCosmosDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ cellType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CellTypeCosmosDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CellTypeCosmosDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cellType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.cellType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

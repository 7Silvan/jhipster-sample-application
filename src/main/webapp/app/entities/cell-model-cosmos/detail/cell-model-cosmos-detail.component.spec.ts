import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CellModelCosmosDetailComponent } from './cell-model-cosmos-detail.component';

describe('CellModelCosmos Management Detail Component', () => {
  let comp: CellModelCosmosDetailComponent;
  let fixture: ComponentFixture<CellModelCosmosDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CellModelCosmosDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ cellModel: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(CellModelCosmosDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CellModelCosmosDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cellModel on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.cellModel).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});

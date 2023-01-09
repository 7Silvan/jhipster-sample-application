import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CharacterizationDataCosmosDetailComponent } from './characterization-data-cosmos-detail.component';

describe('CharacterizationDataCosmos Management Detail Component', () => {
  let comp: CharacterizationDataCosmosDetailComponent;
  let fixture: ComponentFixture<CharacterizationDataCosmosDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CharacterizationDataCosmosDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ characterizationData: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CharacterizationDataCosmosDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CharacterizationDataCosmosDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load characterizationData on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.characterizationData).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

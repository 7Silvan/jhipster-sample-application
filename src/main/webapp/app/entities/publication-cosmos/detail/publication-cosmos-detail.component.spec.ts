import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PublicationCosmosDetailComponent } from './publication-cosmos-detail.component';

describe('PublicationCosmos Management Detail Component', () => {
  let comp: PublicationCosmosDetailComponent;
  let fixture: ComponentFixture<PublicationCosmosDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PublicationCosmosDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ publication: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PublicationCosmosDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PublicationCosmosDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load publication on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.publication).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
